package de.og.batterycreator.gui.widgets.overview;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.main.IconCreatorFrame;

public class StaticIconGridCreator {

	private static Graphics2D initGraphics(final BufferedImage over) {
		final Graphics2D g2d = over.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		return g2d;
	}

	public static BufferedImage createBigGrid(final List<ImageIcon> iconMap) {
		return createBigGrid(iconMap, iconMap.size());
	}

	public static BufferedImage createBigGrid(final List<ImageIcon> iconMap, final int maxanz) {
		if (iconMap != null && iconMap.size() > 0) {
			final int anzahl = Math.min(maxanz, iconMap.size());

			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();
			final int w = iw * 10 + 11;
			final int volleZehner = anzahl / 10 + 1;
			final int h = ih * volleZehner + (volleZehner + 1);

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = initGraphics(over);

			// Lopp über alle Bilder
			for (int i = 0; i < anzahl; i++) {
				final int z = i / 10;
				final int e = i % 10;
				final int index = z * 10 + e;
				final ImageIcon img = iconMap.get(index);
				g2d.drawImage(img.getImage(), e * (iw + 1), z * (ih + 1), null);
			}
			return over;
		}
		return null;
	}

	public static BufferedImage createOneLineGrid(final List<ImageIcon> iconMap, final boolean drawReflection) {
		return createOneLineGrid(iconMap, drawReflection, iconMap.size());
	}

	public static BufferedImage createOneLineGrid(final List<ImageIcon> iconMap, final boolean drawReflection, final int maxanz) {
		if (iconMap != null && iconMap.size() > 0) {
			final int anzahl = Math.min(maxanz, iconMap.size());

			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();

			final int ih = img1.getIconHeight();

			// offsets berechnen
			final int w = iw * anzahl + (anzahl - 1);
			int h = ih;
			if (drawReflection)
				h = 2 * ih + 1;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = initGraphics(over);

			// Lopp über alle Bilder
			for (int i = 0; i < anzahl; i++) {
				final ImageIcon img = iconMap.get(i);
				final int x = i * (iw + 1);
				g2d.drawImage(img.getImage(), x, 1, null);
				if (drawReflection) {
					final BufferedImage flipimg = StaticImageHelper.createReflectionImage(StaticImageHelper.convertImageIcon(img), true);
					g2d.drawImage(flipimg, x, 1 * (ih + 1), null);
				}
			}
			return over;
		}
		return null;
	}

	public static BufferedImage createTwoLineGrid(final List<ImageIcon> iconMap1, final List<ImageIcon> iconMap2) {
		if (iconMap1 != null && iconMap1.size() > 0 && iconMap2 != null && iconMap2.size() > 0) {
			final ImageIcon img1 = iconMap1.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();

			final int iconPerLine = Math.max(iconMap1.size(), iconMap2.size());

			final int w = iw * iconPerLine + iconPerLine - 1;
			final int h = ih * 2 + 1;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = initGraphics(over);

			// First Line
			for (int index = 0; index < iconMap1.size(); index++) {
				final ImageIcon img = iconMap1.get(index);
				g2d.drawImage(img.getImage(), index * (iw + 1), 0, null);
			}

			// 2. Line
			for (int index = 0; index < iconMap2.size(); index++) {
				final ImageIcon img = iconMap2.get(index);
				g2d.drawImage(img.getImage(), index * (iw + 1), 1 * (ih + 1), null);
			}

			return over;
		}
		return null;
	}

	// ##########################################################
	// Battery Specialties
	// ##########################################################

	public static BufferedImage createBatteryTwoLineGrid(final List<ImageIcon> iconMap) {
		if (iconMap != null && iconMap.size() > 100) {
			// Die richtigen Icons aus der Liste der BatterieIcons suchen
			final List<ImageIcon> iconMap1 = extractBatteryIconsForSmallOverviews(iconMap);
			final List<ImageIcon> iconMap2 = extractBatteryChargeIconsForSmallOverviews(iconMap);
			return createTwoLineGrid(iconMap1, iconMap2);
		}
		return null;
	}

	public static BufferedImage createBatteryOneLineGrid(final List<ImageIcon> iconMap, final boolean drawReflection) {
		if (iconMap != null && iconMap.size() > 100) {
			// Die richtigen Icons aus der Liste der BatterieIcons suchen
			final List<ImageIcon> iconMap1 = extractBatteryIconsForSmallOverviews(iconMap);
			iconMap1.addAll(extractBatteryChargeIconsForSmallOverviews(iconMap));
			return createOneLineGrid(iconMap1, drawReflection);
		}
		return null;
	}

	private static List<ImageIcon> extractBatteryIconsForSmallOverviews(final List<ImageIcon> iconMap) {
		final List<ImageIcon> iconMapOut = new ArrayList<ImageIcon>();
		// normal Icon
		for (int z = 0; z <= 5; z++) {
			int index = z * 20;
			if (IconCreatorFrame.globalSettings.isSmallOverviewsOtherNmbers())
				index = index + 5 - z;
			final ImageIcon img = iconMap.get(index);
			iconMapOut.add(img);
		}
		return iconMapOut;
	}

	private static List<ImageIcon> extractBatteryChargeIconsForSmallOverviews(final List<ImageIcon> iconMap) {
		final List<ImageIcon> iconMapOut = new ArrayList<ImageIcon>();
		// normal Icon
		for (int z = 0; z <= 5; z++) {
			int index = 101 + z * 20;
			if (IconCreatorFrame.globalSettings.isSmallOverviewsOtherNmbers())
				index = index + 5 - z;
			final ImageIcon img = iconMap.get(index);
			iconMapOut.add(img);
		}
		return iconMapOut;
	}

	public static BufferedImage createBatteryIconBlockWithCharge(final List<ImageIcon> iconMap) {
		if (iconMap != null && iconMap.size() > 100) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();
			final int w = iw * 20 + 21;
			final int h = ih * 11 + 10;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = initGraphics(over);

			for (int z = 0; z < 10; z++) {
				for (int e = 0; e < 10; e++) {
					final int index = z * 10 + e;
					final ImageIcon img = iconMap.get(index);
					g2d.drawImage(img.getImage(), e * (iw + 1), z * (ih + 1), null);
				}
			}
			final ImageIcon img = iconMap.get(100);
			g2d.drawImage(img.getImage(), 0 * iw, 10 * (ih + 1), null);

			// // Charge Icons
			for (int z = 0; z < 10; z++) {
				for (int e = 0; e < 10; e++) {
					final int index = 101 + z * 10 + e;
					final ImageIcon imgc = iconMap.get(index);
					g2d.drawImage(imgc.getImage(), 10 * (iw + 1) + e * (iw + 1), z * (ih + 1), null);
				}
			}
			final ImageIcon img100c = iconMap.get(201);
			g2d.drawImage(img100c.getImage(), 10 * (iw + 1) + 0 * iw, 10 * (ih + 1), null);

			return over;
		}
		return null;
	}

}

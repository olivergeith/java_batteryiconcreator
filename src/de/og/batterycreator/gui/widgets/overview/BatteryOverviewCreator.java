package de.og.batterycreator.gui.widgets.overview;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.main.IconCreatorFrame;

public class BatteryOverviewCreator {

	final static String	banner	= "Created with '" + IconCreatorFrame.APP_NAME + "' V" + IconCreatorFrame.VERSION_NR + " by OlliG";

	public BatteryOverviewCreator() {
	}

	public static BufferedImage createOverview(final List<ImageIcon> iconMap, final String name) {
		if (iconMap != null && iconMap.size() > 100) {

			final BufferedImage iconBlock = createBigIconBlockWithCharge(iconMap);
			final int iw = iconBlock.getWidth();
			final int ih = iconBlock.getHeight();
			final int w = iw;
			final int offsetOben = 50;
			final int offsetUnten = 35;
			final int h = ih + offsetOben + offsetUnten;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, w, h);
			g2d.setColor(Color.white);
			g2d.drawString(name, 2, 20);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
			g2d.setColor(Color.gray);
			g2d.drawString(banner, 2, 32);
			g2d.drawString(IconCreatorFrame.HOMEPAGE_URL, 2, h - offsetUnten + 20);
			g2d.setColor(Color.white);
			g2d.fillRect(0, 40, w, 2);
			g2d.fillRect(0, h - offsetUnten, w, 2);
			g2d.fillRect(0, h - 2, w, 2);

			g2d.drawImage(iconBlock, 1, offsetOben, null);

			return over;
		}
		return null;
	}

	public static BufferedImage createSmallBatteryOverview(final List<ImageIcon> iconMap, final String name) {
		if (iconMap != null && iconMap.size() > 100) {

			// Iconblock besorgen
			BufferedImage iconBlock;
			switch (IconCreatorFrame.globalSettings.getSmallOverViewStyle()) {
				default:
				case 0:
					iconBlock = createOneLineIconBlockWithReflection(iconMap);
					break;
				case 1:
					iconBlock = createOneLineIconBlock(iconMap);
					break;
				case 2:
					iconBlock = createTwoLineSmallIconBlock(iconMap);
					break;
				case 3:
					iconBlock = createBigIconBlock(iconMap);
					break;
				case 4:
					iconBlock = createBigIconBlockWithCharge(iconMap);
					break;

			}

			// Gr�ssen berechnen
			final int iw = iconBlock.getWidth();
			final int ih = iconBlock.getHeight();
			final int offsetX = 15;
			final int offsetOben = 40;
			final int offsetUnten = 30;

			int w = iw + 2 * offsetX;
			final int h = ih + offsetOben + offsetUnten;

			if (w < 350)
				w = 350;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			switch (IconCreatorFrame.globalSettings.getSmallBackgroundStyle()) {
				default:
				case 0:
					drawBackground01(w, h, g2d);
					break;
				case 1:
					drawBackground02(w, h, g2d, false);
					break;
				case 2:
					drawBackground02(w, h, g2d, true);
					break;
			}

			// Name Text
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
			FontMetrics metrix = g2d.getFontMetrics();
			Rectangle2D strRect = metrix.getStringBounds(name, g2d);
			int strxpos = (int) (Math.round(w / 2) - Math.round(strRect.getWidth() / 2));
			g2d.setColor(Color.white);
			g2d.drawString(name, strxpos, 25);
			// Banner Text
			g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
			metrix = g2d.getFontMetrics();
			strRect = metrix.getStringBounds(banner, g2d);
			strxpos = (int) (Math.round(w / 2) - Math.round(strRect.getWidth() / 2));
			g2d.setColor(Color.white);
			g2d.drawString(banner, strxpos, h - 17);

			g2d.drawImage(iconBlock, w / 2 - iw / 2, offsetOben, null);

			return over;
		}
		return null;
	}

	// #########################################################################
	// IconBlocks
	// #########################################################################
	private static BufferedImage createTwoLineSmallIconBlock(final List<ImageIcon> iconMap) {
		if (iconMap != null && iconMap.size() > 100) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();

			final int w = iw * 6 + 7;
			final int h = ih * 2 + 3;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// normal Icon
			for (int z = 0; z <= 5; z++) {
				int index = z * 20;
				if (IconCreatorFrame.globalSettings.isSmallOverviewsOtherNmbers())
					index = index + 5 - z;
				final ImageIcon img = iconMap.get(index);
				g2d.drawImage(img.getImage(), 1 + z * (iw + 1), 1, null);
			}

			// // Charge Icons
			for (int z = 0; z <= 5; z++) {
				int index = 101 + z * 20;
				if (IconCreatorFrame.globalSettings.isSmallOverviewsOtherNmbers())
					index = index + 5 - z;
				final ImageIcon imgc = iconMap.get(index);
				g2d.drawImage(imgc.getImage(), 1 + z * (iw + 1), 1 + 1 * (ih + 1), null);
			}

			return over;
		}
		return null;
	}

	private static BufferedImage createOneLineIconBlockWithReflection(final List<ImageIcon> iconMap) {
		if (iconMap != null && iconMap.size() > 100) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();

			// // Logo basteln
			// BufferedImage logo =
			// StaticImageHelper.convertImageIcon(IconStore.logoIcon);
			// logo = StaticImageHelper.resizeAdvanced2Height(logo,
			// Math.round(ih * 1.5f));
			// final BufferedImage logoflip =
			// StaticImageHelper.createReflectionImage(logo, true);

			// offsets berechnen
			final int w = iw * 12 + 13;
			final int h = ih * 2 + 3;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// g2d.drawImage(logo, offsetX + xStart, offsetOben + ih -
			// logo.getHeight(), null);
			// g2d.drawImage(logoflip, offsetX + xStart, 1 + 1 * (ih + 1) +
			// offsetOben, null);

			for (int z = 0; z <= 5; z++) {
				int index = z * 20;
				if (IconCreatorFrame.globalSettings.isSmallOverviewsOtherNmbers())
					index = index + 5 - z;
				final ImageIcon img = iconMap.get(index);
				final int x = 1 + z * (iw + 1);

				g2d.drawImage(img.getImage(), x, 1, null);
				final BufferedImage flipimg = StaticImageHelper.createReflectionImage(StaticImageHelper.convertImageIcon(img), true);
				g2d.drawImage(flipimg, x, 1 + 1 * (ih + 1), null);
			}
			for (int z = 0; z <= 5; z++) {
				int index = 101 + z * 20;
				if (IconCreatorFrame.globalSettings.isSmallOverviewsOtherNmbers())
					index = index + 5 - z;
				final int x = 1 + (z + 6) * (iw + 1);
				final ImageIcon img = iconMap.get(index);
				g2d.drawImage(img.getImage(), x, 1, null);
				final BufferedImage flipimg = StaticImageHelper.createReflectionImage(StaticImageHelper.convertImageIcon(img), true);
				g2d.drawImage(flipimg, x, 1 + 1 * (ih + 1), null);
			}

			return over;
		}
		return null;
	}

	private static BufferedImage createOneLineIconBlock(final List<ImageIcon> iconMap) {
		if (iconMap != null && iconMap.size() > 100) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();

			// offsets berechnen
			final int w = iw * 12 + 13;
			final int h = ih + 2;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			for (int z = 0; z <= 5; z++) {
				int index = z * 20;
				if (IconCreatorFrame.globalSettings.isSmallOverviewsOtherNmbers())
					index = index + 5 - z;
				final ImageIcon img = iconMap.get(index);
				final int x = 1 + z * (iw + 1);
				g2d.drawImage(img.getImage(), x, 1, null);
			}
			for (int z = 0; z <= 5; z++) {
				int index = 101 + z * 20;
				if (IconCreatorFrame.globalSettings.isSmallOverviewsOtherNmbers())
					index = index + 5 - z;
				final int x = 1 + (z + 6) * (iw + 1);
				final ImageIcon img = iconMap.get(index);
				g2d.drawImage(img.getImage(), x, 1, null);
			}

			return over;
		}
		return null;
	}

	public static BufferedImage createBigIconBlock(final List<ImageIcon> iconMap) {
		if (iconMap != null && iconMap.size() > 100) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();
			final int w = iw * 10 + 12;
			final int h = ih * 11 + 13;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			for (int z = 0; z < 10; z++) {
				for (int e = 0; e < 10; e++) {
					final int index = z * 10 + e;
					final ImageIcon img = iconMap.get(index);
					g2d.drawImage(img.getImage(), 1 + e * (iw + 1), 1 + z * (ih + 1), null);
				}
			}
			final ImageIcon img = iconMap.get(100);
			g2d.drawImage(img.getImage(), 1 + 0 * iw, 1 + 10 * (ih + 1), null);

			return over;
		}
		return null;
	}

	public static BufferedImage createBigIconBlockWithCharge(final List<ImageIcon> iconMap) {
		if (iconMap != null && iconMap.size() > 100) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();
			final int w = iw * 20 + 22;
			final int h = ih * 11 + 13;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			for (int z = 0; z < 10; z++) {
				for (int e = 0; e < 10; e++) {
					final int index = z * 10 + e;
					final ImageIcon img = iconMap.get(index);
					g2d.drawImage(img.getImage(), 1 + e * (iw + 1), 1 + z * (ih + 1), null);
				}
			}
			final ImageIcon img = iconMap.get(100);
			g2d.drawImage(img.getImage(), 1 + 0 * iw, 1 + 10 * (ih + 1), null);

			// // Charge Icons
			for (int z = 0; z < 10; z++) {
				for (int e = 0; e < 10; e++) {
					final int index = 101 + z * 10 + e;
					final ImageIcon imgc = iconMap.get(index);
					g2d.drawImage(imgc.getImage(), 1 + 10 * (iw + 1) + e * (iw + 1), 1 + z * (ih + 1), null);
				}
			}
			final ImageIcon img100c = iconMap.get(201);
			g2d.drawImage(img100c.getImage(), 1 + 10 * (iw + 1) + 0 * iw, 1 + 10 * (ih + 1), null);

			return over;
		}
		return null;
	}

	// #########################################################################
	// Backgrounds
	// #########################################################################

	private static void drawBackground01(final int w, final int h, final Graphics2D g2d) {
		// Zur�ck auf normales paint
		g2d.setPaintMode();
		// Background
		g2d.setColor(new Color(128, 128, 128, 255));
		g2d.fillRoundRect(1, 1, w - 2, h - 2, 20, 20);
		g2d.setColor(new Color(32, 32, 32));
		g2d.fillRoundRect(3, 3, w - 6, h - 6, 20, 20);
		// Composite COlor setzen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f));
		final Color col1 = new Color(255, 255, 255, 25);
		final Color col2 = new Color(255, 255, 255, 10);
		final GradientPaint gradientFill = new GradientPaint(-20, -30, col1, w + 40, -30, col2);
		g2d.setPaint(gradientFill);
		g2d.fillArc(-20, -30, w + 40, 60, 0, 360);
		g2d.setPaint(new Color(255, 255, 255, 30));
		g2d.drawArc(-20, -30, w + 40, 60, 0, 360);
		// Zur�ck auf normales paint
		g2d.setPaintMode();

	}

	private static void drawBackground02(final int w, final int h, final Graphics2D g2d, final boolean smallframe) {
		// Zur�ck auf normales paint
		g2d.setPaintMode();
		// Background
		final int radius = 8;

		Color col1 = new Color(92, 92, 92, 255);
		Color col2 = new Color(32, 32, 32, 255);
		GradientPaint gradientFill = new GradientPaint(0, 0, col1, 0, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(1, 1, w - 2, h - 2, radius + 3, radius + 3);

		col1 = new Color(64, 64, 64, 255);
		col2 = new Color(32, 32, 32, 255);
		gradientFill = new GradientPaint(0, 0, col1, w, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(3, 3, w - 6, h - 6, radius + 3, radius + 3);

		// Reflektion
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f));
		col1 = new Color(192, 192, 192, 192);
		col2 = new Color(32, 32, 32, 192);
		gradientFill = new GradientPaint(0, 0, col2, w / 2, 0, col1);
		g2d.setPaint(gradientFill);
		final Polygon p = new Polygon();
		p.addPoint(3, 3);
		p.addPoint(3, h - 3);
		p.addPoint(w / 2 - 30, h - 3);
		p.addPoint(w / 2 + 30, 3);
		g2d.fillPolygon(p);
		// Zur�ck auf normales paint
		g2d.setPaintMode();

		// innerer rand
		col1 = new Color(92, 92, 92, 255);
		col2 = new Color(32, 32, 32, 255);
		gradientFill = new GradientPaint(0, 0, col2, 0, h, col1);
		g2d.setPaint(gradientFill);
		if (smallframe) {
			g2d.fillRoundRect(10, 10, w - 20, h - 20, radius, radius);
		} else {
			g2d.fillRoundRect(10, 32, w - 20, h - 42, radius, radius);
		}
		// fl�che
		col1 = new Color(48, 48, 48, 255);
		col2 = new Color(22, 22, 22, 255);
		gradientFill = new GradientPaint(0, 0, col1, w / 2, h, col2);
		g2d.setPaint(gradientFill);
		if (smallframe) {
			g2d.fillRoundRect(11, 11, w - 22, h - 22, radius, radius);
		} else {
			g2d.fillRoundRect(11, 33, w - 22, h - 44, radius, radius);
		}

		// Zur�ck auf normales paint
		g2d.setPaintMode();

	}

}
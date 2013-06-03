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
import java.util.Vector;
import javax.swing.ImageIcon;
import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.main.IconCreatorFrame;

public class OverviewCreator {

	final static public String	banner	= "Created with '" + IconCreatorFrame.APP_NAME + "' V" + IconCreatorFrame.VERSION_NR + " by OlliG";

	public OverviewCreator() {
	}

	public static ImageIcon createOverviewIcon(final List<ImageIcon> iconMap, final String name) {
		final BufferedImage bimg = createOverviewImage(iconMap, name);
		if (bimg != null)
			return new ImageIcon(bimg);
		return null;
	}

	public static BufferedImage createOverviewImage(final List<ImageIcon> iconMap, final String name) {
		if (iconMap != null && iconMap.size() > 0) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();
			final int w = iw * 10 + 11;
			final int offsetOben = 50;
			final int offsetUnten = 35;
			final int volleZehner = iconMap.size() / 10 + 1;

			final int h = ih * volleZehner + (volleZehner + 1) + offsetOben + offsetUnten;

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
			g2d.drawString("Created with ''The Battery Icon Creator'' V" + IconCreatorFrame.VERSION_NR + " by OlliG", 2, 32);
			g2d.drawString(IconCreatorFrame.HOMEPAGE_URL, 2, h - offsetUnten + 20);
			g2d.setColor(Color.white);
			g2d.fillRect(0, 40, w, 2);
			g2d.fillRect(0, h - offsetUnten, w, 2);
			g2d.fillRect(0, h - 2, w, 2);

			// Lopp über alle Bilder
			for (int i = 0; i < iconMap.size(); i++) {
				final int z = i / 10;
				final int e = i % 10;
				final int index = z * 10 + e;
				final ImageIcon img = iconMap.get(index);
				g2d.drawImage(img.getImage(), 1 + e * (iw + 1), 1 + z * (ih + 1) + offsetOben, null);
			}
			return over;
		}
		return null;
	}

	// #########################################################################
	// Stripe Image
	// #########################################################################

	public static ImageIcon createResizedStripeIcon(final Vector<ImageIcon> iconMap, final String name, final int maxanz, final int height) {
		final BufferedImage bimg = createResizedStripeImage(iconMap, name, maxanz, height);
		if (bimg != null)
			return new ImageIcon(bimg);
		return null;
	}

	public static ImageIcon createStripeIcon(final Vector<ImageIcon> iconMap, final String name, final int maxanz) {
		final BufferedImage bimg = createStripeImage(iconMap, name, maxanz);
		if (bimg != null)
			return new ImageIcon(bimg);
		return null;
	}

	public static BufferedImage createResizedStripeImage(final Vector<ImageIcon> iconMap, final String name, final int maxanz, final int height) {
		final BufferedImage bimg = createStripeImage(iconMap, name, maxanz);
		if (bimg != null)
			return StaticImageHelper.resize2Height(bimg, height);
		return null;
	}

	private static BufferedImage createStripeImage(final Vector<ImageIcon> iconMap, final String name, final int maxanz) {
		if (iconMap != null && iconMap.size() > 0) {
			final int anzahl = Math.min(maxanz, iconMap.size());

			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();
			final int w = iw * anzahl + (anzahl + 1);
			final int h = ih;
			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();

			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, w, h);

			// Lopp über alle Bilder
			for (int i = 0; i < iconMap.size(); i++) {
				final ImageIcon img = iconMap.elementAt(i);
				g2d.drawImage(img.getImage(), 1 + i * (iw + 1), 1, null);
			}
			return over;
		}
		return null;
	}

	// #########################################################################
	// Overviews
	// #########################################################################

	public static BufferedImage createOverviewImage(final List<ImageIcon> iconMap, final String name, final int style, final int background) {
		if (iconMap != null && iconMap.size() > 0) {

			// Iconblock besorgen
			BufferedImage iconBlock;
			switch (style) {
				default:
				case 0:
					iconBlock = StaticIconGridCreator.createBatteryOneLineGrid(iconMap, true);
					break;
				case 1:
					iconBlock = StaticIconGridCreator.createBatteryOneLineGrid(iconMap, false);
					break;
				case 2:
					iconBlock = StaticIconGridCreator.createBigGrid(iconMap);
					break;

			}

			// Grössen berechnen
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

			switch (background) {
				default:
				case 0:
					drawBackground01(w, h, g2d, offsetOben - 10, offsetUnten);
					break;
				case 1:
					drawBackgroundTV(w, h, g2d, false);
					break;
				case 2:
					drawBackgroundTV(w, h, g2d, true);
					break;
				case 3:
					drawBackgroundFrame(w, h, g2d);
					break;
				case 4:
					drawBackgroundOldStyle(w, h, g2d, offsetOben - 5, offsetUnten);
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

	// //
	// #########################################################################
	// // IconBlocks
	// //
	// #########################################################################
	//
	// public static BufferedImage createOneLineIconBlock(final List<ImageIcon>
	// iconMap, final int maxanz, final boolean drawReflection) {
	// if (iconMap != null && iconMap.size() > 0) {
	// final int anzahl = Math.min(maxanz, iconMap.size());
	//
	// final ImageIcon img1 = iconMap.get(0);
	// final int iw = img1.getIconWidth();
	//
	// final int ih = img1.getIconHeight();
	//
	// // offsets berechnen
	// final int w = iw * anzahl + (anzahl + 2);
	// int h = 1 * ih + 2;
	// if (drawReflection)
	// h = 2 * ih + 3;
	//
	// final BufferedImage over = new BufferedImage(w, h,
	// BufferedImage.TYPE_INT_ARGB);
	// final Graphics2D g2d = over.createGraphics();
	// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	// RenderingHints.VALUE_ANTIALIAS_ON);
	//
	// // Lopp über alle Bilder
	// for (int i = 0; i < anzahl; i++) {
	// final ImageIcon img = iconMap.get(i);
	// final int x = 1 + i * (iw + 1);
	// if (drawReflection) {
	// g2d.drawImage(img.getImage(), x, 1, null);
	// final BufferedImage flipimg =
	// StaticImageHelper.createReflectionImage(StaticImageHelper.convertImageIcon(img),
	// true);
	// g2d.drawImage(flipimg, x, 1 + 1 * (ih + 1), null);
	// }
	// }
	// return over;
	// }
	// return null;
	// }
	//
	// public static BufferedImage createBigIconBlock(final List<ImageIcon>
	// iconMap) {
	// if (iconMap != null && iconMap.size() > 0) {
	// final ImageIcon img1 = iconMap.get(0);
	// final int iw = img1.getIconWidth();
	// final int ih = img1.getIconHeight();
	// final int w = iw * 10 + 11;
	// final int volleZehner = iconMap.size() / 10 + 1;
	// final int h = ih * volleZehner + (volleZehner + 1);
	//
	// final BufferedImage over = new BufferedImage(w, h,
	// BufferedImage.TYPE_INT_ARGB);
	// final Graphics2D g2d = over.createGraphics();
	// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	// RenderingHints.VALUE_ANTIALIAS_ON);
	//
	// // Lopp über alle Bilder
	// for (int i = 0; i < iconMap.size(); i++) {
	// final int z = i / 10;
	// final int e = i % 10;
	// final int index = z * 10 + e;
	// final ImageIcon img = iconMap.get(index);
	// g2d.drawImage(img.getImage(), 1 + e * (iw + 1), 1 + z * (ih + 1), null);
	// }
	// return over;
	// }
	// return null;
	// }
	//
	// public static BufferedImage createIconBlockWithFilenames(final
	// List<ImageIcon> iconMap, final List<String> filenames, final String name)
	// {
	// if (iconMap != null && iconMap.size() > 0) {
	// final ImageIcon img1 = iconMap.get(0);
	// final int iw = img1.getIconWidth();
	// final int ih = img1.getIconHeight();
	// final int w = iw * 10 + 21;
	// final int h = ih * iconMap.size() + iconMap.size() + 1;
	//
	// final BufferedImage over = new BufferedImage(w, h,
	// BufferedImage.TYPE_INT_ARGB);
	// final Graphics2D g2d = over.createGraphics();
	// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	// RenderingHints.VALUE_ANTIALIAS_ON);
	//
	// g2d.setColor(Color.white);
	// for (int i = 0; i < iconMap.size(); i++) {
	// final ImageIcon img = iconMap.get(i);
	// g2d.drawImage(img.getImage(), 1, 1 + i * (ih + 1), null);
	// g2d.drawString(filenames.get(i), 5 + iw, (i + 1) * (ih + 1) - 4);
	// }
	// return over;
	// }
	// return null;
	// }

	// #########################################################################
	// Backgrounds
	// #########################################################################

	protected static void drawBackground01(final int w, final int h, final Graphics2D g2d, final int offsetOben, final int offsetUnten) {
		// Zurück auf normales paint
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
		g2d.fillArc(-20, -offsetOben, w + 40, 2 * offsetOben, 0, 360);
		g2d.setPaint(new Color(255, 255, 255, 30));
		g2d.drawArc(-20, -offsetOben, w + 40, 2 * offsetOben, 0, 360);
		// Zurück auf normales paint
		g2d.setPaintMode();

	}

	protected static void drawBackgroundTV(final int w, final int h, final Graphics2D g2d, final boolean smallframe) {
		// Zurück auf normales paint
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
		col2 = new Color(32, 32, 32, 0);
		gradientFill = new GradientPaint(0, 0, col2, w / 2, 0, col1);
		g2d.setPaint(gradientFill);
		final Polygon p = new Polygon();
		p.addPoint(3, 3);
		p.addPoint(3, h - 3);
		p.addPoint((int) Math.round(w * 0.33333), h - 3);
		p.addPoint((int) Math.round(w * 0.66666), 3);
		g2d.fillPolygon(p);
		// Zurück auf normales paint
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
		// fläche
		col1 = new Color(32, 32, 32, 255);
		col2 = new Color(16, 16, 16, 255);
		gradientFill = new GradientPaint(0, 0, col1, w / 2, h, col2);
		g2d.setPaint(gradientFill);
		if (smallframe) {
			g2d.fillRoundRect(11, 11, w - 22, h - 22, radius, radius);
		} else {
			g2d.fillRoundRect(11, 33, w - 22, h - 44, radius, radius);
		}

		// Zurück auf normales paint
		g2d.setPaintMode();
	}

	protected static void drawBackgroundOldStyle(final int w, final int h, final Graphics2D g2d, final int offsetOben, final int offsetUnten) {
		// Zurück auf normales paint
		g2d.setPaintMode();

		// Background
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, w, h);

		// deviderlines
		g2d.setColor(Color.white);
		g2d.fillRect(0, offsetOben - 2, w, 2);
		g2d.fillRect(0, h - offsetUnten, w, 2);
		g2d.fillRect(0, h - 2, w, 2);

		// Zurück auf normales paint
		g2d.setPaintMode();
	}

	protected static void drawBackgroundBigTV(final int w, final int h, final Graphics2D g2d, final int offsetOben) {
		// Zurück auf normales paint
		g2d.setPaintMode();
		// Background
		final int radius = 12;

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
		col2 = new Color(32, 32, 32, 0);
		gradientFill = new GradientPaint(0, 0, col2, w / 2, 0, col1);
		g2d.setPaint(gradientFill);
		final Polygon p = new Polygon();
		p.addPoint(3, 3);
		p.addPoint(3, h - 3);
		p.addPoint((int) Math.round(w * 0.33333), h - 3);
		p.addPoint((int) Math.round(w * 0.66666), 3);
		g2d.fillPolygon(p);
		// Zurück auf normales paint
		g2d.setPaintMode();

		// innerer rand
		col1 = new Color(92, 92, 92, 255);
		col2 = new Color(32, 32, 32, 255);
		gradientFill = new GradientPaint(0, 0, col2, 0, h, col1);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(15, offsetOben, w - 30, h - offsetOben - 15, radius, radius);
		// fläche
		col1 = new Color(32, 32, 32, 255);
		col2 = new Color(16, 16, 16, 255);
		gradientFill = new GradientPaint(0, 0, col1, w / 2, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(16, offsetOben + 1, w - 32, h - offsetOben - 17, radius, radius);

		// Zurück auf normales paint
		g2d.setPaintMode();
	}

	protected static void drawBackgroundFrame(final int w, final int h, final Graphics2D g2d) {
		// Zurück auf normales paint
		g2d.setPaintMode();
		// Background
		final int radius = 20;
		final int offsetOben = 40;

		g2d.setPaint(Color.gray);
		g2d.fillRoundRect(1, 1, w - 2, h - 2, radius + 6, radius + 6);

		Color col1 = new Color(32, 32, 32, 255);
		Color col2 = new Color(92, 92, 92, 255);
		GradientPaint gradientFill = new GradientPaint(0, h / 2, col1, 0, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(2, 2, w - 4, h - 4, radius + 6, radius + 6);

		col1 = new Color(64, 64, 64, 255);
		col2 = new Color(92, 92, 92, 128);
		gradientFill = new GradientPaint(0, 0, col1, 0, h / 2, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(8, 8, w - 16, h - 16, radius + 2, radius + 2);

		// Reflektion
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f));

		col1 = new Color(192, 192, 192, 255);
		col2 = new Color(32, 32, 32, 128);
		gradientFill = new GradientPaint(0, 0, col2, 0, 2 * offsetOben, col1);
		g2d.setPaint(gradientFill);
		g2d.fillArc(-50, -offsetOben, w + 100, 3 * offsetOben, 0, 360);
		g2d.setPaint(new Color(255, 255, 255, 30));
		g2d.drawArc(-50, -offsetOben, w + 100, 3 * offsetOben, 0, 360);
		// Zurück auf normales paint
		g2d.setPaintMode();

		// innerer rand
		col1 = new Color(64, 64, 64, 64);
		col2 = new Color(32, 32, 32, 255);
		gradientFill = new GradientPaint(0, 0, col2, 0, h, col1);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(10, 10, w - 20, h - 20, radius, radius);
		// fläche
		col1 = new Color(32, 32, 32, 255);
		col2 = new Color(16, 16, 16, 255);
		gradientFill = new GradientPaint(0, 0, col1, w / 2, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(11, 11, w - 22, h - 22, radius, radius);

		// Zurück auf normales paint
		g2d.setPaintMode();
	}

}

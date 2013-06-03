package de.og.batterycreator.gui.widgets.overview;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
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

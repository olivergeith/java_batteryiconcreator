package de.og.batterycreator.gui.widgets.overview;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
import de.og.batterycreator.main.IconCreatorFrame;

public class BatteryOverviewCreator {

	final static String	banner	= "Created with '" + IconCreatorFrame.APP_NAME + "' V" + IconCreatorFrame.VERSION_NR + " by OlliG";

	public BatteryOverviewCreator() {
	}

	public static BufferedImage createOverview(final List<ImageIcon> iconMap, final String name) {
		if (iconMap != null && iconMap.size() > 100) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();
			final int w = iw * 20 + 21;
			final int offsetOben = 50;
			final int offsetUnten = 35;
			final int h = ih * 11 + 12 + offsetOben + offsetUnten;

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

			for (int z = 0; z < 10; z++) {
				for (int e = 0; e < 10; e++) {
					final int index = z * 10 + e;
					final ImageIcon img = iconMap.get(index);
					g2d.drawImage(img.getImage(), 1 + e * (iw + 1), 1 + z * (ih + 1) + offsetOben, null);
				}
			}
			final ImageIcon img = iconMap.get(100);
			g2d.drawImage(img.getImage(), 1 + 0 * iw, 1 + 10 * (ih + 1) + offsetOben, null);

			// // Charge Icons
			for (int z = 0; z < 10; z++) {
				for (int e = 0; e < 10; e++) {
					final int index = 101 + z * 10 + e;
					final ImageIcon imgc = iconMap.get(index);
					g2d.drawImage(imgc.getImage(), 1 + 10 * (iw + 1) + e * (iw + 1), 1 + z * (ih + 1) + offsetOben, null);
				}
			}
			final ImageIcon img100c = iconMap.get(201);
			g2d.drawImage(img100c.getImage(), 1 + 10 * (iw + 1) + 0 * iw, 1 + 10 * (ih + 1) + offsetOben, null);

			return over;
		}
		return null;
	}

	public static BufferedImage createSmallBatteryOverview(final List<ImageIcon> iconMap, final String name) {
		if (iconMap != null && iconMap.size() > 100) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();
			int w = iw * 6 + 7;
			if (w < 300)
				w = 300;
			final int offsetOben = 50;
			final int offsetUnten = 35;
			final int h = ih * 2 + 12 + offsetOben + offsetUnten;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(new Color(0, 0, 0));
			g2d.fillRect(0, 0, w, h);
			g2d.setColor(Color.white);
			g2d.drawString(name, 2, 20);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.drawString(banner, 2, 32);
			g2d.drawString(IconCreatorFrame.HOMEPAGE_URL, 2, h - offsetUnten + 20);
			g2d.setColor(Color.white);
			g2d.fillRect(0, 40, w, 2);
			g2d.fillRect(0, h - offsetUnten, w, 2);
			g2d.fillRect(0, h - 2, w, 2);

			for (int z = 0; z <= 5; z++) {
				final int index = z * 20;
				final ImageIcon img = iconMap.get(index);
				g2d.drawImage(img.getImage(), 1 + z * (iw + 1), 1 + offsetOben, null);
			}

			// // Charge Icons
			for (int z = 0; z <= 5; z++) {
				final int index = 101 + z * 20;
				final ImageIcon imgc = iconMap.get(index);
				g2d.drawImage(imgc.getImage(), 1 + z * (iw + 1), 1 + 1 * (ih + 1) + offsetOben, null);
			}

			return over;
		}
		return null;
	}

	public static BufferedImage createSmallBatteryOverview2(final List<ImageIcon> iconMap, final String name) {
		if (iconMap != null && iconMap.size() > 100) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();
			final int offsetX = 10;
			final int neededWidth = iw * 6 + 5 + 2 * offsetX;
			int w = neededWidth + 10;
			if (w < 350)
				w = 350;
			final int offsetOben = 45;
			final int offsetUnten = 25;
			final int h = ih * 2 + offsetOben + offsetUnten;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Background
			g2d.setColor(new Color(128, 128, 128, 255));
			g2d.fillRoundRect(3, 3, w - 6, h - 6, 20, 20);
			g2d.setColor(new Color(32, 32, 32));
			g2d.fillRoundRect(5, 5, w - 10, h - 10, 20, 20);
			// Composite COlor setzen
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f));
			final Color col1 = new Color(255, 255, 255, 25);
			final Color col2 = new Color(255, 255, 255, 10);
			final GradientPaint gradientFill = new GradientPaint(-20, -30, col1, w + 40, -30, col2);
			g2d.setPaint(gradientFill);
			g2d.fillArc(-20, -30, w + 40, 65, 0, 360);
			g2d.setPaint(new Color(255, 255, 255, 30));
			g2d.drawArc(-20, -30, w + 40, 65, 0, 360);

			// Zur�ck auf normales paint
			g2d.setPaintMode();
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
			g2d.drawString(banner, strxpos, h - 10);

			// g2d.setColor(Color.LIGHT_GRAY);
			// g2d.drawString("Created with '" + IconCreatorFrame.APP_NAME +
			// "' V" + IconCreatorFrame.VERSION_NR + " by OlliG", 8, 32);
			// g2d.drawString(IconCreatorFrame.HOMEPAGE_URL, 2, h - offsetUnten
			// + 20);

			int xStart = 5;
			if (neededWidth < w) {
				xStart = w / 2 - neededWidth / 2;
			}
			for (int z = 0; z <= 5; z++) {
				final int index = z * 20;
				final ImageIcon img = iconMap.get(index);
				g2d.drawImage(img.getImage(), offsetX + xStart + z * (iw + 1), offsetOben, null);
			}

			// // Charge Icons
			for (int z = 0; z <= 5; z++) {
				final int index = 101 + z * 20;
				final ImageIcon imgc = iconMap.get(index);
				g2d.drawImage(imgc.getImage(), offsetX + xStart + z * (iw + 1), 1 + 1 * (ih + 1) + offsetOben, null);
			}

			return over;
		}
		return null;
	}

}

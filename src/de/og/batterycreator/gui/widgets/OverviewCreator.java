package de.og.batterycreator.gui.widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;

import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.main.IconCreatorFrame;

public class OverviewCreator {

	public OverviewCreator() {
	}

	public static ImageIcon createResizedOverviewIcon(final List<ImageIcon> iconMap, final String name, final int size) {
		final BufferedImage bimg = createResizedOverviewImage(iconMap, name, size);
		if (bimg != null)
			return new ImageIcon(bimg);
		return null;
	}

	public static BufferedImage createResizedOverviewImage(final List<ImageIcon> iconMap, final String name, final int size) {
		final BufferedImage bimg = createOverviewImage(iconMap, name);
		if (bimg != null)
			return StaticImageHelper.resizeLongestSide2Size(bimg, size);
		return null;
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
			g2d.drawString("http://forum.xda-developers.com/showthread.php?t=1918500", 2, h - offsetUnten + 20);
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

	public static BufferedImage createStripeImage(final Vector<ImageIcon> iconMap, final String name, final int maxanz) {
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

}

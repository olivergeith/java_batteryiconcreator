package de.og.batterycreator.gui.widgets.overview;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Vector;
import javax.swing.ImageIcon;
import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.main.IconCreatorFrame;

public class StripeviewCreator {

	final static public String	banner	= "Created with '" + IconCreatorFrame.APP_NAME + "' V" + IconCreatorFrame.VERSION_NR + " by OlliG";

	public StripeviewCreator() {
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
}

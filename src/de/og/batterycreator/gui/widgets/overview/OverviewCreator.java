package de.og.batterycreator.gui.widgets.overview;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
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

}

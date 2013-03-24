package de.og.batterycreator.creators.wifi;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class TextWifiCreator extends AbstractWifiCreator {
	public static String name = "TextWifi";

	private static final int imgWidth = 41;
	private static final int imgHeight = 41;
	private static final int gap = 2;

	public TextWifiCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	@Override
	public String toString() {
		return name;
	}

	public Rectangle calculateRectForLevel(final int level) {

		final int offsetlinks = 6;
		final int y = 1;
		final int height = 5;
		final int maxWidth = imgWidth - (4 * gap) - offsetlinks;
		final int stufenbreite = Math.round(maxWidth / 5f);
		final int width = stufenbreite;
		final int x = offsetlinks + (level * (gap + stufenbreite));
		final Rectangle rect = new Rectangle(x, y, width, height);
		return rect;
	}

	@Override
	public ImageIcon createImage(final int level, final boolean fully) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);
		g2d.setStroke(new BasicStroke(2f));

		if (level == 0 && fully == true) {
			for (int i = 0; i < 5; i++) {
				g2d.setColor(getSettings().getColorInActiv());
				final Rectangle rect = calculateRectForLevel(i);
				g2d.drawRect(rect.x + 1, rect.y, rect.width - 1, rect.height - 1);
			}
		} else {
			for (int i = 0; i < 5; i++) {
				g2d.setColor(getConnectColor(fully));
				final Rectangle rect = calculateRectForLevel(i);
				if (i <= level)
					g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
				else {
					g2d.setColor(getSettings().getColorInActiv());
					g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
				}
			}
		}
		final FontMetrics metrix = g2d.getFontMetrics();
		g2d.setColor(getConnectColor(fully));
		final Font font = getSettings().getFont();
		final Font font2 = font.deriveFont(14f);
		g2d.setFont(font2);
		final String wifi = "Wifi";
		Rectangle2D strRect = metrix.getStringBounds(wifi, g2d);
		int strxpos = 1 + (int) (Math.round(img.getWidth() / 2) - Math.round(strRect.getWidth() / 2));
		int strypos = img.getHeight() / 2 + 16;
		g2d.drawString(wifi, strxpos + 6, 18);

		g2d.setFont(font);
		final String str = "" + level;
		strRect = metrix.getStringBounds(str, g2d);
		strxpos = 1 + (int) (Math.round(img.getWidth() / 2) - Math.round(strRect.getWidth() / 2));
		strypos = img.getHeight() / 2 + 16;

		g2d.drawString(str, strxpos, strypos);

		// final Rectangle rectin = new Rectangle(1, 2, 3, imgHeight - 4);
		// final Rectangle rectout = new Rectangle(3, 2, 3, imgHeight - 4);
		// g2d.setColor(stylSettings.getInWifiColor());
		// g2d.fillRect(rectin.x, rectin.y, rectin.width, rectin.height);
		// g2d.setColor(stylSettings.getOutWifiColor());
		// g2d.fillRect(rectout.x, rectout.y, rectout.width, rectout.height);

		// Filewriting
		img = writeFile(getFileName(level, fully), img);
		return new ImageIcon(img);
	}

	@Override
	public ImageIcon createInOutImage(final boolean in, final boolean out) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img, true);

		final Rectangle rectout = new Rectangle(1, 2, 3, imgHeight - 4);
		final Rectangle rectin = new Rectangle(3, 2, 3, imgHeight - 4);
		if (in) {
			g2d.setColor(getSettings().getInColor());
			g2d.fillRect(rectin.x, rectin.y, rectin.width, rectin.height);
		}
		if (out) {
			g2d.setColor(getSettings().getOutColor());
			g2d.fillRect(rectout.x, rectout.y, rectout.width, rectout.height);
		}

		// Filewriting
		img = writeFile(getFileNameInOut(in, out), img);
		return new ImageIcon(img);
	}

}

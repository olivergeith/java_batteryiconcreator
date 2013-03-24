package de.og.batterycreator.creators.wifi;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class ForkWifiCreator extends AbstractWifiCreator {
	public static String name = "ForkWifi";

	private static final int imgWidth = 41;
	private static final int imgHeight = 41;
	private static final int gap = 2;
	private final int height = Math.round((imgHeight - 2 - (4 * gap)) / 5f);

	public ForkWifiCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	@Override
	public String toString() {
		return name;
	}

	public Rectangle calculateRectForLevel(final int level) {

		final int x = 6;
		final int y = imgHeight + gap - 2 - (level + 1) * (height + gap);
		final int maxWidth = imgWidth - x;
		final int stufenbreite = Math.round(maxWidth / 5f);
		final int width = (level + 1) * stufenbreite;
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
				g2d.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
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

		// Filewriting
		img = writeFile(getFileName(level, fully), img);
		return new ImageIcon(img);
	}

	@Override
	public ImageIcon createInOutImage(final boolean in, final boolean out) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img, true);

		final Rectangle rectout = new Rectangle(1, 2, 5, (imgHeight - 4) / 2 - 1);
		final Rectangle rectin = new Rectangle(1, 3 + (imgHeight - 4) / 2, 5, (imgHeight - 4) / 2 - 1);
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

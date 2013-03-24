package de.og.batterycreator.creators.wifi;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class BrickWifi1Creator extends AbstractWifiCreator {
	public static String name = "BrickWifi.V1";

	private static final int imgMitte = 18;
	private static final int imgWidth = 41;
	private static final int imgHeight = 41;
	private static final int height = 6;
	private static final int width = 4;
	private static final int gap = 2;

	public BrickWifi1Creator(final RomSettings romSettings) {
		super(romSettings);
	}

	@Override
	public String toString() {
		return name;
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
				final Rectangle rect = new Rectangle(imgMitte - i * width, imgHeight - (i + 1) * (height + gap), (1 + 2 * i) * width, height);
				g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
			}
		} else {
			for (int i = 0; i < 5; i++) {
				g2d.setColor(getConnectColor(fully));

				final Rectangle rect = new Rectangle(imgMitte - i * width, imgHeight - (i + 1) * (height + gap), (1 + 2 * i) * width, height);
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

		final Rectangle rectin = new Rectangle(imgMitte - width - 1, imgHeight - (height + 1), width, height);
		final Rectangle rectout = new Rectangle(imgMitte + width + 1, imgHeight - (height + 1), width, height);
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

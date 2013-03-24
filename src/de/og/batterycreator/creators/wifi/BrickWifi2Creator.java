package de.og.batterycreator.creators.wifi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class BrickWifi2Creator extends AbstractWifiCreator {
	public static String name = "BrickWifi.V2";

	private static final int imgMitte = 17;
	private static final int imgWidth = 39;
	private static final int imgHeight = 36;
	private static final int height = 5;
	private static final int width = 4;
	private static final int gap = 1;
	private static final int offsetUnten = 4;

	public BrickWifi2Creator(final RomSettings romSettings) {
		super(romSettings);
		settings.setInColor(Color.white);
		settings.setOutColor(Color.white);
		settings.setColor(Color.LIGHT_GRAY);
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
				final Rectangle rect = new Rectangle(imgMitte - i * width, imgHeight - offsetUnten - height - (i) * (height + gap), (1 + 2 * i) * width, height);
				g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
			}
		} else {
			for (int i = 0; i < 5; i++) {
				g2d.setColor(getConnectColor(fully));

				final Rectangle rect = new Rectangle(imgMitte - i * width, imgHeight - offsetUnten - height - (i) * (height + gap), (1 + 2 * i) * width, height);
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

		final Rectangle rectin = new Rectangle(10, 20, 18, 18);
		final Rectangle rectout = new Rectangle(10, 1, 18, 18);
		if (in) {
			g2d.setColor(getSettings().getInColor());
			g2d.fillArc(rectin.x, rectin.y, rectin.width, rectin.height, 65, 50);
		}
		if (out) {
			g2d.setColor(getSettings().getOutColor());
			g2d.fillArc(rectout.x, rectout.y, rectout.width, rectout.height, -65, -50);
		}

		// Filewriting
		img = writeFile(getFileNameInOut(in, out), img);
		return new ImageIcon(img);
	}

}

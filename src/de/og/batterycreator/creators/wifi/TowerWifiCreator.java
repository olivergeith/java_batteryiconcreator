package de.og.batterycreator.creators.wifi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class TowerWifiCreator extends AbstractWifiCreator {
	public static String name = "TowerWifi";

	private static final int imgMitte = 18;
	private static final int imgWidth = 41;
	private static final int imgHeight = 41;
	private static final int height = 6;
	private static final int width = 7;
	private static final int stroke = 4;

	public TowerWifiCreator(final RomSettings romSettings) {
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
		g2d.setStroke(new BasicStroke(stroke));
		final int offsetunten = 5;
		for (int i = 4; i >= 0; i--) {
			Color col = getConnectColor(fully);

			final Rectangle rect = new Rectangle(imgMitte - (i * width), imgHeight - offsetunten - ((1 + i) * height), width * (2 * i + 1), height
					* (2 * i + 1));

			if (i > 0) {
				if (i > level) {
					col = getSettings().getColorInActiv();
				}
				if (level == 0 && fully == true)
					col = getSettings().getColorInActiv().darker().darker();
				rect.y = rect.y - 2;
				g2d.setColor(getSettings().getBackgroundColor());
				g2d.fillArc(rect.x - 1, rect.y - 1, rect.width + 2, rect.height + 2, 50, 80);
				g2d.setColor(col);
				g2d.fillArc(rect.x, rect.y, rect.width, rect.height, 50, 80);
			} else {
				if (level == 0 && fully == true)
					col = getSettings().getColorInActiv().darker().darker();
				g2d.setColor(getSettings().getBackgroundColor());
				g2d.fillArc(rect.x - 2, rect.y - 2, rect.width + 4, rect.height + 4, 0, 360);
				g2d.setColor(col);
				g2d.fillArc(rect.x - 1, rect.y - 1, rect.width + 2, rect.height + 2, 0, 360);
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

		final Rectangle rectin = new Rectangle(imgMitte - width + 1, height * 3 + 2, width * 3, height * 3);
		final Rectangle rectout = new Rectangle(imgMitte - width + 1, 1, width * 3, height * 3);
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

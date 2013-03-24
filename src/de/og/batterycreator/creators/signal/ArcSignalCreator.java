package de.og.batterycreator.creators.signal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class ArcSignalCreator extends AbstractSignalCreator {

	private static final int WINKEL = 60;

	public static String name = "ArcSignal";

	private static final int imgMitte = 0;
	private static final int imgWidth = 41;
	private static final int imgHeight = 41;
	private static final int height = 8;
	private static final int width = 8;
	private static final int offsetunten = imgHeight;

	public ArcSignalCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	@Override
	public ImageIcon createImage(final int level, final boolean fully) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);
		for (int i = 4; i >= 0; i--) {
			Color col = getConnectColor(fully);
			if (i > level) {
				col = getSettings().getColorInActiv();
			}
			if (level == NULL_LEVEL)
				col = getSettings().getColorInActiv().darker().darker();
			final int x = imgMitte - (i * width);
			int y = offsetunten - ((1 + i) * height);
			final int w = width * (2 * i + 1);
			final int h = height * (2 * i + 1);
			y = y - 2; // zwei pixel höher bitte
			g2d.setColor(Color.black);
			g2d.fillArc(x - 1, y - 1, w + 2, h + 2, 0, WINKEL);
			g2d.setColor(col);
			g2d.fillArc(x, y, w, h, 0, WINKEL);
			if (i == 0) {
				g2d.fillArc(x, y, width, height, 0, 360);
			}
		}
		final Rectangle rectin = new Rectangle(width + 2, imgHeight - 4, width * 2 - 2, 3);
		final Rectangle rectout = new Rectangle(width * 3 + 2, imgHeight - 4, width * 2, 3);
		g2d.setColor(getSettings().getColorInActiv());
		if (level == NULL_LEVEL)
			g2d.setColor(getSettings().getColorInActiv().darker().darker());
		g2d.fillRect(rectin.x, rectin.y, rectin.width, rectin.height);
		g2d.fillRect(rectout.x, rectout.y, rectout.width, rectout.height);

		// Filewriting
		img = writeFile(getFileName(level, fully), img);
		return new ImageIcon(img);
	}

	@Override
	public ImageIcon createInOutImage(final boolean in, final boolean out) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img, true);

		final Rectangle rectin = new Rectangle(width + 2, imgHeight - 4, width * 2 - 2, 3);
		final Rectangle rectout = new Rectangle(width * 3 + 2, imgHeight - 4, width * 2, 3);
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

	@Override
	public String toString() {
		return name;
	}

}

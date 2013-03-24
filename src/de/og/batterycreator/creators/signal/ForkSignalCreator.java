package de.og.batterycreator.creators.signal;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class ForkSignalCreator extends AbstractSignalCreator {

	public static String name = "ForkSignal";

	private static final int imgWidth = 41;
	private static final int imgHeight = 41;
	private static final int gap = 1;
	private final int breite = Math.round((imgWidth - 2 - (4 * gap)) / 5f);

	public ForkSignalCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	private Rectangle calculateRectForLevel(final int level) {

		final int offsetUnten = 6;

		final int maxHeight = imgHeight - offsetUnten;
		final int stufenhoehe = Math.round(maxHeight / 5f);
		final int hoehe = (level + 1) * stufenhoehe;
		final int y = imgHeight - offsetUnten - hoehe;
		final int x = level * (gap + breite);
		final Rectangle rect = new Rectangle(x, y, breite, hoehe);
		return rect;
	}

	@Override
	public ImageIcon createImage(final int level, final boolean fully) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);
		g2d.setStroke(new BasicStroke(2f));

		if (level == NULL_LEVEL) {
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

		g2d.setColor(getSettings().getColorInActiv());
		final Rectangle rectout = new Rectangle(0, imgHeight - 5, imgWidth / 2 - 2, 5);
		final Rectangle rectin = new Rectangle(imgWidth / 2, imgHeight - 5, imgWidth / 2 - 1, 5);
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

		final Rectangle rectout = new Rectangle(0, imgHeight - 5, imgWidth / 2 - 2, 5);
		final Rectangle rectin = new Rectangle(imgWidth / 2, imgHeight - 5, imgWidth / 2 - 1, 5);

		// final Rectangle rectout = new Rectangle(0, imgHeight - 6, imgWidth,
		// 3);
		// final Rectangle rectin = new Rectangle(0, imgHeight - 3, imgWidth,
		// 3);
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

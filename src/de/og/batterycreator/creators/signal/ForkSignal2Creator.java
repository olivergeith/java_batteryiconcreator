package de.og.batterycreator.creators.signal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class ForkSignal2Creator extends AbstractSignalCreator {

	public static String name = "ForkSignal.V2";

	private static final int imgWidth = 39;
	private static final int imgHeight = 36;
	private static int offsetlinks = 9;
	private static final int gap = 1;
	private final int breite = Math.round((imgWidth - offsetlinks - (4 * gap)) / 5f);

	public ForkSignal2Creator(final RomSettings romSettings) {
		super(romSettings);
		settings.setInColor(Color.white);
		settings.setOutColor(Color.white);
		settings.setColor(Color.LIGHT_GRAY);
	}

	private Rectangle calculateRectForLevel(final int level) {

		final int offsetUnten = 4;
		final int offsetOben = 4;

		final int maxHeight = imgHeight - offsetUnten - offsetOben;
		final int stufenhoehe = Math.round(maxHeight / 5f);
		final int hoehe = (level + 1) * stufenhoehe;
		final int y = imgHeight - offsetUnten - hoehe;
		final int x = level * (gap + breite) + offsetlinks;
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

		// Filewriting
		img = writeFile(getFileName(level, fully), img);
		return new ImageIcon(img);
	}

	@Override
	public ImageIcon createInOutImage(final boolean in, final boolean out) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img, true);

		final Rectangle rectin = new Rectangle(25, 20, 18, 18);
		final Rectangle rectout = new Rectangle(25, 1, 18, 18);
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

	@Override
	public String toString() {
		return name;
	}

}

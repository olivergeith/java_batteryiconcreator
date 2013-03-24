package de.og.batterycreator.creators.wifi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.og.batterycreator.cfg.RomSettings;

public class TopCornerWifiCreator extends AbstractWifiCreator {
	private static final Logger LOGGER = LoggerFactory.getLogger(TopCornerWifiCreator.class);
	public static String name = "TopCornerWifi";

	private static final int imgMitte = 0;
	private static final int imgWidth = 41;
	private static final int imgHeight = 41;
	private static final int height = 8;
	private static final int width = 8;
	private static final int stroke = 4;
	private static final int offsetunten = height + 2;

	public TopCornerWifiCreator(final RomSettings romSettings) {
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
		if (level == 0 && fully == true) {
			for (int i = 4; i >= 0; i--) {
				final Rectangle rect = new Rectangle(imgMitte - (i * width), offsetunten - ((1 + i) * height), width * (2 * i + 1), height * (2 * i + 1));
				LOGGER.debug("Wifirect = " + rect);
				rect.y = rect.y - 2;
				g2d.setColor(Color.black);
				g2d.fillArc(rect.x - 1, rect.y - 1, rect.width + 2, rect.height + 2, 0, -90);
				g2d.setColor(getSettings().getColorInActiv());
				g2d.fillArc(rect.x, rect.y, rect.width, rect.height, 0, -90);
				if (i == 0) {
					g2d.setColor(getSettings().getColorInActiv());
					g2d.fillArc(rect.x, rect.y, rect.width, rect.height, 0, 360);
				}
			}
		} else {
			for (int i = 4; i >= 0; i--) {
				Color col = getConnectColor(fully);
				final Rectangle rect = new Rectangle(imgMitte - (i * width), offsetunten - ((1 + i) * height), width * (2 * i + 1), height * (2 * i + 1));

				if (i > level) {
					col = getSettings().getColorInActiv();
				}
				rect.y = rect.y - 2;
				g2d.setColor(Color.black);
				g2d.fillArc(rect.x - 1, rect.y - 1, rect.width + 2, rect.height + 2, 0, -90);
				g2d.setColor(col);
				g2d.fillArc(rect.x, rect.y, rect.width, rect.height, 0, -90);
				if (i == 0) {
					g2d.fillArc(rect.x, rect.y, rect.width, rect.height, 0, 360);
				}
			}
		}

		final Rectangle rectin = new Rectangle(width, 0, width * 4, 3);
		final Rectangle rectout = new Rectangle(0, height, 3, height * 4);
		g2d.setColor(getSettings().getColorInActiv());
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

		final Rectangle rectin = new Rectangle(width, 0, width * 4, 3);
		final Rectangle rectout = new Rectangle(0, height, 3, height * 4);
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

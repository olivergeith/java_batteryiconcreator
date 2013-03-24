package de.og.batterycreator.creators.wifi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class CircleWifiCreator extends AbstractWifiCreator {
	public static String name = "CircleWifi";

	private static final int imgMitte = 22;
	private static final int imgWidth = 44;
	private static final int imgHeight = 44;
	private static final int height = 4;
	private static final int width = 4;

	public CircleWifiCreator(final RomSettings romSettings) {
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
		if (level == 0 && fully == true) {
			for (int i = 4; i >= 0; i--) {
				final int x = imgMitte - ((i + 1) * width);
				final int y = imgMitte - ((i + 1) * height);
				final int w = width * (2 * (i + 1));
				final int h = height * (2 * (i + 1));

				g2d.setColor(Color.black);
				g2d.fillArc(x - 1, y - 1, w + 2, h + 2, 0, 360);
				g2d.setColor(getSettings().getColorInActiv());
				g2d.fillArc(x, y, w, h, 0, 360);
			}
		} else {
			for (int i = 4; i >= 0; i--) {
				Color col = getConnectColor(fully);
				if (i > level) {
					col = getSettings().getColorInActiv();
				}
				final int x = imgMitte - ((i + 1) * width);
				final int y = imgMitte - ((i + 1) * height);
				final int w = width * (2 * (i + 1));
				final int h = height * (2 * (i + 1));
				g2d.setColor(Color.black);
				g2d.fillArc(x - 1, y - 1, w + 2, h + 2, 0, 360);
				g2d.setColor(col);
				g2d.fillArc(x, y, w, h, 0, 360);
			}
		}

		// Black
		g2d.setColor(getSettings().getColorInActiv());
		final int pw = 14;
		final int ph = 18;
		final int px = imgWidth / 2 - pw / 2;
		final int py = imgHeight / 2;
		final Polygon poliin = createUpDownTriangle(px, py + 4, pw, ph); // oben
		final Polygon poliou = createUpDownTriangle(px, py - 4, pw, -ph); // unten
		g2d.fillPolygon(poliin);
		g2d.fillPolygon(poliou);

		// pw = 8;
		// ph = 10;
		// px = imgWidth / 2 - pw / 2;
		// py = imgHeight / 2;
		// poliin = createUpDownTriangle(px, py + 6, pw, ph); // oben
		// poliou = createUpDownTriangle(px, py - 6, pw, -ph); // oben
		//
		// g2d.setColor(stylSettings.getInWifiColor());
		// g2d.fillPolygon(poliin);
		// g2d.setColor(stylSettings.getOutWifiColor());
		// g2d.fillPolygon(poliou);

		// Filewriting
		img = writeFile(getFileName(level, fully), img);
		return new ImageIcon(img);
	}

	@Override
	public ImageIcon createInOutImage(final boolean in, final boolean out) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img, true);

		final int pw = 8;
		final int ph = 10;
		final int px = imgWidth / 2 - pw / 2;
		final int py = imgHeight / 2;
		final Polygon poliin = createUpDownTriangle(px, py + 6, pw, ph); // oben
		final Polygon poliou = createUpDownTriangle(px, py - 6, pw, -ph); // oben

		if (in) {
			g2d.setColor(getSettings().getInColor());
			g2d.fillPolygon(poliin);
		}
		if (out) {
			g2d.setColor(getSettings().getOutColor());
			g2d.fillPolygon(poliou);
		}

		// Filewriting
		img = writeFile(getFileNameInOut(in, out), img);
		return new ImageIcon(img);
	}

}

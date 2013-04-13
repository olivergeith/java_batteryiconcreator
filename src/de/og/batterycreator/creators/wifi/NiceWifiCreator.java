package de.og.batterycreator.creators.wifi;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class NiceWifiCreator extends AbstractWifiCreator {
	public static String name = "NiceWifi";

	private static final int imgWidth = 50;
	private static final int imgHeight = 44;

	public NiceWifiCreator(final RomSettings romSettings) {
		super(romSettings);

		settings.setOutColor(Color.lightGray);
		settings.setInColor(Color.lightGray);
		settings.setColor(new Color(230, 230, 230, 185));
		settings.setColorInActiv(new Color(128, 128, 128, 128));
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
		for (int i = 0; i < 5; i++) {
			Color col = getConnectColor(fully);
			if (i > level) {
				col = getSettings().getColorInActiv();
			}
			final int mx = imgWidth / 2;
			final int my = imgHeight - 7;
			final int r = 4 + i * 7;
			g2d.setStroke(new BasicStroke(5));

			if (level == 0 && fully == true) {
				col = getSettings().getColorInActiv();
				// g2d.setStroke(new BasicStroke(7));
			}
			g2d.setColor(col);

			if (i == 0) {
				Draw2DFunktions.fillCircle(g2d, mx, my, 6, 0, 360);
			} else
				Draw2DFunktions.drawCircle(g2d, mx, my, r, 45, 90);
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

		final int pw = 8;
		final int ph = 10;
		final int px = imgWidth / 2 - pw / 2;
		final int py = imgHeight / 2;
		final Polygon poliin = createUpDownTriangle(px, py + 2, pw, ph); // oben
		final Polygon poliou = createUpDownTriangle(px, py - 2, pw, -ph); // unten

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

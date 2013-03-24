package de.og.batterycreator.creators.wifi;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class StarGateWifiCreator extends AbstractWifiCreator {
	public static String name = "StarGate-Wifi";

	private static final int imgWidth = 40;
	private static final int imgHeight = 40;
	private static final int stroke = 4;

	public StarGateWifiCreator(final RomSettings romSettings) {
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

		final int pw = 8;
		final int ph = 12;
		final int px = imgWidth / 2 - pw / 2;
		final int py = imgHeight / 2 - pw / 2;
		final Vector<Polygon> polis = new Vector<Polygon>();
		polis.add(createUpDownTriangle(px, 1, pw, ph)); // oben
		polis.add(createLeftRightTriangle(1, py, pw, ph)); // rechts
		polis.add(createUpDownTriangle(px, imgHeight - 1, pw, -ph)); // unten
		polis.add(createLeftRightTriangle(imgWidth - 1, py, pw, -ph));// links

		g2d.setColor(getSettings().getColorInActiv());
		final Rectangle rect = new Rectangle(5, 5, imgWidth - 10, imgHeight - 10);
		g2d.drawArc(rect.x, rect.y, rect.width, rect.height, 0, 360);
		if (level == 0 && fully == true) {
			for (int i = 1; i < 5; i++) {
				g2d.fillPolygon(polis.get(i - 1));
			}
		} else {
			g2d.setColor(getConnectColor(fully));
			// g2d.drawArc(rect.x, rect.y, rect.width, rect.height, 0, 360);

			g2d.drawArc(rect.x, rect.y, rect.width, rect.height, 0, level * 90);
			for (int i = 1; i < 5; i++) {

				if (i <= level) {
					g2d.fillPolygon(polis.get(i - 1));
				} else {
					g2d.setColor(getSettings().getColorInActiv());
					g2d.fillPolygon(polis.get(i - 1));
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

		if (in) {
			g2d.setColor(getSettings().getInColor());
			g2d.fillArc(imgWidth / 2 - 4, imgHeight / 2 - 4, 8, 8, 0, 360);
		}
		if (out) {
			g2d.setColor(getSettings().getOutColor());
			g2d.drawArc(imgWidth / 2 - 5, imgHeight / 2 - 5, 10, 10, 0, 360);
		}

		// Filewriting
		img = writeFile(getFileNameInOut(in, out), img);
		return new ImageIcon(img);
	}

}

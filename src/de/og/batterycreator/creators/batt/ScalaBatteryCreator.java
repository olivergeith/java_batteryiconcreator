package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class ScalaBatteryCreator extends AbstractIconCreator {

	protected static String name = "ScalaBattery";

	private BufferedImage scala;
	private BufferedImage scalaCharge;

	public ScalaBatteryCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setColoredIcon(true);
		settings.setShowChargeSymbol(false);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		settings.setStrokewidth(2);
		scala = createScala(false);
		scalaCharge = createScala(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {
		if (percentage == 0) {
			scala = createScala(false);
			scalaCharge = createScala(true);
		}
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(82, 41, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img, true);

		final int offsetStart = 10;

		if (!charge)
			g2d.drawImage(scala, 40 - offsetStart - 4 * percentage, 0, null);
		else
			g2d.drawImage(scalaCharge, 40 - offsetStart - 4 * percentage, 0, null);

		g2d.setColor(settings.getActivFontColor(percentage));
		// g2d.setColor(new Color(255, 255, 255, 160));
		g2d.fillRect(38, 0, 2, 5);
		g2d.fillRect(40, 0, 2, 41);
		g2d.fillRect(42, 0, 2, 5);

		g2d.fillRect(38, 36, 2, 20);
		g2d.fillRect(42, 36, 2, 20);

		g2d.setColor(new Color(255, 255, 255));
		g2d.drawRect(0, 0, 81, 40);
		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	private BufferedImage createScala(final boolean charge) {
		final BufferedImage scala = new BufferedImage(440, 41, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(scala);

		final int offsetStart = 10;
		final int width = 2;
		final int gap = 2;
		final int h1 = 10;
		final int h5 = 13;
		final int h10 = 18;

		for (int i = 0; i <= 100; i++) {

			g2d.setColor(settings.getActivIconColor(i, charge));

			final int x = offsetStart + i * (width + gap);

			int height = h1;
			if (i % 10 == 0) {
				height = h10;
			} else if (i % 5 == 0)
				height = h5;
			final int y = 41 - height;
			g2d.fillRect(x, y, width, height);

			g2d.setColor(settings.getActivFontColor(i, charge));
			if (height == h10) {
				final FontMetrics metrix = g2d.getFontMetrics();
				// Farbe für Schrift
				final String str = "" + i;
				final Rectangle2D strRect = metrix.getStringBounds(str, g2d);
				g2d.drawString(str, x + settings.getFontXOffset() - Math.round(strRect.getWidth() / 2), 17 + settings.getFontYOffset());
			}

		}

		return scala;
	}

	@Override
	public String toString() {
		return name;
	}

}

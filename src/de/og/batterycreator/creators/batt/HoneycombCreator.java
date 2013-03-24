package de.og.batterycreator.creators.batt;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class HoneycombCreator extends AbstractIconCreator {

	protected static String name = "HoneyCombBattery";

	public HoneycombCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setStrokewidth(2);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		settings.setReduceFontOn100(-4);
	}

	@Override
	public boolean supportsStrokeWidth() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(41, 41, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		g2d.setColor(settings.getIconColorInActiv());
		g2d.drawArc(2, 2, 37, 37, -20, -250);
		g2d.drawArc(7, 7, 27, 27, -90, -250);

		g2d.setColor(settings.getActivIconColor(percentage, charge));
		if (percentage <= 50) {
			g2d.drawArc(2, 2, 37, 37, -20, -Math.round(percentage * (250f / 50f)));
		} else {
			g2d.drawArc(2, 2, 37, 37, -20, -250);
			g2d.drawArc(7, 7, 27, 27, -90, -Math.round((percentage - 50) * (250f / 50f)));
		}

		// Schrift
		drawPercentage(g2d, percentage, charge, img);
		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);

	}

	@Override
	public String toString() {
		return name;
	}

}

package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class ClockPointerCreator extends AbstractIconCreator {

	protected static String name = "ClockPointerBattery";

	public ClockPointerCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setShowFont(false);
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

		for (int j = 0; j < 100; j = j + 5) {
			drawBubbleCircle(j, g2d, charge, percentage >= j, percentage);
		}
		for (int j = 0; j <= 100; j = j + 1) {
			drawZeiger(j, g2d, charge, percentage >= j, percentage);
		}
		drawPercentage(g2d, percentage, charge, img);

		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	private void drawBubbleCircle(final int winkel, final Graphics2D g2d, final boolean charge, final boolean activ, final int percentage) {
		Color col = settings.getIconColorInActiv();
		if (activ) {
			col = settings.getActivIconColor(percentage, charge);
		} else {
			// if (charge)
			// col = Color.green.darker();
		}
		g2d.setColor(col);
		final int w = Math.round(180 - (3.6f * winkel));
		final int r = 19;
		final int x = (int) Math.round(r * Math.sin(w * Math.PI / 180));
		final int y = (int) Math.round(r * Math.cos(w * Math.PI / 180));

		g2d.fillArc(19 + x, 19 + y, 4, 4, 0, 360);
	}

	private void drawZeiger(final int winkel, final Graphics2D g2d, final boolean charge, final boolean activ, final int percentage) {
		final Color col = settings.getActivIconColor(percentage, charge);
		g2d.setColor(col);
		final int w = Math.round(180 - (3.6f * winkel));
		final int r = 19;
		final int x = (int) Math.round(r * Math.sin(w * Math.PI / 180));
		final int y = (int) Math.round(r * Math.cos(w * Math.PI / 180));

		if (winkel == percentage)
			g2d.drawLine(21, 21, 21 + x, 21 + y);
	}

	@Override
	public String toString() {
		return name;
	}
}

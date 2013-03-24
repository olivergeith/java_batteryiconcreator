package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class BoxCreator extends AbstractIconCreator {

	protected static String name = "BoxBattery";

	public BoxCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontXOffset(-1);
		settings.setStrokewidth(4);
	}

	@Override
	public boolean supportsFlip() {
		return true;
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
		BufferedImage img = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		g2d.setColor(settings.getIconColorInActiv());

		final int w = settings.getStrokewidth();

		g2d.fillRect(0, 0, 40, w);
		g2d.fillRect(0, 40 - w, 40, w);
		g2d.fillRect(0, 0, w, 40);
		g2d.fillRect(40 - w, 0, w, 40);

		final Color col = settings.getActivIconColor(percentage, charge);
		g2d.setXORMode(col);
		final int winkel = Math.round(360f / 100f * percentage);

		if (settings.isFlip())
			g2d.fillArc(-20, -20, 80, 80, 135, winkel);
		else
			g2d.fillArc(-20, -20, 80, 80, 135, -winkel);

		g2d.setPaintMode();
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

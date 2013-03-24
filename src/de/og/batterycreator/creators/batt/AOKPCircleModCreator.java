package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class AOKPCircleModCreator extends AbstractIconCreator {

	protected static String name = "AOKPCircleModBattery";

	public AOKPCircleModCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontXOffset(-1);
		settings.setFontYOffset(-1);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
		settings.setStrokewidth(1);
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
		BufferedImage img = new BufferedImage(41, 41, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		g2d.setColor(settings.getIconColorInActiv());
		final int arcs = 7 + settings.getStrokewidth();
		final int gap = 8;
		final int onearc = 360 / arcs;

		for (int i = 0; i < arcs; i++)
			g2d.fillArc(0, 0, 41, 41, i * onearc + gap / 2, onearc - gap);

		g2d.setXORMode(settings.getActivIconColor(percentage, charge));
		// g2d.setColor(stylSettings.getActivIconColor(percentage, charge));
		if (settings.isFlip())
			g2d.fillArc(0, 0, 41, 41, 90, -Math.round(percentage * (360f / 100f)));
		else
			g2d.fillArc(0, 0, 41, 41, 90, +Math.round(percentage * (360f / 100f)));
		g2d.setPaintMode();

		g2d.setColor(Color.black);
		g2d.fillArc(6, 6, 29, 29, 0, 360);

		// percentage
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

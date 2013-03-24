package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class ArcCreator extends AbstractIconCreator {

	public ArcCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	protected static String name = "ArcBattery";

	@Override
	public boolean supportsFlip() {
		return true;
	}

	@Override
	public boolean supportsGradient() {
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

		final int d = 41;

		g2d.setColor(settings.getIconColorInActiv());
		int ecke = 1;
		g2d.fillArc(ecke, ecke, d - ecke * 2, d - ecke * 2, 0, 360);

		g2d.setColor(Color.black);
		ecke = 4;
		g2d.fillArc(ecke, ecke, d - ecke * 2, d - ecke * 2, 0, 360);

		if (settings.isBattGradient()) {
			final Color col1 = settings.getActivIconColor(percentage, charge);
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(1, 1, col1, 1, 41, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		if (!settings.isFlip()) {
			ecke = 0;
			g2d.fillArc(ecke, ecke, d - ecke * 2, d - ecke * 2, 90, -Math.round(percentage * (360f / 100f)));

		} else {
			ecke = 0;
			g2d.fillArc(ecke, ecke, d - ecke * 2, d - ecke * 2, 90, +Math.round(percentage * (360f / 100f)));
		}
		g2d.setColor(Color.black);
		ecke = 6;
		g2d.fillArc(ecke, ecke, d - ecke * 2, d - ecke * 2, 0, 360);

		// for later customisation...
		// g2d.setColor(stylSettings.getIconColorInActiv());
		// g2d.fillArc(10, 10, 21, 21, 0, 360);

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

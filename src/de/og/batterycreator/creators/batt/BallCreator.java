package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class BallCreator extends AbstractIconCreator {

	public BallCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	protected static String name = "BallBattery";

	@Override
	public boolean supportsFlip() {
		return true;
	}

	@Override
	public boolean supportsNoBg() {
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
		final int imgWidth = 41;
		final int imgHeight = 41;
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		if (settings.isBattGradient()) {
			final Color col1 = settings.getActivIconColor(percentage, charge);
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(0, 0, col1, 0, 41, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}

		final int radius = Math.round(4 + 16f * percentage / 100f);

		// Draw2DFunktions.fillCircle(g2d, imgWidth / 2, imgHeight / 2, radius,
		// 0, 360);
		Draw2DFunktions.fillCircle(g2d, radius, imgHeight - radius, radius, 0, 360);

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

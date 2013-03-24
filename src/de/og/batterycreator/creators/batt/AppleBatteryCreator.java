package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class AppleBatteryCreator extends AbstractIconCreator {

	protected static String name = "ApfelBattery";

	public AppleBatteryCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setShowFont(false);
		settings.setIconColor(Color.white);
		settings.setFontXOffset(-3);
		settings.setColoredIcon(false);
		settings.setShowChargeSymbol(false);
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
		BufferedImage img = new BufferedImage(51, 41, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		g2d.setColor(Color.gray);
		final int w = Math.round(39f / 100f * percentage);

		final int cornerRad = 7;
		g2d.fillRoundRect(1, 7, 45, 27, cornerRad + 2, cornerRad + 2); // Battery
		g2d.fillRoundRect(45, 15, 6, 11, cornerRad - 2, cornerRad - 2); // BatteryKnob

		if (settings.isBattGradient()) {
			final Color col1 = settings.getIconColorInActiv();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(3, 9, col2, 3, 9 + 23, col1);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getIconColorInActiv());
		}
		g2d.fillRoundRect(3, 9, 41, 23, cornerRad, cornerRad); // InnerBattery

		g2d.setColor(settings.getIconColorInActiv());
		g2d.fillRoundRect(46, 17, 3, 7, cornerRad - 2, cornerRad - 2); // InnerBatteryKnob

		if (settings.isBattGradient()) {
			final Color col1 = settings.getActivIconColor(percentage, charge);
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(4, 10, col1, 4, 10 + 21, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		g2d.fillRoundRect(4, 10, w, 21, cornerRad - 3, cornerRad - 3); // Batterylevel
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

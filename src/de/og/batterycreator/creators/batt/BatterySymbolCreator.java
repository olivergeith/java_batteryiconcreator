package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class BatterySymbolCreator extends AbstractIconCreator {

	public BatterySymbolCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setBattGradient(true);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(true);
		settings.setLowBattTheshold(1);
		settings.setMedBattTheshold(50);
	}

	protected static String	name	= "BatterySymbol";

	@Override
	public boolean supportsBattGradient() {
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

		g2d.setColor(Color.gray);
		g2d.fillRect(5, 3, 31, 37); // Battery Border
		g2d.fillRect(12, 0, 17, 3); // Battery Knob

		if (settings.isBattGradient()) {
			final Color col1 = settings.getIconColorInActiv();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(7, 5, col2, 7 + 27, 5, col1);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getIconColorInActiv());
		}
		g2d.fillRect(7, 5, 27, 33); // Inner Battery

		int h = Math.round(31f / 100f * percentage);
		if (h < 2)
			h = 2;

		if (settings.isBattGradient()) {
			final Color col1 = settings.getActivIconColor(percentage, charge);
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(8, h, col1, 8 + 25, h, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		g2d.fillRect(8, 6 + 31 - h, 25, h); // Battery Border

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

package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class BatterySymbolCreatorV2 extends AbstractIconCreator {

	public BatterySymbolCreatorV2(final RomSettings romSettings) {
		super(romSettings);
		settings.setBattGradient(true);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(false);
		settings.setLowBattTheshold(1);
		settings.setMedBattTheshold(30);
		settings.setFontXOffset(0);
		settings.setFontYOffset(3);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(3);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
	}

	protected static String	name	= "BatterySymbol.V2";

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
		final int imgWidth = 41;
		final int imgHeight = 41;

		final int battXOffset = 7;
		final int battYOffset = 6;
		final int battWidth = 27;
		final int battHeight = 35;
		final int cornerRad = 7;

		final int knobHeight = 4;
		final int knobOffset = 7;

		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		g2d.setColor(settings.getIconColorInActiv().brighter().brighter());
		// Batt Border
		g2d.fillRoundRect(battXOffset, battYOffset, battWidth, battHeight, cornerRad + 2, cornerRad + 2);
		// batt Knob
		g2d.fillRoundRect(battXOffset + knobOffset, 1, battWidth - (2 * knobOffset), knobHeight, cornerRad - 2, cornerRad - 2);

		// Inner Battery
		if (settings.isBattGradient()) {
			final Color col1 = settings.getIconColorInActiv();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(7, 5, col2, 7 + 27, 5, col1);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getIconColorInActiv());
		}
		g2d.fillRoundRect(battXOffset + 2, battYOffset + 2, battWidth - 4, battHeight - 4, cornerRad, cornerRad);

		// level rect
		int h = Math.round((battHeight - 6) / 100f * percentage);
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
		g2d.fillRoundRect(battXOffset + 3, (battYOffset + 3) + (battHeight - 6) - h, battWidth - 6, h, cornerRad - 2, cornerRad - 2); // Battery
																																		// Border

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

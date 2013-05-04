package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class SimpleBatteryCreatorV2 extends AbstractIconCreator {

	public SimpleBatteryCreatorV2(final RomSettings romSettings) {
		super(romSettings);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
		settings.setReduceFontOn100(-2);
		settings.setBattGradient(true);
		settings.setBattGradientLevel(1);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(false);
		settings.setLowBattTheshold(1);
		settings.setMedBattTheshold(30);
		settings.setFontXOffset(-1);
		settings.setStrokewidth(2);
		settings.setIconXOffset(-1);
	}

	protected static String	name	= "SimpleBattery.V2";

	@Override
	public boolean supportsGradient() {
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
		final int imgWidth = 41;
		final int imgHeight = 41;
		final int rand = settings.getStrokewidth();
		final int cornerRad = 5;

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		if (settings.isBattGradient()) {
			final Color col1 = settings.getIconColorInActiv().brighter();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(7, 5, col2, 7 + 27, 5, col1);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getIconColorInActiv());
		}
		final int xoff = 9;
		final int yoff = 5;
		g2d.fillRoundRect(xoff, yoff, imgWidth - 2 * xoff, imgHeight - yoff - 3, cornerRad, cornerRad);

		// Knob
		final int knobxoff = 15;
		final int knobyoff = 2;
		g2d.fillRoundRect(knobxoff, knobyoff, imgWidth - 2 * knobxoff, 3, cornerRad, cornerRad);

		int h = Math.round(39f / 100f * percentage);
		if (h < 3)
			h = 3;

		if (settings.isBattGradient()) {
			final Color col1 = settings.getActivIconColor(percentage, charge);
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(8, h, col1, 8 + 25, h, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		g2d.fillRoundRect(xoff - rand, imgHeight - 1 - h, imgWidth - 2 * (xoff - rand), h, cornerRad + 2, cornerRad + 2);

		// ClearingKnob
		g2d.setBackground(new Color(255, 255, 255, 0));
		g2d.clearRect(0, 0, knobxoff - rand, yoff - 0);
		g2d.clearRect(imgWidth - (knobxoff - rand), 0, knobxoff - rand, yoff - 0);

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

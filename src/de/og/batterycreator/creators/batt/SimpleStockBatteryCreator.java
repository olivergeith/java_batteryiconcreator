package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class SimpleStockBatteryCreator extends AbstractIconCreator {

	public SimpleStockBatteryCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		settings.setReduceFontOn100(-5);
		settings.setBattGradient(true);
		settings.setBattGradientLevel(1);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(false);
		settings.setLowBattTheshold(1);
		settings.setMedBattTheshold(30);
		settings.setFontXOffset(-1);
		settings.setFontYOffset(3);
		settings.setStrokewidth(2);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(3);
		settings.setIconColor(Color.green.darker());
		settings.setIconChargeColor(Color.yellow);
		settings.setResizeChargeSymbolHeight(33);

	}

	protected static String	name	= "Stock.JellyBean";

	@Override
	public boolean supportsGradient() {
		return true;
	}

	@Override
	public boolean supportsStrokeWidth() {
		return true;
	}

	@Override
	public boolean isNativeXXHDPI() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {
		final int imgWidth = 54;
		final int imgHeight = 54;
		final int rand = settings.getStrokewidth();

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
		final int xoff = 9 + rand;
		final int yoff = 2;
		g2d.fillRect(xoff, yoff, imgWidth - 2 * xoff, imgHeight - 2 * yoff);

		// level
		int h = Math.round((imgHeight - 2 * yoff) / 100f * percentage);
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
		g2d.fillRect(xoff, imgHeight - yoff - h, imgWidth - 2 * xoff, h);

		// ClearingKnob
		final int knobxoff = 16 + rand;
		final int knobyoff = 7;
		g2d.setBackground(new Color(255, 255, 255, 0));
		g2d.clearRect(0, 0, knobxoff, knobyoff);
		g2d.clearRect(imgWidth - knobxoff, 0, knobxoff, knobyoff);

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

package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.BattSettings;
import de.og.batterycreator.cfg.RomSettings;

public class BatteryVerticalSymbolCreator extends AbstractIconCreator {

	protected static String	name		= "BatteryVerticalSymbol";

	private final int		imgHeight	= 41;
	private final int		imgWidth	= 45;

	public BatteryVerticalSymbolCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setIconColorInActiv(BattSettings.COLOR_INACTIV.darker());
		settings.setFontXOffset(-2);
		settings.setBattGradient(true);
	}

	@Override
	public boolean supportsFlip() {
		return true;
	}

	@Override
	public boolean supportsStrokeWidth() {
		return true;
	}

	@Override
	public boolean supportsBattGradient() {
		return true;
	}

	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {
		final int height = 35 - settings.getStrokewidth();
		final int knobHeight = 17;
		final int knobWidth = 4;
		final int width = imgWidth - knobWidth;
		final int offsetOben = (imgHeight - height) / 2;
		final int offsetKnob = (imgHeight - knobHeight) / 2;

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		g2d.setColor(settings.getIconColorInActiv().brighter());
		int w = Math.round((width - 6) / 100f * percentage);
		if (w < 2)
			w = 2;

		if (!settings.isFlip()) {
			g2d.fillRect(0, offsetOben, width, height); // Battery Border
			g2d.fillRect(width, offsetKnob, knobWidth, knobHeight); // Battery
																	// Knob
			if (settings.isBattGradient()) {
				final Color col1 = settings.getIconColorInActiv();
				final Color col2 = getBattGardientSecondColor(col1);
				final GradientPaint gradientFill = new GradientPaint(2, offsetOben + 2, col2, 2, height - 4, col1);
				g2d.setPaint(gradientFill);
			} else {
				g2d.setColor(settings.getIconColorInActiv());
			}
			g2d.fillRect(2, offsetOben + 2, width - 4, height - 4); // Inner

			if (settings.isBattGradient()) {
				final Color col1 = settings.getActivIconColor(percentage, charge);
				final Color col2 = getBattGardientSecondColor(col1);
				final GradientPaint gradientFill = new GradientPaint(3, offsetOben + 3, col1, 3, height - 6, col2);
				g2d.setPaint(gradientFill);
			} else {
				g2d.setColor(settings.getActivIconColor(percentage, charge));
			}
			g2d.fillRect(3, offsetOben + 3, w, height - 6); // Battery Level
		} else {
			g2d.fillRect(0, offsetKnob, knobWidth, knobHeight); // Battery Knob
			g2d.fillRect(knobWidth, offsetOben, width, height); // Battery
																// Border
			if (settings.isBattGradient()) {
				final Color col1 = settings.getIconColorInActiv();
				final Color col2 = getBattGardientSecondColor(col1);
				final GradientPaint gradientFill = new GradientPaint(knobWidth + 2, offsetOben + 2, col2, knobWidth + 2, offsetOben + 2 + height - 4, col1);
				g2d.setPaint(gradientFill);
			} else {
				g2d.setColor(settings.getIconColorInActiv());
			}
			g2d.fillRect(knobWidth + 2, offsetOben + 2, width - 4, height - 4); // Inner
			// Battery
			if (settings.isBattGradient()) {
				final Color col1 = settings.getActivIconColor(percentage, charge);
				final Color col2 = getBattGardientSecondColor(col1);
				final GradientPaint gradientFill = new GradientPaint(knobWidth + width - 3 - w, offsetOben + 3, col1, knobWidth + width - 3 - w, offsetOben + 3
						+ height - 6, col2);
				g2d.setPaint(gradientFill);
			} else {
				g2d.setColor(settings.getActivIconColor(percentage, charge));
			}
			g2d.fillRect(knobWidth + width - 3 - w, offsetOben + 3, w, height - 6); // Battery
			// Level
		}
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

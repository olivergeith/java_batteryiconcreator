package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

/**
 * @author Oliver
 * 
 */
public class ArcCreator2 extends AbstractIconCreator {

	protected static String name = "ArcBattery2";

	public ArcCreator2(final RomSettings romSettings) {
		super(romSettings);
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

	@Override
	public boolean supportsNoBg() {
		return true;
	}

	@Override
	public boolean supportsGradient() {
		return true;
	}

	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(41, 41, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		if (!settings.isNoBG()) {
			g2d.setColor(settings.getIconColorInActiv());
			g2d.drawArc(2, 2, 37, 37, 0, 360);
		}

		if (settings.isBattGradient()) {
			final Color col1 = settings.getActivIconColor(percentage, charge);
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(0, 0, col1, 0, 41, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		if (settings.isFlip())
			g2d.drawArc(2, 2, 37, 37, 95, +Math.round(percentage * (360f / 102f)));
		else
			g2d.drawArc(2, 2, 37, 37, 85, -Math.round(percentage * (360f / 102f)));

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

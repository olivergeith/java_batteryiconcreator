package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class KnobBatteryCreator extends AbstractIconCreator {

	public KnobBatteryCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		settings.setBattGradient(true);
		settings.setBattGradientLevel(1);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(false);
		settings.setLowBattTheshold(1);
		settings.setMedBattTheshold(30);
		settings.setFontXOffset(0);
		settings.setFontYOffset(-2);
		settings.setIconXOffset(0);
		settings.setIconYOffset(1);

	}

	protected static String	name	= "KnobBattery";

	@Override
	public boolean supportsBattGradient() {
		return true;
	}

	@Override
	public boolean supportsNoBg() {
		return true;
	}

	@Override
	public boolean supportsExtraColor1() {
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

		final int radius = 20;
		final int mitteX = 21;
		final int mitteY = 21;

		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		g2d.setColor(settings.getIconColorInActiv().brighter().brighter());
		// Batt Border
		Draw2DFunktions.fillCircle(g2d, mitteX, mitteY, radius, 0, 360);

		// Inner Battery
		if (settings.isBattGradient()) {
			final Color col1 = settings.getIconColorInActiv().darker();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(5, 5, col2, 35, 35, col1);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getIconColorInActiv().darker());
		}
		Draw2DFunktions.fillCircle(g2d, mitteX, mitteY, radius - 1, 0, 360);

		// level
		final int winkel = Math.round(3.6f * percentage);
		// if (settings.isBattGradient()) {
		// final Color col1 = settings.getActivIconColor(percentage, charge);
		// final Color col2 = getBattGardientSecondColor(col1);
		// final GradientPaint gradientFill = new GradientPaint(5, 5, col2, 35,
		// 35, col1);
		// g2d.setPaint(gradientFill);
		// } else {
		g2d.setColor(settings.getActivIconColor(percentage, charge));
		// }
		Draw2DFunktions.fillCircle(g2d, mitteX, mitteY, radius - 2, 90, winkel);

		// Bubble
		g2d.setColor(Color.darkGray.darker());
		Draw2DFunktions.fillCircle(g2d, mitteX, mitteY, radius - 7, 0, 360);

		Color col1 = settings.getActivIconColor(percentage, charge);
		if (settings.isNoBG()) {
			col1 = settings.getExtraColor1();
		}
		if (settings.isBattGradient()) {
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(5, 5, col1, 35, 35, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(col1);
		}
		Draw2DFunktions.fillCircle(g2d, mitteX, mitteY, radius - 9, 0, 360);

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

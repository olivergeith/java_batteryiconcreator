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
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
		settings.setBattGradient(true);
		settings.setBattGradientLevel(2);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(false);
		settings.setLowBattTheshold(1);
		settings.setMedBattTheshold(30);
		settings.setFontXOffset(-1);
		settings.setFontYOffset(0);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(0);
		settings.setResizeChargeSymbolHeight(30);
		settings.setStrokewidth(3);
	}

	protected static String	name	= "KnobBattery";

	@Override
	public boolean isNativeXXHDPI() {
		return true;
	}

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

		// Create a graphics contents on the buffered image
		final int imgWidth = 64;
		final int imgHeight = 64;

		final int radius1 = 31; // aussen
		final int radius2 = 29; // innen
		final int radius3 = 27; // level
		final int radius4 = 21 - settings.getStrokewidth(); // Bubble border
		final int radius5 = 19 - settings.getStrokewidth(); // Bubble
		final int mitteX = imgWidth / 2;
		final int mitteY = imgHeight / 2;

		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		// Batt Border
		g2d.setColor(settings.getIconColorInActiv().brighter().brighter());
		Draw2DFunktions.fillCircle(g2d, mitteX, mitteY, radius1, 0, 360);

		// Inner Battery
		if (settings.isBattGradient()) {
			final Color col1 = settings.getIconColorInActiv().darker();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(10, 10, col2, imgWidth - 10, imgHeight - 10, col1);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getIconColorInActiv().darker());
		}
		Draw2DFunktions.fillCircle(g2d, mitteX, mitteY, radius2, 0, 360);

		// level
		final int winkel = Math.round(3.6f * percentage);
		g2d.setColor(settings.getActivIconColor(percentage, charge));
		Draw2DFunktions.fillCircle(g2d, mitteX, mitteY, radius3, 90, winkel);

		// Bubble Rand
		g2d.setColor(settings.getIconColorInActiv().darker().darker());
		Draw2DFunktions.fillCircle(g2d, mitteX, mitteY, radius4, 0, 360);

		// Bubble
		Color col1 = settings.getActivIconColor(percentage, charge);
		if (settings.isNoBG()) {
			col1 = settings.getExtraColor1();
		}
		if (settings.isBattGradient()) {
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(10, 10, col1, imgWidth - 10, imgHeight - 10, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(col1);
		}
		Draw2DFunktions.fillCircle(g2d, mitteX, mitteY, radius5, 0, 360);

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

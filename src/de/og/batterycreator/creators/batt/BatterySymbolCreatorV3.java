package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class BatterySymbolCreatorV3 extends AbstractIconCreator {

	public BatterySymbolCreatorV3(final RomSettings romSettings) {
		super(romSettings);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		settings.setBattGradient(true);
		settings.setBattGradientLevel(4);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(false);
		settings.setLowBattTheshold(1);
		settings.setMedBattTheshold(30);
		settings.setFontXOffset(7);
		settings.setFontYOffset(-8);
		settings.setIconXOffset(6);
		settings.setIconYOffset(-5);

	}

	protected static String	name	= "BatterySymbol.V3";

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

		final int battXOffset = 2;
		final int battYOffset = 6;
		final int battWidth = 25;
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
			final int x = battXOffset + 2;
			final int y = battYOffset + 2;
			final int ex = battXOffset + battWidth;
			final int ey = battYOffset + 2;
			g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv().brighter(), x, y, ex, ey, true));
		} else {
			g2d.setColor(settings.getIconColorInActiv());
		}
		g2d.fillRoundRect(battXOffset + 2, battYOffset + 2, battWidth - 4, battHeight - 4, cornerRad, cornerRad);

		// level rect
		int h = Math.round((battHeight - 6) / 100f * percentage);
		if (h < 2)
			h = 2;
		if (settings.isBattGradient()) {
			final int x = battXOffset + 3;
			final int y = h;
			final int ex = battXOffset + battWidth;
			final int ey = h;
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), x, y, ex, ey, false));
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		g2d.fillRoundRect(battXOffset + 3, (battYOffset + 3) + (battHeight - 6) - h, battWidth - 6, h, cornerRad - 2, cornerRad - 2);

		// Bubble
		final int radius = 12;
		final int mitteX = 27;
		final int mitteY = 15;

		g2d.setColor(Color.darkGray);
		Draw2DFunktions.fillCircle(g2d, mitteX, mitteY, radius + 2, 0, 360);

		Color col1 = settings.getActivIconColor(percentage, charge);
		if (settings.isNoBG()) {
			col1 = settings.getExtraColor1();
		}
		if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(col1, 8, 8, imgWidth, 30, false));
		} else {
			g2d.setColor(col1);
		}
		Draw2DFunktions.fillCircle(g2d, mitteX, mitteY, radius, 0, 360);

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

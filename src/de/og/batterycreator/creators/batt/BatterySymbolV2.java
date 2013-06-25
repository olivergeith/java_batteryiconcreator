package de.og.batterycreator.creators.batt;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class BatterySymbolV2 extends AbstractIconCreator {

	public BatterySymbolV2(final RomSettings romSettings) {
		super(romSettings);
		settings.setBattGradient(true);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(false);
		settings.setLowBattTheshold(1);
		settings.setMedBattTheshold(30);
		settings.setFontXOffset(-1);
		settings.setFontYOffset(6);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(6);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
		settings.setResizeChargeSymbolHeight(33);

	}

	protected static String	name	= "BatterySymbol.V2";

	@Override
	public boolean supportsBattGradient() {
		return true;
	}

	@Override
	public boolean supportsTexture() {
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

		// Create a graphics contents on the buffered image
		final int imgWidth = 64;
		final int imgHeight = 64;

		final int battXOffset = 12;
		final int battYOffset = 9;
		final int battWidth = 40;
		final int battHeight = 55;
		final int cornerRad = 9;

		final int knobHeight = 6;
		final int knobOffset = 12;

		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		g2d.setColor(settings.getIconColorInActiv().brighter().brighter());
		// Batt Border
		g2d.fillRoundRect(battXOffset, battYOffset, battWidth, battHeight, cornerRad + 2, cornerRad + 2);
		// batt Knob
		g2d.fillRoundRect(battXOffset + knobOffset, 1, battWidth - (2 * knobOffset), knobHeight, cornerRad - 2, cornerRad - 2);

		// Inner Battery
		if (settings.isBattGradient() || settings.isUseTexture()) {
			final int x = battXOffset + 3;
			final int y = battYOffset;
			final int ex = battXOffset + battWidth;
			final int ey = battYOffset;
			g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv(), x, y, ex, ey, true));
		} else {
			g2d.setColor(settings.getIconColorInActiv());
		}
		g2d.fillRoundRect(battXOffset + 3, battYOffset + 3, battWidth - 6, battHeight - 6, cornerRad, cornerRad);

		// level rect
		int h = Math.round((battHeight - 8) / 100f * percentage);
		if (h < 2)
			h = 2;

		if (settings.isUseTexture()) {
			g2d.setPaint(getTexturePaint(settings.getActivIconColor(percentage, charge)));
		} else if (settings.isBattGradient()) {
			final int x = battXOffset + 3;
			final int y = battYOffset;
			final int ex = battXOffset + battWidth;
			final int ey = battYOffset;
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), x, y, ex, ey, false));
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		g2d.fillRoundRect(battXOffset + 4, (battYOffset + 4) + (battHeight - 8) - h, battWidth - 8, h, cornerRad - 2, cornerRad - 2); // Battery
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

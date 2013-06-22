package de.og.batterycreator.creators.batt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.BattSettings;
import de.og.batterycreator.cfg.RomSettings;

public class BatterySymbolVerticalV1 extends AbstractIconCreator {

	protected static String	name		= "BatterySymbol.Vertical.V1";

	private final int		imgHeight	= 41;
	private final int		imgWidth	= 45;

	public BatterySymbolVerticalV1(final RomSettings romSettings) {
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
				g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv(), 2, offsetOben + 2, 2, height - 4, true));
			} else {
				g2d.setColor(settings.getIconColorInActiv());
			}
			g2d.fillRect(2, offsetOben + 2, width - 4, height - 4); // Inner

			if (settings.isBattGradient()) {
				g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), 3, offsetOben + 3, 3, height - 6, false));
			} else {
				g2d.setColor(settings.getActivIconColor(percentage, charge));
			}
			g2d.fillRect(3, offsetOben + 3, w, height - 6); // Battery Level
		} else {
			g2d.fillRect(0, offsetKnob, knobWidth, knobHeight); // Battery Knob
			g2d.fillRect(knobWidth, offsetOben, width, height); // Battery
																// Border
			if (settings.isBattGradient()) {
				g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv(), knobWidth + 2, offsetOben + 2, knobWidth + 2, height - 4, true));
			} else {
				g2d.setColor(settings.getIconColorInActiv());
			}
			g2d.fillRect(knobWidth + 2, offsetOben + 2, width - 4, height - 4); // Inner
			// Battery
			if (settings.isBattGradient()) {
				final int x = knobWidth + width - 3 - w;
				g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), x, offsetOben + 3, x, offsetOben + 3 + height - 6,
						false));
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

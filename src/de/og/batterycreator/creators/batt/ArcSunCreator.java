package de.og.batterycreator.creators.batt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class ArcSunCreator extends AbstractIconCreator {

	public ArcSunCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	protected static String	name	= "ArcSunBattery";

	@Override
	public boolean supportsFlip() {
		return true;
	}

	@Override
	public boolean supportsNoBg() {
		return true;
	}

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
		BufferedImage img = new BufferedImage(41, 41, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		if (!settings.isNoBG()) {
			if (settings.isBattGradient()) {
				g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv(), 0, 0, img.getWidth(), img.getHeight(), true));
			} else {
				g2d.setColor(settings.getIconColorInActiv());
			}
			g2d.fillArc(1, 1, 39, 39, 0, 360);
		}

		if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), 0, 0, img.getWidth(), img.getHeight(), false));
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		if (settings.isFlip())
			g2d.fillArc(0, 0, 41, 41, 90, -Math.round(percentage * (360f / 100f)));
		else
			g2d.fillArc(0, 0, 41, 41, 90, +Math.round(percentage * (360f / 100f)));

		// for later customisation...
		// g2d.setColor(stylSettings.getIconColorInActiv());
		// g2d.fillArc(10, 10, 21, 21, 0, 360);

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

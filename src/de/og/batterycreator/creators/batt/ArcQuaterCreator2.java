package de.og.batterycreator.creators.batt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class ArcQuaterCreator2 extends AbstractIconCreator {

	public ArcQuaterCreator2(final RomSettings romSettings) {
		super(romSettings);
	}

	protected static String name = "ArcQuaterBattery";

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

		g2d.setColor(settings.getIconColorInActiv());
		g2d.fillArc(-41, 0, 82, 82, 0, 90);

		g2d.setColor(settings.getActivIconColor(percentage, charge));
		g2d.fillArc(-41, 0, 82, 82, 0, Math.round(percentage * (90f / 100f)));

		// // for later customisation...
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

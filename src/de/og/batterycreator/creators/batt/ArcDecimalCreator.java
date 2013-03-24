package de.og.batterycreator.creators.batt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class ArcDecimalCreator extends AbstractIconCreator {

	public ArcDecimalCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	protected static String name = "DecimalArcBattery";

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

		final int einer = percentage % 10;
		final int zehner = percentage / 10;

		g2d.setColor(settings.getIconColorInActiv());
		g2d.drawArc(6, 6, 29, 29, 0, 360);
		g2d.drawArc(2, 2, 37, 37, 0, 360);

		g2d.setColor(settings.getIconColorInActiv());

		g2d.setColor(settings.getActivIconColor(percentage, charge));
		g2d.drawArc(6, 6, 29, 29, 90, -Math.round(einer * (360f / 10f)));
		g2d.drawArc(2, 2, 37, 37, 90, -Math.round(zehner * (360f / 10f)));

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

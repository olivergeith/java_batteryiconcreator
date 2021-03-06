package de.og.batterycreator.creators.batt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class ArcQuaterCreator2 extends AbstractIconCreator {

	public ArcQuaterCreator2(final RomSettings romSettings) {
		super(romSettings);
		settings.setBattGradient(true);
	}

	protected static String	name	= "ArcQuaterBattery";

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

		if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv(), 0, 0, img.getWidth(), img.getHeight(), true));
		} else {
			g2d.setColor(settings.getIconColorInActiv());
		}
		g2d.fillArc(-41, 0, 82, 82, 0, 90);

		if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), 0, 0, img.getWidth(), img.getHeight(), false));
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		g2d.fillArc(-41, 0, 82, 82, 0, Math.round(percentage * (90f / 100f)));

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

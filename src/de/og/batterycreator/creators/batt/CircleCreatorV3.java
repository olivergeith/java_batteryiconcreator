package de.og.batterycreator.creators.batt;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class CircleCreatorV3 extends AbstractIconCreator {

	public CircleCreatorV3(final RomSettings romSettings) {
		super(romSettings);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		settings.setFlip(true);
	}

	/**
	 * 
	 */
	protected static String	name	= "CircleMod.V3";

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
			g2d.setColor(settings.getIconColorInActiv());
			g2d.setStroke(new BasicStroke(3f));
			g2d.drawArc(7, 7, 27, 27, 0, 360);
		}
		g2d.setStroke(new BasicStroke(3f));

		if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), 0, 0, 0, img.getHeight(), false));
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}

		if (settings.isFlip())
			g2d.drawArc(7, 7, 27, 27, 95, +Math.round(percentage * (360f / 103.5f)));
		else
			g2d.drawArc(7, 7, 27, 27, 85, -Math.round(percentage * (360f / 103.5f)));

		int w = 0;
		if (charge == true) {
			w = Math.round(percentage * (360f / 100));
		}
		g2d.setStroke(new BasicStroke(3f));

		g2d.drawArc(2, 2, 37, 37, w + 25, 40);
		g2d.drawArc(2, 2, 37, 37, w + 115, 40);
		g2d.drawArc(2, 2, 37, 37, w + 205, 40);
		g2d.drawArc(2, 2, 37, 37, w + 295, 50);

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

package de.og.batterycreator.creators.batt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class PlanetV1 extends AbstractIconCreator {

	public PlanetV1(final RomSettings romSettings) {
		super(romSettings);
		settings.setLowBattTheshold(1);
		settings.setMedBattTheshold(30);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(true);
		settings.setStrokewidth(5);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		settings.setResizeChargeSymbolHeight(33);
	}

	@Override
	public boolean supportsStrokeWidth() {
		return true;
	}

	@Override
	public boolean isNativeXXHDPI() {
		return true;
	}

	/**
	 * 
	 */
	protected static String	name		= "Planet.V1";

	private final int		imgWidth	= 64;
	private final int		imgHeight	= 64;
	private final int		radius		= 22;
	private final int		radiusP		= 8;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		// hintergrund
		g2d.setColor(settings.getIconColorInActiv());
		g2d.setStroke(new BasicStroke(settings.getStrokewidth()));
		Draw2DFunktions.drawCircle(g2d, imgWidth / 2, imgHeight / 2, radius, 0, 360);

		// Prozent Arc
		g2d.setStroke(new BasicStroke(settings.getStrokewidth()));
		g2d.setColor(settings.getActivIconColor(percentage, charge));
		final int w = Math.round(3.6f * percentage);
		Draw2DFunktions.drawCircle(g2d, imgWidth / 2, imgHeight / 2, radius, 90, w);

		// Planet

		final int w2 = Math.round(180 + (3.6f * percentage));
		final int x = imgWidth / 2 + (int) Math.round(radius * Math.sin(w2 * Math.PI / 180));
		final int y = imgHeight / 2 + (int) Math.round(radius * Math.cos(w2 * Math.PI / 180));
		g2d.setColor(Color.DARK_GRAY.darker());
		Draw2DFunktions.fillCircle(g2d, x, y, radiusP + 2, 90, 360);
		g2d.setColor(settings.getActivIconColor(percentage, charge));
		Draw2DFunktions.fillCircle(g2d, x, y, radiusP, 90, 360);

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

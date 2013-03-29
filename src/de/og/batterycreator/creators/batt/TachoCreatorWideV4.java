package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class TachoCreatorWideV4 extends AbstractIconCreator {

	protected static String name = "TachoBattery.Wide.V4";

	public TachoCreatorWideV4(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontYOffset(7);
		settings.setFontXOffset(-1);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		settings.setReduceFontOn100(0);
		settings.setIconXOffset(0);
		settings.setIconYOffset(0);
		settings.setResizeChargeSymbolHeight(30);
		settings.setLowBattTheshold(0);
		settings.setMedBattTheshold(40);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(true);
		settings.setStrokewidth(4);
		settings.setNoBG(true);
	}

	@Override
	public boolean supportsStrokeWidth() {
		return true;
	}

	@Override
	public boolean supportsNoBg() {
		return true;
	}

	private final int imgWidth = 82;
	private final int imgHeight = 41;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight,
				BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		drawScala(g2d, charge, percentage);
		if (!settings.isNoBG()) {
			drawGlow(g2d, percentage, charge);
		}
		drawPercentage(g2d, percentage, charge, img);

		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	private void drawScala(final Graphics2D g2d, final boolean charge,
			final int percentage) {
		Color col;
		// Hintergrund
		col = settings.getIconColorInActiv();

		final int x = imgWidth / 2;
		final int y = imgHeight;
		final int radius = imgHeight + 1 - settings.getStrokewidth();
		g2d.setColor(col);
		Draw2DFunktions.drawCircle(g2d, x, y, radius, 0, 180);

		// Winkel
		col = settings.getActivIconColor(percentage, charge);

		g2d.setColor(col);
		final int w = Math.round(180 - (1.8f * percentage));
		Draw2DFunktions.fillCircle(g2d, x, y, radius, w, 180 - w);
	}

	/**
	 * Draws a glow behind a charge Symbol or number
	 * 
	 * @param g2d
	 * @param percentage
	 * @param charge
	 * @param img
	 */
	private void drawGlow(final Graphics2D g2d, final int percentage,
			final boolean charge) {
		final int centertranparenz = 190;

		// getting the Colors right...
		final Color col = settings.getActivIconColor(percentage, charge);
		final Color col2 = new Color(col.getRed(), col.getGreen(),
				col.getBlue(), centertranparenz);
		final Color col3 = new Color(col.getRed(), col.getGreen(),
				col.getBlue(), 0);

		// creating paint
		final Point2D center = new Point2D.Float(imgWidth / 2, imgHeight);
		final float radius = imgHeight - 10;
		final float[] dist = { 0.0f, 1.0f };
		final Color[] colors = { col2, col3 };
		final RadialGradientPaint p = new RadialGradientPaint(center, radius,
				dist, colors);

		// painting
		g2d.setPaint(p);
		Draw2DFunktions.fillCircle(g2d, imgWidth / 2, imgHeight - 2, imgHeight,
				0, 360);
		// g2d.fillArc(-10, -10, imgWidth + 20, imgHeight + 20, 0, 360);
	}

	@Override
	public String toString() {
		return name;
	}
}

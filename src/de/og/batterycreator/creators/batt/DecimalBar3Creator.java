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

public class DecimalBar3Creator extends AbstractIconCreator {

	protected static String name = "DecimalBar3Battery";

	final int imgWidth = 82;
	final int imgHeight = 41;

	public DecimalBar3Creator(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontXOffset(-1);
		settings.setFontYOffset(-7);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		settings.setReduceFontOn100(0);
		settings.setResizeChargeSymbolHeight(33);
		settings.setColoredFont(false);
		settings.setStrokewidth(10);
		settings.setLowBattTheshold(0);
		settings.setMedBattTheshold(40);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(false);
		settings.setNoBG(false);
	}

	@Override
	public boolean supportsStrokeWidth() {
		return true;
	}

	@Override
	public boolean supportsNoBg() {
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
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);
		final int bars = settings.getStrokewidth();
		final int w = Math.round(imgWidth / (float) bars);

		g2d.setColor(settings.getIconColorInActiv());
		// erstmal die Balken...
		for (int i = 0; i < bars; i++) {
			final int x = (i * w);
			final int y = 25;
			final int h = imgHeight - y;
			g2d.fillRect(x, y, w - 2, h);
		}

		g2d.setXORMode(settings.getActivIconColor(percentage, charge));

		g2d.fillRect(0, 0, Math.round(imgWidth / 100f * percentage), imgHeight);

		g2d.setPaintMode();

		if (!settings.isNoBG()) {
			drawGlow(g2d, percentage, charge);
		}

		drawPercentage(g2d, percentage, charge, img);
		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	/**
	 * Draws a glow behind a charge Symbol or number
	 * 
	 * @param g2d
	 * @param percentage
	 * @param charge
	 * @param img
	 */
	private void drawGlow(final Graphics2D g2d, final int percentage, final boolean charge) {
		final int centertranparenz = 220;

		// getting the Colors right...
		final Color col = settings.getActivIconColor(percentage, charge);
		final Color col2 = new Color(col.getRed(), col.getGreen(), col.getBlue(), centertranparenz);
		final Color col3 = new Color(col.getRed(), col.getGreen(), col.getBlue(), 0);

		// creating paint
		final Point2D center = new Point2D.Float(imgWidth / 2, 0);
		final float radius = imgHeight - 5;
		final float[] dist = { 0.0f, 1.0f };
		final Color[] colors = { col2, col3 };
		final RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);

		// painting
		g2d.setPaint(p);
		Draw2DFunktions.fillCircle(g2d, imgWidth / 2, 0, imgHeight, 0, 360);
		// g2d.fillArc(-10, -10, imgWidth + 20, imgHeight + 20, 0, 360);
	}

	@Override
	public String toString() {
		return name;
	}

}

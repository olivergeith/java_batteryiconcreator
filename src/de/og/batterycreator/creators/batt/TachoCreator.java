package de.og.batterycreator.creators.batt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class TachoCreator extends AbstractIconCreator {

	protected static String name = "TachoBattery";

	public TachoCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontYOffset(-11);
		settings.setFontXOffset(-1);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		settings.setReduceFontOn100(-2);
		settings.setIconXOffset(0);
		settings.setIconYOffset(-5);
		settings.setResizeChargeSymbolHeight(25);
		settings.setLowBattTheshold(0);
		settings.setMedBattTheshold(40);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(true);
	}

	private final int imgWidth = 43;
	private final int imgHeight = 43;

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

		for (int j = 0; j <= 100; j = j + 1) {
			drawScala(j, g2d, charge, percentage);
		}
		if (!settings.isNoBG()) {
			drawGlow(g2d, percentage, charge);
		}
		drawZeiger(g2d, charge, percentage);
		drawPercentage(g2d, percentage, charge, img);

		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	private void drawScala(final int winkel, final Graphics2D g2d, final boolean charge, final int percentage) {
		Color col;
		if (winkel <= percentage)
			col = settings.getActivIconColor(winkel, charge);
		else
			col = settings.getIconColorInActiv();

		g2d.setColor(col);
		final int w = Math.round(-90 + (1.8f * winkel));
		final int r = 19;
		final int x = imgWidth / 2 + (int) Math.round(r * Math.sin(w * Math.PI / 180));
		final int y = imgHeight - 2 - (int) Math.round(r * Math.cos(w * Math.PI / 180));

		Draw2DFunktions.fillCircle(g2d, x, y, 2, 0, 360);
	}

	private void drawZeiger(final Graphics2D g2d, final boolean charge, final int percentage) {
		final Color col = settings.getActivIconColor(percentage, charge);
		g2d.setColor(col);
		final int w = Math.round(-90 + (1.8f * percentage));
		final int r = 10;
		final int x = imgWidth / 2 + (int) Math.round(r * Math.sin(w * Math.PI / 180));
		final int y = imgHeight - 2 - (int) Math.round(r * Math.cos(w * Math.PI / 180));

		g2d.drawLine(imgWidth / 2, imgHeight - 2, x, y);

		g2d.setStroke(new BasicStroke(2f));
		final int r2 = 16;
		final int x2 = imgWidth / 2 + (int) Math.round(r2 * Math.sin(w * Math.PI / 180));
		final int y2 = imgHeight - 2 - (int) Math.round(r2 * Math.cos(w * Math.PI / 180));

		g2d.drawLine(imgWidth / 2, imgHeight - 2, x2, y2);

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
		final int centertranparenz = 150;

		// getting the Colors right...
		final Color col = settings.getActivIconColor(percentage, charge);
		final Color col2 = new Color(col.getRed(), col.getGreen(), col.getBlue(), centertranparenz);
		final Color col3 = new Color(col.getRed(), col.getGreen(), col.getBlue(), 0);

		// creating paint
		final Point2D center = new Point2D.Float(imgWidth / 2, imgHeight);
		final float radius = imgHeight - 20;
		final float[] dist = {
				0.0f, 1.0f
		};
		final Color[] colors = {
				col2, col3
		};
		final RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);

		// painting
		g2d.setPaint(p);
		Draw2DFunktions.fillCircle(g2d, imgWidth / 2, imgHeight - 2, imgHeight - 15, 0, 360);
		// g2d.fillArc(-10, -10, imgWidth + 20, imgHeight + 20, 0, 360);
	}

	@Override
	public String toString() {
		return name;
	}
}

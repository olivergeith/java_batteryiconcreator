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

public class CastawayCreatorFlip extends AbstractIconCreator {

	protected static String name = "CastawayBattery.Flip";

	public CastawayCreatorFlip(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontXOffset(-10);
		settings.setFontYOffset(-11);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		settings.setIconXOffset(-10);
		settings.setIconYOffset(-11);
		settings.setResizeChargeSymbolHeight(16);
		settings.setLowBattTheshold(0);
		settings.setMedBattTheshold(40);
		settings.setUseGradiantForMediumColor(true);
	}

	@Override
	public boolean supportsNoBg() {
		return true;
	}

	private final int imgWidth = 41;
	private final int imgHeight = 41;

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

		drawCircle(g2d, charge, percentage);
		if (!settings.isNoBG()) {
			for (int j = 0; j <= 100; j = j + 20) {
				drawScalaDots(j, g2d, charge, percentage);
			}
		}
		drawGlow(g2d, percentage, charge);
		drawZeiger(g2d, charge, percentage);
		drawPercentage(g2d, percentage, charge, img);

		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	private void drawCircle(final Graphics2D g2d, final boolean charge, final int percentage) {
		final Color col = settings.getActivIconColor(percentage, charge);
		g2d.setColor(col);
		Draw2DFunktions.drawCircle(g2d, imgWidth - 2, imgHeight - 2, imgWidth - 8, 90, 180);
		// g2d.drawArc(-imgWidth + 8 + 2, 8 - 2, 2 * imgWidth - 16, 2 * imgWidth
		// - 16, 0, 360);
	}

	private void drawScalaDots(final int winkel, final Graphics2D g2d, final boolean charge, final int percentage) {
		final Color col = settings.getActivIconColor(percentage, charge);
		g2d.setColor(col);
		final int w = Math.round(-90 + (0.9f * winkel));
		final int r = 32;
		final int x = imgWidth - 2 + (int) Math.round(r * Math.sin(w * Math.PI / 180));
		final int y = imgHeight - 2 - (int) Math.round(r * Math.cos(w * Math.PI / 180));

		g2d.fillArc(x - 2, y - 2, 5, 5, 0, 360);
	}

	private void drawZeiger(final Graphics2D g2d, final boolean charge, final int percentage) {
		final Color col = settings.getActivIconColor(percentage, charge);
		g2d.setColor(col);
		final int w = Math.round(-90 + (0.9f * percentage));
		final int r = 13;
		final int x = imgWidth - 2 + (int) Math.round(r * Math.sin(w * Math.PI / 180));
		final int y = imgHeight - 2 - (int) Math.round(r * Math.cos(w * Math.PI / 180));

		g2d.drawLine(imgWidth - 2, imgHeight - 2, x, y);

		g2d.setStroke(new BasicStroke(2f));
		final int r2 = 26;
		final int x2 = imgWidth - 2 + (int) Math.round(r2 * Math.sin(w * Math.PI / 180));
		final int y2 = imgHeight - 2 - (int) Math.round(r2 * Math.cos(w * Math.PI / 180));

		g2d.drawLine(imgWidth - 2, imgHeight - 2, x2, y2);

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
		final Point2D center = new Point2D.Float(imgWidth, imgHeight);
		final float radius = 35;
		final float[] dist = {
				0.0f, 1.0f
		};
		final Color[] colors = {
				col2, col3
		};
		final RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);

		// painting
		g2d.setPaint(p);
		g2d.fillArc(-10, -10, imgWidth + 20, imgHeight + 20, 0, 360);
	}

	@Override
	public String toString() {
		return name;
	}
}

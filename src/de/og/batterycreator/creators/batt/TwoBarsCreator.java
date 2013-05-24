package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class TwoBarsCreator extends AbstractIconCreator {

	public TwoBarsCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setBattGradient(true);
		settings.setFontXOffset(-1);
		settings.setColoredFont(true);
	}

	protected static String name = "TwoBarsBattery";

	@Override
	public boolean supportsBattGradient() {
		return true;
	}

	@Override
	public boolean supportsNoBg() {
		return true;
	}

	@Override
	public boolean supportsStrokeWidth() {
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
		final int ih = 41;
		final int iw = 41;
		BufferedImage img = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		// inactiv Rects
		final int barheight = 6;
		final int offsetobenunten = settings.getStrokewidth();
		if (!settings.isNoBG()) {

			// Bar 1 Background
			Rectangle rec = new Rectangle(1, offsetobenunten, iw - 2, barheight);
			if (settings.isBattGradient()) {
				final Color col1 = settings.getIconColorInActiv();
				final Color col2 = getBattGardientSecondColor(col1);
				final GradientPaint gradientFill = new GradientPaint(rec.x, rec.y, col2, rec.x, rec.y + barheight, col1);
				g2d.setPaint(gradientFill);
			} else {
				g2d.setColor(settings.getIconColorInActiv());
			}
			g2d.fillRect(rec.x, rec.y, rec.width, rec.height);

			// Bar 2 Background
			rec = new Rectangle(1, ih - offsetobenunten - barheight, iw - 2, barheight);
			if (settings.isBattGradient()) {
				final Color col1 = settings.getIconColorInActiv();
				final Color col2 = getBattGardientSecondColor(col1);
				final GradientPaint gradientFill = new GradientPaint(rec.x, rec.y, col2, rec.x, rec.y + barheight, col1);
				g2d.setPaint(gradientFill);
			} else {
				g2d.setColor(settings.getIconColorInActiv());
			}
			g2d.fillRect(rec.x, rec.y, rec.width, rec.height);

		}
		// aktiv
		final int z = Math.min(50, percentage);
		final int e = Math.max(0, percentage - 50);
		final int we = Math.round(39f / 50f * e);
		final int wz = Math.round(39f / 50f * z);

		final Rectangle rece = new Rectangle(1, offsetobenunten, we, barheight);
		if (settings.isBattGradient()) {
			final Color col1 = settings.getActivIconColor(percentage, charge);
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(rece.x, rece.y, col1, rece.x, rece.y + barheight, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		g2d.fillRect(rece.x, rece.y, rece.width, rece.height);

		final Rectangle recz = new Rectangle(1, ih - offsetobenunten - barheight, wz, barheight);
		if (settings.isBattGradient()) {
			final Color col1 = settings.getActivIconColor(percentage, charge);
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(recz.x, recz.y, col1, recz.x, recz.y + barheight, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		g2d.fillRect(recz.x, recz.y, recz.width, recz.height);

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

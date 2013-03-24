package de.og.batterycreator.creators.batt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class DecimalBarCreator extends AbstractIconCreator {

	protected static String name = "DecimalBarBattery";

	public DecimalBarCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontXOffset(-1);
		settings.setColoredFont(true);
		settings.setStrokewidth(6);
	}

	@Override
	public boolean supportsStrokeWidth() {
		return true;
	}

	@Override
	public boolean supportsFlip() {
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
		final int icodim = 40;
		BufferedImage img = new BufferedImage(icodim, icodim, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);
		final int bars = settings.getStrokewidth();
		final int w = Math.round(icodim / (float) bars);

		g2d.setColor(settings.getIconColorInActiv());

		if (!settings.isFlip()) {
			// erstmal die Balken...
			for (int i = 0; i < bars; i++) {
				final int x = (i * w);
				final int y = icodim - (w + i * w);
				final int h = icodim - y;
				g2d.fillRect(x, y, w - 1, h);
			}
		} else {
			// erstmal die Balken...
			for (int i = 0; i < bars; i++) {
				final int x = (i * w);
				final int y = icodim - ((bars - i) * w);
				final int h = icodim - y;
				g2d.fillRect(x, y, w - 1, h);
			}
		}

		g2d.setXORMode(settings.getActivIconColor(percentage, charge));

		if (!settings.isFlip()) {
			g2d.fillRect(0, 0, Math.round(icodim / 100f * percentage), icodim);
		} else {
			final int wr = Math.round(icodim / 100f * percentage);
			g2d.fillRect(icodim - wr, 0, wr, icodim);
		}
		g2d.setPaintMode();
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

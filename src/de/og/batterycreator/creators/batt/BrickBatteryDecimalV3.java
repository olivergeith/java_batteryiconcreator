package de.og.batterycreator.creators.batt;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class BrickBatteryDecimalV3 extends AbstractIconCreator {

	public BrickBatteryDecimalV3(final RomSettings romSettings) {
		super(romSettings);
		settings.setBattGradient(true);
	}

	@Override
	public boolean supportsBattGradient() {
		return true;
	}

	@Override
	public boolean supportsNoBg() {
		return true;
	}

	@Override
	public boolean supportsFlip() {
		return true;
	}

	protected static String	name	= "BrickBattery.Decimal.V3";

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

		final int hd = percentage / 100;

		// inactiv Rects
		final int bw = 19;

		if (!settings.isNoBG()) {
			final Rectangle rec = new Rectangle(1, 1, iw - 2, ih - 2);
			if (settings.isBattGradient()) {
				g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv(), 0, 0, img.getWidth(), img.getHeight(), true));
			} else {
				g2d.setColor(settings.getIconColorInActiv());
			}
			g2d.fillRect(rec.x, rec.y, rec.width, rec.height);
		}
		// aktiv
		int e;
		int z;
		int he;
		int hz;
		if (!settings.isFlip()) {
			e = percentage % 10;
			z = percentage / 10;
			he = Math.round(37f / 9f * e);
			hz = Math.round(37f / 9f * z);
		} else {
			z = Math.min(50, percentage);
			e = Math.max(0, percentage - 50);
			he = Math.round(37f / 49f * e);
			hz = Math.round(37f / 49f * z);
		}

		final Rectangle rece = new Rectangle(22, 39 - he, bw - 2, he);
		if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), rece.x, rece.y, rece.x + rece.width, rece.y, false));
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		g2d.fillRect(rece.x, rece.y, rece.width, rece.height);

		final Rectangle recz = new Rectangle(2, 39 - hz, bw - 2, hz);
		if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), recz.x, recz.y, recz.x + recz.width, recz.y, false));
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		g2d.fillRect(recz.x, recz.y, recz.width, recz.height);

		if (hd == 1) {
			final Rectangle rech = new Rectangle(0, 0, iw, ih);
			if (settings.isBattGradient()) {
				g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), rech.x, rech.y, rech.x + rech.width, rech.y, false));
			} else {
				g2d.setColor(settings.getActivIconColor(percentage, charge));
			}
			g2d.fillRect(rech.x, rech.y, rech.width, rech.height);
		}

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

package de.og.batterycreator.creators.batt;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class BoxCreatorV3 extends AbstractIconCreator {

	protected static String	name	= "BoxBattery.V3";

	public BoxCreatorV3(final RomSettings romSettings) {
		super(romSettings);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
		settings.setFontXOffset(-1);
		settings.setStrokewidth(2);
		settings.setBattGradient(true);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(false);
		settings.setLowBattTheshold(1);
		settings.setMedBattTheshold(50);
	}

	@Override
	public boolean supportsStrokeWidth() {
		return true;
	}

	@Override
	public boolean supportsBattGradient() {
		return true;
	}

	@Override
	public boolean supportsLinearGradient() {
		return true;
	}

	@Override
	public boolean supportsNoBg() {
		return true;
	}

	@Override
	public boolean isNativeXXHDPI() {
		return true;
	}

	@Override
	public boolean supportsTexture() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {
		final int imgWidth = 54;
		final int imgHeight = 54;
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		g2d.setColor(settings.getIconColorInActiv().brighter());
		final int w = settings.getStrokewidth();
		g2d.fillRect(0, 0, imgWidth, w);
		g2d.fillRect(0, imgHeight - w, imgHeight, w);
		g2d.fillRect(0, 0, w, imgHeight);
		g2d.fillRect(imgWidth - w, 0, w, imgHeight);

		if (!settings.isNoBG()) {
			if (settings.isBattGradient() || settings.isUseTexture() || settings.isLinearGradient()) {
				g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), w, w, imgWidth - w, imgHeight - w, true));
			} else {
				g2d.setColor(settings.getIconColorInActiv());
			}
			g2d.fillRect(w, w, imgWidth - w - w, imgHeight - w - w); // Inner
																		// Battery
		}
		int h = Math.round((imgHeight - w - w - 2) / 100f * percentage);
		if (h < 2)
			h = 2;

		if (settings.isLinearGradient()) {
			final Point2D start = new Point2D.Float(w, imgHeight - w);
			final Point2D end = new Point2D.Float(w, w);
			final LinearGradientPaint gradientFill = settings.createLinearGradientPaint(start, end);
			g2d.setPaint(gradientFill);
		} else if (settings.isUseTexture()) {
			g2d.setPaint(getTexturePaint());
		} else if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), w, w, imgWidth - w, w, false));
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		g2d.fillRect(w + 1, imgHeight - w - 1 - h, imgWidth - w - w - 2, h);

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

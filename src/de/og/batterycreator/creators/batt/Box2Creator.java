package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.cfg.RomSettings;

public class Box2Creator extends AbstractIconCreator {

	protected static String	name	= "BoxBattery.2";

	public Box2Creator(final RomSettings romSettings) {
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
				final Color col1 = settings.getIconColorInActiv();
				final Color col2 = getBattGardientSecondColor(col1);
				final GradientPaint gradientFill = new GradientPaint(w, w, col2, imgWidth - w, imgHeight - w, col1);
				g2d.setPaint(gradientFill);
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
			final TexturePaint slatetp = new TexturePaint(StaticImageHelper.convertImageIcon(settings.getTextureIcon()), new Rectangle(0, 0, 64, 64));
			g2d.setPaint(slatetp);
		} else if (settings.isBattGradient()) {

			final Color col1 = settings.getActivIconColor(percentage, charge);
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(w, w, col1, imgWidth - w, w, col2);
			g2d.setPaint(gradientFill);

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

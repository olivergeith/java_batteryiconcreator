package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class Rush25Battery extends AbstractIconCreator {

	public Rush25Battery(final RomSettings romSettings) {
		super(romSettings);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
		settings.setReduceFontOn100(-3);
		settings.setBattGradient(false);
		settings.setBattGradientLevel(2);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(false);
		settings.setLowBattTheshold(1);
		settings.setMedBattTheshold(40);
		settings.setFontXOffset(-19);
		settings.setFontYOffset(9);
		settings.setStrokewidth(2);
		settings.setIconXOffset(28);
		settings.setIconYOffset(0);
		settings.setResizeChargeSymbolHeight(33);
		settings.setMoveIconWithText(false);
		settings.setAddPercent(true);
		settings.setShowAdditionalFontOnCharge(true);
		settings.setUseChargeColor(false);

	}

	protected static String	name	= "Rush.25.Battery";

	@Override
	public boolean supportsBattGradient() {
		return true;
	}

	@Override
	public boolean isNativeXXHDPI() {
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
		final int imgWidth = 90;
		final int imgHeight = 54;
		final int xoff = 2;
		final int width = 28 + settings.getStrokewidth();
		final int yoffunten = 2;
		final int y = 1;
		final int x = imgWidth - xoff - width;
		final int height = imgHeight - y - yoffunten;

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		if (settings.isBattGradient()) {
			final Color col1 = settings.getIconColorInActiv().brighter();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(x, y, col2, x + width, y, col1);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getIconColorInActiv());
		}
		g2d.fillRect(x, y, width, height);

		// level
		int h = Math.round((imgHeight - y - yoffunten) / 100f * percentage);
		if (h < 2)
			h = 2;

		if (settings.isBattGradient()) {
			final Color col1 = settings.getActivIconColor(percentage, charge);
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(x, y, col1, x + width, y, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
		}
		g2d.fillRect(x, imgHeight - yoffunten - h, width, height);

		// ClearingKnob
		final int knobyoff = 7;
		g2d.setBackground(new Color(255, 255, 255, 0));
		g2d.clearRect(0, 0, imgWidth - xoff - width + width / 3, knobyoff);
		g2d.clearRect(imgWidth - xoff - width / 3, 0, imgWidth, knobyoff);
		// g2d.setPaint(Color.red);
		// g2d.fillRect(0, 0, imgWidth - xoff - 2 + width / 3, knobyoff);
		// g2d.setPaint(Color.green);
		// g2d.fillRect(imgWidth - xoff - width / 3, 0, imgWidth, knobyoff);

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

package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class FaecherCreatorWide extends AbstractIconCreator {

	protected static String	name	= "Faecher.Wide";

	public FaecherCreatorWide(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontYOffset(10);
		settings.setFontXOffset(-1);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		settings.setReduceFontOn100(-3);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(9);
		settings.setResizeChargeSymbolHeight(30);
		settings.setLowBattTheshold(0);
		settings.setMedBattTheshold(40);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(true);
		// settings.setStrokewidth(4);
		settings.setNoBG(false);
		settings.setBattGradient(true);
	}

	@Override
	public boolean isNativeXXHDPI() {
		return true;
	}

	@Override
	public boolean supportsGradient() {
		return true;
	}

	@Override
	public boolean supportsNoBg() {
		return true;
	}

	@Override
	public boolean supportsExtraColor1() {
		return true;
	}

	private final int	imgWidth	= 70;
	private final int	imgHeight	= 54;

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

		drawScala(g2d, charge, percentage);
		drawPercentage(g2d, percentage, charge, img);

		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	private void drawScala(final Graphics2D g2d, final boolean charge, final int percentage) {
		Color col;
		final int x = imgWidth / 2;
		final int y = imgHeight - 18;
		final int radius = imgWidth / 2 - 2;
		// Hintergrund
		col = settings.getIconColorInActiv();
		g2d.setColor(col.brighter().brighter());
		Draw2DFunktions.fillCircle(g2d, x, y, radius + 2, 0, 180);
		g2d.setColor(col.darker());
		Draw2DFunktions.fillCircle(g2d, x, y, radius, 0, 180);

		// Winkel
		col = settings.getActivIconColor(percentage, charge);
		g2d.setColor(col);
		final int w = Math.round(180 - (1.8f * percentage));
		Draw2DFunktions.fillCircle(g2d, x, y, radius, w, 180 - w);
		// Bubble
		col = settings.getIconColorInActiv();
		g2d.setColor(col.brighter().brighter());
		Draw2DFunktions.fillCircle(g2d, x, y, radius - 15, 0, 360);

		Color col1 = settings.getActivIconColor(percentage, charge);
		if (settings.isNoBG()) {
			col1 = settings.getExtraColor1();
		}
		if (settings.isBattGradient()) {
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(16, 16, col1, 36, 36, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setColor(col1);
		}
		Draw2DFunktions.fillCircle(g2d, x, y, radius - 17, 0, 360);
	}

	@Override
	public String toString() {
		return name;
	}
}

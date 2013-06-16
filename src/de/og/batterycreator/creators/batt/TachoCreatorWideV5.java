package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class TachoCreatorWideV5 extends AbstractIconCreator {

	protected static String	name	= "TachoBattery.Wide.V5";

	public TachoCreatorWideV5(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontYOffset(16);
		settings.setFontXOffset(-1);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
		settings.setReduceFontOn100(-2);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(-13);
		settings.setResizeChargeSymbolHeight(30);
		settings.setLowBattTheshold(0);
		settings.setMedBattTheshold(40);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(true);
		settings.setStrokewidth(4);
		settings.setExtraColor1(Color.white);
	}

	@Override
	public boolean supportsExtraColor1() {
		return true;
	}

	@Override
	public boolean supportsNoBg() {
		return true;
	}

	@Override
	public boolean supportsBattGradient() {
		return true;
	}

	@Override
	public boolean supportsTexture() {
		return true;
	}

	@Override
	public boolean supportsStrokeWidth() {
		return true;
	}

	@Override
	public boolean isNativeXXHDPI() {
		return true;
	}

	private final int	imgWidth	= 108;
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
		// Hintergrund
		col = settings.getIconColorInActiv();

		final int x = imgWidth / 2;
		final int y = imgHeight;
		int radius = imgHeight - 1;
		// // Composite COlor setzen
		// g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN,
		// 1f));

		// aussen rand
		g2d.setColor(col.brighter());
		Draw2DFunktions.fillCircle(g2d, x, y, radius, 0, 180);

		// Skala
		if (!settings.isNoBG() && (settings.isBattGradient() || settings.isUseTexture())) {
			final Color col1 = settings.getIconColorInActiv().brighter();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(0, 0, col1, imgWidth, imgHeight, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setPaint(settings.getIconColorInActiv());
		}
		Draw2DFunktions.fillCircle(g2d, x, y, radius - 2, 0, 180);

		// Level malen
		if (settings.isUseTexture()) {
			g2d.setPaint(getTexturePaint());
		} else if (settings.isBattGradient()) {
			final Color col1 = settings.getActivIconColor(percentage, charge).brighter();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(0, 0, col2, imgWidth, imgHeight, col1);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setPaint(settings.getActivIconColor(percentage, charge));
		}
		final int w = Math.round(180 - (1.8f * percentage));
		Draw2DFunktions.fillCircle(g2d, x, y, radius - 3, w, 180 - w);
		// Zeiger
		g2d.setPaint(Color.darkGray.darker());
		Draw2DFunktions.fillCircle(g2d, x, y, radius - 3, w - 2, 4);
		g2d.setPaint(settings.getExtraColor1());
		Draw2DFunktions.fillCircle(g2d, x, y, radius, w - 1, 2);

		// Skala innerer rand
		if (settings.isBattGradient() || settings.isUseTexture()) {
			final Color col1 = settings.getIconColorInActiv().brighter();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(0, 0, col1, imgWidth, imgHeight, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setPaint(settings.getIconColorInActiv());
		}
		radius = radius - (14 + settings.getStrokewidth());
		Draw2DFunktions.fillCircle(g2d, x, y, radius, 0, 180);
		g2d.setColor(col.brighter());
		Draw2DFunktions.fillCircle(g2d, x, y, radius - 1, 0, 180);

		// Composite COlor setzen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		Draw2DFunktions.fillCircle(g2d, x, y, radius - 3, 0, 180);

		// Normales Paint setzen
		g2d.setPaintMode();

	}

	@Override
	public String toString() {
		return name;
	}
}

package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class TachoGauge extends AbstractIconCreator {

	protected static String	name	= "TachoBattery.Gauge";

	public TachoGauge(final RomSettings romSettings) {
		super(romSettings);
		settings.setMoveIconWithText(false);
		settings.setFontXOffset(7);
		settings.setFontYOffset(18);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		settings.setReduceFontOn100(-2);
		settings.setIconXOffset(7);
		settings.setIconYOffset(14);
		settings.setResizeChargeSymbolHeight(30);
		settings.setLowBattTheshold(0);
		settings.setMedBattTheshold(40);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(true);
		settings.setStrokewidth(4);
		settings.setExtraColor1(Color.white);
		settings.setDrawZeiger(true);
		settings.setFlip(true);
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

	@Override
	public boolean supportsZeiger() {
		return true;
	}

	@Override
	public boolean supportsFlip() {
		return true;
	}

	private final int	imgWidth	= 96;
	private final int	imgHeight	= 64;

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

		final int x = imgHeight;
		final int y = imgHeight;
		int radius = imgHeight - 1;
		// // Composite COlor setzen
		// g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN,
		// 1f));

		// aussen rand
		g2d.setColor(col.brighter());
		Draw2DFunktions.fillCircle(g2d, x, y, radius, 180, -115);
		// links rechts
		if (settings.isFlip())
			Draw2DFunktions.fillCircle(g2d, x, y, radius - 2, 180, -115);

		// Skala
		if (!settings.isNoBG() && (settings.isBattGradient() || settings.isUseTexture())) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv(), 0, 0, imgWidth, imgHeight, true));
		} else {
			g2d.setPaint(settings.getIconColorInActiv());
		}
		if (settings.isFlip())
			Draw2DFunktions.fillCircle(g2d, x, y, radius - 2, 178, -111);
		else
			Draw2DFunktions.fillCircle(g2d, x, y, radius - 2, 180, -115);

		// Level malen
		if (settings.isUseTexture()) {
			g2d.setPaint(getTexturePaint());
		} else if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge).brighter(), 0, 0, imgWidth, imgHeight, false));
		} else {
			g2d.setPaint(settings.getActivIconColor(percentage, charge));
		}
		final int w = Math.round(180 - (1.15f * percentage));
		Draw2DFunktions.fillCircle(g2d, x, y, radius - 3, w, 180 - w);
		// Zeiger
		if (settings.isDrawZeiger()) {
			g2d.setPaint(Color.darkGray.darker());
			Draw2DFunktions.fillCircle(g2d, x, y, radius - 3, w - 3, 6);
			g2d.setPaint(settings.getExtraColor1());
			Draw2DFunktions.fillCircle(g2d, x, y, radius, w - 2, 4);
		}
		// Skala innerer rand
		if (settings.isBattGradient() || settings.isUseTexture()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv().brighter(), 0, 0, imgWidth, imgHeight, true));
		} else {
			g2d.setPaint(settings.getIconColorInActiv());
		}
		radius = radius - (14 + settings.getStrokewidth());
		Draw2DFunktions.fillCircle(g2d, x, y, radius, 180, -115);
		// Skala innerer rand
		g2d.setColor(col.brighter());
		Draw2DFunktions.fillCircle(g2d, x, y, radius - 1, 180, -115);

		// Inneren Halbkreis clearen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		Draw2DFunktions.fillCircle(g2d, x, y, radius - 3, 180, -180);

		// Normales Paint setzen
		g2d.setPaintMode();

	}

	@Override
	public String toString() {
		return name;
	}
}

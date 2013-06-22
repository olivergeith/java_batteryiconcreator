package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class CircleCreatorV4 extends AbstractIconCreator {

	protected static String	name	= "CircleMod.V4";

	public CircleCreatorV4(final RomSettings romSettings) {
		super(romSettings);
		settings.setMoveIconWithText(true);
		settings.setFontYOffset(0);
		settings.setFontXOffset(-1);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
		settings.setReduceFontOn100(-5);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(0);
		settings.setResizeChargeSymbolHeight(30);
		settings.setLowBattTheshold(0);
		settings.setMedBattTheshold(40);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(false);
		settings.setStrokewidth(5);
		settings.setExtraColor1(Color.white);
		settings.setDrawZeiger(true);
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
	public boolean supportsFlip() {
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
	public boolean supportsStrokeWidth() {
		return true;
	}

	private final int	imgWidth	= 64;
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

		final int x = imgWidth / 2;
		final int y = imgHeight / 2;
		int radius = imgHeight / 2 - 1;
		final int dicke = 5 + settings.getStrokewidth();

		// Level malen
		if (settings.isUseTexture()) {
			g2d.setPaint(getTexturePaint());
		} else if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), 0, 0, imgWidth, imgHeight, false));
		} else {
			g2d.setPaint(settings.getActivIconColor(percentage, charge));
		}
		final int w = Math.round(3.6f * percentage);
		Draw2DFunktions.fillCircle(g2d, x, y, radius, 90, w);

		radius = radius - dicke;

		// ineerer rand
		g2d.setPaint(col.darker());
		Draw2DFunktions.fillCircle(g2d, x, y, radius, 0, 360);
		g2d.setPaint(col.brighter());
		Draw2DFunktions.fillCircle(g2d, x, y, radius - 2, 0, 360);

		if (settings.isDrawZeiger()) {
			g2d.setPaint(Color.darkGray.darker());
			Draw2DFunktions.fillCircle(g2d, x, y, radius + dicke + 1, 90 + w - 4, 8);
			g2d.setPaint(settings.getExtraColor1());
			Draw2DFunktions.fillCircle(g2d, x, y, radius + dicke + 1, 90 + w - 3, 6);
		}

		// Inneren Halbkreis clearen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		Draw2DFunktions.fillCircle(g2d, x, y, radius - 3, 0, 360);
		// Normales Paint setzen
		g2d.setPaintMode();

	}

	@Override
	public String toString() {
		return name;
	}
}

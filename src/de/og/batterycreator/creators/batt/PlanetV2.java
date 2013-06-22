package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class PlanetV2 extends AbstractIconCreator {

	protected static String	name	= "Planet.V2";

	public PlanetV2(final RomSettings romSettings) {
		super(romSettings);
		settings.setMoveIconWithText(true);
		settings.setFontYOffset(1);
		settings.setFontXOffset(-1);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		settings.setReduceFontOn100(-2);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(1);
		settings.setResizeChargeSymbolHeight(30);
		settings.setLowBattTheshold(0);
		settings.setMedBattTheshold(40);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(false);
		settings.setStrokewidth(1);
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
	public boolean supportsFlip() {
		return true;
	}

	@Override
	public boolean isNativeXXHDPI() {
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
		int radius = imgHeight / 2 - 2;
		final int dicke = 12;

		// aussen rand
		g2d.setPaint(col.brighter());
		Draw2DFunktions.drawCircle(g2d, x, y, radius, 0, 360);
		Draw2DFunktions.drawCircle(g2d, x, y, radius - 1, 0, 360);

		// Level malen
		if (settings.isUseTexture()) {
			g2d.setPaint(getTexturePaint());
		} else if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), 0, 0, imgWidth, imgHeight, false));
		} else {
			g2d.setPaint(settings.getActivIconColor(percentage, charge));
		}
		final int w = Math.round(3.6f * percentage);
		Draw2DFunktions.fillCircle(g2d, x, y, radius - 3, 90, w);

		// Inneren Halbkreis clearen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		radius = radius - dicke;
		Draw2DFunktions.fillCircle(g2d, x, y, radius, 0, 360);
		// Normales Paint setzen
		g2d.setPaintMode();

		g2d.setPaint(col.brighter());
		Draw2DFunktions.drawCircle(g2d, x, y, radius - 2, 0, 360);
		Draw2DFunktions.drawCircle(g2d, x, y, radius - 3, 0, 360);

	}

	@Override
	public String toString() {
		return name;
	}
}

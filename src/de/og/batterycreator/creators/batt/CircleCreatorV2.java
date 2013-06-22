package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

/**
 * @author Oliver
 * 
 */
public class CircleCreatorV2 extends AbstractIconCreator {

	protected static String	name	= "CircleMod.V2";

	public CircleCreatorV2(final RomSettings romSettings) {
		super(romSettings);
		settings.setStrokewidth(5);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
		settings.setResizeChargeSymbolHeight(33);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(-1);
		settings.setFontXOffset(-1);
		settings.setFontYOffset(1);
		settings.setUseGradiantForMediumColor(true);
	}

	@Override
	public boolean isNativeXXHDPI() {
		return true;
	}

	@Override
	public boolean supportsFlip() {
		return true;
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
	public boolean supportsTexture() {
		return true;
	}

	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {
		final int imgWidth = 64;
		final int imgHeight = 64;
		final int radius = imgHeight / 2 - 3;
		final int dicke = settings.getStrokewidth();
		final int x = imgWidth / 2;
		final int y = imgHeight / 2;

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		// hintergrund malen
		g2d.setColor(settings.getIconColorInActiv());
		Draw2DFunktions.fillCircle(g2d, x, y, radius, 0, 360);
		// Inneren Halbkreis clearen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		Draw2DFunktions.fillCircle(g2d, x, y, radius - dicke, 0, 360);
		// Zurück auf normales paint
		g2d.setPaintMode();

		// Composite COlor setzen
		// g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN,
		// 1f));
		// Level malen
		if (settings.isUseTexture()) {
			g2d.setPaint(getTexturePaint());
		} else if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), 0, 0, imgWidth, imgHeight, false));
		} else {
			final Color col = settings.getActivIconColor(percentage, charge);
			g2d.setPaint(col);
		}

		if (settings.isFlip())
			Draw2DFunktions.fillCircle(g2d, x, x, radius + 2, 90, -Math.round(percentage * (360f / 100f)));
		else
			Draw2DFunktions.fillCircle(g2d, x, y, radius + 2, 90, +Math.round(percentage * (360f / 100f)));

		// Inneren Halbkreis clearen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		Draw2DFunktions.fillCircle(g2d, x, y, radius - dicke - 2, 0, 360);
		// Zurück auf normales paint
		g2d.setPaintMode();

		// % malen
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

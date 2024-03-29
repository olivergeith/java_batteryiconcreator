package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class BoxCreatorV1 extends AbstractIconCreator {

	protected static String	name	= "BoxBattery.V1";

	public BoxCreatorV1(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontXOffset(-1);
		settings.setFontYOffset(1);
		settings.setStrokewidth(6);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
		settings.setUseGradiantForMediumColor(true);

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
	public boolean isNativeXXHDPI() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {
		final int imgWidth = 64;
		final int imgHeight = 64;
		final int w = 2 + settings.getStrokewidth();
		final int x = imgWidth / 2;
		final int y = imgHeight / 2;

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		g2d.setColor(settings.getIconColorInActiv());
		g2d.fillRect(0, 0, imgWidth, imgHeight);

		// Level malen
		if (settings.isUseTexture()) {
			g2d.setPaint(getTexturePaint(settings.getActivIconColor(percentage, charge)));
		} else if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), 0, 0, imgWidth, imgHeight, false));
		} else {
			final Color col = settings.getActivIconColor(percentage, charge);
			g2d.setPaint(col);
		}

		final int winkel = Math.round(percentage * (360f / 100f));

		if (settings.isFlip())
			Draw2DFunktions.fillCircle(g2d, x, x, imgHeight, 135, winkel);
		else
			Draw2DFunktions.fillCircle(g2d, x, y, imgHeight, 135, -winkel);

		// Inneren Halbkreis clearen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		g2d.fillRect(w, w, imgWidth - 2 * w, imgHeight - 2 * w);
		// Zur�ck auf normales paint
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

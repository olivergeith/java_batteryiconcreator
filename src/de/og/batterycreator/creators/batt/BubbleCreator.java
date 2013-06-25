package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class BubbleCreator extends AbstractIconCreator {

	protected static String	name	= "BubbleBattery";

	public BubbleCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontXOffset(-1);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		settings.setFlip(true);
		settings.setResizeChargeSymbolHeight(33);
		settings.setLowBattTheshold(0);
		settings.setUseGradiantForMediumColor(true);
		settings.setBattGradient(true);
		settings.setExtraColor1(Color.white);
		settings.setStrokewidth(5);
		settings.setDrawZeiger(true);
		settings.setBattGradientLevel(5);

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
	public boolean supportsStrokeWidth() {
		return true;
	}

	@Override
	public boolean supportsFlip() {
		return true;
	}

	@Override
	public boolean supportsLinearGradient() {
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
	public boolean supportsZeiger() {
		return true;
	}

	@Override
	public boolean supportsExtraColor1() {
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

		// hintergrund
		if (!settings.isNoBG()) {
			// Hintergrund icon umfärben in Hintergrundfarbe
			if (settings.isBattGradient() || settings.isUseTexture()) {
				g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv(), 0, 0, imgWidth, imgHeight, true));
			} else {
				g2d.setPaint(settings.getIconColorInActiv());
			}
			Draw2DFunktions.fillCircle(g2d, imgWidth / 2, imgHeight / 2, imgHeight / 2 - 1, 0, 360);
		}

		// Composite COlor setzen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));
		// Paint zusammenbasteln
		if (settings.isLinearGradient()) {
			Point2D start = new Point2D.Float(0, imgHeight);
			Point2D end = new Point2D.Float(0, 0);
			if (settings.isFlip()) {
				start = new Point2D.Float(0, 0);
				end = new Point2D.Float(imgWidth, 0);
			}
			final LinearGradientPaint gradientFill = settings.createLinearGradientPaint(start, end);
			g2d.setPaint(gradientFill);
		} else if (settings.isUseTexture()) {
			g2d.setPaint(getTexturePaint(settings.getActivIconColor(percentage, charge)));
		} else if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge).brighter(), 0, 0, imgWidth, imgHeight, false));
		} else {
			final Color col = settings.getActivIconColor(percentage, charge);
			g2d.setPaint(col);
		}

		int strokew = 0;
		if (settings.isFlip()) {
			strokew = settings.getStrokewidth();
		}

		// level malen
		final int h = Math.round((imgHeight - 2 * strokew) / 100f * percentage);
		final int y = imgHeight - h - strokew;
		g2d.fillRect(0, y, imgWidth, h);
		// Zeiger
		if (settings.isDrawZeiger()) {
			g2d.setPaint(Color.darkGray.darker());
			g2d.fillRect(0, y - 2, imgWidth, 4);
			g2d.setPaint(settings.getExtraColor1());
			g2d.fillRect(0, y - 1, imgWidth, 2);
		}

		// Ring malen
		g2d.setPaintMode();
		if (settings.isFlip()) {
			g2d.setColor(settings.getActivIconColor(percentage, charge));
			final int arcx = Math.round(strokew / 2);
			final int arcy = Math.round(strokew / 2);
			final int arcw = imgHeight - strokew;
			final int arch = imgWidth - strokew;
			g2d.drawArc(arcx, arcy, arcw, arch, 0, 360);
		}

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

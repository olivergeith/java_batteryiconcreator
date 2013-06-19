package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class SliderCreator extends AbstractIconCreator {

	protected static String	name		= "SliderBattery";

	final int				imgWidth	= 108;
	final int				imgHeight	= 54;

	public SliderCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontXOffset(0);
		settings.setFontYOffset(-7);
		settings.setIconXOffset(0);
		settings.setIconYOffset(-7);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		settings.setReduceFontOn100(0);
		settings.setResizeChargeSymbolHeight(33);
		settings.setColoredFont(false);
		settings.setStrokewidth(3);
		settings.setLowBattTheshold(0);
		settings.setMedBattTheshold(40);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(false);
		settings.setNoBG(true);
		settings.setExtraColor1(Color.darkGray.brighter());
	}

	@Override
	public boolean supportsNoBg() {
		return true;
	}

	@Override
	public boolean supportsFlip() {
		return true;
	}

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

	@Override
	public boolean supportsExtraColor1() {
		return true;
	}

	private final int	radiusP		= 8;				// Radius Knob
	private final int	offsetx		= 10;
	private final int	offsety		= imgHeight - 15;
	private final int	barHeight	= 6;				// Radius Knob

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

		// Bar
		final int rahmen = settings.getStrokewidth() - 1;
		g2d.setColor(settings.getExtraColor1());
		g2d.fillRect(offsetx - rahmen, offsety - rahmen, imgWidth - 2 * (offsetx - rahmen), barHeight + 2 * rahmen);

		// Inneren Bar clearen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		g2d.fillRect(offsetx, offsety, imgWidth - 2 * (offsetx), barHeight);
		// Zurück auf normales paint
		g2d.setPaintMode();

		if (settings.isBattGradient()) {
			final Color col1 = settings.getIconColorInActiv();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(offsetx, offsety, col2, offsetx, offsety + barHeight, col1);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setPaint(settings.getIconColorInActiv());
			// g2d.setColor(settings.getIconColorInActiv().darker());
		}

		g2d.fillRect(offsetx, offsety, imgWidth - 2 * (offsetx), barHeight);

		// Procente
		final int w = Math.round((imgWidth - 2 * offsetx) / 100f * percentage);
		// Composite COlor setzen
		// g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN,
		// 1f));

		if (settings.isBattGradient()) {
			final Color col1 = settings.getActivIconColor(percentage, charge);
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(offsetx, offsety, col1, offsetx, offsety + barHeight, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setPaint(settings.getActivIconColor(percentage, charge));
		}
		g2d.fillRect(offsetx, offsety, w, barHeight);

		g2d.setPaintMode();
		if (!settings.isNoBG()) {
			drawGlow(g2d, percentage, charge);
		}

		// Planet rund
		if (!settings.isFlip()) {
			g2d.setColor(Color.DARK_GRAY.darker());
			final int x = offsetx + w;
			final int y = offsety + barHeight / 2;
			Draw2DFunktions.fillCircle(g2d, x, y, radiusP + 2, 90, 360);
			if (settings.isBattGradient()) {
				final Color col1 = settings.getActivIconColor(percentage, charge);
				final Color col2 = getBattGardientSecondColor(col1);
				final GradientPaint gradientFill = new GradientPaint(x - radiusP, y - radiusP, col1, x + radiusP, y + radiusP, col2);
				g2d.setPaint(gradientFill);
			} else {
				g2d.setColor(settings.getActivIconColor(percentage, charge));
			}
			Draw2DFunktions.fillCircle(g2d, x, y, radiusP, 90, 360);
		} else {
			// rechteck
			g2d.setColor(Color.DARK_GRAY.darker());
			final int x = offsetx + w - 5;
			final int y = offsety - 5;
			final int width = 10;
			final int heigth = 5 + barHeight + 5;
			g2d.fillRect(x - 2, y - 2, width + 4, heigth + 4);
			if (settings.isBattGradient()) {
				final Color col1 = settings.getActivIconColor(percentage, charge);
				final Color col2 = getBattGardientSecondColor(col1);
				final GradientPaint gradientFill = new GradientPaint(x, y, col1, x + width, y + heigth, col2);
				g2d.setPaint(gradientFill);
			} else {
				g2d.setColor(settings.getActivIconColor(percentage, charge));
			}
			g2d.fillRect(x, y, width, heigth);
		}
		g2d.setPaintMode();
		// %%%%
		drawPercentage(g2d, percentage, charge, img);
		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	/**
	 * Draws a glow behind a charge Symbol or number
	 * 
	 * @param g2d
	 * @param percentage
	 * @param charge
	 * @param img
	 */
	private void drawGlow(final Graphics2D g2d, final int percentage, final boolean charge) {
		final int centertranparenz = 220;

		// getting the Colors right...
		final Color col = settings.getActivIconColor(percentage, charge);
		final Color col2 = new Color(col.getRed(), col.getGreen(), col.getBlue(), centertranparenz);
		final Color col3 = new Color(col.getRed(), col.getGreen(), col.getBlue(), 0);

		// creating paint
		final Point2D center = new Point2D.Float(imgWidth / 2, 0);
		final float radius = imgHeight - 5;
		final float[] dist = {
				0.0f, 1.0f
		};
		final Color[] colors = {
				col2, col3
		};
		final RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);

		// painting
		g2d.setPaint(p);
		Draw2DFunktions.fillCircle(g2d, imgWidth / 2, 0, imgHeight, 0, 360);
	}

	@Override
	public String toString() {
		return name;
	}

}

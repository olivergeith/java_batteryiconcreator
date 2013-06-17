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

public class Zoopa3QuaterCreator extends AbstractIconCreator {

	protected static String	name	= "Zoopa.3Quater";

	public Zoopa3QuaterCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setMoveIconWithText(true);
		settings.setFontXOffset(-1);
		settings.setFontYOffset(9);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
		settings.setReduceFontOn100(-2);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(9);
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
	public boolean supportsStrokeWidth() {
		return true;
	}

	@Override
	public boolean isNativeXXHDPI() {
		return true;
	}

	@Override
	public boolean supportsFlip() {
		return true;
	}

	private final int	imgWidth	= 84;
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
		final int radius = imgWidth / 2 - 1;
		final int einer = percentage % 10;
		final int zehner = percentage / 10;
		final int einerdicke = 6;
		final int zehnerdicke = 9;
		drawSegmente(g2d, charge, percentage, einer, radius, einerdicke, 10);
		drawSegmente(g2d, charge, percentage, zehner, radius - einerdicke - 2, zehnerdicke, 10);

		// Normales Paint setzen
		g2d.setPaintMode();

	}

	private void drawSegmente(final Graphics2D g2d, final boolean charge, final int percentage, final int selectedValue, final int radius, final int dicke,
			final int anzahlSegmente) {
		final int x = imgWidth / 2;
		final int y = x;
		final int segmente = anzahlSegmente;
		final int gap = settings.getStrokewidth(); // in grad
		final float winkelSegment = (230f - (segmente - 1) * gap) / segmente;

		// Skala Hintergergrund einer
		for (int i = 0; i < segmente; i++) {
			if (settings.isNoBG()) {
				if (i <= selectedValue || percentage == 100) {
					setSelectedPaint(g2d, charge, percentage);
				} else {
					setHintergrundPaint(g2d);
				}
			} else {
				if (i < selectedValue || percentage == 100) {
					if (!settings.isFlip())
						setSelectedPaint(g2d, charge, percentage);
					else
						setExtraPaint(g2d);
				} else if (i == selectedValue || percentage == 100) {
					if (settings.isFlip())
						setSelectedPaint(g2d, charge, percentage);
					else
						setExtraPaint(g2d);
				} else {
					setHintergrundPaint(g2d);
				}
			}
			final int startwinkel = Math.round(205f - i * (winkelSegment + gap));
			Draw2DFunktions.fillCircle(g2d, x, y, radius, startwinkel, -(int) winkelSegment);
		}
		// Inneren Halbkreis clearen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		Draw2DFunktions.fillCircle(g2d, x, y, radius - dicke, 0, 360);

		// Normales Paint setzen
		g2d.setPaintMode();
	}

	private void setHintergrundPaint(final Graphics2D g2d) {
		if (!settings.isNoBG() && (settings.isBattGradient() || settings.isUseTexture())) {
			final Color col1 = settings.getIconColorInActiv().brighter();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(0, 0, col1, imgWidth, imgHeight, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setPaint(settings.getIconColorInActiv());
		}
	}

	private void setSelectedPaint(final Graphics2D g2d, final boolean charge, final int percentage) {
		if (settings.isBattGradient()) {
			final Color col1 = settings.getActivIconColor(percentage, charge).brighter();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(0, 0, col2, imgWidth, imgHeight, col1);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setPaint(settings.getActivIconColor(percentage, charge));
		}
	}

	private void setExtraPaint(final Graphics2D g2d) {
		if (settings.isBattGradient()) {
			final Color col1 = settings.getExtraColor1().brighter();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(0, 0, col2, imgWidth, imgHeight, col1);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setPaint(settings.getExtraColor1());
		}
	}

	@Override
	public String toString() {
		return name;
	}
}

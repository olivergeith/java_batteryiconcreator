package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class ZoopaClockWide extends AbstractIconCreator {

	protected static String	name	= "Zoopa.Clock.Wide";

	public ZoopaClockWide(final RomSettings romSettings) {
		super(romSettings);
		settings.setMoveIconWithText(false);
		settings.setFontXOffset(-1);
		settings.setFontYOffset(14);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
		settings.setReduceFontOn100(0);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(14);
		settings.setResizeChargeSymbolHeight(30);
		settings.setLowBattTheshold(0);
		settings.setMedBattTheshold(40);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(true);
		settings.setStrokewidth(2);
		settings.setExtraColor1(Color.white);
		settings.setNoBG(true);
	}

	@Override
	public boolean supportsExtraColor1() {
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

	@Override
	public boolean supportsZeiger() {
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
		int radius = imgHeight - 1;
		if (settings.isDrawZeiger() && !settings.isFlip()) {
			radius = imgHeight - 4;
		}
		final int einer = percentage % 20;
		final int zehner = percentage / 20;
		final int einerdicke = 5;
		final int zehnerdicke = 8;
		if (settings.isFlip()) {
			drawSegmente(g2d, charge, percentage, zehner, radius, zehnerdicke, 5, false);
			drawSegmente(g2d, charge, percentage, einer, radius - zehnerdicke - 1, einerdicke, 20, settings.isDrawZeiger());
		} else {
			drawSegmente(g2d, charge, percentage, einer, radius, einerdicke, 20, settings.isDrawZeiger());
			drawSegmente(g2d, charge, percentage, zehner, radius - einerdicke - 1, zehnerdicke, 5, false);
		}

		// Normales Paint setzen
		g2d.setPaintMode();

	}

	private void drawSegmente(final Graphics2D g2d, final boolean charge, final int percentage, final int selectedValue, final int radius, final int dicke,
			final int anzahlSegmente, final boolean drawZeiger) {
		final int x = imgWidth / 2;
		final int y = imgHeight;
		final int segmente = anzahlSegmente;
		final int gap = settings.getStrokewidth(); // in grad
		final float winkelSegment = (180f - (segmente - 1) * gap) / segmente;

		// Skala Hintergergrund
		for (int i = 0; i < segmente; i++) {
			if (i <= selectedValue || percentage == 100) {
				setSelectedPaint(g2d, charge, percentage);
			} else {
				setHintergrundPaint(g2d);
			}

			if (drawZeiger && i == selectedValue) {
				setExtraPaint(g2d);
			}

			final int startwinkel = Math.round(180f - i * (winkelSegment + gap));
			if (drawZeiger && i == selectedValue) {
				Draw2DFunktions.fillCircle(g2d, x, y, radius + 4, startwinkel, -(int) winkelSegment);
			} else {
				Draw2DFunktions.fillCircle(g2d, x, y, radius, startwinkel, -(int) winkelSegment);
			}
		}
		// Inneren Halbkreis clearen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		Draw2DFunktions.fillCircle(g2d, x, y, radius - dicke, 0, 360);

		// Normales Paint setzen
		g2d.setPaintMode();
	}

	private void setHintergrundPaint(final Graphics2D g2d) {
		if (!settings.isNoBG() && (settings.isBattGradient() || settings.isUseTexture())) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv().brighter(), 0, 0, imgWidth, imgHeight, true));
		} else {
			g2d.setPaint(settings.getIconColorInActiv());
		}
	}

	private void setSelectedPaint(final Graphics2D g2d, final boolean charge, final int percentage) {
		if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge).brighter(), 0, 0, imgWidth, imgHeight, false));
		} else {
			g2d.setPaint(settings.getActivIconColor(percentage, charge));
		}
	}

	private void setExtraPaint(final Graphics2D g2d) {
		g2d.setPaint(settings.getExtraColor1());
	}

	@Override
	public String toString() {
		return name;
	}
}

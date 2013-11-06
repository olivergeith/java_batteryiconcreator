package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class ZoopaGaugeV2 extends AbstractIconCreator {

	protected static String	name	= "Zoopa.Gauge.V2";

	public ZoopaGaugeV2(final RomSettings romSettings) {
		super(romSettings);
		settings.setMoveIconWithText(false);
		settings.setFontXOffset(13);
		settings.setFontYOffset(18);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
		settings.setReduceFontOn100(-3);
		settings.setShowAdditionalFontOnCharge(true);
		settings.setIconXOffset(-18);
		settings.setIconYOffset(-18);
		settings.setResizeChargeSymbolHeight(30);
		settings.setLowBattTheshold(0);
		settings.setMedBattTheshold(40);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(true);
		settings.setStrokewidth(2);
		settings.setExtraColor1(Color.white);
		settings.setFlip(true);
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
		final int radius = imgHeight - 1;
		final int einer = percentage % 10;
		final int zehner = percentage / 10;

		int einerdicke = 0;
		if (settings.isFlip()) {
			einerdicke = 3;
		}
		final int zehnerdicke = 19 - einerdicke;

		if (settings.isFlip()) {
			drawBogen(g2d, charge, percentage, percentage, radius, einerdicke);
			drawSegmente(g2d, charge, percentage, zehner, radius - einerdicke - 2, zehnerdicke, 10);
			drawZeiger(g2d, charge, percentage, zehner, einer, radius - einerdicke - 2, zehnerdicke, 10);
		} else {
			drawSegmente(g2d, charge, percentage, zehner, radius, zehnerdicke, 10);
			drawZeiger(g2d, charge, percentage, zehner, einer, radius, zehnerdicke, 10);
		}
		// Normales Paint setzen
		g2d.setPaintMode();

	}

	private void drawBogen(final Graphics2D g2d, final boolean charge, final int percentage, final int selectedValue, final int radius, final int dicke) {
		final int x = imgWidth;
		final int y = imgHeight;

		final int winkelSegment = 90 * selectedValue / 100;

		// Skala Hintergergrund einer
		setHintergrundPaint(g2d);
		Draw2DFunktions.fillCircle(g2d, x, y, radius, 90, 90);
		setSelectedPaint(g2d, charge, percentage);
		Draw2DFunktions.fillCircle(g2d, x, y, radius, 180, -winkelSegment);
		// Inneren Halbkreis clearen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		Draw2DFunktions.fillCircle(g2d, x, y, radius - dicke, 0, 360);

		// Normales Paint setzen
		g2d.setPaintMode();
	}

	private void drawSegmente(final Graphics2D g2d, final boolean charge, final int percentage, final int selectedValue, final int radius, final int dicke,
			final int anzahlSegmente) {
		final int x = imgWidth;
		final int y = imgHeight;
		final int segmente = anzahlSegmente;
		int gap = settings.getStrokewidth(); // in grad
		if (anzahlSegmente == 100)
			gap = 10;
		final float winkelSegment = (90f - (segmente - 1) * gap) / segmente;

		// Skala Hintergergrund einer
		for (int i = 0; i < segmente; i++) {
			if (i < selectedValue || percentage == 100) {
				setSelectedPaint(g2d, charge, percentage);
			} else {
				setHintergrundPaint(g2d);
			}
			final int startwinkel = Math.round(180f - i * (winkelSegment + gap));
			Draw2DFunktions.fillCircle(g2d, x, y, radius, startwinkel, -(int) winkelSegment);
		}
		// Inneren Halbkreis clearen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		Draw2DFunktions.fillCircle(g2d, x, y, radius - dicke, 0, 360);

		// Normales Paint setzen
		g2d.setPaintMode();
	}

	private void drawZeiger(final Graphics2D g2d, final boolean charge, final int percentage, int zehner, final int einer, final int radius, final int dicke,
			final int anzahlSegmente) {
		final int x = imgHeight;
		final int y = x;
		final int segmente = anzahlSegmente;
		int gap = settings.getStrokewidth(); // in grad
		if (gap > 6)
			gap = 6;
		final float winkelSegment = (90f - (segmente - 1) * gap) / segmente;

		final int radiusDelta = dicke * einer / 10;

		// Skala Hintergergrund einer
		setSelectedPaint(g2d, charge, percentage);

		if (zehner == 10)
			zehner = 9;
		final int startwinkel = Math.round(180f - zehner * (winkelSegment + gap));
		Draw2DFunktions.fillCircle(g2d, x, y, radius - dicke + radiusDelta, startwinkel, -(int) winkelSegment);

		// Inneren Halbkreis clearen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		Draw2DFunktions.fillCircle(g2d, x, y, radius - dicke, 0, 360);

		// Normales Paint setzen
		g2d.setPaintMode();
	}

	private void setHintergrundPaint(final Graphics2D g2d) {
		if (!settings.isNoBG() && (settings.isBattGradient() || settings.isUseTexture())) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv(), 0, 0, imgWidth, imgHeight, true));
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

	@Override
	public String toString() {
		return name;
	}
}

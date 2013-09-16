package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;

public class BarGraphWideV2 extends AbstractIconCreator {

	protected static String	name	= "BarGraph.Wide.V2";

	public BarGraphWideV2(final RomSettings romSettings) {
		super(romSettings);
		settings.setMoveIconWithText(false);
		settings.setFontXOffset(-1);
		settings.setFontYOffset(16);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
		settings.setReduceFontOn100(-2);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(12);
		settings.setResizeChargeSymbolHeight(30);
		settings.setLowBattTheshold(0);
		settings.setMedBattTheshold(40);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(true);
		settings.setStrokewidth(2);
		settings.setExtraColor1(Color.white);
	}

	@Override
	public boolean supportsExtraColor1() {
		return true;
	}

	@Override
	public boolean supportsExtraColor2() {
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
		final int offsetOben = 1;
		final int einer = percentage % 10;
		final int zehner = percentage / 10;
		final int einerdicke = 14;
		final int zehnerdicke = 38;
		drawSegmente(g2d, charge, percentage, einer, offsetOben, einerdicke, 10);
		drawSegmente(g2d, charge, percentage, zehner, offsetOben + einerdicke + 2, zehnerdicke, 10);
		clearRoundArea(g2d, imgWidth / 2 - 1, -Math.round(imgHeight * 13 / 4), (int) Math.round(imgHeight * 13.75 / 4));
		clearRoundArea(g2d, imgWidth / 2 - 1, imgHeight * 2, (int) Math.round(imgHeight * 1.5));
		if (settings.isDrawZeiger()) {
			drawZeiger(g2d, charge, percentage, zehner, offsetOben + einerdicke + 2, zehnerdicke, 10);
			clearRoundArea(g2d, imgWidth / 2 - 1, imgHeight * 2, (int) Math.round(imgHeight * 1.4));
		}
		// Normales Paint setzen
		g2d.setPaintMode();

	}

	private void clearRoundArea(final Graphics2D g2d, final int x, final int y, final int radius) {
		// Inneren Halbkreis clearen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 1f));
		Draw2DFunktions.fillCircle(g2d, x, y, radius, 0, 360);

		// Normales Paint setzen
		g2d.setPaintMode();

	}

	private void drawSegmente(final Graphics2D g2d, final boolean charge, final int percentage, final int selectedValue, final int offsetOben, final int dicke,
			final int anzahlSegmente) {
		final int segmente = anzahlSegmente;
		final int gap = settings.getStrokewidth(); // in grad

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

			final int stepWidth = Math.round((imgWidth - 4 - (anzahlSegmente - 1) * gap) / anzahlSegmente);
			final int x = 2 + i * (stepWidth + gap);
			g2d.fillRect(x, offsetOben, stepWidth, dicke);
		}

		// Normales Paint setzen
		g2d.setPaintMode();
	}

	private void drawZeiger(final Graphics2D g2d, final boolean charge, final int percentage, int selectedValue, final int offsetOben, final int dicke,
			final int anzahlSegmente) {

		final int gap = settings.getStrokewidth(); // in grad
		final int stepWidth = Math.round((imgWidth - 4 - (anzahlSegmente - 1) * gap) / anzahlSegmente);
		final int maxWidth = 10 * stepWidth + 9 * gap;
		final int xpos = 2 + Math.round(maxWidth * percentage / 100);
		// rahmen
		g2d.setPaint(settings.getIconColorInActiv().darker());
		g2d.fillRect(xpos - 2, offsetOben, 5, dicke);

		// Skala Hintergergrund einer

		if (settings.isFlip())
			setSelectedPaint(g2d, charge, percentage);
		else
			setExtraPaint2(g2d);

		if (selectedValue == 10)
			selectedValue = 9;

		g2d.fillRect(xpos - 1, offsetOben, 3, dicke);

		// Normales Paint setzen
		g2d.setPaintMode();
	}

	private void setHintergrundPaint(final Graphics2D g2d) {
		if (!settings.isNoBG() && (settings.isBattGradient() || settings.isUseTexture())) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv(), 0, 0, imgWidth, 0, true));
		} else {
			g2d.setPaint(settings.getIconColorInActiv());
		}
	}

	private void setSelectedPaint(final Graphics2D g2d, final boolean charge, final int percentage) {
		if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge).brighter(), 0, 0, imgWidth, 0, false));
		} else {
			g2d.setPaint(settings.getActivIconColor(percentage, charge));
		}
	}

	private void setExtraPaint(final Graphics2D g2d) {
		if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getExtraColor1().brighter(), 0, 0, imgWidth, 0, false));
		} else {
			g2d.setPaint(settings.getExtraColor1());
		}
	}

	private void setExtraPaint2(final Graphics2D g2d) {
		if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getExtraColor2().brighter(), 0, 0, imgWidth, 0, false));
		} else {
			g2d.setPaint(settings.getExtraColor2());
		}
	}

	@Override
	public String toString() {
		return name;
	}
}

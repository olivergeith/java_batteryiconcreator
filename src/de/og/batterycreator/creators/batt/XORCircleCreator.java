package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.gui.widgets.iconselector.xorcircleselector.XorCircleSelector;

public class XORCircleCreator extends AbstractIconCreator {

	public static final ImageIcon	myIcon	= XorCircleSelector.icon01;	;
	protected static String			name	= "XorCircleBattery";

	public XORCircleCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(-1);
		settings.setFontXOffset(-1);
		settings.setFontYOffset(1);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
		settings.setResizeChargeSymbolHeight(33);
		settings.setLowBattTheshold(0);
		settings.setUseGradiantForMediumColor(true);
	}

	@Override
	public boolean supportsTexture() {
		return true;
	}

	@Override
	public boolean isNativeXXHDPI() {
		return true;
	}

	@Override
	public boolean supportsXOrIcon() {
		return true;
	}

	@Override
	public boolean supportsFlip() {
		return true;
	}

	protected ImageIcon getXORIcon() {
		return myIcon;
	}

	@Override
	public boolean supportsBattGradient() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {
		ImageIcon xorIcon = settings.getXorIcon();
		if (xorIcon == null) {
			xorIcon = myIcon;
		}
		final int imgWidth = xorIcon.getIconWidth();
		final int imgHeight = xorIcon.getIconHeight();
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		// Hintergrund icon malen
		g2d.drawImage(xorIcon.getImage(), 0, 0, null);

		// Composite COlor setzen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));

		// Hintergrund icon umfärben in Hintergrundfarbe
		if (settings.isBattGradient() || settings.isUseTexture()) {
			final Color col1 = settings.getIconColorInActiv();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(0, 0, col1, imgWidth, imgHeight, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setPaint(settings.getIconColorInActiv());
		}
		g2d.fillRect(0, 0, imgWidth, imgHeight);

		// Level malen
		if (settings.isUseTexture()) {
			final TexturePaint slatetp = new TexturePaint(StaticImageHelper.convertImageIcon(settings.getTextureIcon()), new Rectangle(0, 0, 64, 64));
			g2d.setPaint(slatetp);
		} else if (settings.isBattGradient()) {
			final Color col1 = settings.getActivIconColor(percentage, charge);
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(0, 0, col2, imgWidth, imgHeight, col1);
			g2d.setPaint(gradientFill);
		} else {
			final Color col = settings.getActivIconColor(percentage, charge);
			g2d.setPaint(col);
		}
		if (settings.isFlip())
			Draw2DFunktions.fillCircle(g2d, imgWidth / 2, imgHeight / 2, imgWidth, 90, -Math.round(percentage * (360f / 100f)));
		else
			Draw2DFunktions.fillCircle(g2d, imgWidth / 2, imgHeight / 2, imgWidth, 90, +Math.round(percentage * (360f / 100f)));

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

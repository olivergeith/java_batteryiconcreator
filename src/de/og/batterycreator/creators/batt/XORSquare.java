package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.BattSettings;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.gui.widgets.iconselector.xorsquareselector.XorSquareSelector;

public class XORSquare extends AbstractIconCreator {

	public static final ImageIcon	myIcon	= XorSquareSelector.icon01;
	protected static String			name	= "XorBattery.Square";

	public XORSquare(final RomSettings romSettings) {
		super(romSettings);
		settings.setIconXOffset(-1);
		settings.setIconYOffset(-1);
		settings.setFontXOffset(-1);
		settings.setFontYOffset(1);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
		settings.setResizeChargeSymbolHeight(33);
		settings.setLowBattTheshold(0);
		settings.setUseGradiantForMediumColor(true);
		settings.setBattGradient(true);
	}

	@Override
	public boolean isNativeXXHDPI() {
		return true;
	}

	@Override
	public boolean supportsXOrSquareIcon() {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {
		ImageIcon xorIcon = settings.getXorSquareIcon();
		if (xorIcon == null) {
			xorIcon = myIcon;
		}
		final BufferedImage backgrnd = getBackgroundImage(xorIcon);
		final int imgWidth = backgrnd.getWidth();
		final int imgHeight = backgrnd.getHeight();
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);

		final Graphics2D g2d = initGrafics2D(img);

		// Hintergrund icon malen
		g2d.drawImage(backgrnd, 0, 0, null);

		// Composite COlor setzen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));

		switch (settings.getOverpaintBackgroundMode()) {
			default:
			case BattSettings.BACKGROUND_OVERPAINT_NO:
				break;
			case BattSettings.BACKGROUND_OVERPAINT_FLAT:
				g2d.setPaint(settings.getIconColorInActiv());
				g2d.fillRect(0, 0, imgWidth, imgHeight);
				break;
			case BattSettings.BACKGROUND_OVERPAINT_GRADIENT:
				g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv(), 0, 0, imgWidth, imgHeight, true));
				g2d.fillRect(0, 0, imgWidth, imgHeight);
				break;
		}
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
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), 0, 0, imgWidth, imgHeight, false));
		} else {
			final Color col = settings.getActivIconColor(percentage, charge);
			g2d.setPaint(col);
		}

		if (!settings.isFlip()) {
			int h = Math.round(imgHeight / 100f * percentage);
			if (h < 2)
				h = 2;
			final int y = img.getHeight() - h;
			g2d.fillRect(0, y, imgWidth, h);
		} else {
			int w = Math.round(imgWidth / 100f * percentage);
			if (w < 2)
				w = 2;
			g2d.fillRect(0, 0, w, imgHeight);
		}

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

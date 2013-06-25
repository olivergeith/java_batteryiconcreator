package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.gui.widgets.iconselector.textureselector.TextureSelector;
import de.og.batterycreator.gui.widgets.iconselector.xorsquareselector.XorSquareSelector;

public class XORCenter extends AbstractIconCreator {

	public static final ImageIcon	myIcon		= XorSquareSelector.icon02;
	public static final ImageIcon	myTexture	= TextureSelector.icon02;
	protected static String			name		= "XorBattery.Center";

	public XORCenter(final RomSettings romSettings) {
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
		settings.setFlip(true);
		settings.setNoBG(true);
		settings.setShowFont(false);
		settings.setUseTexture(true);
		settings.setTextureIcon(myTexture);
		settings.setXorSquareIcon(myIcon);
		settings.setDropShadowIcon(true);
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

	@Override
	public boolean supportsBattGradient() {
		return true;
	}

	@Override
	public boolean supportsTexture() {
		return true;
	}

	@Override
	public boolean supportsNoBg() {
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
		final int imgWidth = xorIcon.getIconWidth();
		final int imgHeight = xorIcon.getIconHeight();
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		// Hintergrund icon malen
		g2d.drawImage(xorIcon.getImage(), 0, 0, null);

		// Composite COlor setzen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));

		if (!settings.isNoBG()) {
			// Hintergrund icon umfärben in Hintergrundfarbe
			if (settings.isBattGradient() || settings.isUseTexture()) {
				g2d.setPaint(getSingelColorGradientPaint(settings.getIconColorInActiv(), 0, 0, imgWidth, imgHeight, true));
			} else {
				g2d.setPaint(settings.getIconColorInActiv());
			}
			g2d.fillRect(0, 0, imgWidth, imgHeight);
		}
		// Paint zusammenbasteln
		if (settings.isUseTexture()) {
			g2d.setPaint(getTexturePaint(settings.getActivIconColor(percentage, charge)));
		} else if (settings.isBattGradient()) {
			g2d.setPaint(getSingelColorGradientPaint(settings.getActivIconColor(percentage, charge), 0, 0, imgWidth, imgHeight, false));
		} else {
			final Color col = settings.getActivIconColor(percentage, charge);
			g2d.setPaint(col);
		}

		int durchmesser = Math.round(imgHeight / 100f * percentage);
		if (durchmesser < 2)
			durchmesser = 2;
		final int ecke = img.getHeight() / 2 - durchmesser / 2;
		if (!settings.isFlip())
			g2d.fillRect(ecke, ecke, durchmesser, durchmesser);
		else
			Draw2DFunktions.fillCircle(g2d, imgWidth / 2, imgHeight / 2, durchmesser / 2, 0, 360);

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

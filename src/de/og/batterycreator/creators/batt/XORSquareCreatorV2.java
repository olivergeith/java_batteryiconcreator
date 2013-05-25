package de.og.batterycreator.creators.batt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.gui.widgets.iconselector.xorsquareselector.XorSquareSelector;

public class XORSquareCreatorV2 extends AbstractIconCreator {

	public static final ImageIcon	myIcon	= XorSquareSelector.icon01;
	protected static String			name	= "XorSquareBattery.V2";
	public static final File		t		= new File("./custom/textures/fire1.png");

	public static final ImageIcon	texture	= new ImageIcon(t.getPath());

	public XORSquareCreatorV2(final RomSettings romSettings) {
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

		// Hintergrund icon umf�rben in Hintergrundfarbe
		if (settings.isBattGradient()) {
			final Color col1 = settings.getIconColorInActiv();
			final Color col2 = getBattGardientSecondColor(col1);
			final GradientPaint gradientFill = new GradientPaint(0, 0, col1, imgWidth, imgHeight, col2);
			g2d.setPaint(gradientFill);
		} else {
			g2d.setPaint(settings.getIconColorInActiv());
		}
		g2d.fillRect(0, 0, imgWidth, imgHeight);

		// Level malen
		if (settings.isBattGradient()) {
			if (settings.isLinearGradient() && !charge) {
				final TexturePaint slatetp = new TexturePaint(StaticImageHelper.convertImageIcon(texture), new Rectangle(0, 0, 64, 64));
				g2d.setPaint(slatetp);

				// final LinearGradientPaint gradientFill =
				// settings.createLinearGradientPaint(start, end);
				// g2d.setPaint(gradientFill);
			} else {
				final Color col1 = settings.getActivIconColor(percentage, charge);
				final Color col2 = getBattGardientSecondColor(col1);
				final GradientPaint gradientFill = new GradientPaint(0, 0, col2, imgWidth, 0, col1);
				g2d.setPaint(gradientFill);
			}
		} else {
			final Color col = settings.getActivIconColor(percentage, charge);
			g2d.setPaint(col);
		}

		int h = Math.round(imgHeight / 100f * percentage);
		if (h < 2)
			h = 2;
		final int y = img.getHeight() - h;
		if (!settings.isFlip())
			g2d.fillRect(0, y, imgWidth, h);
		else
			g2d.fillRect(0, 0, h, imgHeight);

		// Zur�ck auf normales paint
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
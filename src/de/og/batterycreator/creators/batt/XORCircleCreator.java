package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class XORCircleCreator extends AbstractIconCreator {

	public static final ImageIcon	myIcon	= new ImageIcon(AbstractIconXORCreator.class.getResource("aokp.png"));
	protected static String			name	= "XorCircleBattery";

	public XORCircleCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontXOffset(-1);
		settings.setFontYOffset(1);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
		settings.setResizeChargeSymbolHeight(35);
		settings.setLowBattTheshold(0);
		settings.setUseGradiantForMediumColor(true);
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

		g2d.setColor(settings.getIconColorInActiv());

		final int w = xorIcon.getIconWidth();
		final int x = img.getWidth() / 2 - w / 2;
		g2d.drawImage(xorIcon.getImage(), x, 0, null);
		final Color col = settings.getActivIconColor(percentage, charge);
		g2d.setXORMode(col);

		if (settings.isFlip())
			g2d.fillArc(0, 0, imgWidth, imgHeight, 90, -Math.round(percentage * (360f / 100f)));
		else
			g2d.fillArc(0, 0, imgWidth, imgHeight, 90, +Math.round(percentage * (360f / 100f)));

		g2d.setPaintMode();

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

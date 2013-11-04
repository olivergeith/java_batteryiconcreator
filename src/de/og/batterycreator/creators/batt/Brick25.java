package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class Brick25 extends AbstractIconCreator {

	public Brick25(final RomSettings romSettings) {
		super(romSettings);
		settings.setIconXOffset(0);
		settings.setIconYOffset(3);
		settings.setFontXOffset(0);
		settings.setFontYOffset(3);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
		settings.setDropShadowFont(true);
		settings.setDropShadowIcon(true);
		settings.setResizeChargeSymbolHeight(32);
	}

	protected static String	name	= "Brick.25";

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {
		final int blocksize = 10;
		final int gap = 3;
		final int imgSize = 2 + 4 * gap + 5 * blocksize;

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		final int hd = percentage / 100;

		for (int j = 0; j <= 24; j++) {
			final int e = j % 5;
			final int z = j / 5;
			final int x = 1 + (e) * (blocksize + gap);
			final int y = imgSize - 1 - (1 + z) * (blocksize) - z * gap;

			final Rectangle rec = new Rectangle(x, y, blocksize, blocksize);
			drawRect(rec, g2d, charge, percentage > j * 4, percentage);
		}
		if (hd == 1) {
			final Rectangle rec = new Rectangle(1, 1, imgSize - 2, imgSize - 2);
			drawRect(rec, g2d, charge, true, percentage);
		}

		// Schrift
		drawPercentage(g2d, percentage, charge, img);
		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	private void drawRect(final Rectangle rect, final Graphics2D g2d, final boolean charge, final boolean activ, final int percentage) {
		Color col = settings.getIconColorInActiv();

		if (activ) {
			col = settings.getActivIconColor(percentage, charge);
		} else {
			// if (charge)
			// col = Color.green.darker();
		}
		g2d.setColor(col);
		g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
	}

	@Override
	public String toString() {
		return name;
	}

}

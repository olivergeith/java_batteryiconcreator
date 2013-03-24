package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class BrickDecimal2Creator extends AbstractIconCreator {

	public BrickDecimal2Creator(final RomSettings romSettings) {
		super(romSettings);
	}

	protected static String name = "DecimalBrickBattery2";

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(41, 41, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		final int e = percentage % 10;
		final int z = percentage / 10;
		final int hd = percentage / 100;

		final int h = 4;

		for (int einer = 0; einer < 10; einer++) {
			final int y = 41 - (einer * 4 + 4);
			final int x = 22;
			final int w = 19;
			final Rectangle rec = new Rectangle(x, y, w, h);
			drawRect(rec, g2d, charge, e > einer, percentage);
		}
		for (int zehner = 0; zehner < 10; zehner++) {
			final int y = 41 - (zehner * 4 + 4);
			final int x = 1;
			final int w = 19;
			final Rectangle rec = new Rectangle(x, y, w, h);
			drawRect(rec, g2d, charge, z > zehner, percentage);
		}
		if (hd == 1) {
			final Rectangle rec = new Rectangle(1, 1, 40, 40);
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

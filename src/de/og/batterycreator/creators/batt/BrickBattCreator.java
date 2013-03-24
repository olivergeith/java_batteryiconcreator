package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class BrickBattCreator extends AbstractIconCreator {

	public BrickBattCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	protected static String name = "BrickBattery";

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

		for (int j = 0; j <= 100; j++) {
			final int h = 3;
			final int w = 3;
			final int x = (j % 10) * 4 + 1;
			final int y = 37 - (j / 10) * 4;

			final Rectangle rec = new Rectangle(x, y, w, h);
			drawRect(rec, g2d, charge, percentage > j, percentage);
		}
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

package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.BattSettings;
import de.og.batterycreator.cfg.RomSettings;

public class ArcGradientCreator extends AbstractIconCreator {

	public ArcGradientCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setIconColorMedBatt(BattSettings.COLOR_AOKP_BLUE);
		settings.setMedBattTheshold(40);
		settings.setLowBattTheshold(0);
		settings.setUseGradiantForMediumColor(true);
		settings.setUseGradiantForNormalColor(true);
	}

	protected static String name = "ArcGradientBattery";

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

		for (int j = 100; j >= 0; j = j - 1) {
			drawRect(j, g2d, charge, percentage > j, percentage);
		}
		drawPercentage(g2d, percentage, charge, img);

		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

	private void drawRect(final int winkel, final Graphics2D g2d, final boolean charge, final boolean activ, final int percentage) {
		Color col = settings.getIconColorInActiv();
		if (activ) {
			col = settings.getActivIconColor(winkel, charge);
		} else {
			// if (charge)
			// col = Color.green.darker();
		}
		g2d.setColor(col);
		final int w = Math.round(175 - (3.6f * winkel));
		final int r = 18;
		final int x = (int) Math.round(r * Math.sin(w * Math.PI / 180));
		final int y = (int) Math.round(r * Math.cos(w * Math.PI / 180));

		g2d.fillArc(18 + x, 18 + y, 5, 5, 0, 360);
	}

	@Override
	public String toString() {
		return name;
	}
}

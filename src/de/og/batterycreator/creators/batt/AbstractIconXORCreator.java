package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public abstract class AbstractIconXORCreator extends AbstractIconCreator {

	public AbstractIconXORCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setFontXOffset(-1);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
	}

	protected abstract ImageIcon getXORIcon();

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.og.creators.AbstractCreator#createImage(int, boolean)
	 */
	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		g2d.setColor(settings.getIconColorInActiv());
		final ImageIcon xorIcon = getXORIcon();

		final int w = xorIcon.getIconWidth();
		final int x = img.getWidth() / 2 - w / 2;
		g2d.drawImage(xorIcon.getImage(), x, 0, null);
		final Color col = settings.getActivIconColor(percentage, charge);
		g2d.setXORMode(col);
		final int h = Math.round(40f / 100f * percentage);
		final int y = img.getHeight() - h;
		g2d.fillRect(0, y, 40, h);

		g2d.setPaintMode();

		drawPercentage(g2d, percentage, charge, img);

		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);
	}

}

package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.og.batterycreator.cfg.RomSettings;

public class BinaryBarsCreator extends AbstractIconCreator {

	private static final Logger LOGGER = LoggerFactory.getLogger(BinaryBarsCreator.class);

	public BinaryBarsCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	protected static String name = "BinaryBarBattery";

	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {

		BufferedImage img = new BufferedImage(41, 41, BufferedImage.TYPE_INT_ARGB);
		// Create a graphics contents on the buffered image
		final Graphics2D g2d = initGrafics2D(img);

		// final JLabel panel = new JLabel(new ImageIcon(img));
		// panel.setText("Icon");
		// getContentPane().add(panel);
		final Rectangle rec64 = new Rectangle(0, 0, 5, 41);
		final Rectangle rec32 = new Rectangle(6, 5, 5, 41);
		final Rectangle rec16 = new Rectangle(12, 10, 5, 41);
		final Rectangle rec8 = new Rectangle(18, 15, 5, 41);
		final Rectangle rec4 = new Rectangle(24, 20, 5, 41);
		final Rectangle rec2 = new Rectangle(30, 25, 5, 41);
		final Rectangle rec1 = new Rectangle(36, 30, 5, 41);

		String binary = Integer.toBinaryString(percentage);
		while (binary.length() < 7)
			binary = "0" + binary;
		LOGGER.debug("Binary Code: " + binary);

		final char c01 = binary.charAt(6);
		final char c02 = binary.charAt(5);
		final char c04 = binary.charAt(4);
		final char c08 = binary.charAt(3);
		final char c16 = binary.charAt(2);
		final char c32 = binary.charAt(1);
		final char c64 = binary.charAt(0);

		// Draw graphics
		drawRect(rec64, g2d, charge, c64 == '1');
		drawRect(rec32, g2d, charge, c32 == '1');
		drawRect(rec16, g2d, charge, c16 == '1');
		drawRect(rec8, g2d, charge, c08 == '1');
		drawRect(rec4, g2d, charge, c04 == '1');
		drawRect(rec2, g2d, charge, c02 == '1');
		drawRect(rec1, g2d, charge, c01 == '1');

		// Schrift
		drawPercentage(g2d, percentage, charge, img);

		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);

	}

	private void drawRect(final Rectangle rect, final Graphics2D g2d, final boolean charge, final boolean activ) {
		Color col = settings.getIconColorInActiv();
		if (activ) {
			if (charge)
				col = settings.getIconChargeColor();
			else
				col = settings.getIconColor();
		}
		g2d.setColor(col);
		g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
	}

	@Override
	public String toString() {
		return name;
	}

}

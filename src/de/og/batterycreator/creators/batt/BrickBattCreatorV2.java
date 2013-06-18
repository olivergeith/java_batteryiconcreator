package de.og.batterycreator.creators.batt;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class BrickBattCreatorV2 extends AbstractIconCreator {

	public BrickBattCreatorV2(final RomSettings romSettings) {
		super(romSettings);
		settings.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		settings.setStrokewidth(2);

	}

	@Override
	public boolean isNativeXXHDPI() {
		return true;
	}

	@Override
	public boolean supportsBattGradient() {
		return true;
	}

	@Override
	public boolean supportsStrokeWidth() {
		return true;
	}

	protected static String	name	= "BrickBattery.V2";
	private int				imgSize	= 0;

	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {
		final int blockSize = 1 + settings.getStrokewidth();
		final int gap = 1;
		imgSize = 10 * blockSize + 2 + 9 * gap;

		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);

		for (int j = 0; j <= 100; j++) {
			final int zehner = j / 10;
			final int einer = j % 10;

			final int x = 1 + (einer) * (blockSize + gap);
			final int y = imgSize - 1 - blockSize - zehner * (blockSize + gap);

			final Rectangle rec = new Rectangle(x, y, blockSize, blockSize);
			drawRect(rec, g2d, charge, percentage > j, percentage);
		}
		drawPercentage(g2d, percentage, charge, img);
		// Filewriting
		img = writeFile(percentage, charge, img);
		return new ImageIcon(img);

	}

	private void drawRect(final Rectangle rect, final Graphics2D g2d, final boolean charge, final boolean activ, final int percentage) {
		if (activ) {
			if (settings.isBattGradient()) {
				final Color col1 = settings.getActivIconColor(percentage, charge).brighter();
				final Color col2 = getBattGardientSecondColor(col1);
				final GradientPaint gradientFill = new GradientPaint(0, 0, col2, imgSize, imgSize, col1);
				g2d.setPaint(gradientFill);
			} else {
				g2d.setPaint(settings.getActivIconColor(percentage, charge));
			}
		} else {
			final Color col = settings.getIconColorInActiv();
			g2d.setColor(col);
		}
		g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
	}

	@Override
	public String toString() {
		return name;
	}

}

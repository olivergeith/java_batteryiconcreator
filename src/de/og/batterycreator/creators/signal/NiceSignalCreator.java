package de.og.batterycreator.creators.signal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;

public class NiceSignalCreator extends AbstractSignalCreator {

	public static String		name		= "NiceSignal";

	private static final int	imgWidth	= 39;
	private static final int	imgHeight	= 36;
	private static int			offsetlinks	= 6;
	private static final int	gap			= 2;
	private final int			breite		= Math.round((imgWidth - offsetlinks - (4 * gap)) / 5f);

	public NiceSignalCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setOutColor(Color.lightGray);
		settings.setInColor(Color.lightGray);
		settings.setColor(new Color(230, 230, 230, 185));
		settings.setColorInActiv(new Color(128, 128, 128, 128));
		//
		// settings.setInColor(Color.white);
		// settings.setOutColor(Color.white);
		// settings.setColor(Color.LIGHT_GRAY);
	}

	private Rectangle calculateRectForLevel(final int level) {

		final int offsetUnten = 1;
		final int offsetOben = 2;

		final int maxHeight = imgHeight - offsetUnten - offsetOben;
		final int stufenhoehe = Math.round(maxHeight / 5f);
		final int hoehe = (level + 1) * stufenhoehe;
		final int y = imgHeight - offsetUnten - hoehe;
		final int x = level * (gap + breite) + offsetlinks;
		final Rectangle rect = new Rectangle(x, y, breite, hoehe);
		return rect;
	}

	@Override
	public ImageIcon createImage(final int level, final boolean fully) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img);
		g2d.setStroke(new BasicStroke(2f));

		if (level == NULL_LEVEL) {
			for (int i = 0; i < 5; i++) {
				g2d.setColor(getSettings().getColorInActiv());
				final Rectangle rect = calculateRectForLevel(i);
				g2d.fillRect(rect.x - gap, rect.y, rect.width + gap, rect.height);
			}
		} else {
			for (int i = 0; i < 5; i++) {
				g2d.setColor(getConnectColor(fully));
				final Rectangle rect = calculateRectForLevel(i);
				if (i <= level)
					g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
				else {
					g2d.setColor(getSettings().getColorInActiv());
					g2d.fillRect(rect.x, rect.y, rect.width, rect.height);
				}
			}
		}

		// Clearing diagonal to get an triangular shape
		g2d.setBackground(new Color(255, 255, 255, 0));
		for (int i = 0; i <= imgHeight + 2; i++) {
			g2d.clearRect(-4 + i, imgHeight - 4 - i, 6, 7);
		}

		// Filewriting
		img = writeFile(getFileName(level, fully), img);
		return new ImageIcon(img);
	}

	@Override
	public ImageIcon createInOutImage(final boolean in, final boolean out) {
		// Create a graphics contents on the buffered image
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGrafics2D(img, true);

		final int pw = 8;
		final int ph = 10;
		final int px = imgWidth - pw - 3;
		final int py = imgHeight / 2;
		final Polygon poliin = createUpDownTriangle(px, py + 2, pw, ph); // oben
		final Polygon poliou = createUpDownTriangle(px, py - 2, pw, -ph); // unten

		if (in) {
			g2d.setColor(getSettings().getInColor());
			g2d.fillPolygon(poliin);
		}
		if (out) {
			g2d.setColor(getSettings().getOutColor());
			g2d.fillPolygon(poliou);
		}

		// Filewriting
		img = writeFile(getFileNameInOut(in, out), img);
		return new ImageIcon(img);
	}

	@Override
	public String toString() {
		return name;
	}

}

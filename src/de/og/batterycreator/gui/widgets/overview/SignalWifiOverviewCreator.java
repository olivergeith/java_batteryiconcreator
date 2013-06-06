package de.og.batterycreator.gui.widgets.overview;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.GlobalSettings;

public class SignalWifiOverviewCreator extends OverviewCreator {

	public SignalWifiOverviewCreator() {
	}

	public static BufferedImage createOverview(final List<ImageIcon> iconMap, final String name) {
		if (iconMap != null && iconMap.size() > 0) {

			// Iconblock besorgen
			BufferedImage iconBlock;
			switch (GlobalSettings.INSTANCE.getSignalWifiOverViewStyle()) {
				default:
				case 0:
					iconBlock = StaticIconGridCreator.createOneLineGrid(iconMap, true);
					break;
				case 1:
					iconBlock = StaticIconGridCreator.createOneLineGrid(iconMap, false);
					break;
			}

			// Grössen berechnen
			final int iw = iconBlock.getWidth();
			final int ih = iconBlock.getHeight();
			final int offsetX = 15;
			final int offsetOben = 40;
			final int offsetUnten = 30;

			int w = iw + 2 * offsetX;
			final int h = ih + offsetOben + offsetUnten;

			if (w < 350)
				w = 350;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			BufferedImage backGrnd;
			switch (GlobalSettings.INSTANCE.getSignalWifiBackgroundStyle()) {
				default:
				case 0:
					backGrnd = StaticBackgroundCreator.drawBackground01(w, h, offsetOben - 10, offsetUnten);
					break;
				case 1:
					backGrnd = StaticBackgroundCreator.drawBackgroundTV(w, h, false);
					break;
				case 2:
					backGrnd = StaticBackgroundCreator.drawBackgroundTV(w, h, true);
					break;
				case 3:
					backGrnd = StaticBackgroundCreator.drawBackgroundFrame(w, h);
					break;
				case 4:
					backGrnd = StaticBackgroundCreator.drawBackgroundGlassFrame(w, h);
					break;
				case 5:
					backGrnd = StaticBackgroundCreator.drawBackgroundOldStyle(w, h, offsetOben - 5, offsetUnten);
					break;
			}
			g2d.drawImage(backGrnd, 0, 0, null);

			// Name Text
			drawCenteredName(g2d, name, w);
			drawCenteredBanner(g2d, w, h);
			g2d.drawImage(iconBlock, w / 2 - iw / 2, offsetOben, null);
			g2d.dispose();
			return over;
		}
		return null;
	}

	private static void drawCenteredBanner(final Graphics2D g2d, final int w, final int h) {
		g2d.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
		final FontMetrics metrix = g2d.getFontMetrics();
		final Rectangle2D strRect = metrix.getStringBounds(banner, g2d);
		final int strxpos = (int) (Math.round(w / 2) - Math.round(strRect.getWidth() / 2));
		g2d.setColor(Color.white);
		g2d.drawString(banner, strxpos, h - 17);
	}

	private static void drawCenteredName(final Graphics2D g2d, final String name, final int w) {
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		final FontMetrics metrix = g2d.getFontMetrics();
		final Rectangle2D strRect = metrix.getStringBounds(name, g2d);
		final int strxpos = (int) (Math.round(w / 2) - Math.round(strRect.getWidth() / 2));
		g2d.setColor(Color.white);
		g2d.drawString(name, strxpos, 25);
	}

	// #########################################################################
	// IconBlocks
	// #########################################################################

	// // // Logo basteln
	// // BufferedImage logo =
	// // StaticImageHelper.convertImageIcon(IconStore.logoIcon);
	// // logo = StaticImageHelper.resizeAdvanced2Height(logo,
	// // Math.round(ih * 1.5f));
	// // final BufferedImage logoflip =
	// // StaticImageHelper.createReflectionImage(logo, true);

}

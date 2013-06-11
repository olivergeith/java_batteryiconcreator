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
import de.og.batterycreator.main.IconCreatorFrame;

public class BatteryOverviewCreator extends OverviewCreator {

	public BatteryOverviewCreator() {
	}

	public static BufferedImage createOverview(final List<ImageIcon> iconMap, final String name) {
		switch (GlobalSettings.INSTANCE.getBigBackgroundStyle()) {
			default:
			case 0:
				return createOverviewOldstyle(iconMap, name);
			case 1:
				return createOverviewNewStyle(iconMap, name);
		}
	}

	public static BufferedImage createOverviewOldstyle(final List<ImageIcon> iconMap, final String name) {
		if (iconMap != null && iconMap.size() > 100) {

			final BufferedImage iconBlock = StaticIconGridCreator.createBatteryIconBlockWithCharge(iconMap);
			final int iw = iconBlock.getWidth();
			final int ih = iconBlock.getHeight();
			final int w = iw;
			final int offsetOben = 50;
			final int offsetUnten = 35;
			final int h = ih + offsetOben + offsetUnten;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, w, h);
			g2d.setColor(Color.white);
			g2d.drawString(name, 2, 20);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
			g2d.setColor(Color.gray);
			g2d.drawString(banner, 2, 32);
			g2d.drawString(IconCreatorFrame.HOMEPAGE_URL, 2, h - offsetUnten + 20);
			g2d.setColor(Color.white);
			g2d.fillRect(0, 40, w, 2);
			g2d.fillRect(0, h - offsetUnten, w, 2);
			g2d.fillRect(0, h - 2, w, 2);

			g2d.drawImage(iconBlock, 1, offsetOben, null);
			g2d.dispose();
			return over;
		}
		return null;
	}

	public static BufferedImage createOverviewNewStyle(final List<ImageIcon> iconMap, final String name) {
		if (iconMap != null && iconMap.size() > 0) {

			final BufferedImage iconBlock = StaticIconGridCreator.createBatteryIconBlockWithCharge(iconMap);
			final int iw = iconBlock.getWidth();
			final int ih = iconBlock.getHeight();
			final int offsetOben = 50;
			final int offsetUnten = 30;
			final int offsetLinks = 20;

			final int w = iw + 2 * offsetLinks;
			final int h = ih + offsetOben + offsetUnten;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Background
			final BufferedImage backGrnd = StaticBackgroundCreator.drawBackgroundBigTV(w, h, offsetOben);
			g2d.drawImage(backGrnd, 0, 0, null);

			// Text
			g2d.setColor(Color.white);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
			g2d.drawString(name, 10, 22);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
			g2d.drawString(banner, 10, 35);
			g2d.drawString(IconCreatorFrame.HOMEPAGE_URL, 10, 45);

			// iconblock malen
			g2d.drawImage(iconBlock, offsetLinks, offsetOben + 5, null);
			g2d.dispose();
			return over;
		}
		return null;
	}

	public static BufferedImage createSmallBatteryOverview(final List<ImageIcon> iconMap, final String name) {
		if (iconMap != null && iconMap.size() > 100) {

			// Iconblock besorgen
			BufferedImage iconBlock;
			switch (GlobalSettings.INSTANCE.getSmallOverViewStyle()) {
				default:
				case 0:
					iconBlock = StaticIconGridCreator.createBatteryOneLineGrid(iconMap, true);
					break;
				case 1:
					iconBlock = StaticIconGridCreator.createBatteryOneLineGrid(iconMap, false);
					break;
				case 2:
					iconBlock = StaticIconGridCreator.createBatteryTwoLineGrid(iconMap);
					break;
				case 3:
					iconBlock = StaticIconGridCreator.createBigGrid(iconMap, 101);
					break;
				case 4:
					iconBlock = StaticIconGridCreator.createBatteryIconBlockWithCharge(iconMap);
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
			switch (GlobalSettings.INSTANCE.getSmallBackgroundStyle()) {
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
					backGrnd = StaticBackgroundCreator.drawBackgroundFrame2(w, h);
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

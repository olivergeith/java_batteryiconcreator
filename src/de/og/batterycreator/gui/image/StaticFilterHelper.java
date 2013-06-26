package de.og.batterycreator.gui.image;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import og.basics.gui.image.StaticImageHelper;
import com.jhlabs.image.GrayscaleFilter;
import com.jhlabs.image.HSBAdjustFilter;
import com.jhlabs.image.TritoneFilter;
import de.og.batterycreator.cfg.BattSettings;

public class StaticFilterHelper {

	public static TexturePaint getHueTexturePaint(final ImageIcon tex, final int hueShift) {
		final BufferedImage imghue = getHueImage(tex, hueShift);
		return new TexturePaint(imghue, new Rectangle(0, 0, tex.getIconWidth(), tex.getIconHeight()));

	}

	public static TexturePaint getHSBTexturePaint(final ImageIcon tex, final int hueShift, final int brightnewwShift, final int saturationShift) {
		final BufferedImage imghue = getHSBImage(tex, hueShift, brightnewwShift, saturationShift);
		return new TexturePaint(imghue, new Rectangle(0, 0, tex.getIconWidth(), tex.getIconHeight()));

	}

	public static BufferedImage getHueImage(final ImageIcon tex, final int hueShift) {
		return getHSBImage(tex, hueShift, 0, 0);
	}

	/**
	 * @param tex
	 * @param hueShift
	 *            1-100
	 * @param brightness
	 *            -100 bis 100
	 * @param saturation
	 *            -100 bis 100
	 * @return
	 */
	public static BufferedImage getHSBImage(final ImageIcon tex, final int hueShift, final int brightness, final int saturation) {
		final HSBAdjustFilter huefilter = new HSBAdjustFilter();
		huefilter.setHFactor(hueShift / 100f);
		huefilter.setSFactor(saturation / 100f);
		huefilter.setBFactor(brightness / 100f);
		final BufferedImage imghue = huefilter.filter(StaticImageHelper.convertImageIcon(tex), null);
		return imghue;
	}

	public static TexturePaint getColorizedTexturePaint(final ImageIcon tex, final Color col) {
		final BufferedImage img = getColorizedImage(tex, col);
		return new TexturePaint(img, new Rectangle(0, 0, tex.getIconWidth(), tex.getIconHeight()));
	}

	public static BufferedImage getColorizedImage(final ImageIcon tex, final Color col) {
		final TritoneFilter filter = new TritoneFilter();
		filter.setMidColor(col.getRGB());
		final BufferedImage img = filter.filter(StaticImageHelper.convertImageIcon(tex), null);
		return img;
	}

	public static TexturePaint getGrayScaleTexturePaint(final ImageIcon tex, final int brightness) {
		final BufferedImage img = getGrayScaleImage(tex, brightness);
		return new TexturePaint(img, new Rectangle(0, 0, tex.getIconWidth(), tex.getIconHeight()));
	}

	public static BufferedImage getGrayScaleImage(final ImageIcon tex, final int brightness) {
		final GrayscaleFilter filter = new GrayscaleFilter();
		BufferedImage img = filter.filter(StaticImageHelper.convertImageIcon(tex), null);
		final HSBAdjustFilter huefilter = new HSBAdjustFilter();
		huefilter.setBFactor(brightness / 100f);
		img = huefilter.filter(img, null);
		return img;
	}

	public static BufferedImage getColorizedBackground(final ImageIcon tex, final Color col, final int brightness) {
		final BufferedImage img = getColorizedImage(tex, col);
		// final HSBAdjustFilter huefilter = new HSBAdjustFilter();
		// huefilter.setBFactor(brightness / 100f);
		// img = huefilter.filter(img, null);
		return img;
	}

	public static BufferedImage getBackgroundImage(final ImageIcon tex, final ImageIcon xorBack, final int brightness, final int mode) {
		switch (mode) {
			case BattSettings.BACKGROUND_TEXTURE_GRAYSCALE:
				return StaticFilterHelper.getGrayScaleImage(tex, brightness);
			case BattSettings.BACKGROUND_TEXTURE_DESATURATE:
				return StaticFilterHelper.getHSBImage(tex, 0, brightness, -100);
			default:
				return StaticImageHelper.convertImageIcon(xorBack);

		}
	}

}

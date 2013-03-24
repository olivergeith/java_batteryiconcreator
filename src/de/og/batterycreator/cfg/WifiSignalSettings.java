package de.og.batterycreator.cfg;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

public class WifiSignalSettings implements Serializable {
	private static final long serialVersionUID = 4747296256398459127L;

	// Konstanten
	public static final Color COLOR_INACTIV = Color.darkGray.brighter();
	public static final Color COLOR_BGRND = Color.black;
	public static final Color COLOR_FONT = Color.white;
	public static final Color COLOR_CHARGE = Color.green.darker();
	public static final Color COLOR_Med_BATT = Color.orange;
	public static final Color COLOR_LOW_BATT = Color.red;
	public static final Color COLOR_AOKP_BLUE = new Color(39, 135, 173);
	public static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 21);

	// Member
	private Color fontColor = COLOR_FONT;
	private Font font = DEFAULT_FONT;
	private boolean transparentBackground = true;
	private Color backgroundColor = COLOR_BGRND;
	private Color colorInActiv = COLOR_INACTIV;
	private Color inColor = Color.red;
	private Color outColor = Color.green.darker();
	private Color color = Color.white;
	private Color colorFully = COLOR_AOKP_BLUE;
	private int maxLevel = 4;

	public Color getColor() {
		return color;
	}

	public void setColor(final Color color) {
		this.color = color;
	}

	public Color getColorFully() {
		return colorFully;
	}

	public void setColorFully(final Color colorFully) {
		this.colorFully = colorFully;
	}

	public boolean isTransparentBackground() {
		return transparentBackground;
	}

	public void setTransparentBackground(final boolean transparentBackground) {
		this.transparentBackground = transparentBackground;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(final Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getColorInActiv() {
		return colorInActiv;
	}

	public void setColorInActiv(final Color colorInActiv) {
		this.colorInActiv = colorInActiv;
	}

	public Color getInColor() {
		return inColor;
	}

	public void setInColor(final Color inColor) {
		this.inColor = inColor;
	}

	public Color getOutColor() {
		return outColor;
	}

	public void setOutColor(final Color outColor) {
		this.outColor = outColor;
	}

	/**
	 * @return the maxLevel
	 */
	public int getMaxLevel() {
		return maxLevel;
	}

	/**
	 * @param maxLevel
	 *            the maxLevel to set
	 */
	public void setMaxLevel(final int maxLevel) {
		this.maxLevel = maxLevel;
	}

	/**
	 * @return the fontColor
	 */
	public Color getFontColor() {
		return fontColor;
	}

	/**
	 * @param fontColor
	 *            the fontColor to set
	 */
	public void setFontColor(final Color fontColor) {
		this.fontColor = fontColor;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font
	 *            the font to set
	 */
	public void setFont(final Font font) {
		this.font = font;
	}
}

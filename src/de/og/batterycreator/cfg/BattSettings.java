package de.og.batterycreator.cfg;

import java.awt.Color;
import java.awt.Font;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * Everything for Batteries...Colors, Fonts etc...
 * 
 * @author geith
 * 
 */
public class BattSettings implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID						= 6505593229306008760L;

	// Konstanten
	public static final Color	COLOR_INACTIV							= Color.darkGray;
	public static final Color	COLOR_BGRND								= Color.black;
	public static final Color	COLOR_FONT								= Color.white;
	public static final Color	COLOR_CHARGE							= Color.green.darker();
	public static final Color	COLOR_Med_BATT							= Color.orange;
	public static final Color	COLOR_LOW_BATT							= Color.red;
	public static final Color	COLOR_AOKP_BLUE							= new Color(39, 135, 173);
	public static final Font	DEFAULT_FONT							= new Font(Font.SANS_SERIF, Font.BOLD, 21);

	// Font colors
	private Color				fontColor								= COLOR_FONT;
	private Color				fontColorLowBatt						= COLOR_LOW_BATT;
	private Color				fontColorMedBatt						= COLOR_Med_BATT;
	private Color				fontChargeColor							= COLOR_FONT;

	// Dropshadow
	private boolean				dropShadowIcon							= false;
	private boolean				dropShadowFont							= false;
	private Color				dropShadowColor							= COLOR_BGRND;
	private int					dropShadowOffsetX						= 0;
	private int					dropShadowOffsetY						= 0;
	/**
	 * 0-6 Adjusting the filter to be more "black"
	 */
	private int					dropShadowOpacity						= 3;
	/**
	 * 1-6 How often should be blurred ?
	 */
	private int					dropShadowBlurryness					= 3;
	// zeiger
	private boolean				drawZeiger								= false;

	// colors
	private Color				extraColor1								= COLOR_AOKP_BLUE;
	private Color				extraColor2								= COLOR_AOKP_BLUE;
	private Color				iconColorInActiv						= COLOR_INACTIV;
	private Color				iconColor								= COLOR_AOKP_BLUE;
	private Color				iconColorLowBatt						= COLOR_LOW_BATT;
	private Color				iconColorMedBatt						= COLOR_Med_BATT;
	private Color				iconChargeColor							= COLOR_CHARGE;
	private Color				iconChargeGlowColor						= Color.white;
	private Color				backgroundColor							= COLOR_BGRND;
	private boolean				transparentBackground					= true;
	private boolean				useChargeColor							= true;

	// glow
	private boolean				glow									= false;
	private boolean				glowForCharge							= false;
	private int					glowRadius								= 20;
	private int					glowXOffset								= 0;
	private int					glowYOffset								= 0;
	private boolean				moveGlowWithText						= true;
	// pulsing charge glow
	private boolean				chargeGlow								= false;
	private int					chargeGlowRadius						= 20;
	private int					chargeGlowOffsetX						= 0;
	private int					chargeGlowOffsetY						= 0;
	private boolean				showAdditionalFontOnCharge				= false;

	// texture manipulation
	public static final int		TEXTURE_FILTER_NON						= 0;
	public static final int		TEXTURE_FILTER_COLORIZE					= 1;
	public static final int		TEXTURE_FILTER_HUE_SHIFT				= 2;
	private int					textureFilterType						= TEXTURE_FILTER_NON;
	private HSBSettings			textureHSB								= new HSBSettings(0, 0, 0);
	// private int hueShift = 0;
	// private int satuationShift = 0;
	// private int brightnessShift = 0;

	// texture as Background
	public static final int		BACKGROUND_ICON							= 0;
	public static final int		BACKGROUND_TEXTURE_GRAYSCALE			= 1;
	public static final int		BACKGROUND_TEXTURE_DESATURATE			= 2;
	public static final int		BACKGROUND_TEXTURE_GRAYSCALE_INVERTED	= 3;
	private int					backgroundTextureMode					= BACKGROUND_ICON;
	private int					backgroundBrightness					= 0;

	public static final int		BACKGROUND_OVERPAINT_NO					= 0;
	public static final int		BACKGROUND_OVERPAINT_FLAT				= 1;
	public static final int		BACKGROUND_OVERPAINT_GRADIENT			= 2;
	private int					overpaintBackgroundMode					= BACKGROUND_OVERPAINT_NO;

	// font and charge icon offset
	private boolean				moveIconWithText						= true;
	private boolean				moveChargeGlowWithText					= true;
	private int					iconXOffset								= 0;
	private int					iconYOffset								= 0;
	private int					fontXOffset								= 0;
	private int					fontYOffset								= 0;
	// font
	private HSBSettings			chargeHSB								= new HSBSettings(0, 0, 0);

	private Font				font									= DEFAULT_FONT;
	private int					reduceFontOn100							= -3;
	private boolean				showFont								= true;
	private boolean				coloredFont								= false;
	private boolean				coloredIcon								= true;
	private boolean				showChargeSymbol						= true;
	private int					resizeChargeSymbolHeight				= 24;
	private boolean				useGradiantForMediumColor				= false;
	private boolean				useGradiantForNormalColor				= false;
	private boolean				flip									= false;
	private boolean				noBG									= false;
	private boolean				battGradient							= false;
	private int					battGradientLevel						= 2;
	private int					strokewidth								= 3;

	private int					lowBattTheshold							= 10;
	private int					MedBattTheshold							= 30;

	private boolean				addPercent								= false;

	private boolean				linearGradient							= false;
	private boolean				useTexture								= false;

	// this is transient because it should not be serialized
	private transient ImageIcon	chargeIcon								= null;
	private int					chargeIconIndex							= 0;
	// this is transient because it should not be serialized
	private transient ImageIcon	xorIcon									= null;
	private int					xorIconIndex							= 0;
	// this is transient because it should not be serialized
	private transient ImageIcon	xorSquareIcon							= null;
	private int					xorSquareIndex							= 0;
	// this is transient because it should not be serialized
	private transient ImageIcon	textureIcon								= null;
	private int					textureIconIndex						= 0;

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(final Color fontColor) {
		this.fontColor = fontColor;
	}

	public Color getFontColorLowBatt() {
		return fontColorLowBatt;
	}

	public void setFontColorLowBatt(final Color fontColorLowBatt) {
		this.fontColorLowBatt = fontColorLowBatt;
	}

	public Color getFontColorMedBatt() {
		return fontColorMedBatt;
	}

	public void setFontColorMedBatt(final Color fontColorMedBatt) {
		this.fontColorMedBatt = fontColorMedBatt;
	}

	public Color getIconColor() {
		return iconColor;
	}

	public void setIconColor(final Color iconColor) {
		this.iconColor = iconColor;
	}

	public Color getIconColorLowBatt() {
		return iconColorLowBatt;
	}

	public void setIconColorLowBatt(final Color iconColorLowBatt) {
		this.iconColorLowBatt = iconColorLowBatt;
	}

	public Color getIconColorMedBatt() {
		return iconColorMedBatt;
	}

	public void setIconColorMedBatt(final Color iconColorMedBatt) {
		this.iconColorMedBatt = iconColorMedBatt;
	}

	public Color getIconChargeColor() {
		return iconChargeColor;
	}

	public void setIconChargeColor(final Color iconChargeColor) {
		this.iconChargeColor = iconChargeColor;
	}

	public int getLowBattTheshold() {
		return lowBattTheshold;
	}

	public void setLowBattTheshold(final int lowBattTheshold) {
		this.lowBattTheshold = lowBattTheshold;
	}

	public int getMedBattTheshold() {
		return MedBattTheshold;
	}

	public void setMedBattTheshold(final int MedBattTheshold) {
		this.MedBattTheshold = MedBattTheshold;
	}

	public Color getIconColorInActiv() {
		return iconColorInActiv;
	}

	public void setIconColorInActiv(final Color iconColorInActiv) {
		this.iconColorInActiv = iconColorInActiv;
	}

	/**
	 * Returns the Color for a Percentage
	 * 
	 * @param percentage
	 * @return
	 */
	public Color getActivIconColor(final int percentage) {
		Color col;
		if (!isColoredIcon()) {
			col = getIconColor();
		} else {
			// Wenn oberhalb der Schwelle oder immer einfarbig gezeichnet werden
			// soll, dann...
			if (percentage >= getMedBattTheshold()) {
				if (useGradiantForNormalColor)
					col = getRadiantColor(getIconColor(), getIconColorMedBatt(), percentage, 100, getMedBattTheshold());
				else
					col = getIconColor();
			} else if (percentage < getMedBattTheshold() && percentage >= getLowBattTheshold()) {
				if (useGradiantForMediumColor)
					col = getRadiantColor(getIconColorLowBatt(), getIconColorMedBatt(), percentage, getLowBattTheshold(), getMedBattTheshold());
				else
					col = getIconColorMedBatt();
			} else {
				col = getIconColorLowBatt();
			}
		}
		return col;
	}

	/**
	 * Returns the Color for a Percentage
	 * 
	 * @param percentage
	 * @return
	 */
	public Color getActivFontColor(final int percentage) {
		Color col;
		if (!isColoredFont()) {
			col = getFontColor();
		} else {
			if (percentage >= getMedBattTheshold()) {
				if (useGradiantForNormalColor)
					col = getRadiantColor(getFontColor(), getFontColorMedBatt(), percentage, 100, getMedBattTheshold());
				else
					col = getFontColor();
			} else if (percentage < getMedBattTheshold() && percentage >= getLowBattTheshold()) {
				if (useGradiantForMediumColor)
					col = getRadiantColor(getFontColorLowBatt(), getFontColorMedBatt(), percentage, getLowBattTheshold(), getMedBattTheshold());
				else
					col = getFontColorMedBatt();
			} else
				col = getFontColorLowBatt();
		}
		return col;
	}

	private Color getRadiantColor(final Color col1, final Color col2, final int percentage, final int min, final int max) {
		final int diff = min - max;
		final int diffpercent = min - percentage;
		final float factor = Math.abs((float) diffpercent / (float) diff);

		final int diffr = col1.getRed() - col2.getRed();
		final int diffg = col1.getGreen() - col2.getGreen();
		final int diffb = col1.getBlue() - col2.getBlue();

		final int r = Math.round(col1.getRed() - diffr * factor);
		final int g = Math.round(col1.getGreen() - diffg * factor);
		final int b = Math.round(col1.getBlue() - diffb * factor);

		final Color col = new Color(r, g, b);
		return col;
	}

	/**
	 * creates a LinearGradientPaint
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public LinearGradientPaint createLinearGradientPaint(final Point2D start, final Point2D end) {
		// getting the Colors right...
		final Color col1 = getIconColorLowBatt();
		final Color col2 = getIconColorLowBatt();
		final Color col3 = getIconColorMedBatt();
		final Color col4 = getIconColor();

		final float f1 = 0.0f;
		final float f2 = getLowBattTheshold() / 100f + 0.00001f;
		final float f3 = getMedBattTheshold() / 100f;
		final float f4 = 1.0f;

		// creating paint
		final float[] dist = {
				f1, f2, f3, f4
		};
		final Color[] colors = {
				col1, col2, col3, col4
		};
		final LinearGradientPaint p = new LinearGradientPaint(start, end, dist, colors);
		return p;
	}

	/**
	 * Returns the Activ Color for percentage, or Chargecolor
	 * 
	 * @param percentage
	 * @param charge
	 * @return
	 */
	public Color getActivIconColor(final int percentage, final boolean charge) {
		Color col;
		if (charge && useChargeColor)
			col = getIconChargeColor();
		else
			col = getActivIconColor(percentage);
		return col;
	}

	/**
	 * Returns the Activ Color for percentage, or Chargecolor
	 * 
	 * @param percentage
	 * @param charge
	 * @return
	 */
	public Color getActivFontColor(final int percentage, final boolean charge) {
		Color col;
		if (charge && useChargeColor)
			col = getFontChargeColor();
		else
			col = getActivFontColor(percentage);
		return col;
	}

	/**
	 * @return the useColoredFont
	 */
	public boolean isColoredFont() {
		return coloredFont;
	}

	/**
	 * @param useColoredFont
	 *            the useColoredFont to set
	 */
	public void setColoredFont(final boolean useColoredFont) {
		coloredFont = useColoredFont;
	}

	/**
	 * @return the coloredIcon
	 */
	public boolean isColoredIcon() {
		return coloredIcon;
	}

	/**
	 * @param coloredIcon
	 *            the coloredIcon to set
	 */
	public void setColoredIcon(final boolean coloredIcon) {
		this.coloredIcon = coloredIcon;
	}

	/**
	 * @return the showFont
	 */
	public boolean isShowFont() {
		return showFont;
	}

	/**
	 * @param showFont
	 *            the showFont to set
	 */
	public void setShowFont(final boolean showFont) {
		this.showFont = showFont;
	}

	/**
	 * @return the showChargeSymbol
	 */
	public boolean isShowChargeSymbol() {
		return showChargeSymbol;
	}

	/**
	 * @param showChargeSymbol
	 *            the showChargeSymbol to set
	 */
	public void setShowChargeSymbol(final boolean showChargeSymbol) {
		this.showChargeSymbol = showChargeSymbol;
	}

	/**
	 * @return the fontChargeColor
	 */
	public Color getFontChargeColor() {
		return fontChargeColor;
	}

	/**
	 * @param fontChargeColor
	 *            the fontChargeColor to set
	 */
	public void setFontChargeColor(final Color fontChargeColor) {
		this.fontChargeColor = fontChargeColor;
	}

	/**
	 * @return the chargeIcon
	 */
	public ImageIcon getChargeIcon() {
		return chargeIcon;
	}

	/**
	 * @param chargeIcon
	 *            the chargeIcon to set
	 */
	public void setChargeIcon(final ImageIcon chargeIcon) {
		this.chargeIcon = chargeIcon;
	}

	/**
	 * @return the useGradiantForNormalColor
	 */
	public boolean isUseGradiantForNormalColor() {
		return useGradiantForNormalColor;
	}

	/**
	 * @param useGradiantForNormalColor
	 *            the useGradiantForNormalColor to set
	 */
	public void setUseGradiantForNormalColor(final boolean useGradiantForNormalColor) {
		this.useGradiantForNormalColor = useGradiantForNormalColor;
	}

	public boolean isUseGradiantForMediumColor() {
		return useGradiantForMediumColor;
	}

	public void setUseGradiantForMediumColor(final boolean useGradiantForMediumColor) {
		this.useGradiantForMediumColor = useGradiantForMediumColor;
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

	/**
	 * @return the fontYOffset
	 */
	public int getFontYOffset() {
		return fontYOffset;
	}

	/**
	 * @param fontYOffset
	 *            the fontYOffset to set
	 */
	public void setFontYOffset(final int fontYOffset) {
		this.fontYOffset = fontYOffset;
	}

	/**
	 * @return the fontXOffset
	 */
	public int getFontXOffset() {
		return fontXOffset;
	}

	/**
	 * @param fontXOffset
	 *            the fontXOffset to set
	 */
	public void setFontXOffset(final int fontXOffset) {
		this.fontXOffset = fontXOffset;
	}

	/**
	 * @return the strokewidth
	 */
	public int getStrokewidth() {
		return strokewidth;
	}

	/**
	 * @param strokewidth
	 *            the strokewidth to set
	 */
	public void setStrokewidth(final int strokewidth) {
		this.strokewidth = strokewidth;
	}

	/**
	 * @return the flip
	 */
	public boolean isFlip() {
		return flip;
	}

	/**
	 * @param flip
	 *            the flip to set
	 */
	public void setFlip(final boolean flip) {
		this.flip = flip;
	}

	/**
	 * @return the reduceFontOn100
	 */
	public int getReduceFontOn100() {
		return reduceFontOn100;
	}

	/**
	 * @param reduceFontOn100
	 *            the reduceFontOn100 to set
	 */
	public void setReduceFontOn100(final int reduceFontOn100) {
		this.reduceFontOn100 = reduceFontOn100;
	}

	/**
	 * @return the iconXOffset
	 */
	public int getIconXOffset() {
		return iconXOffset;
	}

	/**
	 * @param iconXOffset
	 *            the iconXOffset to set
	 */
	public void setIconXOffset(final int iconXOffset) {
		this.iconXOffset = iconXOffset;
	}

	/**
	 * @return the iconYOffset
	 */
	public int getIconYOffset() {
		return iconYOffset;
	}

	/**
	 * @param iconYOffset
	 *            the iconYOffset to set
	 */
	public void setIconYOffset(final int iconYOffset) {
		this.iconYOffset = iconYOffset;
	}

	/**
	 * @return the resizeChargeSymbolHeight
	 */
	public int getResizeChargeSymbolHeight() {
		return resizeChargeSymbolHeight;
	}

	/**
	 * @param resizeChargeSymbolHeight
	 *            the resizeChargeSymbolHeight to set
	 */
	public void setResizeChargeSymbolHeight(final int resizeChargeSymbolHeight) {
		this.resizeChargeSymbolHeight = resizeChargeSymbolHeight;
	}

	/**
	 * @return the transparentBackground
	 */
	public boolean isTransparentBackground() {
		return transparentBackground;
	}

	/**
	 * @param transparentBackground
	 *            the transparentBackground to set
	 */
	public void setTransparentBackground(final boolean transparentBackground) {
		this.transparentBackground = transparentBackground;
	}

	/**
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		// if (!isTransparentBackground())
		return backgroundColor;
		// else
		// return Color.black;
	}

	/**
	 * @param backgroundColor
	 *            the backgroundColor to set
	 */
	public void setBackgroundColor(final Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return the noBG
	 */
	public boolean isNoBG() {
		return noBG;
	}

	/**
	 * @param noBG
	 *            the noBG to set
	 */
	public void setNoBG(final boolean noBG) {
		this.noBG = noBG;
	}

	/**
	 * @return the battGradient
	 */
	public boolean isBattGradient() {
		return battGradient;
	}

	/**
	 * @param battGradient
	 *            the battGradient to set
	 */
	public void setBattGradient(final boolean battGradient) {
		this.battGradient = battGradient;
	}

	/**
	 * @return the battGradientLevel
	 */
	public int getBattGradientLevel() {
		return battGradientLevel;
	}

	/**
	 * @param battGradientLevel
	 *            the battGradientLevel to set
	 */
	public void setBattGradientLevel(final int battGradientLevel) {
		this.battGradientLevel = battGradientLevel;
	}

	/**
	 * @return the useChargeColor
	 */
	public boolean isUseChargeColor() {
		return useChargeColor;
	}

	/**
	 * @param useChargeColor
	 *            the useChargeColor to set
	 */
	public void setUseChargeColor(final boolean useChargeColor) {
		this.useChargeColor = useChargeColor;
	}

	/**
	 * @return the iconChargeGlowColor
	 */
	public Color getIconChargeGlowColor() {
		return iconChargeGlowColor;
	}

	/**
	 * @param iconChargeGlowColor
	 *            the iconChargeGlowColor to set
	 */
	public void setIconChargeGlowColor(final Color iconChargeGlowColor) {
		this.iconChargeGlowColor = iconChargeGlowColor;
	}

	/**
	 * @return the chargeGlow
	 */
	public boolean isChargeGlow() {
		return chargeGlow;
	}

	/**
	 * @param chargeGlow
	 *            the chargeGlow to set
	 */
	public void setChargeGlow(final boolean chargeGlow) {
		this.chargeGlow = chargeGlow;
	}

	/**
	 * @return the chargeGlowRadius
	 */
	public int getChargeGlowRadius() {
		return chargeGlowRadius;
	}

	/**
	 * @param chargeGlowRadius
	 *            the chargeGlowRadius to set
	 */
	public void setChargeGlowRadius(final int chargeGlowRadius) {
		this.chargeGlowRadius = chargeGlowRadius;
	}

	/**
	 * @return the glow
	 */
	public boolean isGlow() {
		return glow;
	}

	/**
	 * @param glow
	 *            the glow to set
	 */
	public void setGlow(final boolean glow) {
		this.glow = glow;
	}

	/**
	 * @return the glowRadius
	 */
	public int getGlowRadius() {
		return glowRadius;
	}

	/**
	 * @param glowRadius
	 *            the glowRadius to set
	 */
	public void setGlowRadius(final int glowRadius) {
		this.glowRadius = glowRadius;
	}

	/**
	 * @return the glowForChargeToo
	 */
	public boolean isGlowForCharge() {
		return glowForCharge;
	}

	/**
	 * @param glowForCharge
	 *            the glowForCharge to set
	 */
	public void setGlowForCharge(final boolean glowForCharge) {
		this.glowForCharge = glowForCharge;
	}

	/**
	 * @return the addPercent
	 */
	public boolean isAddPercent() {
		return addPercent;
	}

	/**
	 * @param addPercent
	 *            the addPercent to set
	 */
	public void setAddPercent(final boolean addPercent) {
		this.addPercent = addPercent;
	}

	public Color getExtraColor1() {
		return extraColor1;
	}

	public void setExtraColor1(final Color extraColor1) {
		this.extraColor1 = extraColor1;
	}

	public Color getExtraColor2() {
		return extraColor2;
	}

	public void setExtraColor2(final Color extraColor2) {
		this.extraColor2 = extraColor2;
	}

	public ImageIcon getXorIcon() {
		return xorIcon;
	}

	public void setXorIcon(final ImageIcon xorIcon) {
		this.xorIcon = xorIcon;
	}

	public ImageIcon getXorSquareIcon() {
		return xorSquareIcon;
	}

	public void setXorSquareIcon(final ImageIcon xorSquareIcon) {
		this.xorSquareIcon = xorSquareIcon;
	}

	public Color getDropShadowColor() {
		return dropShadowColor;
	}

	public void setDropShadowColor(final Color dropShadowColor) {
		this.dropShadowColor = dropShadowColor;
	}

	public boolean isDropShadowFont() {
		return dropShadowFont;
	}

	public void setDropShadowFont(final boolean dropShadowFont) {
		this.dropShadowFont = dropShadowFont;
	}

	public int getDropShadowOffsetX() {
		return dropShadowOffsetX;
	}

	public void setDropShadowOffsetX(final int dropShadowOffsetX) {
		this.dropShadowOffsetX = dropShadowOffsetX;
	}

	public int getDropShadowOffsetY() {
		return dropShadowOffsetY;
	}

	public void setDropShadowOffsetY(final int dropShadowOffsetY) {
		this.dropShadowOffsetY = dropShadowOffsetY;
	}

	public int getDropShadowOpacity() {
		return dropShadowOpacity;
	}

	public void setDropShadowOpacity(final int dropShadowOpacity) {
		this.dropShadowOpacity = dropShadowOpacity;
	}

	public int getDropShadowBlurryness() {
		return dropShadowBlurryness;
	}

	public void setDropShadowBlurryness(final int dropShadowBlurryness) {
		this.dropShadowBlurryness = dropShadowBlurryness;
	}

	public boolean isLinearGradient() {
		return linearGradient;
	}

	public void setLinearGradient(final boolean linearGradient) {
		this.linearGradient = linearGradient;
	}

	public boolean isUseTexture() {
		return useTexture;
	}

	public void setUseTexture(final boolean useTexture) {
		this.useTexture = useTexture;
	}

	public ImageIcon getTextureIcon() {
		return textureIcon;
	}

	public void setTextureIcon(final ImageIcon textureIcon) {
		this.textureIcon = textureIcon;
	}

	public int getGlowXOffset() {
		return glowXOffset;
	}

	public void setGlowXOffset(final int glowXOffset) {
		this.glowXOffset = glowXOffset;
	}

	public int getGlowYOffset() {
		return glowYOffset;
	}

	public void setGlowYOffset(final int glowYOffset) {
		this.glowYOffset = glowYOffset;
	}

	public boolean isMoveIconWithText() {
		return moveIconWithText;
	}

	public void setMoveIconWithText(final boolean moveIconWithText) {
		this.moveIconWithText = moveIconWithText;
	}

	public boolean isMoveGlowWithText() {
		return moveGlowWithText;
	}

	public void setMoveGlowWithText(final boolean moveGlowWithText) {
		this.moveGlowWithText = moveGlowWithText;
	}

	public boolean isDropShadowIcon() {
		return dropShadowIcon;
	}

	public void setDropShadowIcon(final boolean dropShadowIcon) {
		this.dropShadowIcon = dropShadowIcon;
	}

	public int getChargeGlowOffsetX() {
		return chargeGlowOffsetX;
	}

	public void setChargeGlowOffsetX(final int chargeGlowOffsetX) {
		this.chargeGlowOffsetX = chargeGlowOffsetX;
	}

	public int getChargeGlowOffsetY() {
		return chargeGlowOffsetY;
	}

	public void setChargeGlowOffsetY(final int chargeGlowOffsetY) {
		this.chargeGlowOffsetY = chargeGlowOffsetY;
	}

	public boolean isMoveChargeGlowWithText() {
		return moveChargeGlowWithText;
	}

	public void setMoveChargeGlowWithText(final boolean moveChargeGlowWithText) {
		this.moveChargeGlowWithText = moveChargeGlowWithText;
	}

	/**
	 * @return the showAdditionalFontOnCharge
	 */
	public boolean isShowAdditionalFontOnCharge() {
		return showAdditionalFontOnCharge;
	}

	/**
	 * @param showAdditionalFontOnCharge
	 *            the showAdditionalFontOnCharge to set
	 */
	public void setShowAdditionalFontOnCharge(final boolean showAdditionalFontOnCharge) {
		this.showAdditionalFontOnCharge = showAdditionalFontOnCharge;
	}

	/**
	 * @return the drawZeiger
	 */
	public boolean isDrawZeiger() {
		return drawZeiger;
	}

	/**
	 * @param drawZeiger
	 *            the drawZeiger to set
	 */
	public void setDrawZeiger(final boolean drawZeiger) {
		this.drawZeiger = drawZeiger;
	}

	public int getTextureFilterType() {
		return textureFilterType;
	}

	public void setTextureFilterType(final int textureFilterType) {
		this.textureFilterType = textureFilterType;
	}

	/**
	 * @return the backgroundTextureMode
	 */
	public int getBackgroundTextureMode() {
		return backgroundTextureMode;
	}

	/**
	 * @param backgroundTextureMode
	 *            the backgroundTextureMode to set
	 */
	public void setBackgroundTextureMode(final int backgroundTextureMode) {
		this.backgroundTextureMode = backgroundTextureMode;
	}

	/**
	 * @return the backgroundBrightness
	 */
	public int getBackgroundBrightness() {
		return backgroundBrightness;
	}

	/**
	 * @param backgroundBrightness
	 *            the backgroundBrightness to set
	 */
	public void setBackgroundBrightness(final int backgroundBrightness) {
		this.backgroundBrightness = backgroundBrightness;
	}

	/**
	 * @return the overpaintBackgroundMode
	 */
	public int getOverpaintBackgroundMode() {
		return overpaintBackgroundMode;
	}

	/**
	 * @param overpaintBackgroundMode
	 *            the overpaintBackgroundMode to set
	 */
	public void setOverpaintBackgroundMode(final int overpaintBackgroundMode) {
		this.overpaintBackgroundMode = overpaintBackgroundMode;
	}

	public HSBSettings getTextureHSB() {
		return textureHSB;
	}

	public void setTextureHSB(final HSBSettings textureHSB) {
		this.textureHSB = textureHSB;
	}

	public HSBSettings getChargeHSB() {
		return chargeHSB;
	}

	public void setChargeHSB(final HSBSettings chargeHSB) {
		this.chargeHSB = chargeHSB;
	}

	public int getChargeIconIndex() {
		return chargeIconIndex;
	}

	public void setChargeIconIndex(final int chargeIconIndex) {
		this.chargeIconIndex = chargeIconIndex;
	}

	public int getTextureIconIndex() {
		return textureIconIndex;
	}

	public void setTextureIconIndex(final int textureIconIndex) {
		this.textureIconIndex = textureIconIndex;
	}

	public int getXorIconIndex() {
		return xorIconIndex;
	}

	public void setXorIconIndex(final int xorIconIndex) {
		this.xorIconIndex = xorIconIndex;
	}

	public int getXorSquareIndex() {
		return xorSquareIndex;
	}

	public void setXorSquareIndex(final int xorSquareIndex) {
		this.xorSquareIndex = xorSquareIndex;
	}

}

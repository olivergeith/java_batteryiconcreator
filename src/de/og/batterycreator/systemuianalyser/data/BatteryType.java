package de.og.batterycreator.systemuianalyser.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import de.og.batterycreator.gui.widgets.overview.BatteryOverviewCreator;

public class BatteryType {

	public static final String		CHARGE_ANIM		= "charge_anim";
	public static final String		BATTERY_PREFIX	= "stat_sys_battery";
	private final List<Integer>		numbers			= new ArrayList<Integer>();
	private final List<Integer>		numbersCharge	= new ArrayList<Integer>();
	private final String			pattern;
	private final String			patternCharge;
	private final List<ImageIcon>	icons			= new ArrayList<ImageIcon>();
	private final List<ImageIcon>	iconsCharge		= new ArrayList<ImageIcon>();
	private final String			drawableFolder;

	public String getPattern() {
		return pattern;
	}

	public BatteryType(final String pattern, final String drawableFolder) {
		this.pattern = pattern;
		patternCharge = pattern + CHARGE_ANIM;
		this.drawableFolder = drawableFolder;
	}

	public List<Integer> getNumbers() {
		return numbers;
	}

	public List<Integer> getNumbersCharge() {
		return numbersCharge;
	}

	public List<ImageIcon> getIcons() {
		return icons;
	}

	public List<ImageIcon> getIconsCharge() {
		return iconsCharge;
	}

	public void addNumber(final int nr, final boolean isCharge) {
		if (isCharge) {
			numbersCharge.add(nr);
		} else {
			numbers.add(nr);
		}
	}

	public void addIcon(final ImageIcon icon, final boolean isCharge) {
		if (isCharge)
			iconsCharge.add(icon);
		else
			icons.add(icon);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BatteryType other = (BatteryType) obj;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else if (!pattern.equals(other.pattern))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BatteryType " + pattern;
	}

	public String toDebugString() {
		return "BatteryType pattern=" + pattern + " in " + drawableFolder + " [contains " + numbers.size() + " icons and " + numbersCharge.size()
				+ " chargeIcons]";
	}

	/**
	 * @return the patternCharge
	 */
	public String getPatternCharge() {
		return patternCharge;
	}

	/**
	 * @return the drawableFolder
	 */
	public String getDrawableFolder() {
		return drawableFolder;
	}

	public BufferedImage getOverview() {
		final List<ImageIcon> iconMap = new ArrayList<ImageIcon>();
		iconMap.addAll(icons);
		iconMap.addAll(iconsCharge);
		return BatteryOverviewCreator.createOverviewNewStyle(iconMap, pattern + " (" + drawableFolder + ")");
	}

	public boolean isOnPercentMod() {
		if (icons.size() == 101 && iconsCharge.size() == 101)
			return true;
		return false;
	}

	public int getBattSize() {
		final ImageIcon icon = icons.get(0);
		if (icon != null)
			return icon.getIconHeight();
		return 0;
	}
}

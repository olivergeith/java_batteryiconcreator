package de.og.batterycreator.systemuianalyser.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import de.og.batterycreator.gui.widgets.overview.BatteryOverviewCreator;

public class BatteryType extends IconType {

	public static final String		CHARGE_ANIM		= "charge_anim";
	public static final String		BATTERY_PREFIX	= "stat_sys_battery";
	public static final String		BATTERY_PREFIX2	= "tw_stat_sys_battery";
	private final String			pattern;
	private final String			patternCharge;
	private final List<ImageIcon>	icons			= new ArrayList<ImageIcon>();
	private final List<ImageIcon>	iconsCharge		= new ArrayList<ImageIcon>();

	public String getPattern() {
		return pattern;
	}

	public BatteryType(final String pattern, final String drawableFolder) {
		super(drawableFolder);
		this.pattern = pattern;
		patternCharge = pattern + CHARGE_ANIM;
	}

	public List<ImageIcon> getIcons() {
		return icons;
	}

	public List<ImageIcon> getIconsCharge() {
		return iconsCharge;
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

	@Override
	public String toDebugString() {
		return "BatteryType in " + getDrawableFolder() + "/" + pattern + " [" + icons.size() + " icons/ " + iconsCharge.size() + " chargeIcons]"
				+ "is 1% MOD: " + isOnPercentMod();
	}

	/**
	 * @return the patternCharge
	 */
	public String getPatternCharge() {
		return patternCharge;
	}

	public BufferedImage getOverview() {
		final List<ImageIcon> iconMap = new ArrayList<ImageIcon>();
		iconMap.addAll(icons);
		iconMap.addAll(iconsCharge);
		return BatteryOverviewCreator.createOverviewNewStyle(iconMap, pattern + " (" + getDrawableFolder() + ")");
	}

	public boolean isOnPercentMod() {
		if (icons.size() >= 101 && iconsCharge.size() >= 101)
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

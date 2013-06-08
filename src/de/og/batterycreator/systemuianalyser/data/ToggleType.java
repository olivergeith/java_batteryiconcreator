package de.og.batterycreator.systemuianalyser.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import de.og.batterycreator.gui.widgets.overview.SignalWifiOverviewCreator;

public class ToggleType extends IconType {

	public static final String		TOGGLE_PREFIX			= "toggle_";
	public static final String		TOGGLE_PREFIX_4_2		= "ic_qs_";
	public static final String		TOGGLE_PREFIX_SAMSUNG	= "stat_";
	public static final String		TOGGLE_NOT_CONTAINS1	= "_sys_";

	private final String			pattern;

	private final List<ImageIcon>	icons					= new ArrayList<ImageIcon>();

	public ToggleType(final String drawableFolder, final String pattern) {
		super(drawableFolder);
		this.pattern = pattern;
	}

	public List<ImageIcon> getIcons() {
		return icons;
	}

	public void addIcon(final ImageIcon icon) {
		icons.add(icon);
	}

	@Override
	public String toString() {
		return toDebugString();
	}

	@Override
	public String toDebugString() {
		return "Toggles in " + getPattern() + " [" + icons.size() + " icons]";
	}

	public BufferedImage getOverview() {
		final List<ImageIcon> iconMap = new ArrayList<ImageIcon>();
		iconMap.addAll(icons);
		return SignalWifiOverviewCreator.createOverviewImage(iconMap, getDrawableFolder());
	}

	public int getSize() {
		final ImageIcon icon = icons.get(0);
		if (icon != null)
			return icon.getIconHeight();
		return 0;
	}

	public boolean isValidIconSet() {
		return icons.size() > 20;
	}

	public String getPattern() {
		return pattern;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final ToggleType other = (ToggleType) obj;
		if (pattern == null) {
			if (other.pattern != null)
				return false;
		} else if (!pattern.equals(other.pattern))
			return false;
		return true;
	}

}

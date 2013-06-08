package de.og.batterycreator.systemuianalyser.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import de.og.batterycreator.gui.widgets.overview.SignalWifiOverviewCreator;

public class WifiSignalType extends IconType {

	public static final String		WIFI_PREFIX		= "stat_sys_wifi_";
	public static final String		SIGNAL_PREFIX	= "stat_sys_signal_";
	private final List<ImageIcon>	icons			= new ArrayList<ImageIcon>();

	public WifiSignalType(final String drawableFolder) {
		super(drawableFolder);
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
		return "Wifi&Signal in " + getDrawableFolder() + " [" + icons.size() + " icons]";
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
		return icons.size() > 10;
	}

}

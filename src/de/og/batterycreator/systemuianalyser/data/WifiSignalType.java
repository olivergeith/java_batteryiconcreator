package de.og.batterycreator.systemuianalyser.data;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.gui.widgets.overview.SignalWifiOverviewCreator;

public class WifiSignalType {

	/**
	 * the Logger for this Class
	 */
	private static final Logger		LOG				= LoggerFactory.getLogger(WifiSignalType.class);

	public static final String		WIFI_PREFIX		= "stat_sys_wifi_";
	public static final String		SIGNAL_PREFIX	= "stat_sys_signal_";
	private final List<ImageIcon>	icons			= new ArrayList<ImageIcon>();
	private final String			drawableFolder;

	public WifiSignalType(final String drawableFolder) {
		this.drawableFolder = drawableFolder;
	}

	public List<ImageIcon> getIcons() {
		return icons;
	}

	public void addIcon(final ImageIcon icon) {
		icons.add(icon);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((drawableFolder == null) ? 0 : drawableFolder.hashCode());
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
		final WifiSignalType other = (WifiSignalType) obj;
		if (drawableFolder == null) {
			if (other.drawableFolder != null)
				return false;
		} else if (!drawableFolder.equals(other.drawableFolder))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return toDebugString();
	}

	public String toDebugString() {
		return "WifiSignalType in " + drawableFolder + " [contains " + icons.size() + " icons]";
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
		return SignalWifiOverviewCreator.createOverviewImage(iconMap, drawableFolder);
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

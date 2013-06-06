package de.og.batterycreator.cfg;

import java.io.Serializable;

public class GlobalSettings implements Serializable {
	private static final long		serialVersionUID			= -1271358085998273369L;

	private RomPreset				romPreset;
	private boolean					showAdvancedButton			= false;
	private boolean					alwaysWriteOverview			= false;
	private int						smallBackgroundStyle		= 0;
	private int						smallOverViewStyle			= 0;
	private boolean					smallOverviewsOtherNmbers	= false;
	private int						bigBackgroundStyle			= 0;

	private int						signalWifiBackgroundStyle	= 0;
	private int						signalWifiOverViewStyle		= 0;

	public static GlobalSettings	INSTANCE					= new GlobalSettings();

	private GlobalSettings() {
		super();
	}

	public RomPreset getRomPreset() {
		return romPreset;
	}

	public void setRomPreset(final RomPreset romPreset) {
		this.romPreset = romPreset;
	}

	public boolean isShowAdvancedButton() {
		return showAdvancedButton;
	}

	public void setShowAdvancedButton(final boolean showAdvancedTab) {
		showAdvancedButton = showAdvancedTab;
	}

	public boolean isAlwaysWriteOverview() {
		return alwaysWriteOverview;
	}

	public void setAlwaysWriteOverview(final boolean alwaysWriteOverview) {
		this.alwaysWriteOverview = alwaysWriteOverview;
	}

	/**
	 * @return the smallBackgroundStyle
	 */
	public int getSmallBackgroundStyle() {
		return smallBackgroundStyle;
	}

	/**
	 * @param smallBackgroundStyle
	 *            the smallBackgroundStyle to set
	 */
	public void setSmallBackgroundStyle(final int smallBackgroundStyle) {
		this.smallBackgroundStyle = smallBackgroundStyle;
	}

	public int getSmallOverViewStyle() {
		return smallOverViewStyle;
	}

	public void setSmallOverViewStyle(final int smallOverViewStyle) {
		this.smallOverViewStyle = smallOverViewStyle;
	}

	public boolean isSmallOverviewsOtherNmbers() {
		return smallOverviewsOtherNmbers;
	}

	public void setSmallOverviewsOtherNmbers(final boolean smallOverviewsOtherNmbers) {
		this.smallOverviewsOtherNmbers = smallOverviewsOtherNmbers;
	}

	public int getBigBackgroundStyle() {
		return bigBackgroundStyle;
	}

	public void setBigBackgroundStyle(final int bigBackgroundStyle) {
		this.bigBackgroundStyle = bigBackgroundStyle;
	}

	/**
	 * @return the signalWifiBackgroundStyle
	 */
	public int getSignalWifiBackgroundStyle() {
		return signalWifiBackgroundStyle;
	}

	/**
	 * @param signalWifiBackgroundStyle
	 *            the signalWifiBackgroundStyle to set
	 */
	public void setSignalWifiBackgroundStyle(final int signalWifiBackgroundStyle) {
		this.signalWifiBackgroundStyle = signalWifiBackgroundStyle;
	}

	/**
	 * @return the signalWifiOverViewStyle
	 */
	public int getSignalWifiOverViewStyle() {
		return signalWifiOverViewStyle;
	}

	/**
	 * @param signalWifiOverViewStyle
	 *            the signalWifiOverViewStyle to set
	 */
	public void setSignalWifiOverViewStyle(final int signalWifiOverViewStyle) {
		this.signalWifiOverViewStyle = signalWifiOverViewStyle;
	}

}

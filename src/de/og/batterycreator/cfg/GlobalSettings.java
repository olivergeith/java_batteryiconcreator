package de.og.batterycreator.cfg;

import java.io.Serializable;

public class GlobalSettings implements Serializable {
	private static final long	serialVersionUID	= -1271358085998272369L;

	private RomPreset			romPreset;
	private boolean				showAdvancedButton	= false;
	private boolean				alwaysWriteOverview	= false;

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

}

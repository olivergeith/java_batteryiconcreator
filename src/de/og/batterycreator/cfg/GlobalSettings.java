package de.og.batterycreator.cfg;

import java.io.Serializable;

public class GlobalSettings implements Serializable {
	private static final long	serialVersionUID	= -1271358085998272369L;
	private RomPreset			romPreset;

	public RomPreset getRomPreset() {
		return romPreset;
	}

	public void setRomPreset(final RomPreset romPreset) {
		this.romPreset = romPreset;
	}

}

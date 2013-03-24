package de.og.batterycreator.creators.batt;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class XORAndroidCreator extends AbstractIconXORCreator {

	public XORAndroidCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	protected static String name = "XAndroidBattery";

	public static final ImageIcon myIcon = new ImageIcon(AbstractIconXORCreator.class.getResource("android.png"));

	@Override
	public String toString() {
		return name;
	}

	@Override
	protected ImageIcon getXORIcon() {
		return myIcon;
	}
}

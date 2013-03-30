package de.og.batterycreator.creators.batt;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class XORBattCreator extends AbstractIconXORCreator {

	public XORBattCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	protected static String name = "XBattBattery";

	public static final ImageIcon myIcon = new ImageIcon(AbstractIconXORCreator.class.getResource("batt.png"));

	@Override
	public String toString() {
		return name;
	}

	@Override
	protected ImageIcon getXORIcon() {
		return myIcon;
	}
}

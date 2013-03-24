package de.og.batterycreator.creators.batt;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class XORVnvCreator extends AbstractIconXORCreator {

	protected static String name = "XVNVNationBattery";
	public static final ImageIcon myIcon = new ImageIcon(AbstractIconXORCreator.class.getResource("vnv.png"));

	public XORVnvCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	protected ImageIcon getXORIcon() {
		return myIcon;
	}

}

package de.og.batterycreator.creators.batt;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class XORSkullCreator extends AbstractIconXORCreator {

	protected static String name = "XSkullBattery";
	public static final ImageIcon myIcon = new ImageIcon(AbstractIconXORCreator.class.getResource("skull.png"));

	public XORSkullCreator(final RomSettings romSettings) {
		super(romSettings);
		settings.setIconXOffset(-2);
		settings.setIconYOffset(-9);
		settings.setFontYOffset(-11);
		// settings.setShowFont(false);
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

package de.og.batterycreator.creators.batt;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class XORApfelCreator extends AbstractIconXORCreator {

	protected static String name = "XApfelBattery";
	public static final ImageIcon myIcon = new ImageIcon(AbstractIconXORCreator.class.getResource("apple.png"));

	public XORApfelCreator(final RomSettings romSettings) {
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

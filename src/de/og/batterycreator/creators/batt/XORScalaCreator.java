package de.og.batterycreator.creators.batt;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;

public class XORScalaCreator extends AbstractIconXORCreator {

	protected static String name = "XScalaBattery";
	public static final ImageIcon myIcon = new ImageIcon(AbstractIconXORCreator.class.getResource("scala.png"));

	public XORScalaCreator(final RomSettings romSettings) {
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

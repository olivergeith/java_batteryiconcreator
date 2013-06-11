package de.og.batterycreator.gui.widgets.colorselectbutton;

import java.awt.Color;
import javax.swing.ImageIcon;

public class MorphologyCrayonPanel extends CrayonPanel {
	private static final long		serialVersionUID	= 1L;

	private static final ImageIcon	icon				= new ImageIcon(CrayonPanel.class.getResource("Morphology.png"));

	@Override
	protected void addColors() {
		createCrayon("Morphology Red", new Color(0xff0019));
		createCrayon("Morphology Green", new Color(0xbbfa00));
		createCrayon("Morphology Neon Green", new Color(0x03fc03));
		createCrayon("Morphology Purple", new Color(0x8e00fa));
		createCrayon("Morphology Pink", new Color(0xfa14F2));
		createCrayon("Morphology Yellow", new Color(0xffec20));
		createCrayon("Morphology Blue", new Color(0x33b5e5));
		createCrayon("Morphology Orange", new Color(0xffa600));
	}

	@Override
	public String getDisplayName() {
		return "Morphology Colors";
	}

	@Override
	protected ImageIcon getIcon() {
		return icon;
	}

	@Override
	protected String getUrl() {
		return "http://forum.xda-developers.com/showthread.php?t=1968724";
	}

}

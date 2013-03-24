package de.og.batterycreator.gui.widgets;

import javax.swing.JComboBox;

import de.og.batterycreator.cfg.RomPreset;

public class MorphpathSystemUIComboBox extends JComboBox<String> {
	private static final long serialVersionUID = 1L;

	public MorphpathSystemUIComboBox() {

		initUI();

	}

	private void initUI() {
		addItem(RomPreset.MORPHPATH_SYSTEMUI);
		addItem(RomPreset.MORPHPATH_SYSTEMUI_SAMSUNG_JB);
		setEditable(false);
	}

}

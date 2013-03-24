package de.og.batterycreator.gui.widgets;

import javax.swing.JComboBox;

import de.og.batterycreator.cfg.RomPreset;

public class MorphpathFrameWorkComboBox extends JComboBox<String> {
	private static final long serialVersionUID = 1L;

	public MorphpathFrameWorkComboBox() {

		initUI();

	}

	private void initUI() {
		addItem(RomPreset.MORPHPATH_FRAMEWORK);
		addItem(RomPreset.MORPHPATH_FRAMEWORK_SAMSUNG_JB);
		setEditable(false);
	}

}

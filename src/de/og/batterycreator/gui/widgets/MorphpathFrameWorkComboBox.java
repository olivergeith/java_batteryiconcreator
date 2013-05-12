package de.og.batterycreator.gui.widgets;

import javax.swing.JComboBox;
import de.og.batterycreator.cfg.RomPreset;

public class MorphpathFrameWorkComboBox extends JComboBox<String> {
	private static final long	serialVersionUID	= 1L;

	public MorphpathFrameWorkComboBox() {

		initUI();

	}

	private void initUI() {
		addItem(RomPreset.MORPHPATH_FRAMEWORK);
		addItem(RomPreset.MORPHPATH_FRAMEWORK_PRELOAD);
		addItem(RomPreset.MORPHPATH_VRTHEME_FRAMEWORK);
		addItem(RomPreset.MORPHPATH_VRTHEME_FRAMEWORK_PRELOAD);
		setEditable(false);
	}

}

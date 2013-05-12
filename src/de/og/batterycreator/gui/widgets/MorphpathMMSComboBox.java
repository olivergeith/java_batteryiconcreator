package de.og.batterycreator.gui.widgets;

import javax.swing.JComboBox;
import de.og.batterycreator.cfg.RomPreset;

public class MorphpathMMSComboBox extends JComboBox<String> {
	private static final long	serialVersionUID	= 1L;

	public MorphpathMMSComboBox() {

		initUI();

	}

	private void initUI() {
		addItem(RomPreset.MORPHPATH_MMS);
		addItem(RomPreset.MORPHPATH_MMS_PRELOAD);
		addItem(RomPreset.MORPHPATH_VRTHEME_MMS);
		addItem(RomPreset.MORPHPATH_VRTHEME_MMS_PRELOAD);
		setEditable(false);
	}

}

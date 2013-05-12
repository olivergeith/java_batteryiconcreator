package de.og.batterycreator.gui.widgets;

import javax.swing.JComboBox;
import de.og.batterycreator.cfg.RomPreset;

public class MorphpathLidroidComboBox extends JComboBox<String> {
	private static final long	serialVersionUID	= 1L;

	public MorphpathLidroidComboBox() {

		initUI();

	}

	private void initUI() {
		addItem(RomPreset.MORPHPATH_LIDROID);
		addItem(RomPreset.MORPHPATH_LIDROID_PRELOAD);
		addItem(RomPreset.MORPHPATH_VRTHEME_LIDROID);
		addItem(RomPreset.MORPHPATH_VRTHEME_LIDROID_PRELOAD);
		setEditable(false);
	}

}

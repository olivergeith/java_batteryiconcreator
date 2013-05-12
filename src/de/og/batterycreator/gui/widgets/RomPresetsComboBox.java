package de.og.batterycreator.gui.widgets;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.cfg.RomPreset;
import de.og.batterycreator.cfg.SettingsPersistor;

public class RomPresetsComboBox extends JComboBox<RomPreset> {
	private static final long	serialVersionUID	= 1L;
	private static final Logger	LOGGER				= LoggerFactory.getLogger(RomPresetsComboBox.class);

	public RomPresetsComboBox() {

		initUI();

	}

	private void initUI() {
		for (final RomPreset pre : RomPreset.getPresets()) {
			addItem(pre);
			// writePreset(pre);
		}
		addAdditionalRomPresetsFromFilesystem();
	}

	private void addAdditionalRomPresetsFromFilesystem() {
		final File dir = new File(SettingsPersistor.ROMSETTINGS_DIR);
		if (dir.exists() && dir.isDirectory()) {
			final File[] presets = dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(final File dir, final String name) {
					return name.toLowerCase().endsWith(SettingsPersistor.ROMSETTINGS_EXTENSION);
				}
			});
			for (final File fi : presets) {
				final RomPreset pre = SettingsPersistor.readPreset(fi);
				if (pre != null) {
					LOGGER.info("Found RomPreset in Filesystem: {}", fi.getPath());
					addItem(pre);
				}
			}
		}

	}

	/**
	 * For testing purposes !!!
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		final JFrame f = new JFrame();
		f.setTitle("Hallo Emmy!!!!!!!");
		f.setBounds(200, 200, 800, 200);
		f.setLayout(new BorderLayout());
		final RomPresetsComboBox combo = new RomPresetsComboBox();
		combo.setSelectedIndex(0);
		f.add(combo, BorderLayout.CENTER);

		f.setVisible(true);
	}

}

package de.og.batterycreator.gui.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.cfg.RomPreset;
import de.og.batterycreator.cfg.SettingsPersistor;
import de.og.batterycreator.gui.iconstore.IconStore;

public class RomPresetsComboBox extends JComboBox<RomPreset> {
	private static final long	serialVersionUID	= 1L;
	private static final Logger	LOGGER				= LoggerFactory.getLogger(RomPresetsComboBox.class);

	public RomPresetsComboBox() {

		initUI();

	}

	private void initUI() {
		setRenderer(new MyListCellRenderer());

		for (final RomPreset pre : RomPreset.getPresets()) {
			addItem(pre);
			// SettingsPersistor.writePreset(pre);
		}
		addAdditionalRomPresetsFromFilesystem();
	}

	private void addAdditionalRomPresetsFromFilesystem() {
		final File dir = new File(SettingsPersistor.ROMPRESETS_DIR);
		if (dir.exists() && dir.isDirectory()) {
			final File[] presets = dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(final File dir, final String name) {
					return name.toLowerCase().endsWith(SettingsPersistor.ROMPRESETS_EXTENSION);
				}
			});
			for (final File fi : presets) {
				final RomPreset pre = SettingsPersistor.readPreset(fi);
				if (pre != null) {
					LOGGER.debug("Found RomPreset in Filesystem: {}", fi.getPath());
					addItem(pre);
				}
			}
		}

	}

	/**
	 * Renderer for Combo
	 */
	private class MyListCellRenderer implements ListCellRenderer<RomPreset> {
		protected DefaultListCellRenderer	defaultRenderer	= new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends RomPreset> list, final RomPreset value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {
			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof RomPreset) {
				final RomPreset pre = value;
				if (renderer.getIcon() == null) {
					if (pre != null && !pre.getRomName().equals(RomPreset.APPLY)) {
						if (pre.getRomName().contains("i9100")) {
							renderer.setIcon(IconStore.presetS2);
							renderer.setBackground(new Color(240, 240, 255)); // rosa
						} else if (pre.getRomName().contains("i9300")) {
							renderer.setIcon(IconStore.presetS3);
							renderer.setBackground(new Color(255, 240, 240)); // rosa
						} else if (pre.getRomName().contains("Galaxy Nexus")) {
							renderer.setIcon(IconStore.presetGN);
							renderer.setBackground(Color.lightGray.brighter());
						} else
							renderer.setIcon(IconStore.preset);
					}
				}
			}
			return renderer;
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
		f.setBounds(200, 200, 800, 80);
		f.setLayout(new BorderLayout());
		final RomPresetsComboBox combo = new RomPresetsComboBox();
		combo.setSelectedIndex(0);
		f.add(combo, BorderLayout.CENTER);

		f.setVisible(true);
	}

}

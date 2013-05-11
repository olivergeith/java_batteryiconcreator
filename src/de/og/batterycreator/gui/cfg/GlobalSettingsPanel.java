package de.og.batterycreator.gui.cfg;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import og.basics.gui.widgets.hidepanel.HidePanel;
import og.basics.jgoodies.JGoodiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import de.og.batterycreator.cfg.GlobalSettings;
import de.og.batterycreator.cfg.RomPreset;
import de.og.batterycreator.cfg.SettingsPersistor;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.main.IconCreatorFrame;

public class GlobalSettingsPanel extends SettingsPanel {
	private static final long		serialVersionUID		= 1L;

	private GlobalSettings			settings				= new GlobalSettings();
	// Presets
	private JComboBox<RomPreset>	romPresetCombo;
	private final JCheckBox			cboxShowAdvancedButton	= createCheckbox("Show 'Advanced Button' on startup (requires restart to take effect)",
																	"Show 'Advanced Button' in buttonbar (requires restart to take effect)");
	private static final Logger		LOGGER					= LoggerFactory.getLogger(GlobalSettingsPanel.class);

	// Construktor
	public GlobalSettingsPanel() {
		myInit();
	}

	private void myInit() {
		// Components
		romPresetCombo = new JComboBox<RomPreset>(RomPreset.getPresets());

		// reading and saving settings
		loadSettingsFromFilesystem();
		romPresetCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				saveSettingsToFilesystem();
			}
		});

		// Layout
		setLayout(new BorderLayout());
		final JLabel label = new JLabel();
		label.setIcon(IconStore.logoIconCFG);
		final JPanel cfg = createSettingsPanel();
		final JScrollPane cfgScroller = new JScrollPane();
		cfgScroller.add(cfg);
		cfgScroller.getViewport().setView(cfg);
		cfgScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(cfgScroller, BorderLayout.WEST);
		this.add(label, BorderLayout.CENTER);
	}

	private JPanel createSettingsPanel() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(createCfgPaneRomPresets(), cc.xyw(1, ++row, 9));

		final JPanel cfp = builder.getPanel();
		// cfp.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		final JPanel out = new JPanel(new BorderLayout());
		out.add(cfp, BorderLayout.CENTER);
		return out;
	}

	private JPanel createCfgPaneRomPresets() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("Load this Rom Preset:"), cc.xyw(2, ++row, 7));
		builder.add(romPresetCombo, cc.xyw(2, ++row, 7));
		builder.add(cboxShowAdvancedButton, cc.xyw(2, ++row, 7));

		final JPanel hide = new HidePanel("What to do on startup of " + IconCreatorFrame.APP_NAME, builder.getPanel());
		return hide;
	}

	public void setSettings(final GlobalSettings settings) {
		if (settings != null) {
			this.settings = settings;
			romPresetCombo.setSelectedItem(settings.getRomPreset());
			cboxShowAdvancedButton.setSelected(settings.isShowAdvancedButton());
			this.repaint();
		}
	}

	public GlobalSettings getSettings() {
		settings.setRomPreset((RomPreset) romPresetCombo.getSelectedItem());
		settings.setShowAdvancedButton(cboxShowAdvancedButton.isSelected());
		return settings;
	}

	@Override
	protected void validateControls() {
		saveSettingsToFilesystem();
	}

	private GlobalSettings loadSettingsFromFilesystem() {
		final GlobalSettings set = SettingsPersistor.loadGlobalSettings();
		LOGGER.info("Reading Global Settings");
		if (set != null) {
			debugGlobalSettings(set);
			setSettings(set);
		}
		return set;
	}

	/**
	 * TODO comment debugGlobalSettings
	 * 
	 * @param set
	 */
	public void debugGlobalSettings(final GlobalSettings set) {
		LOGGER.info("--> RomPreset to load on start: {}", set.getRomPreset().getRomName());
		LOGGER.info("--> showAdvanced Button on start: {}", set.isShowAdvancedButton());
	}

	private void saveSettingsToFilesystem() {
		final GlobalSettings set = getSettings();
		LOGGER.info("Saving Global Settings");
		debugGlobalSettings(set);
		SettingsPersistor.saveGlobalSettings(set);
	}
}
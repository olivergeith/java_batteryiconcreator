package de.og.batterycreator.gui.cfg;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import og.basics.gui.html.HTMLFileDisplay;
import og.basics.gui.lookandfeel.LookendfeelCombobox;
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
import de.og.batterycreator.gui.widgets.RomPresetsComboBox;
import de.og.batterycreator.main.IconCreatorFrame;

public class GlobalSettingsPanel extends SettingsPanel {
	private static final long			serialVersionUID				= 1L;

	// Presets
	private JComboBox<RomPreset>		romPresetCombo;
	private final JCheckBox				cboxShowAdvancedButton			= createCheckbox("Show 'Advanced Button' on startup (requires restart to take effect)",
																				"Show 'Advanced Button' in buttonbar (requires restart to take effect)");

	private final JCheckBox				cboxAlwaysWriteOverviews		= createCheckbox("Always write Overview-Png's",
																				"If selected, overviews are written to filesystem, even it the overview already exists! (May take more time on startup of the Rom Fumbler)");

	private final LookendfeelCombobox	looks							= new LookendfeelCombobox();

	private final JComboBox<String>		smallBackgroundStyleCombo		= new JComboBox<String>();
	private final JComboBox<String>		smallOverviewStyleCombo			= new JComboBox<String>();
	private final JCheckBox				cboxSmallOverviewsOtherNumbers	= createCheckbox("Small Overviews start with #5", "Small Overviews start with #5");

	private static final Logger			LOGGER							= LoggerFactory.getLogger(GlobalSettingsPanel.class);

	// Construktor
	public GlobalSettingsPanel() {
		myInit();
	}

	private void myInit() {

		// Components
		romPresetCombo = new RomPresetsComboBox();
		romPresetCombo.setMaximumRowCount(20);
		smallBackgroundStyleCombo.addItem("Fancy Tile");
		smallBackgroundStyleCombo.addItem("Television");
		smallBackgroundStyleCombo.addItem("Television.V2");
		smallBackgroundStyleCombo.addItem("Frame");
		smallBackgroundStyleCombo.addItem("Old Style");

		smallOverviewStyleCombo.addItem("One line with reflection");
		smallOverviewStyleCombo.addItem("One line");
		smallOverviewStyleCombo.addItem("Two line");
		smallOverviewStyleCombo.addItem("Big");
		smallOverviewStyleCombo.addItem("Big with charge");
		// reading and saving settings
		loadSettingsFromFilesystem();

		smallBackgroundStyleCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				saveSettingsToFilesystem();
			}
		});

		smallOverviewStyleCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				saveSettingsToFilesystem();
			}
		});

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
		this.add(label, BorderLayout.EAST);
		// Adding Howto, if Helpfile exists !
		final File help = new File("./help/GlobalSettings.htm");
		if (help.exists()) {
			this.add(new HTMLFileDisplay(help), BorderLayout.CENTER);
		}

	}

	private JPanel createSettingsPanel() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(createOnStartSettings(), cc.xyw(1, ++row, 9));
		builder.add(createMiscSettings(), cc.xyw(1, ++row, 9));

		final JPanel cfp = builder.getPanel();
		// cfp.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		final JPanel out = new JPanel(new BorderLayout());
		out.add(cfp, BorderLayout.CENTER);
		return out;
	}

	private JPanel createOnStartSettings() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("Load this Rom Preset:"), cc.xyw(2, ++row, 7));
		builder.add(romPresetCombo, cc.xyw(2, ++row, 7));
		builder.add(cboxShowAdvancedButton, cc.xyw(2, ++row, 7));
		builder.add(cboxAlwaysWriteOverviews, cc.xyw(2, ++row, 7));

		final JPanel hide = new HidePanel("What to do on startup of " + IconCreatorFrame.APP_NAME, builder.getPanel());
		return hide;
	}

	private JPanel createMiscSettings() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("Change Look&Feel to:"), cc.xyw(2, ++row, 7));
		builder.add(looks, cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Background for small battery overviews"), cc.xyw(2, ++row, 7));
		builder.add(smallBackgroundStyleCombo, cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Style for small battery overviews"), cc.xyw(2, ++row, 7));
		builder.add(smallOverviewStyleCombo, cc.xyw(2, ++row, 7));
		builder.add(cboxSmallOverviewsOtherNumbers, cc.xyw(2, ++row, 7));

		final JPanel hide = new HidePanel("Misc Settings", builder.getPanel());
		return hide;
	}

	public void setSettings(final GlobalSettings settings) {
		if (settings != null) {
			IconCreatorFrame.globalSettings = settings;
			romPresetCombo.setSelectedItem(settings.getRomPreset());
			cboxShowAdvancedButton.setSelected(settings.isShowAdvancedButton());
			cboxAlwaysWriteOverviews.setSelected(settings.isAlwaysWriteOverview());
			smallBackgroundStyleCombo.setSelectedIndex(settings.getSmallBackgroundStyle());
			smallOverviewStyleCombo.setSelectedIndex(settings.getSmallOverViewStyle());
			cboxSmallOverviewsOtherNumbers.setSelected(settings.isSmallOverviewsOtherNmbers());
			this.repaint();
		}
	}

	public GlobalSettings getSettings() {
		IconCreatorFrame.globalSettings.setRomPreset((RomPreset) romPresetCombo.getSelectedItem());
		IconCreatorFrame.globalSettings.setShowAdvancedButton(cboxShowAdvancedButton.isSelected());
		IconCreatorFrame.globalSettings.setAlwaysWriteOverview(cboxAlwaysWriteOverviews.isSelected());
		IconCreatorFrame.globalSettings.setSmallBackgroundStyle(smallBackgroundStyleCombo.getSelectedIndex());
		IconCreatorFrame.globalSettings.setSmallOverViewStyle(smallOverviewStyleCombo.getSelectedIndex());
		IconCreatorFrame.globalSettings.setSmallOverviewsOtherNmbers(cboxSmallOverviewsOtherNumbers.isSelected());
		return IconCreatorFrame.globalSettings;
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
	 * 
	 * @param set
	 */
	public void debugGlobalSettings(final GlobalSettings set) {
		LOGGER.info("--> RomPreset to load on start: {}", set.getRomPreset().getRomName());
		LOGGER.info("--> showAdvanced Button on start: {}", set.isShowAdvancedButton());
		LOGGER.info("--> alwaysWriteOverviews {}", set.isAlwaysWriteOverview());
		LOGGER.info("--> SmallBackgroundStyle {}", set.getSmallBackgroundStyle());
		LOGGER.info("--> SmallOverviewStyle {}", set.getSmallOverViewStyle());
		LOGGER.info("--> SmallOverviewStartWithOtherNumbers {}", set.isSmallOverviewsOtherNmbers());
	}

	private void saveSettingsToFilesystem() {
		final GlobalSettings set = getSettings();
		LOGGER.info("Saving Global Settings");
		debugGlobalSettings(set);
		SettingsPersistor.saveGlobalSettings(set);
	}
}

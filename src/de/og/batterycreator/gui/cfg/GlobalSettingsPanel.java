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
	private final JComboBox<String>		bigBackgroundStyleCombo			= new JComboBox<String>();

	private final JComboBox<String>		signalWifiBackgroundStyleCombo	= new JComboBox<String>();
	private final JComboBox<String>		signalWifiOverviewStyleCombo	= new JComboBox<String>();

	private static final Logger			LOGGER							= LoggerFactory.getLogger(GlobalSettingsPanel.class);

	// Construktor
	public GlobalSettingsPanel() {
		myInit();
	}

	private void myInit() {

		// Components
		romPresetCombo = new RomPresetsComboBox();
		romPresetCombo.setMaximumRowCount(20);

		bigBackgroundStyleCombo.addItem("Oldstyle");
		bigBackgroundStyleCombo.addItem("Television");

		smallBackgroundStyleCombo.addItem("Fancy Tile");
		smallBackgroundStyleCombo.addItem("Television.V1");
		smallBackgroundStyleCombo.addItem("Television.V2");
		smallBackgroundStyleCombo.addItem("Frame");
		smallBackgroundStyleCombo.addItem("Glass Frame");
		smallBackgroundStyleCombo.addItem("Old Style");

		smallOverviewStyleCombo.addItem("One line with reflection");
		smallOverviewStyleCombo.addItem("One line");
		smallOverviewStyleCombo.addItem("Two line");
		smallOverviewStyleCombo.addItem("Big");
		smallOverviewStyleCombo.addItem("Big with charge");

		signalWifiBackgroundStyleCombo.addItem("Fancy Tile");
		signalWifiBackgroundStyleCombo.addItem("Television thick top border");
		signalWifiBackgroundStyleCombo.addItem("Television");
		signalWifiBackgroundStyleCombo.addItem("Frame");
		signalWifiBackgroundStyleCombo.addItem("Glass Frame");
		signalWifiBackgroundStyleCombo.addItem("Old Style");

		signalWifiOverviewStyleCombo.addItem("One line with reflection");
		signalWifiOverviewStyleCombo.addItem("One line");

		// reading and saving settings
		loadSettingsFromFilesystem();

		romPresetCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				saveSettingsToFilesystem();
			}
		});

		bigBackgroundStyleCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				saveSettingsToFilesystem();
			}
		});

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

		signalWifiBackgroundStyleCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				saveSettingsToFilesystem();
			}
		});
		signalWifiOverviewStyleCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
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
		builder.add(createBatteryOverviewSettings(), cc.xyw(1, ++row, 9));
		builder.add(createSignalWifiOverviewSettings(), cc.xyw(1, ++row, 9));
		builder.add(createMiscSettings(), cc.xyw(1, ++row, 9));

		final JPanel cfp = builder.getPanel();
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

	private JPanel createBatteryOverviewSettings() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createGroupLabel("Battery overview"), cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Background"), cc.xyw(2, ++row, 7));
		builder.add(bigBackgroundStyleCombo, cc.xyw(2, ++row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Battery overview (small)"), cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Background"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Style"), cc.xyw(6, row, 3));
		builder.add(smallBackgroundStyleCombo, cc.xyw(2, ++row, 3));
		builder.add(smallOverviewStyleCombo, cc.xyw(6, row, 3));
		builder.add(cboxSmallOverviewsOtherNumbers, cc.xyw(2, ++row, 7));

		final JPanel hide = new HidePanel("Overviews Battery Renderer", builder.getPanel());
		return hide;
	}

	private JPanel createSignalWifiOverviewSettings() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("Background"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Style"), cc.xyw(6, row, 3));
		builder.add(signalWifiBackgroundStyleCombo, cc.xyw(2, ++row, 3));
		builder.add(signalWifiOverviewStyleCombo, cc.xyw(6, row, 3));

		final JPanel hide = new HidePanel("Overviews Signal and Wifi Renderer", builder.getPanel());
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

		final JPanel hide = new HidePanel("Misc Settings", builder.getPanel());
		return hide;
	}

	public void setSettings(final GlobalSettings settings) {
		if (settings != null) {
			GlobalSettings.INSTANCE = settings;
			romPresetCombo.setSelectedItem(settings.getRomPreset());
			cboxShowAdvancedButton.setSelected(settings.isShowAdvancedButton());
			cboxAlwaysWriteOverviews.setSelected(settings.isAlwaysWriteOverview());
			bigBackgroundStyleCombo.setSelectedIndex(settings.getBigBackgroundStyle());
			smallBackgroundStyleCombo.setSelectedIndex(settings.getSmallBackgroundStyle());
			smallOverviewStyleCombo.setSelectedIndex(settings.getSmallOverViewStyle());
			cboxSmallOverviewsOtherNumbers.setSelected(settings.isSmallOverviewsOtherNmbers());

			signalWifiBackgroundStyleCombo.setSelectedIndex(settings.getSignalWifiBackgroundStyle());
			signalWifiOverviewStyleCombo.setSelectedIndex(settings.getSignalWifiOverViewStyle());

			this.repaint();
		}
	}

	public GlobalSettings getSettings() {
		GlobalSettings.INSTANCE.setRomPreset((RomPreset) romPresetCombo.getSelectedItem());
		GlobalSettings.INSTANCE.setShowAdvancedButton(cboxShowAdvancedButton.isSelected());
		GlobalSettings.INSTANCE.setAlwaysWriteOverview(cboxAlwaysWriteOverviews.isSelected());
		GlobalSettings.INSTANCE.setBigBackgroundStyle(bigBackgroundStyleCombo.getSelectedIndex());
		GlobalSettings.INSTANCE.setSmallBackgroundStyle(smallBackgroundStyleCombo.getSelectedIndex());
		GlobalSettings.INSTANCE.setSmallOverViewStyle(smallOverviewStyleCombo.getSelectedIndex());
		GlobalSettings.INSTANCE.setSmallOverviewsOtherNmbers(cboxSmallOverviewsOtherNumbers.isSelected());
		GlobalSettings.INSTANCE.setSignalWifiOverViewStyle(signalWifiOverviewStyleCombo.getSelectedIndex());
		GlobalSettings.INSTANCE.setSignalWifiBackgroundStyle(signalWifiBackgroundStyleCombo.getSelectedIndex());

		return GlobalSettings.INSTANCE;
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
		LOGGER.info("--> BigBackgroundStyle {}", set.getBigBackgroundStyle());
		LOGGER.info("--> SmallBackgroundStyle {}", set.getSmallBackgroundStyle());
		LOGGER.info("--> SmallOverviewStyle {}", set.getSmallOverViewStyle());
		LOGGER.info("--> SmallOverviewStartWithOtherNumbers {}", set.isSmallOverviewsOtherNmbers());
		LOGGER.info("--> SignalWifiBackgroundStyle {}", set.getSignalWifiBackgroundStyle());
		LOGGER.info("--> SignalWifiOverViewStyle {}", set.getSignalWifiOverViewStyle());
	}

	private void saveSettingsToFilesystem() {
		final GlobalSettings set = getSettings();
		LOGGER.info("Saving Global Settings");
		debugGlobalSettings(set);
		SettingsPersistor.saveGlobalSettings(set);
	}
}

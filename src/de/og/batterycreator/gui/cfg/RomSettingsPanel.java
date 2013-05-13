package de.og.batterycreator.gui.cfg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import og.basics.gui.html.HTMLFileDisplay;
import og.basics.gui.icon.CommonIconProvider;
import og.basics.gui.widgets.hidepanel.HidePanel;
import og.basics.jgoodies.JGoodiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import de.og.batterycreator.cfg.RomPreset;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.cfg.SettingsPersistor;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.DrawableComboBox;
import de.og.batterycreator.gui.widgets.MorphPathWidget;
import de.og.batterycreator.gui.widgets.RomPresetsComboBox;
import de.og.batterycreator.gui.widgets.SliderAndLabel;
import de.og.batterycreator.gui.widgets.TemplateChooser;

public class RomSettingsPanel extends SettingsPanel {
	private static final long		serialVersionUID				= 1L;
	private static final Logger		LOGGER							= LoggerFactory.getLogger(RomSettingsPanel.class);

	private final JButton			loadButton						= new JButton(CommonIconProvider.BUTTON_ICON_OPEN);
	private final JButton			saveButton						= new JButton(CommonIconProvider.BUTTON_ICON_SAVE);
	private final JButton			exportButton					= new JButton("Export RomPreset", IconStore.presetExport);

	private RomSettings				settings						= new RomSettings();

	JCheckBox						cboxUseAdvResize				= createCheckbox("Use advanced Resize-Algorithm",
																			"(Experimental) Advanced Resize-Algorith...might give better results on small imagesizes!?");

	JCheckBox						cboxVRTheme						= createCheckbox("is VRTheme Template", "Check this if you choose vrtheme template");
	JCheckBox						cboxPreload						= createCheckbox("Rom uses /preload partition",
																			"Check this if you have a Samsung Stock Rom with preload partition");
	JCheckBox						cboxMMSForEmos					= createCheckbox("use Mms.apk for emoticons",
																			"Check this if you want to flash emoticons to Mms.apk");

	// Presets
	JComboBox<RomPreset>			romPresetCombo					= new RomPresetsComboBox();

	// Drawable
	DrawableComboBox				frameworkDrawableFolderCombo	= new DrawableComboBox();
	DrawableComboBox				systemUIDrawableFolderCombo		= new DrawableComboBox();
	DrawableComboBox				lidroidDrawableFolderCombo		= new DrawableComboBox();
	DrawableComboBox				emoticonsDrawableFolderCombo	= new DrawableComboBox();

	MorphPathWidget					morphSystemUIWidget				= new MorphPathWidget("system/app/SystemUI.apk/res/");
	MorphPathWidget					morphFrameworkWidget			= new MorphPathWidget("system/framework/framework-res.apk/res/");
	MorphPathWidget					morphLidroidWidget				= new MorphPathWidget("system/framework/lidroid-res.apk/res/");
	MorphPathWidget					morphMMSWidget					= new MorphPathWidget("system/app/Mms.apk/res/");

	// Battery
	SliderAndLabel					sliderBattSize					= systemUIDrawableFolderCombo.getSliderBattSize();
	JTextField						filepattern						= new JTextField();
	JTextField						filepatternCharge				= new JTextField();

	// Lockhandle
	JTextField						lockHandleFileName				= new JTextField();
	SliderAndLabel					lockHandleSize					= frameworkDrawableFolderCombo.getSliderLockSize();

	// Toggles
	SliderAndLabel					toggleSize						= systemUIDrawableFolderCombo.getSliderToggleSize();
	JCheckBox						cboxUseLidroid					= createCheckbox("lidroid-res.apk for Toggles (S3 only)",
																			"Morph Toggles to framework/lidroid-res.apk (Galaxy S3 only dont play with this on S2 !)");

	// Weather
	SliderAndLabel					weatherSize						= frameworkDrawableFolderCombo.getSliderWeatherSize();
	// Emoticons
	SliderAndLabel					emoSize							= emoticonsDrawableFolderCombo.getSliderEmoSize();

	// Notification
	JTextField						notificationFileName			= new JTextField();
	SliderAndLabel					notificationHeight				= systemUIDrawableFolderCombo.getSliderNotificationSize();

	// Signal stuff
	private final JTextField		fileNameSignalIn				= new JTextField();
	private final JTextField		fileNameSignalOut				= new JTextField();
	private final JTextField		fileNameSignalInOut				= new JTextField();
	private final JTextField		fileSignalPattern				= new JTextField();
	private final JTextField		fileSignalPatternFully			= new JTextField();

	// Wifi stuff
	private final JTextField		fileNameWifiIn					= new JTextField();
	private final JTextField		fileNameWifiOut					= new JTextField();
	private final JTextField		fileNameWifiInOut				= new JTextField();
	private final JTextField		fileWifiPattern					= new JTextField();
	private final JTextField		fileWifiPatternFully			= new JTextField();

	// Template
	private final TemplateChooser	templateChooser					= new TemplateChooser();

	// Construktor
	public RomSettingsPanel() {
		initComponents();
		myInit();
		makeButtonBar();
		setSettings(settings);
	}

	/**
	 * Initialize Components
	 */
	private void initComponents() {
		cboxUseLidroid.setForeground(Color.red.darker().darker());
		// cboxUseLidroid.setVisible(false);

		romPresetCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final RomPreset pre = (RomPreset) romPresetCombo.getSelectedItem();
				applyRomPresets(pre);
				romPresetCombo.setSelectedIndex(0);
			}

		});
		// romPresetCombo.setSelectedIndex(7);
		romPresetCombo.setSelectedIndex(0);
		romPresetCombo.setMaximumRowCount(20);

		loadButton.setToolTipText("Load Rom Settings");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				setSettings(SettingsPersistor.loadRomSettings());
			}
		});
		saveButton.setToolTipText("Save Rom Settings");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				SettingsPersistor.saveRomSettings("MyRomSettings", getSettings());
			}
		});
		exportButton.setToolTipText("Export Rom Settings --> RomPreset");
		exportButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				SettingsPersistor.saveRomPresetFromRomSettings(getSettings());
			}
		});
	}

	private void myInit() {

		templateChooser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final String template = (String) templateChooser.getSelectedItem();
				cboxVRTheme.setSelected(template.contains("vrtheme") || template.contains("VRTheme"));
				cboxPreload.setSelected(template.contains("preload"));
				validateControls();
			}
		});

		setLayout(new BorderLayout());
		final JLabel label = new JLabel();
		label.setIcon(IconStore.logoIcon);

		final JPanel cfg = createSettingsPanel();
		final JScrollPane cfgScroller = new JScrollPane();
		cfgScroller.add(cfg);
		cfgScroller.getViewport().setView(cfg);
		// cfgScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		cfgScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(cfgScroller, BorderLayout.WEST);
		this.add(label, BorderLayout.EAST);
		// Adding Howto, if Helpfile exists !
		final File help = new File("./help/RomSettings.htm");
		if (help.exists()) {
			this.add(new HTMLFileDisplay(help), BorderLayout.CENTER);
		}
	}

	/**
	 * 
	 * @param pre
	 */
	public void applyRomPresets(final RomPreset pre) {
		if (!pre.getRomName().equals(RomPreset.APPLY)) {
			LOGGER.info("Setting RomPreset to : {}", pre.getRomName());

			systemUIDrawableFolderCombo.setSelectedItem(pre.getSystemUIDrawableFolder());
			frameworkDrawableFolderCombo.setSelectedItem(pre.getFrameworkDrawableFolder());
			filepattern.setText(pre.getFilePattern());
			filepatternCharge.setText(pre.getFilePatternCharge());
			lockHandleSize.setValue(pre.getLockHandleSize());
			notificationHeight.setValue(pre.getNotificationHeight());
			toggleSize.setValue(pre.getToggleSize());
			weatherSize.setValue(pre.getWeatherSize());
			emoSize.setValue(pre.getEmoSize());
			sliderBattSize.setValue(pre.getBattsize());
			cboxUseLidroid.setSelected(pre.isUseLidroid());
			templateChooser.setSelectedItem(pre.getTemplate());
			cboxPreload.setSelected(pre.isUsePreload());
			cboxVRTheme.setSelected(pre.isUseVRThemeTemplate());
			cboxMMSForEmos.setSelected(pre.isUseMMSforEmoticons());
			validateControls();

			// lidroidDrawableFolderCombo.setEnabled(pre.isUseLidroid());
		}
	}

	private JPanel createSettingsPanel() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(createCfgPaneRomPresets(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPaneRomSettings(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPaneBattery(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPaneLockhandle(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPaneToggle(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPaneWeather(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPaneEmos(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPaneNotification(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPaneWifi(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPaneSignal(), cc.xyw(1, ++row, 9));

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

		builder.add(JGoodiesHelper.createBlackLabel("Rom Presets"), cc.xyw(2, ++row, 3));
		builder.add(romPresetCombo, cc.xyw(2, ++row, 5));

		final JPanel hide = new HidePanel("Rom Presets", builder.getPanel());
		return hide;
	}

	private JPanel createCfgPaneRomSettings() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("TemplateFile for flashable-Zip"), cc.xyw(2, ++row, 3));
		builder.add(templateChooser, cc.xyw(2, ++row, 3));
		builder.add(cboxVRTheme, cc.xyw(6, row, 3));
		builder.add(cboxPreload, cc.xyw(6, ++row, 3));

		builder.add(JGoodiesHelper.createBlackLabel("Morph-Path to SystemUI"), cc.xyw(2, ++row, 3));
		builder.add(morphSystemUIWidget, cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Morph-Path to Framework"), cc.xyw(2, ++row, 3));
		builder.add(morphFrameworkWidget, cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Morph-Path to Lidroid.apk"), cc.xyw(2, ++row, 3));
		builder.add(morphLidroidWidget, cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Morph-Path to Mms.apk"), cc.xyw(2, ++row, 3));
		builder.add(morphMMSWidget, cc.xyw(2, ++row, 7));

		builder.add(JGoodiesHelper.createBlackLabel("Choose your SystemUI's resolution"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Choose your Framework's resolution"), cc.xyw(6, row, 3));
		builder.add(systemUIDrawableFolderCombo, cc.xyw(2, ++row, 3));
		builder.add(frameworkDrawableFolderCombo, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createBlackLabel("Choose your lidroid-res's resolution"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Choose your Mms.apk's resolution"), cc.xyw(6, row, 3));
		builder.add(lidroidDrawableFolderCombo, cc.xyw(2, ++row, 3));
		builder.add(emoticonsDrawableFolderCombo, cc.xyw(6, row, 3));

		builder.add(cboxUseLidroid, cc.xyw(2, ++row, 3));
		builder.add(cboxMMSForEmos, cc.xyw(6, row, 3));

		final JPanel hide = new HidePanel("Main Rom Settings...", builder.getPanel());
		return hide;
	}

	private JPanel createCfgPaneBattery() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("FileName-Pattern Nomal / Charge"), cc.xyw(2, ++row, 7));
		builder.add(filepattern, cc.xyw(2, ++row, 3));
		builder.add(filepatternCharge, cc.xyw(6, row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Battery Size (hight)"), cc.xyw(2, ++row, 7));
		builder.add(sliderBattSize, cc.xyw(2, ++row, 1));
		builder.add(sliderBattSize.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(cboxUseAdvResize, cc.xyw(6, row, 3));

		final JPanel hide = new HidePanel("Battery Filenames & Size ...", builder.getPanel());
		return hide;
	}

	private JPanel createCfgPaneLockhandle() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("Lockhandle Filename"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Size"), cc.xyw(6, row, 3));
		builder.add(lockHandleFileName, cc.xyw(2, ++row, 3));
		builder.add(lockHandleSize, cc.xyw(6, row, 1));
		builder.add(lockHandleSize.getValueLabel(), cc.xyw(8, row, 1));

		final JPanel hide = new HidePanel("Lockhandle Filename & Size ...", builder.getPanel(), false);
		return hide;
	}

	private JPanel createCfgPaneToggle() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("ToggleSize (is set via Rom Presets)"), cc.xyw(2, ++row, 3));
		builder.add(toggleSize, cc.xyw(2, ++row, 1));
		builder.add(toggleSize.getValueLabel(), cc.xyw(4, row, 1));

		final JPanel hide = new HidePanel("Toggles", builder.getPanel());
		return hide;
	}

	private JPanel createCfgPaneWeather() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("WeatherSize (is set via Rom Presets)"), cc.xyw(2, ++row, 3));
		builder.add(weatherSize, cc.xyw(2, ++row, 1));
		builder.add(weatherSize.getValueLabel(), cc.xyw(4, row, 1));

		final JPanel hide = new HidePanel("Weather", builder.getPanel());
		return hide;
	}

	private JPanel createCfgPaneEmos() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("Emoticon Size (is set via Rom Presets)"), cc.xyw(2, ++row, 3));
		builder.add(emoSize, cc.xyw(2, ++row, 1));
		builder.add(emoSize.getValueLabel(), cc.xyw(4, row, 1));

		final JPanel hide = new HidePanel("Emoticons", builder.getPanel());
		return hide;
	}

	private JPanel createCfgPaneNotification() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("Notification BG Filename"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Size (height)"), cc.xyw(6, row, 3));
		builder.add(notificationFileName, cc.xyw(2, ++row, 3));
		builder.add(notificationHeight, cc.xyw(6, row, 1));
		builder.add(notificationHeight.getValueLabel(), cc.xyw(8, row, 1));

		final JPanel hide = new HidePanel("Notification BG Filename & Size ...", builder.getPanel(), false);
		return hide;
	}

	private JPanel createCfgPaneWifi() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("Filename Data In"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Data Out"), cc.xyw(6, row, 3));
		builder.add(fileNameWifiIn, cc.xyw(2, ++row, 3));
		builder.add(fileNameWifiOut, cc.xyw(6, row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Data InOut"), cc.xyw(2, ++row, 3));
		builder.add(fileNameWifiInOut, cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Pattern"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Fileextens. 'fully'"), cc.xyw(6, row, 3));
		builder.add(fileWifiPattern, cc.xyw(2, ++row, 3));
		builder.add(fileWifiPatternFully, cc.xyw(6, row, 3));

		final JPanel hide = new HidePanel("Wifi Filenames & Output ..", builder.getPanel(), false);
		return hide;
	}

	private JPanel createCfgPaneSignal() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createGroupLabel("Signal Filenames & Output ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Data In"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Data Out"), cc.xyw(6, row, 3));
		builder.add(fileNameSignalIn, cc.xyw(2, ++row, 3));
		builder.add(fileNameSignalOut, cc.xyw(6, row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Data InOut"), cc.xyw(2, ++row, 3));
		builder.add(fileNameSignalInOut, cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Filename Pattern"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Fileextens. 'fully'"), cc.xyw(6, row, 3));
		builder.add(fileSignalPattern, cc.xyw(2, ++row, 3));
		builder.add(fileSignalPatternFully, cc.xyw(6, row, 3));

		final JPanel hide = new HidePanel("Signal Filenames & Output ...", builder.getPanel(), false);
		return hide;
	}

	public void setSettings(final RomSettings settings) {
		if (settings != null) {
			this.settings = settings;

			cboxUseAdvResize.setSelected(settings.isUseAdvancedResize());
			cboxVRTheme.setSelected(settings.isUseVRThemeTemplate());
			cboxPreload.setSelected(settings.isUsePreload());
			cboxMMSForEmos.setSelected(settings.isUseMMSForEmoticons());

			// Drawables
			systemUIDrawableFolderCombo.setSelectedItem(settings.getSystemUIDrawableFolder());
			frameworkDrawableFolderCombo.setSelectedItem(settings.getFrameworkDrawableFolder());
			lidroidDrawableFolderCombo.setSelectedItem(settings.getLidroidDrawableFolder());
			emoticonsDrawableFolderCombo.setSelectedItem(settings.getEmoticonsDrawableFolder());
			// Morphpath
			// morphSystemUIComboBox.setSelectedItem(settings.getMorphPath2SystemUIRes());
			// morphpathFrameworkComboBox.setSelectedItem(settings.getMorphPath2FrameworkRes());
			// morphpathLidroidComboBox.setSelectedItem(settings.getMorphPath2SystemUIRes());
			// morphpathEmosComboBox.setSelectedItem(settings.getMorphPath2Emoticons());
			// Battery
			filepattern.setText(settings.getFilePattern());
			filepatternCharge.setText(settings.getFilePatternCharge());
			sliderBattSize.setValue(settings.getBattIconSize());

			// Signal stuff
			fileNameSignalIn.setText(settings.getFileSignalIn());
			fileNameSignalOut.setText(settings.getFileSignalOut());
			fileNameSignalInOut.setText(settings.getFileSignalInOut());
			fileSignalPattern.setText(settings.getFileSignalPattern());
			fileSignalPatternFully.setText(settings.getFileSignalEXtensionFully());

			// Wifi stuff
			fileNameWifiIn.setText(settings.getFileWifiIn());
			fileNameWifiOut.setText(settings.getFileWifiOut());
			fileNameWifiInOut.setText(settings.getFileWifiInOut());
			fileWifiPattern.setText(settings.getFileWifiPattern());
			fileWifiPatternFully.setText(settings.getFileWifiEXtensionFully());

			// Lockhandle
			lockHandleFileName.setText(settings.getLockHandleFileName());
			lockHandleSize.setValue(settings.getLockHandleSize());
			// Notification
			notificationFileName.setText(settings.getNotificationBGFilename());
			notificationHeight.setValue(settings.getNotificationHeight());
			// toggle
			toggleSize.setValue(settings.getToggleSize());
			cboxUseLidroid.setSelected(settings.isUseLidroid());
			// weather
			weatherSize.setValue(settings.getWeatherSize());
			// emoticon
			emoSize.setValue(settings.getEmoSize());
			// template
			templateChooser.setSelectedItem(settings.getTemplate());

			validateControls();
			this.repaint();
		}
	}

	public RomSettings getSettings() {
		settings.setUseAdvancedResize(cboxUseAdvResize.isSelected());
		settings.setUseVRThemeTemplate(cboxVRTheme.isSelected());
		settings.setUsePreload(cboxPreload.isSelected());
		settings.setUseMMSForEmoticons(cboxMMSForEmos.isSelected());
		// Drawables
		settings.setSystemUIDrawableFolder((String) systemUIDrawableFolderCombo.getSelectedItem());
		settings.setFrameworkDrawableFolder((String) frameworkDrawableFolderCombo.getSelectedItem());
		settings.setLidroidDrawableFolder((String) lidroidDrawableFolderCombo.getSelectedItem());
		settings.setEmoticonsDrawableFolder((String) emoticonsDrawableFolderCombo.getSelectedItem());
		// Morphpath
		settings.setMorphPath2SystemUIRes(morphSystemUIWidget.getText());
		settings.setMorphPath2Framework(morphFrameworkWidget.getText());
		settings.setMorphPath2Lidroid(morphLidroidWidget.getText());
		settings.setMorphPath2MMS(morphMMSWidget.getText());
		// Battery
		settings.setFilePattern(filepattern.getText());
		settings.setFilePatternCharge(filepatternCharge.getText());
		settings.setBattIconSize(sliderBattSize.getValue());

		// Signal stuff
		settings.setFileSignalIn(fileNameSignalIn.getText());
		settings.setFileSignalOut(fileNameSignalOut.getText());
		settings.setFileSignalInOut(fileNameSignalInOut.getText());
		settings.setFileSignalPattern(fileSignalPattern.getText());
		settings.setFileSignalEXtensionFully(fileSignalPatternFully.getText());
		// Wifi stuff
		settings.setFileWifiIn(fileNameWifiIn.getText());
		settings.setFileWifiOut(fileNameWifiOut.getText());
		settings.setFileWifiInOut(fileNameWifiInOut.getText());
		settings.setFileWifiPattern(fileWifiPattern.getText());
		settings.setFileWifiEXtensionFully(fileWifiPatternFully.getText());
		// Lockhandle
		settings.setLockHandleFileName(lockHandleFileName.getText());
		settings.setLockHandleSize(lockHandleSize.getValue());
		// Notification
		settings.setNotificationBGFilename(notificationFileName.getText());
		settings.setNotificationHeight(notificationHeight.getValue());
		// toggle
		settings.setToggleSize(toggleSize.getValue());
		settings.setUseLidroid(cboxUseLidroid.isSelected());
		// weather
		settings.setWeatherSize(weatherSize.getValue());
		// Emo
		settings.setEmoSize(emoSize.getValue());

		// template
		settings.setTemplate((String) templateChooser.getSelectedItem());

		return settings;
	}

	/**
	 * Creating buttonbar
	 */
	private void makeButtonBar() {
		final JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(loadButton);
		toolBar.add(saveButton);
		toolBar.add(exportButton);
		add(toolBar, BorderLayout.NORTH);
	}

	@Override
	protected void validateControls() {
		LOGGER.info("validating Rom-Copntrols");
		lidroidDrawableFolderCombo.setEnabled(cboxUseLidroid.isSelected());
		morphLidroidWidget.setEnabled(cboxUseLidroid.isSelected());

		emoticonsDrawableFolderCombo.setEnabled(cboxMMSForEmos.isSelected());
		morphMMSWidget.setEnabled(cboxMMSForEmos.isSelected());

		morphSystemUIWidget.setUsePreload(cboxPreload.isSelected());
		morphSystemUIWidget.setUseVRTheme(cboxVRTheme.isSelected());

		morphFrameworkWidget.setUsePreload(cboxPreload.isSelected());
		morphFrameworkWidget.setUseVRTheme(cboxVRTheme.isSelected());

		morphLidroidWidget.setUsePreload(cboxPreload.isSelected());
		morphLidroidWidget.setUseVRTheme(cboxVRTheme.isSelected());

		morphMMSWidget.setUsePreload(cboxPreload.isSelected());
		morphMMSWidget.setUseVRTheme(cboxVRTheme.isSelected());
	}
}

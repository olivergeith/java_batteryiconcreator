package de.og.batterycreator.gui.cfg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import og.basics.gui.icon.CommonIconProvider;
import og.basics.jgoodies.JGoodiesHelper;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import de.og.batterycreator.cfg.RomPreset;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.cfg.SettingsPersistor;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.DrawableComboBox;
import de.og.batterycreator.gui.widgets.MorphpathFrameWorkComboBox;
import de.og.batterycreator.gui.widgets.MorphpathSystemUIComboBox;
import de.og.batterycreator.gui.widgets.SliderAndLabel;
import de.og.batterycreator.gui.widgets.TemplateChooser;

public class RomSettingsPanel extends SettingsPanel {
	private static final long		serialVersionUID				= 1L;

	private RomSettings				settings						= new RomSettings();

	JCheckBox						cboxUseAdvResize				= createCheckbox("Use advanced Resize-Algorithm",
																			"(Experimental) Advanced Resize-Algorith...might give better results on small imagesizes!?");

	// Presets
	JComboBox<RomPreset>			romPresetCombo					= new JComboBox<RomPreset>(RomPreset.getPresets());

	// Drawable
	DrawableComboBox				frameworkDrawableFolderCombo	= new DrawableComboBox();
	DrawableComboBox				systemUIDrawableFolderCombo		= new DrawableComboBox();
	DrawableComboBox				lidroidDrawableFolderCombo		= new DrawableComboBox();

	// Morphpathes
	MorphpathSystemUIComboBox		morphpathSystemUIComboBox		= new MorphpathSystemUIComboBox();
	MorphpathFrameWorkComboBox		morphpathFrameworkComboBox		= new MorphpathFrameWorkComboBox();
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
	SliderAndLabel					emoSize							= frameworkDrawableFolderCombo.getSliderEmoSize();
	JCheckBox						cboxUseMms						= createCheckbox("Morph to Mms.apk",
																			"Morph Emoticons to Mms.apk instead of framework-res.apk");

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
				if (!pre.getRomName().equals(RomPreset.APPLY)) {
					systemUIDrawableFolderCombo.setSelectedItem(pre.getSystemUIDrawableFolder());
					frameworkDrawableFolderCombo.setSelectedItem(pre.getFrameworkDrawableFolder());
					filepattern.setText(pre.getFilePattern());
					filepatternCharge.setText(pre.getFilePatternCharge());
					romPresetCombo.setSelectedIndex(0);
					lockHandleSize.setValue(pre.getLockHandleSize());
					notificationHeight.setValue(pre.getNotificationHeight());
					toggleSize.setValue(pre.getToggleSize());
					weatherSize.setValue(pre.getWeatherSize());
					emoSize.setValue(pre.getEmoSize());
					sliderBattSize.setValue(pre.getBattsize());
					cboxUseLidroid.setSelected(pre.isUseLidroid());
					lidroidDrawableFolderCombo.setEnabled(pre.isUseLidroid());
					templateChooser.setSelectedItem(pre.getTemplate());
					morphpathSystemUIComboBox.setSelectedItem(pre.getMorphPath2SystemUIRes());
					morphpathFrameworkComboBox.setSelectedItem(pre.getMorphPath2FrameworkRes());
				}
			}
		});
		// romPresetCombo.setSelectedIndex(7);
		romPresetCombo.setSelectedIndex(0);
		romPresetCombo.setMaximumRowCount(20);
	}

	private void myInit() {
		setLayout(new BorderLayout());
		final JLabel label = new JLabel();
		label.setIcon(IconStore.logoIcon);

		final JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab("RomSettings Tab1", IconStore.cfgIcon, createTabPaneRomSettings(), "RomSettings");
		tabPane.addTab("RomSettings Tab2", IconStore.cfgIcon, createTabPaneWifiColors(), "RomSettings");
		this.add(tabPane, BorderLayout.WEST);
		this.add(label, BorderLayout.CENTER);
		// this.add(createTabPaneRomSettings(), BorderLayout.WEST);
	}

	public JPanel createTabPaneWifiColors() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createGroupLabel("Wifi Filenames & Output ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
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

		final JPanel cfp = builder.getPanel();
		// cfp.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		final JPanel out = new JPanel(new BorderLayout());
		out.add(cfp, BorderLayout.CENTER);
		return out;
	}

	public JPanel createTabPaneRomSettings() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createGroupLabel("Rom-Presets..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(romPresetCombo, cc.xyw(2, ++row, 5));

		builder.add(JGoodiesHelper.createGroupLabel("Template..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("TemplateFile for flashable-Zip"), cc.xyw(2, ++row, 3));
		builder.add(templateChooser, cc.xyw(2, ++row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Output-path in flashable-zips..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Morph-Path"), cc.xyw(2, ++row, 3));
		builder.add(morphpathSystemUIComboBox, cc.xyw(2, ++row, 7));
		builder.add(morphpathFrameworkComboBox, cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Choose your SystemUI's resolution"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Choose your Framework's resolution"), cc.xyw(6, row, 3));
		builder.add(systemUIDrawableFolderCombo, cc.xyw(2, ++row, 3));
		builder.add(frameworkDrawableFolderCombo, cc.xyw(6, row, 3));

		// builder.add(JGoodiesHelper.createGroupLabel("Resizing..."), cc.xyw(2,
		// ++row, 7));
		// builder.addSeparator("", cc.xyw(2, ++row, 7));
		// builder.add(cboxUseAdvResize, cc.xyw(2, ++row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Battery Filenames & Size ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("FileName-Pattern Nomal / Charge"), cc.xyw(2, ++row, 7));
		builder.add(filepattern, cc.xyw(2, ++row, 3));
		builder.add(filepatternCharge, cc.xyw(6, row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Battery Size (hight)"), cc.xyw(2, ++row, 7));
		builder.add(sliderBattSize, cc.xyw(2, ++row, 1));
		builder.add(sliderBattSize.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(cboxUseAdvResize, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Lockhandle Filename & Size ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Lockhandle Filename"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Size"), cc.xyw(6, row, 3));
		builder.add(lockHandleFileName, cc.xyw(2, ++row, 3));
		builder.add(lockHandleSize, cc.xyw(6, row, 1));
		builder.add(lockHandleSize.getValueLabel(), cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createGroupLabel("Toggle & Weather & Emoticon Size ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("ToggleSize (is set via Rom Presets)"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("WeatherSize (is set via Rom Presets)"), cc.xyw(6, row, 3));
		builder.add(toggleSize, cc.xyw(2, ++row, 1));
		builder.add(toggleSize.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(weatherSize, cc.xyw(6, row, 1));
		builder.add(weatherSize.getValueLabel(), cc.xyw(8, row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Choose your lidroid-res's resolution"), cc.xyw(6, ++row, 3));
		builder.add(cboxUseLidroid, cc.xyw(2, ++row, 3));
		builder.add(lidroidDrawableFolderCombo, cc.xyw(6, row, 3));
		builder.add(JGoodiesHelper.createGroupLabel("Emoticons ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Emoticon Size (is set via Rom Presets)"), cc.xyw(2, ++row, 3));
		builder.add(emoSize, cc.xyw(2, ++row, 1));
		builder.add(emoSize.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(cboxUseMms, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createGroupLabel("Notification BG Filename & Size ..."), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Notification BG Filename"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Size (height)"), cc.xyw(6, row, 3));
		builder.add(notificationFileName, cc.xyw(2, ++row, 3));
		builder.add(notificationHeight, cc.xyw(6, row, 1));
		builder.add(notificationHeight.getValueLabel(), cc.xyw(8, row, 1));

		final JPanel cfp = builder.getPanel();
		// cfp.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		final JPanel out = new JPanel(new BorderLayout());
		out.add(cfp, BorderLayout.CENTER);
		return out;
	}

	public void setSettings(final RomSettings settings) {
		if (settings != null) {
			this.settings = settings;

			cboxUseAdvResize.setSelected(settings.isUseAdvancedResize());

			// Drawables
			systemUIDrawableFolderCombo.setSelectedItem(settings.getSystemUIDrawableFolder());
			frameworkDrawableFolderCombo.setSelectedItem(settings.getFrameworkDrawableFolder());
			lidroidDrawableFolderCombo.setSelectedItem(settings.getLidroidDrawableFolder());
			// Morphpath
			morphpathSystemUIComboBox.setSelectedItem(settings.getMorphPath2SystemUIRes());
			morphpathFrameworkComboBox.setSelectedItem(settings.getMorphPath2FrameworkRes());
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
			cboxUseMms.setSelected(settings.isUseMmsForEmoticons());
			// template
			templateChooser.setSelectedItem(settings.getTemplate());

			validateControls();
			this.repaint();
		}
	}

	public RomSettings getSettings() {
		settings.setUseAdvancedResize(cboxUseAdvResize.isSelected());

		// Drawables
		settings.setSystemUIDrawableFolder((String) systemUIDrawableFolderCombo.getSelectedItem());
		settings.setFrameworkDrawableFolder((String) frameworkDrawableFolderCombo.getSelectedItem());
		settings.setLidroidDrawableFolder((String) lidroidDrawableFolderCombo.getSelectedItem());
		settings.setMorphPath2SystemUIRes((String) morphpathSystemUIComboBox.getSelectedItem());
		settings.setMorphPath2Framework((String) morphpathFrameworkComboBox.getSelectedItem());
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
		settings.setUseMmsForEmoticons(cboxUseMms.isSelected());

		// template
		settings.setTemplate((String) templateChooser.getSelectedItem());

		return settings;
	}

	/**
	 * Creating buttonbar
	 */
	private void makeButtonBar() {
		final LoadSettingsAktion loadAktion = new LoadSettingsAktion("Load Rom-Settings", CommonIconProvider.BUTTON_ICON_OPEN);
		final SaveSettingsAktion saveAktion = new SaveSettingsAktion("Save Rom-Settings", CommonIconProvider.BUTTON_ICON_SAVE);
		final JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(loadAktion);
		toolBar.add(saveAktion);
		add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * Load Settings Action
	 * 
	 */
	private class LoadSettingsAktion extends AbstractAction {
		private static final long	serialVersionUID	= 1L;

		public LoadSettingsAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			setSettings(SettingsPersistor.loadRomSettings());
		}
	}

	/**
	 * Save Settings Action
	 * 
	 */
	private class SaveSettingsAktion extends AbstractAction {
		private static final long	serialVersionUID	= 1L;

		public SaveSettingsAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			SettingsPersistor.saveRomSettings("MyRomSettings", getSettings());
		}
	}

	@Override
	protected void validateControls() {
		lidroidDrawableFolderCombo.setEnabled(cboxUseLidroid.isSelected());
	}
}

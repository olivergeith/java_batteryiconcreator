package de.og.batterycreator.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import og.basics.gui.LToolBar;
import og.basics.gui.icon.CommonIconProvider;
import og.basics.util.StaticExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.cfg.GlobalSettings;
import de.og.batterycreator.cfg.RomPreset;
import de.og.batterycreator.creators.batt.AbstractIconCreator;
import de.og.batterycreator.creators.batt.NoBattIcons;
import de.og.batterycreator.creators.signal.AbstractSignalCreator;
import de.og.batterycreator.creators.signal.NoSignalIcons;
import de.og.batterycreator.creators.wifi.AbstractWifiCreator;
import de.og.batterycreator.creators.wifi.NoWifiIcons;
import de.og.batterycreator.gui.cfg.GlobalSettingsPanel;
import de.og.batterycreator.gui.cfg.RomSettingsPanel;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.panels.BatteryPanel;
import de.og.batterycreator.gui.panels.LockHandlePanel;
import de.og.batterycreator.gui.panels.SignalPanel;
import de.og.batterycreator.gui.panels.WifiPanel;
import de.og.batterycreator.gui.panels.fileset.RecurseFileSetSelector;
import de.og.batterycreator.gui.panels.iconset.IconSetDeployer;
import de.og.batterycreator.gui.panels.iconset.IconSetSelector;
import de.og.batterycreator.gui.panels.iconset.RawIconSetSelector;
import de.og.batterycreator.gui.panels.notification.NotificationAreaBG;
import de.og.batterycreator.gui.panels.recurseiconset.RecurseIconSetSelector;
import de.og.batterycreator.gui.panels.xmlset.RecurseXMLSetSelector;
import de.og.batterycreator.zip.ZipElementCollection;
import de.og.batterycreator.zip.ZipMaker;

public class IconCreatingPanelNew extends JPanel {
	@SuppressWarnings("unused")
	private static final int				TAB_INDEX_GLOBALSETTINGS	= 0;
	@SuppressWarnings("unused")
	private static final int				TAB_INDEX_ROMSETTINGS		= 1;
	private static final int				TAB_INDEX_RENDERER			= 2;
	@SuppressWarnings("unused")
	private static final int				TAB_INDEX_ICONSETS			= 3;
	private static final int				TAB_INDEX_ADVANCED			= 4;
	private static final Logger				LOGGER						= LoggerFactory.getLogger(IconCreatingPanelNew.class);
	private static final long				serialVersionUID			= -2956273745014471932L;

	private final JButton					openZipFolderButton			= new JButton(IconStore.folderZipIcon);
	private final JButton					zipButton					= new JButton(IconStore.zipIcon);
	private final JButton					createButton				= new JButton(CommonIconProvider.BUTTON_ICON_START);
	private final JToggleButton				advancedToggle				= new JToggleButton(IconStore.moreIcon, false);
	private final JTabbedPane				tabPane						= new JTabbedPane();
	private final LToolBar					toolBar						= new LToolBar();
	private final RomSettingsPanel			romSettingsPanel			= new RomSettingsPanel();
	private final BatteryPanel				battPanel					= new BatteryPanel(romSettingsPanel.getSettings());
	private final WifiPanel					wifiPanel					= new WifiPanel(romSettingsPanel.getSettings());
	private final SignalPanel				signalPanel					= new SignalPanel(romSettingsPanel.getSettings());

	private final GlobalSettingsPanel		globalSettingsPanel			= new GlobalSettingsPanel();

	private final LockHandlePanel			lockHandleSelector			= new LockHandlePanel();
	private final IconSetSelector			signalWifiBox				= new IconSetSelector("Signal$Wifi", "./custom/signalwifi/");
	private final IconSetSelector			toggleBox					= new IconSetSelector("Toggles", "./custom/toggles/");
	private final IconSetSelector			battBox						= new IconSetSelector("Batteries", "./custom/batteries/");
	private final IconSetSelector			powerwidgetBox				= new IconSetSelector("PowerWidgetToggles", "./custom/powerwidget/");
	private final IconSetSelector			weatherBox					= new IconSetSelector("Weather", "./custom/weather/");
	private final IconSetSelector			emoBox						= new IconSetSelector("Emoticons", "./custom/emoticons/");
	private final NotificationAreaBG		notificationBG				= new NotificationAreaBG();
	private final RawIconSetSelector		systemUIBox					= new RawIconSetSelector("SystemUIMods", "./custom/systemui-mods/");
	private final RawIconSetSelector		frameworkresBox				= new RawIconSetSelector("FrameworkResMods", "./custom/frameworkres-mods/");
	private final RecurseXMLSetSelector		xmlBox						= new RecurseXMLSetSelector();

	private final RecurseFileSetSelector	filesetBox					= new RecurseFileSetSelector();
	private final RecurseIconSetSelector	iconsetBox					= new RecurseIconSetSelector();

	// Treadstuff
	private final JProgressBar				progressBar					= new JProgressBar();
	private Thread							t							= null;
	private boolean							isrunning					= false;
	// private boolean stopnow = false;
	private final int						maxsteps					= 19;
	private int								step						= 0;

	private final JFrame					parentFrame;

	private JTabbedPane						advancedTabPane;

	public IconCreatingPanelNew(final JFrame parentFrame) {
		this.parentFrame = parentFrame;
		initUI();
	}

	private void initUI() {
		setLayout(new BorderLayout());
		openZipFolderButton.setToolTipText("Open folder of flashble Zips!");
		openZipFolderButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				StaticExecutor.openFolder(ZipMaker.FLASHABLEZIP_OUT_DIR);
			}
		});
		zipButton.setToolTipText("Create flashble Zip!");
		zipButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				startZipThread();
			}
		});
		createButton.setToolTipText("Create/Refresh icons");
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				create();
			}
		});

		// Tooglebutton
		advancedToggle.setSelected(false);
		advancedToggle.setToolTipText("Show Advanced Features...Noobs stay away!!!!");
		advancedToggle.addActionListener(new ActionListener() {
			boolean	firstTime	= true;

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (firstTime == true) {
					JOptionPane
							.showMessageDialog(
									IconCreatingPanelNew.this,
									"You are entering dangerous terrain!!!\n\nNoobs keep away from those advanced features!!!\n\nYou need to know what you are doing!!!\n- You know how your Rom looks from the inside?\n- You know how your apk's look from the inside ?\n No? then stay away from this feature!",
									"Attention", JOptionPane.WARNING_MESSAGE);
					firstTime = false;

				}
				validatePanel(advancedToggle.isSelected());
			}
		});

		// Prograssbar int
		progressBar.setIndeterminate(false);
		progressBar.setMinimum(0);
		progressBar.setMaximum(maxsteps);
		progressBar.setStringPainted(true);
		resetProgressBar();

		advancedTabPane = new JTabbedPane();
		final JTabbedPane rendererTabPane = new JTabbedPane();
		final JTabbedPane iconsetsTabPane = new JTabbedPane();

		tabPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabPane.addTab("GlobalSettings", IconStore.settings1, globalSettingsPanel, "Program global settings");
		tabPane.addTab("RomSettings", IconStore.cfgIcon, romSettingsPanel, "RomSettings");
		// Renderer Tabbed Pane
		rendererTabPane.addTab("Battery", IconStore.batteryIcon, battPanel, "Create your batteries here");
		rendererTabPane.addTab("Wifi", IconStore.wifiIcon, wifiPanel, "Create your Wifi Icons here");
		rendererTabPane.addTab("Signal", IconStore.signalIcon, signalPanel, "Create your Signal Icons here");
		rendererTabPane.addTab("NotificationPanel", IconStore.notificationIcon, notificationBG, "Transparent Notification Panel");
		tabPane.addTab("Renderer", IconStore.rendererIcon, rendererTabPane, "Icon Renderes");

		// Renderer Tabbed Pane
		iconsetsTabPane.addTab("Toggles", IconStore.toggleIcon, toggleBox, "Predefined Toggle-Icon-Sets");
		iconsetsTabPane.addTab("CM Powerwidget", IconStore.powerwidgetIcon, powerwidgetBox, "Predefined PowerWidget Toggle-Icon-Sets");
		iconsetsTabPane.addTab("Signal$Wifi", IconStore.signalwifiIcon, signalWifiBox, "Predefined Signal- and Wifi-Icon-Sets");
		iconsetsTabPane.addTab("Weather", IconStore.weatherIcon, weatherBox, "Predefined Weather-Icon-Sets");
		iconsetsTabPane.addTab("Emoticons", IconStore.emoIcon, emoBox, "Predefined Emoticon-Icon-Sets");
		iconsetsTabPane.addTab("Lockring", IconStore.lockringIcon, lockHandleSelector, "See your choosen Lockring!");
		iconsetsTabPane.addTab("Batteries", IconStore.batteryIcon, battBox, "Predefined Battery-Icon-Sets");
		tabPane.addTab("Icon-Sets", IconStore.iconsetsIcon, iconsetsTabPane, "Custom Icon-Sets");

		advancedTabPane.addTab("SystemUI Mods", IconStore.androidredIcon, systemUIBox, "Get an Overview of your icons");
		advancedTabPane.addTab("FrameWorkRes Mods", IconStore.androidblueIcon, frameworkresBox, "Get an Overview of your icons");
		advancedTabPane.addTab("Themes/Morphs", IconStore.folderIcon, iconsetBox, "Add any Themes/Morphs you want...");
		advancedTabPane.addTab("Filesets", IconStore.folder2Icon, filesetBox, "Add Filesets like apk's, lib's, media...");
		advancedTabPane.addTab("XML-Sets", IconStore.folder2Icon, xmlBox, "Add XML-Sets to any apk...Most dangerous!");

		tabPane.setSelectedIndex(TAB_INDEX_RENDERER);
		// Panel zusammensetzen
		add(tabPane, BorderLayout.CENTER);
		// add(configPane, BorderLayout.WEST);
		add(progressBar, BorderLayout.SOUTH);
		makeButtonBar();
		// validate GlobalSettings
		applyGlobalSettingsOnStart();
	}

	private void applyGlobalSettingsOnStart() {
		final GlobalSettings gset = globalSettingsPanel.getSettings();
		if (gset == null) {
			LOGGER.error("No GlobalSettings!!!");
			return;
		}
		// rom preset
		final RomPreset preset = gset.getRomPreset();
		if (preset != null && !preset.getRomName().equals(RomPreset.APPLY)) {
			LOGGER.info("Setting your RomPreset to : {}", preset.getRomName());
			romSettingsPanel.applyRomPresets(preset);
		} else {
			LOGGER.info("No RomPresets choosen to be applied on startup...using Defaults!!!");
		}
		// advanced Tab
		LOGGER.info("Showing advanced toggle = {}", gset.isShowAdvancedButton());
		advancedToggle.setVisible(gset.isShowAdvancedButton());

	}

	private void validatePanel(final boolean advancedMode) {
		if (advancedMode == false)
			tabPane.remove(advancedTabPane);
		else {
			tabPane.addTab("Advanced Theming", IconStore.additionalIcon, advancedTabPane, "Advanced Stuff!!");
			tabPane.setSelectedIndex(TAB_INDEX_ADVANCED);
		}

	}

	/**
	 * Creating buttonbar
	 */
	private void makeButtonBar() {
		toolBar.setFloatable(false);
		toolBar.add(advancedToggle);
		toolBar.add(new JPanel());
		toolBar.add(createButton);
		toolBar.add(zipButton);
		toolBar.addSeparator();
		toolBar.add(openZipFolderButton);
	}

	/**
	 * Checking if there is a risk of duplicate Filenames in Zip
	 */
	private boolean doChecksBeforeZipping() {

		String msg = "";

		final AbstractIconCreator activBattCreator = battPanel.getActivBattCreator();
		if (activBattCreator != null && !(activBattCreator instanceof NoBattIcons) //
				&& battBox.getAllFilenamesAndPath().size() > 0) {
			msg += "Warning: You have selected Battery-Icons in Renderer-Tab and in IconSets-Tab!\n";
		}
		final AbstractWifiCreator activWifiCreator = wifiPanel.getActivWifiCreator();
		final AbstractSignalCreator activSignalCreator = signalPanel.getActivSignalCreator();
		if ((activWifiCreator != null && !activWifiCreator.toString().equals(NoWifiIcons.name)) //
				|| (activSignalCreator != null && !activSignalCreator.toString().equals(NoSignalIcons.name))//
				&& signalWifiBox.getAllFilenamesAndPath().size() > 0) {
			msg += "Warning: You have selected Signal/Wifi-Icons in Renderer-Tab and in IconSets-Tab!\n";
		}

		if (msg.length() > 0) {
			msg += "\n...this may lead to Zipping Errors due to duplicate Filenames!\n";
			msg += "\nDo you want to continue anyway ?!\n";
			final int status = JOptionPane.showConfirmDialog(null, msg, "Duplicate Icon-Type Warning", JOptionPane.YES_NO_OPTION);
			if (status == JOptionPane.YES_OPTION) {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	/**
	 * Zip the flashable Zip!
	 */
	private void doZip() {
		// first create everything again, to fill the deploy area with latest
		// settings
		create();

		// do some initial checks...and stop if something is wrong!
		if (doChecksBeforeZipping() == false)
			return;

		final ZipElementCollection zipCollection = new ZipElementCollection();
		final ZipMaker zipper = new ZipMaker(romSettingsPanel.getSettings().getTemplate());
		final Vector<String> files2add2Lidroid = new Vector<String>();
		final Vector<String> files2add2SystemUI = new Vector<String>();
		final Vector<String> files2add2Framework = new Vector<String>();
		final Vector<String> emoticonFiles = new Vector<String>();
		// adding Battery Icons
		updateProgressBar(step++, "Adding Battery Icons (if configured)");
		final AbstractIconCreator activBattCreator = battPanel.getActivBattCreator();
		if (activBattCreator != null) {
			files2add2SystemUI.addAll(activBattCreator.getAllFilenamesAndPath());
		}
		// Add Wifi Icons
		updateProgressBar(step++, "Adding Wifi Icons (if configured)");
		final AbstractWifiCreator activWifiCreator = wifiPanel.getActivWifiCreator();
		if (activWifiCreator != null && !activWifiCreator.toString().equals(NoWifiIcons.name)) {
			files2add2SystemUI.addAll(activWifiCreator.getAllFilenamesAndPath());
		}
		// // Add Signal Icons
		updateProgressBar(step++, "Adding Signal Icons (if configured)");
		final AbstractSignalCreator activSignalCreator = signalPanel.getActivSignalCreator();
		if (activSignalCreator != null && !activSignalCreator.toString().equals(NoSignalIcons.name)) {
			files2add2SystemUI.addAll(activSignalCreator.getAllFilenamesAndPath());
		}

		// Add Toggles
		updateProgressBar(step++, "Adding Toggle Icons (if configured)");
		if (romSettingsPanel.getSettings().isUseLidroid() == true) {
			files2add2Lidroid.addAll(toggleBox.getAllFilenamesAndPath());
		} else { // default
			files2add2SystemUI.addAll(toggleBox.getAllFilenamesAndPath());
		}

		// Add PowerWidget Toggles
		updateProgressBar(step++, "Adding PowerWidgetToggle Icons (if configured)");
		files2add2SystemUI.addAll(powerwidgetBox.getAllFilenamesAndPath());

		// Add SignalWifi
		updateProgressBar(step++, "Adding Signal%Wifi Icons (if configured)");
		files2add2SystemUI.addAll(signalWifiBox.getAllFilenamesAndPath());

		// Add Battery IconSets
		updateProgressBar(step++, "Adding Battery Icon-Sets (if configured)");
		files2add2SystemUI.addAll(battBox.getAllFilenamesAndPath());

		// Add Weather
		updateProgressBar(step++, "Adding Weather Icons (if configured)");
		files2add2Framework.addAll(weatherBox.getAllFilenamesAndPath());
		// Add Emoticons
		updateProgressBar(step++, "Adding Emoticons Emoticons (if configured)");
		emoticonFiles.addAll(emoBox.getAllFilenamesAndPath());
		// Lockhandle
		updateProgressBar(step++, "Adding Lock Icons (if configured)");
		files2add2Framework.addAll(lockHandleSelector.getAllFilenamesAndPath());
		// notification BG
		updateProgressBar(step++, "Adding Notification Background (if configured)");
		files2add2SystemUI.addAll(notificationBG.getAllFilenamesAndPath());

		// SystemUI
		updateProgressBar(step++, "Adding SystemUI Mods (if configured)");
		files2add2SystemUI.addAll(systemUIBox.getAllFilenamesAndPath());
		// FrameWorkRES
		updateProgressBar(step++, "Adding framework-res Mods (if configured)");
		files2add2Framework.addAll(frameworkresBox.getAllFilenamesAndPath());

		// ZipElementCollection anlegen und alle Zipelemente einfügen
		updateProgressBar(step++, "Creating ZipCollection");
		zipCollection.addElements(files2add2SystemUI, romSettingsPanel.getSettings().getFolderSystemUIInZip());
		zipCollection.addElements(files2add2Framework, romSettingsPanel.getSettings().getFolderFrameworkInZip());
		zipCollection.addElements(files2add2Lidroid, romSettingsPanel.getSettings().getFolderLidroidInZip());
		zipCollection.addElements(emoticonFiles, romSettingsPanel.getSettings().getFolderEmoticonsInZip());

		// Adding Them MORPH
		updateProgressBar(step++, "Adding Theme/Morph to ZipCollection");
		if (iconsetBox.getSelectedSet() != null) {
			zipCollection.addElements(iconsetBox.getSelectedSet().getFilenamesAndPath(), iconsetBox.getSelectedSet().getAllPathInZip());
		}
		// Adding XTRAS
		updateProgressBar(step++, "Adding XTRAS to ZipCollection");
		if (filesetBox.getSelectedSet() != null) {
			zipCollection.addElements(filesetBox.getSelectedSet().getFilenamesAndPath(), filesetBox.getSelectedSet().getAllPathInZip());
		}
		// Adding XXML-Set
		updateProgressBar(step++, "Adding XML's to ZipCollection");
		if (xmlBox.getSelectedSet() != null) {
			zipCollection.addElements(xmlBox.getSelectedSet().getFilenamesAndPath(), xmlBox.getSelectedSet().getAllPathInZip());
		}

		// now the actual zipping...
		try {
			updateProgressBar(step++, "Choose ZipFilename....");
			final boolean saved = zipper.addFilesToArchive(zipCollection.getZipelEments(), activBattCreator.getCreatorName());
			// all ok ? Then Messagebox
			if (saved == true) {
				updateProgressBar(step++, "Done Successfully!");
				JOptionPane.showMessageDialog(IconCreatingPanelNew.this, "Zip was created successfully", "Zip creating", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (final Exception e) {
			// Error during zip...
			updateProgressBar(step++, "Done With Error!");
			JOptionPane.showMessageDialog(IconCreatingPanelNew.this, //
					"ERROR: Zip was not created !!!\n" //
							+ e.getMessage(), "Zip creating ERROR", //
					JOptionPane.ERROR_MESSAGE);
			LOGGER.error("" + e.getMessage());
		}
		resetProgressBar();
	}

	/**
	 * Creates the desired Icons ;-)
	 */
	private void create() {
		resetProgressBar();
		// Creating Battery Icons
		updateProgressBar(step++, "Creating Battery Icons (if configured)");
		battPanel.createAllImages(romSettingsPanel.getSettings());

		// Creating Wifi Icons
		updateProgressBar(step++, "Creating Wifi Icons (if configured)");
		wifiPanel.createAllImages(romSettingsPanel.getSettings());

		// Creating Signal Icons
		updateProgressBar(step++, "Creating Signal Icons (if configured)");
		signalPanel.createAllImages(romSettingsPanel.getSettings());

		// creating notification
		updateProgressBar(step++, "Deploying Notification Background (if configured)");
		notificationBG.createAllImages(romSettingsPanel.getSettings());

		// creating lockHandle
		updateProgressBar(step++, "Deploying Lockring (if configured)");
		lockHandleSelector.createAllImages(romSettingsPanel.getSettings());

		// creating toggles
		updateProgressBar(step++, "Deploying Toggles (if configured)");
		toggleBox.createAllImages(romSettingsPanel.getSettings().getToggleSize());

		// creating powerwidget
		updateProgressBar(step++, "Deploying PowerWidget Toggles (if configured)");
		powerwidgetBox.createAllImages(romSettingsPanel.getSettings().getToggleSize());

		// creating Signal&Wifi
		updateProgressBar(step++, "Deploying Signal&Wifi (if configured)");
		signalWifiBox.createAllImages(romSettingsPanel.getSettings().getBattIconSize());

		// creating Battery Icon-Sets
		updateProgressBar(step++, "Deploying BatteryIconSets (if configured)");
		battBox.createAllImages(romSettingsPanel.getSettings().getBattIconSize());

		// creating weather
		updateProgressBar(step++, "Deploying Weather Icons (if configured)");
		weatherBox.createAllImages(romSettingsPanel.getSettings().getWeatherSize());

		// creating emoticons
		updateProgressBar(step++, "Deploying Emoticons (if configured)");
		emoBox.createAllImages(romSettingsPanel.getSettings().getEmoSize());

		// Systemui Mods
		updateProgressBar(step++, "Deploying SystemUI Icons (if configured)");
		systemUIBox.createAllImages(IconSetDeployer.NO_RESIZING);

		// frame Mods
		updateProgressBar(step++, "Deploying Framework-res Icons (if configured)");
		frameworkresBox.createAllImages(IconSetDeployer.NO_RESIZING);
		resetProgressBar();
	}

	/**
	 * Startet den Thread
	 * 
	 * @param startDir
	 */
	private void startZipThread() {
		if (t != null)
			stopThread();
		if (t == null) {
			t = new Thread(new Runnable() {
				@Override
				public void run() {
					parentFrame.setEnabled(false);
					// stopnow = false;
					isrunning = true;
					doZip();
					parentFrame.toFront();
					parentFrame.setEnabled(true);
					parentFrame.toFront();
					isrunning = false;
				}
			});

			t.start();
		}
	}

	public synchronized void stopThread() {
		if (t != null) {
			// stopnow = true;
			t = null;
		}
	}

	/**
	 * @return true, wenn Thread noch läuft
	 */
	public synchronized boolean isTreadRunning() {
		return isrunning;
	}

	private void updateProgressBar(final int value, final String text) {
		LOGGER.info("Progress: " + value + " " + text);
		progressBar.setValue(value);
		progressBar.setString(text);
	}

	private void resetProgressBar() {
		step = 1;
		progressBar.setValue(0);
		progressBar.setString("Create your Icons");
	}

	/**
	 * @return the toolBar
	 */
	public LToolBar getToolBar() {
		return toolBar;
	}

}

package de.og.batterycreator.systemuianalyser.gui;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import og.basics.gui.file.FileDialogs;
import og.basics.jgoodies.JGoodiesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.gui.cfg.RomSettingsPanel;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.overview.OverviewPanel;
import de.og.batterycreator.systemuianalyser.analyser.BatteryAnalyser;
import de.og.batterycreator.systemuianalyser.analyser.NotifyAnalyser;
import de.og.batterycreator.systemuianalyser.analyser.ToggleAnalyser;
import de.og.batterycreator.systemuianalyser.analyser.WifiSignalAnalyser;
import de.og.batterycreator.systemuianalyser.data.BatteryType;
import de.og.batterycreator.systemuianalyser.data.NotifyIconType;
import de.og.batterycreator.systemuianalyser.data.ToggleType;
import de.og.batterycreator.systemuianalyser.data.WifiSignalType;
import de.og.batterycreator.zip.ZipArchiveExtractor;

public class APKAnalyzerDialog extends JDialog {
	private static final long			serialVersionUID		= -1605180725450582074L;
	private static final Logger			LOG						= LoggerFactory.getLogger(APKAnalyzerDialog.class);
	private File						zipFile					= new File("SystemUI.apk");
	private final JButton				zipChooseButton			= new JButton("Load SystemUI.apk", IconStore.androidblueIcon);
	private final JProgressBar			progressBar				= new JProgressBar();

	// Battery Components
	private final JButton				exportBatteriesButton	= new JButton("Export selected BatteryMod", IconStore.iconsetsIcon);
	private final JList<BatteryType>	battTypeList			= new JList<BatteryType>();
	private final OverviewPanel			battOverPanel			= new OverviewPanel();
	private final RomSettingsPanel		settingsPanel;
	private final JLabel				battSizeLabel			= new JLabel("Height of selected battery: --");
	private final JCheckBox				cboxDefaultBatterySizes	= createCheckbox("Use default battery sizes",
																		"Uncheck to use the sizes within your SystemUI.apk");
	// Wifi Components
	private final JList<WifiSignalType>	wifiTypeList			= new JList<WifiSignalType>();
	private final JLabel				wifiSizeLabel			= new JLabel("Height of selected icons: --");
	private final JButton				exportwifiButton		= new JButton("Export selected Icons", IconStore.iconsetsIcon);
	private final OverviewPanel			wifiOverPanel			= new OverviewPanel();

	// Toggle Components
	private final JList<ToggleType>		toggleTypeList			= new JList<ToggleType>();
	private final JLabel				toggleSizeLabel			= new JLabel("Height of selected icons: --");
	private final JButton				exportToggleButton		= new JButton("Export selected Icons", IconStore.iconsetsIcon);
	private final OverviewPanel			toggleOverPanel			= new OverviewPanel();

	// Notify Components
	private final JList<NotifyIconType>	notifyTypeList			= new JList<NotifyIconType>();
	private final JLabel				notifySizeLabel			= new JLabel("Height of selected icons: --");
	private final JButton				exportNotifyButton		= new JButton("Export selected Icons", IconStore.androidredIcon);
	private final OverviewPanel			notifyOverPanel			= new OverviewPanel();

	public APKAnalyzerDialog(final Window parentFrame, final RomSettingsPanel settingsPanel) {
		super(parentFrame, "SystemUI - Analyzer", ModalityType.APPLICATION_MODAL);
		this.settingsPanel = settingsPanel;
		guiInit(parentFrame);
	}

	private void guiInit(final Window parentFrame) {
		// general stuff
		final int width = 600;
		final int height = 750;
		int x = 500;
		int y = 100;
		// Positioning releativ to Parent
		if (parentFrame != null) {
			x = parentFrame.getBounds().x + 500;
			y = parentFrame.getBounds().y + 10;
		}
		setPreferredSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
		setBounds(x, y, width, height);

		// components
		zipChooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				analyze();
			}
		});
		progressBar.setIndeterminate(false);
		progressBar.setStringPainted(true);
		progressBar.setString("SystemUI Analyzer");

		// wifi components
		wifiTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		wifiTypeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				validateWifiPanel();
			}
		});
		exportwifiButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				exportWifiSignalIconSet();
			}

		});
		exportwifiButton.setEnabled(false);

		// toggle components
		toggleTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		toggleTypeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				validateTogglePanel();
			}
		});
		exportToggleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				exportToggleIconSet();
			}

		});
		exportToggleButton.setEnabled(false);

		// notify components
		notifyTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		notifyTypeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				validateNotifyPanel();
			}
		});
		exportNotifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				exportNotifyIconSet();
			}

		});
		exportNotifyButton.setEnabled(false);

		// batt components
		cboxDefaultBatterySizes.setSelected(true);
		battTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		battTypeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				validateControlls();
			}
		});
		exportBatteriesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				exportBatteryIconSet();
			}
		});
		exportBatteriesButton.setEnabled(false);

		final JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab("Batteries", IconStore.batteryIcon, createBatteryPanel(), "See what kind of batteries are found in your SystemUI!");
		tabPane.addTab("Wifi/Signal", IconStore.signalwifiIcon, createWifiPanel(), "See what kind of signal & wifi icons are found in your SystemUI!");
		tabPane.addTab("Toggles", IconStore.toggleIcon, createTogglePanel(), "See what kind of toggle icons are found in your SystemUI!");
		tabPane.addTab("Notification Icons", IconStore.androidredIcon, createNotifyPanel(), "See what kind of notification icons are found in your SystemUI!");

		setLayout(new BorderLayout());
		this.add(makeToolBar(), BorderLayout.NORTH);
		this.add(tabPane, BorderLayout.CENTER);
		this.add(progressBar, BorderLayout.SOUTH);
	}

	protected void validateControlls() {
		validateBatteryPanel();
		validateWifiPanel();
		validateTogglePanel();
	}

	private void validateTogglePanel() {
		final ToggleType type = toggleTypeList.getSelectedValue();
		if (type != null) {
			toggleSizeLabel.setText("Height of selected icons: " + type.getSize());
			toggleOverPanel.setOverview(new ImageIcon(type.getOverview()), true);
			exportToggleButton.setEnabled(true);

		} else {
			toggleSizeLabel.setText("Height of selected icons: --");
			toggleOverPanel.setOverview(null, false);
			exportToggleButton.setEnabled(false);
		}
	}

	private void validateNotifyPanel() {
		final NotifyIconType type = notifyTypeList.getSelectedValue();
		if (type != null) {
			notifySizeLabel.setText("Height of selected icons: " + type.getSize());
			notifyOverPanel.setOverview(new ImageIcon(type.getOverview()), true);
			exportNotifyButton.setEnabled(true);

		} else {
			notifySizeLabel.setText("Height of selected icons: --");
			notifyOverPanel.setOverview(null, false);
			exportNotifyButton.setEnabled(false);
		}
	}

	private void validateWifiPanel() {
		final WifiSignalType type = wifiTypeList.getSelectedValue();
		if (type != null) {
			wifiSizeLabel.setText("Height of selected icons: " + type.getSize());
			wifiOverPanel.setOverview(new ImageIcon(type.getOverview()), true);
			exportwifiButton.setEnabled(true);

		} else {
			wifiSizeLabel.setText("Height of selected icons: --");
			wifiOverPanel.setOverview(null, false);
			exportwifiButton.setEnabled(false);
		}
	}

	private void validateBatteryPanel() {
		final BatteryType type = battTypeList.getSelectedValue();
		if (type != null) {
			final RomSettings set = settingsPanel.getSettings();
			set.setFrameworkDrawableFolder(type.getDrawableFolder());
			set.setSystemUIDrawableFolder(type.getDrawableFolder());
			set.setEmoticonsDrawableFolder(type.getDrawableFolder());
			set.setLidroidDrawableFolder(type.getDrawableFolder());
			set.setFilePattern(type.getPattern());
			set.setFilePatternCharge(type.getPatternCharge());

			settingsPanel.setSettings(set);
			settingsPanel.setSettingsAutoSizes(set);
			if (!cboxDefaultBatterySizes.isSelected()) {
				settingsPanel.forceBattSizes(type.getBattSize());
			}

			battSizeLabel.setText("Height of selected battery: " + type.getBattSize());
			battOverPanel.setOverview(new ImageIcon(type.getOverview()), true);
			exportBatteriesButton.setEnabled(true);

		} else {
			battSizeLabel.setText("Height of selected battery: --");
			battOverPanel.setOverview(null, false);
			exportBatteriesButton.setEnabled(false);
		}
	}

	private JToolBar makeToolBar() {
		final JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.add(zipChooseButton);
		// toolbar.add(exportIconSetButton);

		return toolbar;
	}

	public File chooseZip() {
		final File choosenFile = FileDialogs.chooseFileFree(zipFile, ".apk", "apk-File");
		if (choosenFile != null) {
			zipFile = choosenFile;
			return zipFile;
		}
		return null;
	}

	public void analyze() {
		final File zipFile = chooseZip();
		LOG.info("Analysing: " + zipFile);
		if (zipFile != null) {
			try {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						setCursor(new Cursor(Cursor.WAIT_CURSOR));
					}
				});
				startAnalyzerThread(zipFile);
			} catch (final Exception e) {
				LOG.error("Exception: ", e);
			}
		}
	}

	/**
	 * 
	 * @param zipFile
	 * @throws Exception
	 */
	private void analyzeZip(final File zipFile) throws Exception {
		LOG.info("Analysing: " + zipFile.getName());
		final File extractDir = new File("./analyzer/" + zipFile.getName());
		// Entpacken
		LOG.info("Extracting to: " + extractDir.getPath());
		progressBar.setIndeterminate(true);
		progressBar.setString("Extracting SystemUI.apk...");
		ZipArchiveExtractor.extractArchive(zipFile, extractDir);

		// Batterien suchen
		LOG.info("Analysing: Batteries");
		progressBar.setString("Analysing: Batteries...");
		final BatteryAnalyser battAnalyser = new BatteryAnalyser(extractDir);
		battAnalyser.analyse();
		battTypeList.removeAll();
		final Vector<BatteryType> types = new Vector<BatteryType>();
		for (final BatteryType type : battAnalyser.getBatteryTypeMap().values()) {
			if (type.isOnPercentMod())
				types.add(type);
		}
		battTypeList.setListData(types);
		battTypeList.repaint();

		// Wifi icons suchen
		LOG.info("Analysing: Wifi&Signal");
		progressBar.setString("Analysing: Wifi&Signal...");
		final WifiSignalAnalyser wifiAnalyser = new WifiSignalAnalyser(extractDir);
		wifiAnalyser.analyse();
		wifiTypeList.removeAll();
		final Vector<WifiSignalType> wifiTypes = new Vector<WifiSignalType>();
		for (final WifiSignalType type : wifiAnalyser.getWifiTypeMap().values()) {
			if (type.isValidIconSet())
				wifiTypes.add(type);
		}
		wifiTypeList.setListData(wifiTypes);
		wifiTypeList.repaint();

		// Toggles suchen
		LOG.info("Analysing: Toggles");
		progressBar.setString("Analysing: Toggles...");
		final ToggleAnalyser toggleAnalyser = new ToggleAnalyser(extractDir);
		toggleAnalyser.analyse();
		toggleTypeList.removeAll();
		final Vector<ToggleType> toggleTypes = new Vector<ToggleType>();
		for (final ToggleType type : toggleAnalyser.getToggleMap().values()) {
			if (type.isValidIconSet())
				toggleTypes.add(type);
		}
		toggleTypeList.setListData(toggleTypes);
		toggleTypeList.repaint();

		// Notigfy Icons suchen
		LOG.info("Analysing: Notify Icons");
		progressBar.setString("Analysing: Notify icons...");
		final NotifyAnalyser notifyAnalyser = new NotifyAnalyser(extractDir);
		notifyAnalyser.analyse();
		notifyTypeList.removeAll();
		final Vector<NotifyIconType> notifyTypes = new Vector<NotifyIconType>();
		for (final NotifyIconType type : notifyAnalyser.getIconMap().values()) {
			if (type.isValidIconSet())
				notifyTypes.add(type);
		}
		notifyTypeList.setListData(notifyTypes);
		notifyTypeList.repaint();

		// validation
		validateControlls();

		// aufräumen
		LOG.info("Cleaning up...deleting : " + extractDir.getPath());
		progressBar.setString("Cleaning up...deleting : " + extractDir.getPath());
		ZipArchiveExtractor.deleteDirRecurse(extractDir);
		// Wenns keinen 1% Mod gibt!
		if (battAnalyser.hasOnePercentMod() == false) {
			JOptionPane.showMessageDialog(this, //
					"ERROR: There is no 1% Battery Mod in your SystemUI !!!\n" + //
							"Try to find a 1% Mod for your Rom first.\n" + //
							"Search XDA Forum for this!", //
					getTitle(),//
					JOptionPane.ERROR_MESSAGE);
			// setVisible(false);
		}
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		progressBar.setString("SystemUI Analyzer");
		progressBar.setIndeterminate(false);
	}

	private JPanel createBatteryPanel() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("1% BatteryMODs in your SystemUI.apk:"), cc.xyw(2, ++row, 5));
		final JScrollPane scroller = new JScrollPane();
		scroller.add(battTypeList);
		scroller.getViewport().setView(battTypeList);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		builder.add(scroller, cc.xyw(2, ++row, 5));
		builder.add(new JLabel(IconStore.logoIconAnalyzer), cc.xyw(8, row, 3));

		builder.add(exportBatteriesButton, cc.xyw(2, ++row, 3));
		builder.add(battSizeLabel, cc.xyw(2, ++row, 3));
		builder.add(cboxDefaultBatterySizes, cc.xyw(6, row, 3));

		final JPanel cfp = builder.getPanel();
		final JPanel out = new JPanel(new BorderLayout());
		out.add(cfp, BorderLayout.NORTH);
		out.add(battOverPanel, BorderLayout.CENTER);
		return out;
	}

	private JPanel createWifiPanel() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("Wifi $ Signal icons in your SystemUI.apk:"), cc.xyw(2, ++row, 5));
		final JScrollPane scroller = new JScrollPane();
		scroller.add(wifiTypeList);
		scroller.getViewport().setView(wifiTypeList);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		builder.add(scroller, cc.xyw(2, ++row, 5));
		builder.add(new JLabel(IconStore.logoIconAnalyzer), cc.xyw(8, row, 3));

		builder.add(wifiSizeLabel, cc.xyw(2, ++row, 3));
		builder.add(exportwifiButton, cc.xyw(2, ++row, 3));

		final JPanel cfp = builder.getPanel();
		final JPanel out = new JPanel(new BorderLayout());
		out.add(cfp, BorderLayout.NORTH);
		out.add(wifiOverPanel, BorderLayout.CENTER);
		return out;
	}

	private JPanel createTogglePanel() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("Toggle icons in your SystemUI.apk:"), cc.xyw(2, ++row, 5));
		final JScrollPane scroller = new JScrollPane();
		scroller.add(toggleTypeList);
		scroller.getViewport().setView(toggleTypeList);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		builder.add(scroller, cc.xyw(2, ++row, 5));
		builder.add(new JLabel(IconStore.logoIconAnalyzer), cc.xyw(8, row, 3));

		builder.add(toggleSizeLabel, cc.xyw(2, ++row, 3));
		builder.add(exportToggleButton, cc.xyw(2, ++row, 3));

		final JPanel cfp = builder.getPanel();
		final JPanel out = new JPanel(new BorderLayout());
		out.add(cfp, BorderLayout.NORTH);
		out.add(toggleOverPanel, BorderLayout.CENTER);
		return out;
	}

	private JPanel createNotifyPanel() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("Notify icons in your SystemUI.apk:"), cc.xyw(2, ++row, 5));
		final JScrollPane scroller = new JScrollPane();
		scroller.add(notifyTypeList);
		scroller.getViewport().setView(notifyTypeList);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		builder.add(scroller, cc.xyw(2, ++row, 5));
		builder.add(new JLabel(IconStore.logoIconAnalyzer), cc.xyw(8, row, 3));

		builder.add(notifySizeLabel, cc.xyw(2, ++row, 3));
		builder.add(exportNotifyButton, cc.xyw(2, ++row, 3));

		final JPanel cfp = builder.getPanel();
		final JPanel out = new JPanel(new BorderLayout());
		out.add(cfp, BorderLayout.NORTH);
		out.add(notifyOverPanel, BorderLayout.CENTER);
		return out;
	}

	/**
	 * @param text
	 *            Text der Checkbox
	 * @param defaultselection
	 *            Defaultselection
	 * @return
	 */
	protected JCheckBox createCheckbox(final String text, final String tooltip) {
		final JCheckBox cbox = new JCheckBox(text);
		cbox.setToolTipText(tooltip);
		cbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				validateControlls();
			}
		});
		return cbox;
	}

	// #########################################################################
	// exporting
	// #########################################################################
	private final IconExporter	exporter	= new IconExporter(this);

	private void exportBatteryIconSet() {
		exporter.exportBatteryIconSet(battTypeList.getSelectedValue());
	}

	private void exportWifiSignalIconSet() {
		exporter.exportWifiSignalIconSet(wifiTypeList.getSelectedValue());
	}

	protected void exportToggleIconSet() {
		exporter.exportToggleIconSet(toggleTypeList.getSelectedValue());
	}

	protected void exportNotifyIconSet() {
		exporter.exportNotifyIconSet(notifyTypeList.getSelectedValue());
	}

	// #########################################################################
	// Tread
	// #########################################################################
	private Thread	t			= null;
	private boolean	isrunning	= false;

	/**
	 * Startet den Thread
	 * 
	 * @param startDir
	 */
	private void startAnalyzerThread(final File zipFile) {
		if (t != null)
			stopThread();
		if (t == null) {
			t = new Thread(new Runnable() {
				@Override
				public void run() {
					APKAnalyzerDialog.this.setEnabled(false);
					// stopnow = false;
					isrunning = true;
					try {
						analyzeZip(zipFile);
					} catch (final Exception e) {
						LOG.error("Exception: ", e);
					}
					// parentFrame.toFront();
					APKAnalyzerDialog.this.setEnabled(true);
					// parentFrame.toFront();
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

}

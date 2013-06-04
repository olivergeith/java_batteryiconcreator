package de.og.batterycreator.systemuianalyser.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import og.basics.gui.file.FileDialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.gui.widgets.overview.OverviewPanel;
import de.og.batterycreator.systemuianalyser.analyser.BatteryAnalyser;
import de.og.batterycreator.systemuianalyser.data.BatteryType;

public class APKAnalyzer extends JDialog {
	private static final long				serialVersionUID	= -1605180725450582074L;
	private File							zipFile				= new File("SystemUI.apk");
	// private final TracePanel tracer = new TracePanel(new
	// DefaultTextFileSaveHandler("./logs", "Analyse.log", ".log",
	// "Analyser Logfile"));
	private final JButton					chooseButton		= new JButton("Choose SystemUI.apk");
	private final JComboBox<BatteryType>	batteryTypeComboBox	= new JComboBox<BatteryType>();
	private final OverviewPanel				overPanel			= new OverviewPanel();

	/**
	 * the Logger for this Class
	 */
	private static final Logger				LOG					= LoggerFactory.getLogger(APKAnalyzer.class);

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final APKAnalyzer a = new APKAnalyzer(null, "RomPreset creator (by analysing SystemUI.apk)");
		a.setVisible(true);
		a.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

	public APKAnalyzer(final Window arg0, final String arg1) {
		super(arg0, arg1, ModalityType.APPLICATION_MODAL);
		guiInit();
	}

	private void guiInit() {
		batteryTypeComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				updateOverview();
			}
		});
		batteryTypeComboBox.setMaximumRowCount(20);
		setPreferredSize(new Dimension(1024, 600));
		setMinimumSize(new Dimension(1024, 600));
		chooseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				try {
					analyse();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});

		setLayout(new BorderLayout());
		this.add(makeToolBar(), BorderLayout.NORTH);
		this.add(overPanel, BorderLayout.CENTER);
	}

	protected void updateOverview() {
		final BatteryType type = (BatteryType) batteryTypeComboBox.getSelectedItem();
		if (type != null) {
			overPanel.setOverview(new ImageIcon(type.getOverview()));
		}

	}

	private JToolBar makeToolBar() {
		final JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.add(chooseButton);
		toolbar.add(batteryTypeComboBox);

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

	public void analyse() throws Exception {
		final File zipFile = chooseZip();
		LOG.info("Analysing: " + zipFile);
		if (zipFile != null) {

			LOG.info("Analysing: " + zipFile.getName());
			final File extractDir = new File("./analyzer/" + zipFile.getName());
			// Entpacken
			LOG.info("Extracting to: " + extractDir.getPath());
			ZipArchiveExtractor.extractArchive(zipFile, extractDir);

			// Batterien suchen
			final BatteryAnalyser battAnalyser = new BatteryAnalyser(extractDir);
			battAnalyser.analyse();

			batteryTypeComboBox.removeAll();
			for (final BatteryType type : battAnalyser.getBatteryTypeMap().values()) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						batteryTypeComboBox.addItem(type);
					}
				});
			}
			batteryTypeComboBox.repaint();

			// battIconListBox.removeAll();
			// final Vector<String> battNames = new Vector<String>();
			// battIconListBox.setListData(battNames);
			// battIconListBox.repaint();

			// Wifi icons suchen

			// signal Icons suchen

			// aufräumen
			// traceinfo("Cleaning up: " + extractDir.getPath());
			// ZipArchiveExtractor.deleteDirRecurse(extractDir);
		}
	}

	/**
	 * @return the zipFile
	 */
	public File getZipFile() {
		return zipFile;
	}

	/**
	 * @param zipFile
	 *            the zipFile to set
	 */
	public void setZipFile(final File zipFile) {
		this.zipFile = zipFile;
	}

}

package de.og.batterycreator.systemuianalyser.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
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
import de.og.batterycreator.systemuianalyser.data.BatteryType;

public class APKAnalyzerDialog extends JDialog {
	private static final long			serialVersionUID	= -1605180725450582074L;
	private File						zipFile				= new File("SystemUI.apk");
	private final JButton				chooseButton		= new JButton("Load SystemUI.apk", IconStore.androidblueIcon);
	private final JList<BatteryType>	battTypeList		= new JList<BatteryType>();
	private final OverviewPanel			overPanel			= new OverviewPanel();
	private static final Logger			LOG					= LoggerFactory.getLogger(APKAnalyzerDialog.class);
	private final RomSettingsPanel		settingsPanel		= new RomSettingsPanel(true);

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final APKAnalyzerDialog a = new APKAnalyzerDialog(null, "RomPreset creator (by analysing SystemUI.apk)");
		a.setVisible(true);
		a.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

	public APKAnalyzerDialog(final Window arg0, final String arg1) {
		super(arg0, arg1, ModalityType.APPLICATION_MODAL);
		guiInit();
	}

	private void guiInit() {
		battTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		battTypeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				validateControlls();
			}
		});
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

		settingsPanel.setVisible(false);
		overPanel.setVisible(false);

		setLayout(new BorderLayout());
		this.add(makeToolBar(), BorderLayout.NORTH);
		final JPanel p = new JPanel(new BorderLayout());
		p.add(createSettingsPanel(), BorderLayout.NORTH);
		p.add(overPanel);
		this.add(p, BorderLayout.CENTER);
		this.add(settingsPanel, BorderLayout.EAST);
	}

	protected void validateControlls() {
		settingsPanel.setVisible(!battTypeList.isSelectionEmpty());
		overPanel.setVisible(!battTypeList.isSelectionEmpty());
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
			updateOverview();

		}

	}

	protected void updateOverview() {
		final BatteryType type = battTypeList.getSelectedValue();
		if (type != null) {
			overPanel.setOverview(new ImageIcon(type.getOverview()), true);
		}

	}

	private JToolBar makeToolBar() {
		final JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.add(chooseButton);

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
			settingsPanel.setVisible(false);

			LOG.info("Analysing: " + zipFile.getName());
			final File extractDir = new File("./analyzer/" + zipFile.getName());
			// Entpacken
			LOG.info("Extracting to: " + extractDir.getPath());
			ZipArchiveExtractor.extractArchive(zipFile, extractDir);

			// Batterien suchen
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
			battTypeList.setMinimumSize(new Dimension(10, 30));

			// Wifi icons suchen

			// signal Icons suchen

			// validation
			validateControlls();

			// aufräumen
			LOG.info("Cleaning up...deleting : " + extractDir.getPath());
			ZipArchiveExtractor.deleteDirRecurse(extractDir);
		}
	}

	private JPanel createSettingsPanel() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("1% Mods in your SystemUI.apk:"), cc.xyw(2, ++row, 7));
		final JScrollPane scroller = new JScrollPane();
		scroller.add(battTypeList);
		scroller.getViewport().setView(battTypeList);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		builder.add(scroller, cc.xyw(2, ++row, 7));

		final JPanel cfp = builder.getPanel();
		final JPanel out = new JPanel(new BorderLayout());
		out.add(cfp, BorderLayout.CENTER);
		return out;
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

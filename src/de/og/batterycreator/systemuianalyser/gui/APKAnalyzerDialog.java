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
	private static final long			serialVersionUID		= -1605180725450582074L;
	private File						zipFile					= new File("SystemUI.apk");
	private final JButton				chooseButton			= new JButton("Load SystemUI.apk", IconStore.androidblueIcon);
	private final JList<BatteryType>	battTypeList			= new JList<BatteryType>();
	private final OverviewPanel			overPanel				= new OverviewPanel();
	private static final Logger			LOG						= LoggerFactory.getLogger(APKAnalyzerDialog.class);
	private final RomSettingsPanel		settingsPanel;
	private final JLabel				battSizeLabel			= new JLabel("Height of selected battery: --");
	private final JCheckBox				cboxDefaultBatterySizes	= createCheckbox("Use default battery sizes",
																		"Uncheck to use the sizes within your SystemUI.apk");

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
		cboxDefaultBatterySizes.setSelected(true);
		battTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		battTypeList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent arg0) {
				validateControlls();
			}
		});
		chooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				try {
					analyze();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
		setLayout(new BorderLayout());
		this.add(makeToolBar(), BorderLayout.NORTH);
		final JPanel p = new JPanel(new BorderLayout());
		p.add(createWestPanel(), BorderLayout.NORTH);
		p.add(overPanel);
		this.add(p, BorderLayout.CENTER);
	}

	protected void validateControlls() {
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
			overPanel.setOverview(new ImageIcon(type.getOverview()), true);

		} else {
			battSizeLabel.setText("Height of selected battery: --");
			overPanel.setOverview(null, false);

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

	public void analyze() throws Exception {
		final File zipFile = chooseZip();
		LOG.info("Analysing: " + zipFile);
		if (zipFile != null) {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
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
			// battTypeList.setMinimumSize(new Dimension(10, 40));

			// Wifi icons suchen

			// signal Icons suchen

			// validation
			validateControlls();

			// aufräumen
			LOG.info("Cleaning up...deleting : " + extractDir.getPath());
			ZipArchiveExtractor.deleteDirRecurse(extractDir);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			// Wenns keinen 1% Mod gibt!
			if (battAnalyser.hasOnePercentMod() == false) {
				JOptionPane.showMessageDialog(this, //
						"ERROR: There is no 1% Battery Mod in your SystemUI !!!\n" + //
								"Try to find a 1% Mod for your Rom first.\n" + //
								"Search XDA Forum for this!", //
						getTitle(),//
						JOptionPane.ERROR_MESSAGE);
				setVisible(false);
			}
		}
	}

	private JPanel createWestPanel() {
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

		builder.add(battSizeLabel, cc.xyw(2, ++row, 3));
		builder.add(cboxDefaultBatterySizes, cc.xyw(6, row, 3));

		final JPanel cfp = builder.getPanel();
		final JPanel out = new JPanel(new BorderLayout());
		out.add(cfp, BorderLayout.CENTER);
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

}

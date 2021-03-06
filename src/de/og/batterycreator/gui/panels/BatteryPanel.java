package de.og.batterycreator.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import og.basics.gui.html.HTMLFileDisplay;
import og.basics.gui.image.StaticImageHelper;
import og.basics.util.StaticExecutor;
import de.og.batterycreator.cfg.GlobalSettings;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.creators.batt.AbstractIconCreator;
import de.og.batterycreator.creators.batt.AppleBatteryCreator;
import de.og.batterycreator.creators.batt.ArcGradientCreator;
import de.og.batterycreator.creators.batt.ArcQuaterCreator2;
import de.og.batterycreator.creators.batt.ArcSunCreator;
import de.og.batterycreator.creators.batt.BallBattery;
import de.og.batterycreator.creators.batt.BarGraphWide;
import de.og.batterycreator.creators.batt.BarGraphWideV2;
import de.og.batterycreator.creators.batt.BarGraphWideV3;
import de.og.batterycreator.creators.batt.BarGraphWideV4;
import de.og.batterycreator.creators.batt.BatterySymbolCreatorV3;
import de.og.batterycreator.creators.batt.BatterySymbolV1;
import de.og.batterycreator.creators.batt.BatterySymbolV2;
import de.og.batterycreator.creators.batt.BatterySymbolVerticalV1;
import de.og.batterycreator.creators.batt.BatterySymbolVerticalV2;
import de.og.batterycreator.creators.batt.BinaryBarsCreator;
import de.og.batterycreator.creators.batt.BinarySquaresCreator;
import de.og.batterycreator.creators.batt.BoxCreatorV1;
import de.og.batterycreator.creators.batt.BoxCreatorV2;
import de.og.batterycreator.creators.batt.BoxCreatorV3;
import de.og.batterycreator.creators.batt.Brick25;
import de.og.batterycreator.creators.batt.BrickBattNoGap;
import de.og.batterycreator.creators.batt.BrickBattV1;
import de.og.batterycreator.creators.batt.BrickBattV2;
import de.og.batterycreator.creators.batt.BrickBatteryDecimalV1;
import de.og.batterycreator.creators.batt.BrickBatteryDecimalV2;
import de.og.batterycreator.creators.batt.BrickBatteryDecimalV3;
import de.og.batterycreator.creators.batt.BubbleCreator;
import de.og.batterycreator.creators.batt.CastawayCreator;
import de.og.batterycreator.creators.batt.CastawayCreator2;
import de.og.batterycreator.creators.batt.CastawayCreator2Flip;
import de.og.batterycreator.creators.batt.CastawayCreatorFlip;
import de.og.batterycreator.creators.batt.CircleCreatorV1;
import de.og.batterycreator.creators.batt.CircleCreatorV2;
import de.og.batterycreator.creators.batt.CircleCreatorV3;
import de.og.batterycreator.creators.batt.CircleCreatorV4;
import de.og.batterycreator.creators.batt.ClockCreator;
import de.og.batterycreator.creators.batt.ClockPointerCreator;
import de.og.batterycreator.creators.batt.DecimalArcCreator;
import de.og.batterycreator.creators.batt.DecimalBar2Creator;
import de.og.batterycreator.creators.batt.DecimalBar3Creator;
import de.og.batterycreator.creators.batt.DecimalBarCreator;
import de.og.batterycreator.creators.batt.FaecherCreatorWide;
import de.og.batterycreator.creators.batt.FontOnlyCreator;
import de.og.batterycreator.creators.batt.HoneycombCreator;
import de.og.batterycreator.creators.batt.KnobBatteryCreator;
import de.og.batterycreator.creators.batt.NoBattIcons;
import de.og.batterycreator.creators.batt.PlanetV1;
import de.og.batterycreator.creators.batt.Rush25Battery;
import de.og.batterycreator.creators.batt.SamsungStockBatteryCreator;
import de.og.batterycreator.creators.batt.ScalaBatteryCreator;
import de.og.batterycreator.creators.batt.SimpleBatteryV1;
import de.og.batterycreator.creators.batt.SimpleBatteryV2;
import de.og.batterycreator.creators.batt.SliderCreator;
import de.og.batterycreator.creators.batt.TachoCreator;
import de.og.batterycreator.creators.batt.TachoCreator3Quater;
import de.og.batterycreator.creators.batt.TachoGauge;
import de.og.batterycreator.creators.batt.TachoWideV1;
import de.og.batterycreator.creators.batt.TachoWideV2;
import de.og.batterycreator.creators.batt.TachoWideV3;
import de.og.batterycreator.creators.batt.TachoWideV4;
import de.og.batterycreator.creators.batt.TachoWideV5;
import de.og.batterycreator.creators.batt.TwoBars;
import de.og.batterycreator.creators.batt.XORApfelCreator;
import de.og.batterycreator.creators.batt.XORCenter;
import de.og.batterycreator.creators.batt.XORCircle;
import de.og.batterycreator.creators.batt.XORSquare;
import de.og.batterycreator.creators.batt.Zoopa3Quater;
import de.og.batterycreator.creators.batt.ZoopaCircle;
import de.og.batterycreator.creators.batt.ZoopaCircleV2;
import de.og.batterycreator.creators.batt.ZoopaClock;
import de.og.batterycreator.creators.batt.ZoopaClockWide;
import de.og.batterycreator.creators.batt.ZoopaGaugeV1;
import de.og.batterycreator.creators.batt.ZoopaGaugeV2;
import de.og.batterycreator.creators.batt.ZoopaWideV1;
import de.og.batterycreator.creators.batt.ZoopaWideV2;
import de.og.batterycreator.creators.batt.ZoopaWideV3;
import de.og.batterycreator.gui.cfg.BattSettingsPanel;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.animator.AnimatorBar;
import de.og.batterycreator.gui.widgets.overview.OverviewPanel;
import de.og.batterycreator.zip.ZipElementCollection;
import de.og.batterycreator.zip.ZipMaker;

public class BatteryPanel extends JPanel {
	private static final long						serialVersionUID		= -5956664471952448919L;

	private final JComboBox<AbstractIconCreator>	combo					= new JComboBox<AbstractIconCreator>();
	private final JButton							openFolderButton		= new JButton(IconStore.folder2Icon);
	private final JButton							exportZipButton			= new JButton(IconStore.zipFileIcon);
	private final JList<String>						battIconList			= new JList<String>();
	private AbstractIconCreator						activBattCreator		= null;
	private final OverviewPanel						battOverviewPanel		= new OverviewPanel();
	private final OverviewPanel						battSmallOverviewPanel	= new OverviewPanel();

	private final BattSettingsPanel					settingsPanel			= new BattSettingsPanel();

	private final AnimatorBar						anibar					= new AnimatorBar(80);					// millies

	public BatteryPanel(final RomSettings romSettings) {
		super();
		fillCreatorList(romSettings);
		initIconList();
		initUI();
	}

	private void fillCreatorList(final RomSettings romSettings) {
		combo.addItem(new NoBattIcons(romSettings));
		combo.addItem(new CircleCreatorV1(romSettings));
		combo.addItem(new CircleCreatorV2(romSettings));
		combo.addItem(new CircleCreatorV3(romSettings));
		combo.addItem(new CircleCreatorV4(romSettings));
		combo.addItem(new PlanetV1(romSettings));
		combo.addItem(new ArcGradientCreator(romSettings));
		combo.addItem(new ArcSunCreator(romSettings));
		combo.addItem(new ArcQuaterCreator2(romSettings));
		combo.addItem(new DecimalArcCreator(romSettings));
		combo.addItem(new HoneycombCreator(romSettings));
		// combo.addItem(new AOKPCircleModCreator(romSettings));

		combo.addItem(new XORCircle(romSettings));
		combo.addItem(new XORSquare(romSettings));
		combo.addItem(new XORCenter(romSettings));

		combo.addItem(new BoxCreatorV1(romSettings));
		combo.addItem(new BoxCreatorV2(romSettings));
		combo.addItem(new BoxCreatorV3(romSettings));

		combo.addItem(new BubbleCreator(romSettings));
		combo.addItem(new BallBattery(romSettings));

		combo.addItem(new Brick25(romSettings));
		combo.addItem(new BrickBattV1(romSettings));
		combo.addItem(new BrickBattV2(romSettings));
		combo.addItem(new BrickBattNoGap(romSettings));
		combo.addItem(new BrickBatteryDecimalV1(romSettings));
		combo.addItem(new BrickBatteryDecimalV2(romSettings));
		combo.addItem(new BrickBatteryDecimalV3(romSettings));

		combo.addItem(new TwoBars(romSettings));
		combo.addItem(new SliderCreator(romSettings));
		combo.addItem(new DecimalBarCreator(romSettings));
		combo.addItem(new DecimalBar2Creator(romSettings));
		combo.addItem(new DecimalBar3Creator(romSettings));
		combo.addItem(new BinaryBarsCreator(romSettings));
		combo.addItem(new BinarySquaresCreator(romSettings));
		combo.addItem(new SamsungStockBatteryCreator(romSettings));
		combo.addItem(new Rush25Battery(romSettings));

		combo.addItem(new SimpleBatteryV1(romSettings));
		combo.addItem(new SimpleBatteryV2(romSettings));

		combo.addItem(new BatterySymbolV1(romSettings));
		combo.addItem(new BatterySymbolV2(romSettings));
		combo.addItem(new BatterySymbolCreatorV3(romSettings));
		combo.addItem(new BatterySymbolVerticalV1(romSettings));
		combo.addItem(new BatterySymbolVerticalV2(romSettings));

		combo.addItem(new AppleBatteryCreator(romSettings));
		combo.addItem(new KnobBatteryCreator(romSettings));

		combo.addItem(new ClockCreator(romSettings));
		combo.addItem(new ClockPointerCreator(romSettings));

		combo.addItem(new CastawayCreator(romSettings));
		combo.addItem(new CastawayCreatorFlip(romSettings));
		combo.addItem(new CastawayCreator2(romSettings));
		combo.addItem(new CastawayCreator2Flip(romSettings));

		combo.addItem(new TachoCreator(romSettings));
		combo.addItem(new TachoWideV1(romSettings));
		combo.addItem(new TachoWideV2(romSettings));
		combo.addItem(new TachoWideV3(romSettings));
		combo.addItem(new TachoWideV4(romSettings));
		combo.addItem(new TachoWideV5(romSettings));
		combo.addItem(new TachoGauge(romSettings));

		combo.addItem(new ZoopaClock(romSettings));
		combo.addItem(new ZoopaClockWide(romSettings));
		combo.addItem(new ZoopaCircle(romSettings));
		combo.addItem(new ZoopaCircleV2(romSettings));
		combo.addItem(new Zoopa3Quater(romSettings));
		combo.addItem(new ZoopaGaugeV1(romSettings));
		combo.addItem(new ZoopaGaugeV2(romSettings));
		combo.addItem(new ZoopaWideV1(romSettings));
		combo.addItem(new ZoopaWideV2(romSettings));
		combo.addItem(new ZoopaWideV3(romSettings));

		combo.addItem(new BarGraphWide(romSettings));
		combo.addItem(new BarGraphWideV2(romSettings));
		combo.addItem(new BarGraphWideV3(romSettings));
		combo.addItem(new BarGraphWideV4(romSettings));

		combo.addItem(new FaecherCreatorWide(romSettings));
		combo.addItem(new TachoCreator3Quater(romSettings));
		combo.addItem(new ScalaBatteryCreator(romSettings));
		combo.addItem(new XORApfelCreator(romSettings));
		combo.addItem(new FontOnlyCreator(romSettings));
	}

	private void initIconList() {
		battIconList.setCellRenderer(new IconListCellRenderer());
		battIconList.setBackground(Color.black);
	}

	private void initUI() {
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final AbstractIconCreator cre = (AbstractIconCreator) combo.getSelectedItem();

				if (cre != null) {
					activBattCreator = cre;
					settingsPanel.enableSupportedFeatures(cre);
					settingsPanel.setSettings(cre.getBattSettings());
					create();
				}
			}
		});

		if (combo.getItemCount() > 0)
			combo.setSelectedIndex(0);
		activBattCreator = (AbstractIconCreator) combo.getSelectedItem();

		openFolderButton.setToolTipText("Open the folder where the icons and overviews are strored");
		openFolderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final AbstractIconCreator cre = (AbstractIconCreator) combo.getSelectedItem();
				if (!(cre instanceof NoBattIcons)) {
					final String path = cre.getPath();
					StaticExecutor.openFolder(path);
				}
			}
		});
		exportZipButton.setToolTipText("Export icons into Zip-File...attention...this is NOT a flashable Zip!");
		exportZipButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				zipExporting();
			}
		});

		combo.setToolTipText("Choose your Battery-Renderer...then press play-button");
		combo.setRenderer(new BattCreatorListCellRenderer());
		combo.setMaximumRowCount(15);
		combo.setMaximumSize(new Dimension(300, 40));
		// Icon Liste
		final JScrollPane scroller = new JScrollPane();
		scroller.add(battIconList);
		scroller.getViewport().setView(battIconList);
		scroller.setPreferredSize(new Dimension(750, 680));

		// Tabbed Pane
		final JTabbedPane tabPane = new JTabbedPane();

		// battTabPane.setTabPlacement(JTabbedPane.LEFT);
		tabPane.addTab("Overview", IconStore.overIcon, battOverviewPanel, "Get an Overview of your icons");
		tabPane.addTab("Swagger Overview", IconStore.overSmallIcon, battSmallOverviewPanel, "Swagger with your creations with thios overview");
		tabPane.addTab("List", IconStore.listIcon, scroller, "Get a List-Overview of your icons");
		// Adding Howto, if Helpfile exists !
		final File howto = new File("./help/Howto-Render-Battery.htm");
		if (howto.exists()) {
			tabPane.addTab("HowTo & Help", IconStore.helpIcon, new HTMLFileDisplay(howto), "Some things you might want to know :-)");
		}

		setLayout(new BorderLayout());

		final JPanel p = new JPanel(new BorderLayout());
		final JToolBar toolBar = makeButtonBar();
		p.add(tabPane, BorderLayout.CENTER);
		p.add(toolBar, BorderLayout.NORTH);
		p.add(anibar, BorderLayout.SOUTH);

		this.add(p, BorderLayout.CENTER);
		this.add(settingsPanel, BorderLayout.WEST);
	}

	/**
	 * Creating buttonbar
	 */
	private JToolBar makeButtonBar() {
		final JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(combo);
		toolBar.add(new JPanel());
		toolBar.add(openFolderButton);
		toolBar.add(exportZipButton);
		// toolBar.add(anibar);
		return toolBar;
	}

	/**
	 * Creates all Icons
	 * 
	 * @param romSettings
	 */
	private void create() {
		createAllImages(null);
	}

	/**
	 * Creates all Icons
	 */
	public void createAllImages(final RomSettings romSettings) {
		final AbstractIconCreator cre = (AbstractIconCreator) combo.getSelectedItem();
		cre.setBattSettings(settingsPanel.getSettings());
		if (romSettings != null)
			cre.setRomSettings(romSettings);

		cre.createAllImages();
		if (cre.getOverviewIcon().equals(IconStore.nothingIcon)) {
			battSmallOverviewPanel.setText("Choose your Battery Style in dropdown box");
			battOverviewPanel.setText("Choose your Battery Style in dropdown box");
			openFolderButton.setEnabled(false);
			exportZipButton.setEnabled(false);
		} else {
			battSmallOverviewPanel.setText("");
			battOverviewPanel.setText("");
			openFolderButton.setEnabled(true);
			exportZipButton.setEnabled(true);
		}
		battSmallOverviewPanel.setOverview(cre.getOverviewSmallIcon(), 100);
		battOverviewPanel.setOverview(cre.getOverviewIcon(), 100);
		battIconList.removeAll();
		battIconList.setListData(cre.getFilenames());
		battIconList.repaint();
		anibar.setIcons(cre.getIcons());
		exportZipButton.setVisible(GlobalSettings.INSTANCE.isShowExportToZipButton());
	}

	/**
	 * Renderer for BattCreator-Combo
	 */
	private class BattCreatorListCellRenderer implements ListCellRenderer<AbstractIconCreator> {
		protected DefaultListCellRenderer	defaultRenderer	= new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends AbstractIconCreator> list, final AbstractIconCreator value, final int index,
				final boolean isSelected, final boolean cellHasFocus) {
			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof AbstractIconCreator) {
				if (isSelected)
					renderer.setBackground(Color.darkGray.darker());
				else
					renderer.setBackground(Color.black);
				renderer.setForeground(Color.lightGray);
				final AbstractIconCreator creator = value;
				if (creator != null && renderer.getIcon() == null) {
					final ImageIcon icon = creator.createImage(45, false);
					final BufferedImage bimg = StaticImageHelper.resize2Height(StaticImageHelper.convertImageIcon(icon), 32);
					renderer.setIcon(new ImageIcon(bimg));
					if (creator.isNativeXXHDPI()) {
						renderer.setToolTipText(getTooltip(creator));
						renderer.setForeground(Color.white);
					} else {
						renderer.setToolTipText(getTooltip(creator));
					}

				}
			}
			return renderer;
		}

		private String getTooltip(final AbstractIconCreator creator) {
			String html = "<html>";

			if (creator.isNativeXXHDPI()) {
				html += "<font size=5 color=green>";
				html += "<b>" + "This Battery Renderer is native xxhdpi" + "</b><br><hr>";
				html += "</font>";
			} else {
				html += "<font size=5 color=blue>";
				html += "<b>" + "This Battery Renderer is native xhdpi" + "</b><br><hr>";
				html += "</font>";
			}
			html += "<font size=3 color=black>";
			html += "Support for the following misc options:<br>";

			if (creator.supportsNoBg())
				html += "- NoBackground<br>";
			if (creator.supportsFlip())
				html += "- Flip<br>";
			if (creator.supportsLinearGradient())
				html += "- LinearGradient<br>";
			if (creator.supportsBattGradient())
				html += "- BattGradient<br>";
			if (creator.supportsTexture())
				html += "- Texture<br>";
			if (creator.supportsExtraColor1())
				html += "- ExtraColor 1<br>";
			if (creator.supportsExtraColor2())
				html += "- ExtraColor 2<br>";
			if (creator.supportsStrokeWidth())
				html += "- Stroke-Width<br>";
			if (creator.supportsZeiger())
				html += "- Zeiger<br>";

			html += "</font>";
			html += "</html>";
			return html;
		}

	}

	/**
	 * Renderer f�r IconList
	 */
	private class IconListCellRenderer implements ListCellRenderer<Object> {
		protected DefaultListCellRenderer	defaultRenderer	= new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {
			String iconName = null;

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof String) {
				iconName = (String) value;
				renderer.setBorder(new EmptyBorder(1, 1, 1, 1));
				renderer.setText(iconName);
				renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				renderer.setBorder(new EmptyBorder(1, 1, 1, 1));
				if (getActivBattCreator() != null)
					renderer.setIcon(getActivBattCreator().getIcons().elementAt(index));
			}
			return renderer;
		}
	}

	/**
	 * @return the activBattCreator
	 */
	public AbstractIconCreator getActivBattCreator() {
		return activBattCreator;
	}

	private void zipExporting() {
		final AbstractIconCreator cre = (AbstractIconCreator) combo.getSelectedItem();
		if (!(cre instanceof NoBattIcons)) {
			final ZipElementCollection zipCollection = new ZipElementCollection();
			zipCollection.addElements(cre.getAllFilenamesAndPath(), cre.getRomSettings().getSystemUIDrawableFolder() + "/");
			try {
				final String name = cre.toString() + "(" + cre.getRomSettings().getSystemUIDrawableFolder() + ")";
				final boolean saved = ZipMaker.createZipArchive(zipCollection.getZipelEments(), name);
				if (saved == true) {
					JOptionPane.showMessageDialog(BatteryPanel.this, "Zip was created successfully", "Zip creating", JOptionPane.INFORMATION_MESSAGE);
				}

			} catch (final Exception e) {
				// Error during zip...
				JOptionPane.showMessageDialog(BatteryPanel.this, //
						"ERROR: Zip was not created !!!\n" //
								+ e.getMessage(), "Zip creating ERROR", //
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}

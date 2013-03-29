package de.og.batterycreator.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.creators.batt.AOKPCircleModCreator;
import de.og.batterycreator.creators.batt.AbstractIconCreator;
import de.og.batterycreator.creators.batt.AppleBatteryCreator;
import de.og.batterycreator.creators.batt.ArcCreator;
import de.og.batterycreator.creators.batt.ArcCreator2;
import de.og.batterycreator.creators.batt.ArcCreator3;
import de.og.batterycreator.creators.batt.ArcCreator4;
import de.og.batterycreator.creators.batt.ArcDecimalCreator;
import de.og.batterycreator.creators.batt.ArcGradientCreator;
import de.og.batterycreator.creators.batt.ArcQuaterCreator2;
import de.og.batterycreator.creators.batt.ArcSunCreator;
import de.og.batterycreator.creators.batt.BatterySymbolCreator;
import de.og.batterycreator.creators.batt.BatteryVerticalSymbolCreator;
import de.og.batterycreator.creators.batt.BinaryBarsCreator;
import de.og.batterycreator.creators.batt.BinarySquaresCreator;
import de.og.batterycreator.creators.batt.Box2Creator;
import de.og.batterycreator.creators.batt.BoxCreator;
import de.og.batterycreator.creators.batt.BrickBattCreator;
import de.og.batterycreator.creators.batt.BrickBattNoGapCreator;
import de.og.batterycreator.creators.batt.BrickDecimal2Creator;
import de.og.batterycreator.creators.batt.BrickDecimal3Creator;
import de.og.batterycreator.creators.batt.BrickDecimalCreator;
import de.og.batterycreator.creators.batt.BubbleCreator;
import de.og.batterycreator.creators.batt.CastawayCreator;
import de.og.batterycreator.creators.batt.CastawayCreator2;
import de.og.batterycreator.creators.batt.CastawayCreator2Flip;
import de.og.batterycreator.creators.batt.CastawayCreatorFlip;
import de.og.batterycreator.creators.batt.ClockCreator;
import de.og.batterycreator.creators.batt.ClockPointerCreator;
import de.og.batterycreator.creators.batt.DecimalBar2Creator;
import de.og.batterycreator.creators.batt.DecimalBarCreator;
import de.og.batterycreator.creators.batt.FontOnlyCreator;
import de.og.batterycreator.creators.batt.HoneycombCreator;
import de.og.batterycreator.creators.batt.NoBattIcons;
import de.og.batterycreator.creators.batt.ScalaBatteryCreator;
import de.og.batterycreator.creators.batt.TachoCreator;
import de.og.batterycreator.creators.batt.TachoCreator3Quater;
import de.og.batterycreator.creators.batt.TachoCreatorWide;
import de.og.batterycreator.creators.batt.TachoCreatorWideV2;
import de.og.batterycreator.creators.batt.TachoCreatorWideV3;
import de.og.batterycreator.creators.batt.TachoCreatorWideV4;
import de.og.batterycreator.creators.batt.TwoBarsCreator;
import de.og.batterycreator.creators.batt.XORAndroidCreator;
import de.og.batterycreator.creators.batt.XORApfelCreator;
import de.og.batterycreator.creators.batt.XORSkullCreator;
import de.og.batterycreator.creators.batt.XORVnvCreator;
import de.og.batterycreator.gui.cfg.BattSettingsPanel;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.OverviewPanel;

public class BatteryPanel extends JPanel {
	private static final long serialVersionUID = -5956664471952448919L;

	private final JComboBox<AbstractIconCreator> combo = new JComboBox<AbstractIconCreator>();

	private final JList<String> battIconList = new JList<String>();
	private AbstractIconCreator activBattCreator = null;
	private final OverviewPanel battOverviewPanel = new OverviewPanel();

	private final BattSettingsPanel settingsPanel = new BattSettingsPanel();

	public BatteryPanel(final RomSettings romSettings) {
		super();
		fillCreatorList(romSettings);
		initIconList();
		initUI();
	}

	private void fillCreatorList(final RomSettings romSettings) {
		combo.addItem(new NoBattIcons(romSettings));
		combo.addItem(new ArcCreator(romSettings));
		combo.addItem(new ArcCreator2(romSettings));
		combo.addItem(new ArcCreator3(romSettings));
		combo.addItem(new ArcCreator4(romSettings));
		combo.addItem(new ArcGradientCreator(romSettings));
		combo.addItem(new ArcSunCreator(romSettings));
		combo.addItem(new ArcQuaterCreator2(romSettings));
		combo.addItem(new ArcDecimalCreator(romSettings));
		combo.addItem(new HoneycombCreator(romSettings));
		combo.addItem(new AOKPCircleModCreator(romSettings));
		combo.addItem(new BoxCreator(romSettings));
		combo.addItem(new Box2Creator(romSettings));
		combo.addItem(new BubbleCreator(romSettings));
		combo.addItem(new BrickBattCreator(romSettings));
		combo.addItem(new BrickBattNoGapCreator(romSettings));
		combo.addItem(new BrickDecimalCreator(romSettings));
		combo.addItem(new BrickDecimal2Creator(romSettings));
		combo.addItem(new BrickDecimal3Creator(romSettings));
		combo.addItem(new TwoBarsCreator(romSettings));
		combo.addItem(new DecimalBarCreator(romSettings));
		combo.addItem(new DecimalBar2Creator(romSettings));
		combo.addItem(new BinaryBarsCreator(romSettings));
		combo.addItem(new BinarySquaresCreator(romSettings));
		combo.addItem(new BatterySymbolCreator(romSettings));
		combo.addItem(new BatteryVerticalSymbolCreator(romSettings));
		combo.addItem(new AppleBatteryCreator(romSettings));
		combo.addItem(new ClockCreator(romSettings));
		combo.addItem(new ClockPointerCreator(romSettings));
		combo.addItem(new CastawayCreator(romSettings));
		combo.addItem(new CastawayCreatorFlip(romSettings));
		combo.addItem(new CastawayCreator2(romSettings));
		combo.addItem(new CastawayCreator2Flip(romSettings));
		combo.addItem(new TachoCreator(romSettings));
		combo.addItem(new TachoCreatorWide(romSettings));
		combo.addItem(new TachoCreatorWideV2(romSettings));
		combo.addItem(new TachoCreatorWideV3(romSettings));
		combo.addItem(new TachoCreatorWideV4(romSettings));
		combo.addItem(new TachoCreator3Quater(romSettings));
		combo.addItem(new ScalaBatteryCreator(romSettings));
		combo.addItem(new XORAndroidCreator(romSettings));
		combo.addItem(new XORApfelCreator(romSettings));
		combo.addItem(new XORSkullCreator(romSettings));
		combo.addItem(new XORVnvCreator(romSettings));
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
					settingsPanel.setSettings(cre.getBattSettings());
					settingsPanel.enableSupportedFeatures(cre.supportsFlip(), cre.supportsStrokeWidth(), cre.supportsNoBg(), cre.supportsGradient());
					create();
				}
			}
		});

		if (combo.getItemCount() > 0)
			combo.setSelectedIndex(0);
		activBattCreator = (AbstractIconCreator) combo.getSelectedItem();

		combo.setToolTipText("Choose your IconCreator...then press play-button");
		combo.setRenderer(new BattCreatorListCellRenderer());
		combo.setMaximumRowCount(15);
		combo.setMaximumSize(new Dimension(300, 40));
		// Icon Liste
		final JScrollPane scroller = new JScrollPane();
		scroller.add(battIconList);
		scroller.getViewport().setView(battIconList);
		scroller.setPreferredSize(new Dimension(750, 500));

		// Tabbed Pane
		final JTabbedPane battTabPane = new JTabbedPane();

		// battTabPane.setTabPlacement(JTabbedPane.LEFT);
		battTabPane.addTab("Overview", IconStore.overIcon, battOverviewPanel, "Get an Overview of your icons");
		battTabPane.addTab("List", IconStore.listIcon, scroller, "Get an Overview of your icons");
		setLayout(new BorderLayout());

		final JPanel p = new JPanel(new BorderLayout());
		final JToolBar toolBar = makeButtonBar();
		p.add(battTabPane, BorderLayout.CENTER);
		p.add(toolBar, BorderLayout.NORTH);

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
		if (cre.getOverviewIcon().equals(IconStore.nothingIcon))
			battOverviewPanel.setText("Choose your Battery Style in dropdown box");
		else
			battOverviewPanel.setText("");
		battOverviewPanel.setOverview(cre.getOverviewIcon());
		battIconList.removeAll();
		battIconList.setListData(cre.getFilenames());
		battIconList.repaint();
	}

	/**
	 * Renderer for BattCreator-Combo
	 */
	private class BattCreatorListCellRenderer implements ListCellRenderer<AbstractIconCreator> {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends AbstractIconCreator> list, final AbstractIconCreator value, final int index,
				final boolean isSelected, final boolean cellHasFocus) {
			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof AbstractIconCreator) {
				if (isSelected)
					renderer.setBackground(Color.darkGray.darker());
				else
					renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				final AbstractIconCreator creator = value;
				if (creator != null && renderer.getIcon() == null) {
					final ImageIcon icon = creator.createImage(45, false);
					final BufferedImage bimg = StaticImageHelper.resize2Height(StaticImageHelper.convertImageIcon(icon), 32);
					renderer.setIcon(new ImageIcon(bimg));
				}
			}
			return renderer;
		}
	}

	/**
	 * Renderer für IconList
	 */
	private class IconListCellRenderer implements ListCellRenderer<Object> {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

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

}

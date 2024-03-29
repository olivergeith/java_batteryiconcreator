package de.og.batterycreator.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import og.basics.gui.html.HTMLFileDisplay;
import og.basics.gui.image.StaticImageHelper;
import og.basics.util.StaticExecutor;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.creators.wifi.AbstractWifiCreator;
import de.og.batterycreator.creators.wifi.BrickWifi1Creator;
import de.og.batterycreator.creators.wifi.BrickWifi2Creator;
import de.og.batterycreator.creators.wifi.CircleWifiCreator;
import de.og.batterycreator.creators.wifi.EmmyWifiCreator;
import de.og.batterycreator.creators.wifi.ForkWifiCreator;
import de.og.batterycreator.creators.wifi.NiceWifiCreator;
import de.og.batterycreator.creators.wifi.NoWifiIcons;
import de.og.batterycreator.creators.wifi.StarGateWifiCreator;
import de.og.batterycreator.creators.wifi.TextWifiCreator;
import de.og.batterycreator.creators.wifi.TopCornerWifiCreator;
import de.og.batterycreator.creators.wifi.TowerWifi2Creator;
import de.og.batterycreator.creators.wifi.TowerWifiCreator;
import de.og.batterycreator.gui.cfg.WifiSignaleSettingsPanel;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.animator.AnimatorBar;
import de.og.batterycreator.gui.widgets.overview.OverviewPanel;

public class WifiPanel extends JPanel {
	private static final long						serialVersionUID	= -4657987890334428414L;

	private final JComboBox<AbstractWifiCreator>	combo				= new JComboBox<AbstractWifiCreator>();
	private final JButton							openFolderButton	= new JButton(IconStore.folder2Icon);
	private AbstractWifiCreator						activWifiCreator;
	private final WifiSignaleSettingsPanel			settingsPanel		= new WifiSignaleSettingsPanel();
	private final OverviewPanel						overPane			= new OverviewPanel();
	private final RomSettings						romSettings;

	private final JList<String>						iconList			= new JList<String>();

	private final AnimatorBar						anibar				= new AnimatorBar(200);				// millies

	public WifiPanel(final RomSettings romSettings) {
		super();
		this.romSettings = romSettings;
		fillFillCreatorList();
		initIconList();
		initUI();
	}

	private void fillFillCreatorList() {
		combo.addItem(new NoWifiIcons(romSettings));
		combo.addItem(new NiceWifiCreator(romSettings));
		combo.addItem(new EmmyWifiCreator(romSettings));
		combo.addItem(new BrickWifi1Creator(romSettings));
		combo.addItem(new BrickWifi2Creator(romSettings));
		combo.addItem(new TowerWifiCreator(romSettings));
		combo.addItem(new TowerWifi2Creator(romSettings));
		combo.addItem(new TopCornerWifiCreator(romSettings));
		combo.addItem(new ForkWifiCreator(romSettings));
		combo.addItem(new StarGateWifiCreator(romSettings));
		combo.addItem(new CircleWifiCreator(romSettings));
		combo.addItem(new TextWifiCreator(romSettings));
	}

	private void initIconList() {
		iconList.setCellRenderer(new IconListCellRenderer());
		iconList.setBackground(Color.black);
	}

	private void initUI() {

		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final AbstractWifiCreator cre = (AbstractWifiCreator) combo.getSelectedItem();
				setActivWifiCreator((AbstractWifiCreator) combo.getSelectedItem());
				settingsPanel.setSettings(cre.getSettings());
				create();
			}

		});
		combo.setRenderer(new WifiCreatorListCellRenderer());
		combo.setSelectedIndex(0);
		combo.setToolTipText("Choose your WifiCreator...then press play-button");
		combo.setMaximumRowCount(10);
		combo.setMaximumSize(new Dimension(400, 40));
		// combo.setPreferredSize(new Dimension(400, 30));

		activWifiCreator = (AbstractWifiCreator) combo.getSelectedItem();
		settingsPanel.setSettings(activWifiCreator.getSettings());

		openFolderButton.setToolTipText("Open the folder where the icons and overviews are strored");
		openFolderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final AbstractWifiCreator cre = (AbstractWifiCreator) combo.getSelectedItem();
				if (!(cre instanceof NoWifiIcons)) {
					final String path = cre.getPath();
					StaticExecutor.openFolder(path);
				}
			}
		});

		setLayout(new BorderLayout());

		// Icon Liste
		final JScrollPane scroller = new JScrollPane();
		scroller.add(iconList);
		scroller.getViewport().setView(iconList);
		scroller.setPreferredSize(new Dimension(750, 680));

		// Tabbed Pane
		final JTabbedPane tabPane = new JTabbedPane();
		tabPane.addTab("Overview", IconStore.overIcon, overPane, "Get an Overview of your icons");
		tabPane.addTab("List", IconStore.listIcon, scroller, "Get an Overview of your icons");
		// Adding Howto, if Helpfile exists !
		final File howto = new File("./help/Howto-Render-SignalWifi.htm");
		if (howto.exists()) {
			tabPane.addTab("HowTo & Help", IconStore.helpIcon, new HTMLFileDisplay(howto), "Some things you might want to know :-)");
		}

		final JPanel p = new JPanel(new BorderLayout());
		p.add(tabPane, BorderLayout.CENTER);
		p.add(makeButtonBar(), BorderLayout.NORTH);
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
		return toolBar;
	}

	private void create() {
		createAllImages(null);
	}

	public void createAllImages(final RomSettings romSettings) {
		if (!activWifiCreator.toString().equals(NoWifiIcons.name)) {
			if (romSettings != null)
				activWifiCreator.setRomSettings(romSettings);
			activWifiCreator.setSettings(settingsPanel.getSettings());
			activWifiCreator.createAllImages();
			overPane.setOverview(activWifiCreator.getOverviewIcon());
			overPane.setText("");
			anibar.setIcons(activWifiCreator.getIcons());
			iconList.removeAll();
			iconList.setListData(activWifiCreator.getFilenames());
			iconList.repaint();
			openFolderButton.setEnabled(true);
		} else {
			overPane.setOverview(IconStore.nothingIcon);
			overPane.setText("    No Wifi Icons selected...choose Wifi icon style in Toolbar");
			anibar.setIcons(null);
			iconList.removeAll();
			iconList.setListData(new Vector<String>());
			iconList.repaint();
			openFolderButton.setEnabled(false);
		}

	}

	/**
	 * Renderer for WifiCreator-Combo
	 */
	private class WifiCreatorListCellRenderer implements ListCellRenderer<AbstractWifiCreator> {
		protected DefaultListCellRenderer	defaultRenderer	= new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends AbstractWifiCreator> list, final AbstractWifiCreator value, final int index,
				final boolean isSelected, final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof AbstractWifiCreator) {
				if (isSelected)
					renderer.setBackground(Color.darkGray.darker());
				else
					renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				// wenn auch in der Combo selbst ein Icon sein soll:
				final AbstractWifiCreator creator = value;
				if (creator != null && renderer.getIcon() == null) {
					ImageIcon icon = creator.createImage(3, true);
					if (icon == null) {
						icon = IconStore.nothingIcon;
						renderer.setIcon(icon);
					} else {
						final BufferedImage bimg = StaticImageHelper.resize2Height(StaticImageHelper.convertImageIcon(icon), 32);
						renderer.setIcon(new ImageIcon(bimg));
					}
				}
			}
			return renderer;
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
				if (getActivWifiCreator() != null)
					renderer.setIcon(getActivWifiCreator().getIcons().elementAt(index));
			}
			return renderer;
		}
	}

	/**
	 * @return the activWifiCreator
	 */
	public AbstractWifiCreator getActivWifiCreator() {
		return activWifiCreator;
	}

	/**
	 * @param activWifiCreator
	 *            the activWifiCreator to set
	 */
	private void setActivWifiCreator(final AbstractWifiCreator activWifiCreator) {
		this.activWifiCreator = activWifiCreator;
	}
}

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
import de.og.batterycreator.creators.signal.AbstractSignalCreator;
import de.og.batterycreator.creators.signal.ArcSignalCreator;
import de.og.batterycreator.creators.signal.BarsSignalCreator;
import de.og.batterycreator.creators.signal.ForkSignalCreator;
import de.og.batterycreator.creators.signal.NiceSignalCreator;
import de.og.batterycreator.creators.signal.NoSignalIcons;
import de.og.batterycreator.creators.signal.TowerSignalCreator;
import de.og.batterycreator.gui.cfg.WifiSignaleSettingsPanel;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.animator.AnimatorBar;
import de.og.batterycreator.gui.widgets.overview.OverviewPanel;

public class SignalPanel extends JPanel {
	private static final long						serialVersionUID	= -4657987890334428414L;

	private final JComboBox<AbstractSignalCreator>	combo				= new JComboBox<AbstractSignalCreator>();
	private final JButton							openFolderButton	= new JButton(IconStore.folder2Icon);
	private final OverviewPanel						overPane			= new OverviewPanel();
	private final WifiSignaleSettingsPanel			settingsPanel		= new WifiSignaleSettingsPanel();
	private AbstractSignalCreator					activSignalCreator;
	private RomSettings								romSettings;
	private final JList<String>						iconList			= new JList<String>();
	private final AnimatorBar						anibar				= new AnimatorBar(250);					// millies

	public SignalPanel(final RomSettings romSettings) {
		super();
		setRomSettings(romSettings);
		fillFillCreatorList();
		initIconList();
		initUI();
	}

	private void fillFillCreatorList() {
		combo.addItem(new NoSignalIcons(romSettings));
		combo.addItem(new NiceSignalCreator(romSettings));
		combo.addItem(new BarsSignalCreator(romSettings));
		combo.addItem(new ForkSignalCreator(romSettings));
		combo.addItem(new ArcSignalCreator(romSettings));
		combo.addItem(new TowerSignalCreator(romSettings));
	}

	private void initIconList() {
		iconList.setCellRenderer(new IconListCellRenderer());
		iconList.setBackground(Color.black);
	}

	private void initUI() {

		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				setActivSignalCreator((AbstractSignalCreator) combo.getSelectedItem());
				final AbstractSignalCreator cre = (AbstractSignalCreator) combo.getSelectedItem();
				settingsPanel.setSettings(cre.getSettings());
				create();
			}

		});
		combo.setRenderer(new SignalCreatorListCellRenderer());
		combo.setSelectedIndex(0);
		combo.setToolTipText("Choose your SignalCreator...then press play-button");
		combo.setMaximumRowCount(10);
		// combo.setPreferredSize(new Dimension(400, 30));
		combo.setMaximumSize(new Dimension(400, 40));
		setActivSignalCreator((AbstractSignalCreator) combo.getSelectedItem());
		setLayout(new BorderLayout());
		openFolderButton.setToolTipText("Open the folder where the icons and overviews are strored");
		openFolderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				final AbstractSignalCreator cre = (AbstractSignalCreator) combo.getSelectedItem();
				if (!(cre instanceof NoSignalIcons)) {
					final String path = cre.getPath();
					StaticExecutor.openFolder(path);
				}
			}
		});

		// Icon Liste
		final JScrollPane scroller = new JScrollPane();
		scroller.add(iconList);
		scroller.getViewport().setView(iconList);
		scroller.setPreferredSize(new Dimension(750, 680));

		// Tabbed Pane
		final JTabbedPane tabPane = new JTabbedPane();
		// battTabPane.setTabPlacement(JTabbedPane.LEFT);
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
		if (!activSignalCreator.toString().equals(NoSignalIcons.name)) {
			if (romSettings != null)
				activSignalCreator.setRomSettings(romSettings);
			activSignalCreator.setSettings(settingsPanel.getSettings());
			activSignalCreator.createAllImages();
			overPane.setOverview(activSignalCreator.getOverviewIcon());
			overPane.setText("");
			anibar.setIcons(activSignalCreator.getIcons());
			iconList.removeAll();
			iconList.setListData(activSignalCreator.getFilenames());
			iconList.repaint();
			openFolderButton.setEnabled(true);
		} else {
			overPane.setOverview(IconStore.nothingIcon);
			overPane.setText("    No Signal Icons selected...choose Signal icon style in Toolbar");
			anibar.setIcons(null);
			iconList.removeAll();
			iconList.setListData(new Vector<String>());
			iconList.repaint();
			openFolderButton.setEnabled(false);
		}

	}

	/**
	 * Renderer for SignalCreator-Combo
	 */
	private class SignalCreatorListCellRenderer implements ListCellRenderer<AbstractSignalCreator> {
		protected DefaultListCellRenderer	defaultRenderer	= new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends AbstractSignalCreator> list, final AbstractSignalCreator value, final int index,
				final boolean isSelected, final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof AbstractSignalCreator) {
				if (isSelected)
					renderer.setBackground(Color.darkGray.darker());
				else
					renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				// wenn auch in der Combo selbst ein Icon sein soll:
				final AbstractSignalCreator creator = value;
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
				if (getActivSignalCreator() != null)
					renderer.setIcon(getActivSignalCreator().getIcons().elementAt(index));
			}
			return renderer;
		}
	}

	public OverviewPanel getSignalOverviewPanel() {
		return overPane;
	}

	/**
	 * @return the romSettings
	 */
	public RomSettings getRomSettings() {
		return romSettings;
	}

	/**
	 * @param romSettings
	 *            the romSettings to set
	 */
	public void setRomSettings(final RomSettings romSettings) {
		this.romSettings = romSettings;
	}

	/**
	 * @return the activSignalCreator
	 */
	public AbstractSignalCreator getActivSignalCreator() {
		return activSignalCreator;
	}

	/**
	 * @param activSignalCreator
	 *            the activSignalCreator to set
	 */
	private void setActivSignalCreator(final AbstractSignalCreator activSignalCreator) {
		this.activSignalCreator = activSignalCreator;
	}

}

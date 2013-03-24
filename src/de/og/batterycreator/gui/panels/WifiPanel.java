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
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;

import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.creators.wifi.AbstractWifiCreator;
import de.og.batterycreator.creators.wifi.BrickWifi1Creator;
import de.og.batterycreator.creators.wifi.BrickWifi2Creator;
import de.og.batterycreator.creators.wifi.CircleWifiCreator;
import de.og.batterycreator.creators.wifi.ForkWifiCreator;
import de.og.batterycreator.creators.wifi.NoWifiIcons;
import de.og.batterycreator.creators.wifi.StarGateWifiCreator;
import de.og.batterycreator.creators.wifi.TextWifiCreator;
import de.og.batterycreator.creators.wifi.TopCornerWifiCreator;
import de.og.batterycreator.creators.wifi.TowerWifi2Creator;
import de.og.batterycreator.creators.wifi.TowerWifiCreator;
import de.og.batterycreator.gui.cfg.WifiSignaleSettingsPanel;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.OverviewPanel;

public class WifiPanel extends JPanel {
	private static final long serialVersionUID = -4657987890334428414L;

	private final JComboBox<AbstractWifiCreator> combo = new JComboBox<AbstractWifiCreator>();
	private AbstractWifiCreator activWifiCreator;
	private final WifiSignaleSettingsPanel settingsPanel = new WifiSignaleSettingsPanel();
	private final OverviewPanel overPane = new OverviewPanel();
	private final RomSettings romSettings;

	public WifiPanel(final RomSettings romSettings) {
		super();
		this.romSettings = romSettings;
		fillFillCreatorList();
		initUI();
	}

	private void fillFillCreatorList() {
		combo.addItem(new NoWifiIcons(romSettings));
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
		combo.setMaximumSize(new Dimension(300, 40));
		activWifiCreator = (AbstractWifiCreator) combo.getSelectedItem();
		settingsPanel.setSettings(activWifiCreator.getSettings());

		setLayout(new BorderLayout());
		overPane.add(makeButtonBar(), BorderLayout.NORTH);
		this.add(overPane, BorderLayout.CENTER);
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
		} else {
			overPane.setOverview(IconStore.nothingIcon);
			overPane.setText("    No Wifi Icons selected...choose Wifi icon style in Toolbar");
		}

	}

	/**
	 * Renderer for WifiCreator-Combo
	 */
	private class WifiCreatorListCellRenderer implements ListCellRenderer<AbstractWifiCreator> {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

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

package de.og.batterycreator.gui.panels.iconset;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import og.basics.gui.html.HTMLFileDisplay;
import og.basics.util.StaticExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.overview.OverviewPanel;

public class IconSetSelector extends JPanel {
	private static final long			serialVersionUID	= -2767025548199058416L;
	private static final Logger			LOGGER				= LoggerFactory.getLogger(IconSetSelector.class);

	private final JList<ImageIcon>		list				= new JList<ImageIcon>();
	private final JComboBox<ImageIcon>	combo				= new JComboBox<ImageIcon>();
	private final JButton				openFolderButton	= new JButton(IconStore.folder2Icon);
	private final OverviewPanel			overPane			= new OverviewPanel();
	private final ImageIcon				nada				= IconStore.nothingIcon;
	private Vector<String>				filenamesAndPath	= new Vector<String>();
	private final Vector<IconSet>		iconSets			= new Vector<IconSet>();
	private File						howtoHtml;
	private final String				rootDir;
	private final String				setTypeName;

	public IconSetSelector(final String setTypeName, final String rootDir) {
		super();
		this.rootDir = rootDir;
		this.setTypeName = setTypeName;
		initUI();
	}

	public String getProviderName() {
		return setTypeName;
	}

	private File[] findCustomDirs(final File dir) {
		if (dir.isDirectory()) {
			final File[] subdirs = dir.listFiles(new FileFilter() {
				@Override
				public boolean accept(final File file) {
					return file.isDirectory() && IconSet.findPNGs(file).length > 0;
				}
			});
			return subdirs;
		}
		return null;
	}

	private File findHowToHtml() {
		final File dir = new File(rootDir);
		final File[] files = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(final File dir, final String name) {
				return name.toLowerCase().equals("howto.htm");
			}
		});
		// File found!!!
		if (files != null && files.length == 1) {
			LOGGER.debug("Found " + files[0].getAbsolutePath());
			return files[0];
		} else
			return null;
	}

	/**
	 * @return the filenamesAndPath
	 */
	public Vector<String> getAllFilenamesAndPath() {
		return filenamesAndPath;
	}

	private void initUI() {
		// about ?
		howtoHtml = findHowToHtml();

		// Icon Liste
		final JScrollPane scroller = new JScrollPane();
		list.setBackground(Color.black);
		list.setCellRenderer(new IconListCellRenderer());
		scroller.add(list);
		scroller.getViewport().setView(list);
		scroller.setPreferredSize(new Dimension(750, 500));

		combo.addItem(nada);
		addSetsFromFilesystem();
		combo.setRenderer(new MyCellRenderer());
		combo.setToolTipText("Choose your " + setTypeName + " Iconset");
		combo.setPreferredSize(new Dimension(400, 30));
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final ImageIcon icon = (ImageIcon) combo.getSelectedItem();
				list.removeAll();
				list.repaint();
				if (!icon.equals(nada)) {
					final int index = combo.getSelectedIndex();
					final IconSet set = iconSets.elementAt(index - 1);
					list.setListData(set.getIcons());
					final ImageIcon over = set.getOverview();
					overPane.setOverview(over, true); // true= autozoom
					overPane.setText("");
				} else {
					overPane.setOverview(icon);
					list.setListData(new Vector<ImageIcon>());
					overPane.setText("   Choose " + setTypeName + "-Set from Dropdownbox");
				}
				list.repaint();
			}
		});
		combo.setSelectedIndex(0);
		combo.setMaximumSize(new Dimension(400, 40));
		combo.setMaximumRowCount(10);
		openFolderButton.setToolTipText("Open the ./custom/-folder for " + setTypeName + "-Icon-Sets");
		openFolderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				StaticExecutor.openFolder(rootDir);
			}
		});

		setLayout(new BorderLayout());
		// Tabbed Pane
		final JTabbedPane tabPane = new JTabbedPane();
		// battTabPane.setTabPlacement(JTabbedPane.LEFT);
		tabPane.addTab("Overview", IconStore.overIcon, overPane, "Get an Overview of your icons");
		tabPane.addTab("List", IconStore.listIcon, scroller, "Get an Overview of your icons");
		if (howtoHtml != null) {
			tabPane.addTab("Howto & Help", IconStore.helpIcon, new HTMLFileDisplay(howtoHtml), "How to Use this feature!");
		}

		this.add(tabPane, BorderLayout.CENTER);
		makeButtonBar();
	}

	/**
	 * Creating buttonbar
	 */
	private void makeButtonBar() {
		final JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(combo);
		toolBar.add(new JPanel());
		toolBar.add(openFolderButton);
		this.add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * 
	 */
	private void addSetsFromFilesystem() {
		LOGGER.info("Loading Custom " + setTypeName + " Icon Sets!");
		final File dir = new File(rootDir);
		if (!dir.exists())
			dir.mkdirs();
		// find subdirs with icon sets
		final File[] setDirs = findCustomDirs(dir);
		if (setDirs != null) {
			for (final File setDir : setDirs) {
				LOGGER.debug(" Found: " + setDir.getName());
				final IconSet set = new IconSet(setDir);
				iconSets.add(set);
				combo.addItem(set.getOverviewStripe());
				// addItem(set.getRepresentivIcon());
			}
		}
	}

	/**
	 * Renderer for WifiCreator-Combo
	 */
	private class MyCellRenderer implements ListCellRenderer<ImageIcon> {
		private final DefaultListCellRenderer	defaultRenderer	= new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends ImageIcon> list, final ImageIcon value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof ImageIcon) {
				// if (isSelected)
				// renderer.setBackground(Color.darkGray.darker());
				// else
				// renderer.setBackground(Color.black);
				// renderer.setForeground(Color.white);
				final ImageIcon icon = value;
				renderer.setIcon(icon);
				if (index > 0) {
					final IconSet set = iconSets.elementAt(index - 1);
					renderer.setText(set.getName());
				}
				if (icon.equals(nada)) {
					renderer.setText("No " + setTypeName + " Icons");
				}
			}
			return renderer;
		}

	}

	/**
	 * For testing purposes !!!
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		final JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Hallo Emmy!!!!!!!");
		f.setBounds(200, 200, 640, 600);
		f.setLayout(new BorderLayout());
		// final IconSetSelector combo = new IconSetSelector("Weather",
		// "./custom/weather/");
		final IconSetSelector combo = new IconSetSelector("Toggle", "./custom/toggles/");
		f.add(combo, BorderLayout.CENTER);

		f.setVisible(true);
	}

	public void createAllImages(final int size) {
		final ImageIcon icon = (ImageIcon) combo.getSelectedItem();
		if (!icon.equals(nada)) {
			final int index = combo.getSelectedIndex();
			final IconSet set = iconSets.elementAt(index - 1);

			final IconSetDeployer depl = new IconSetDeployer(set, setTypeName);
			depl.createAllImages(size);
			filenamesAndPath = depl.getAllFilenamesAndPath();
		} else {
			filenamesAndPath = new Vector<String>();
		}
	}

	/**
	 * Renderer für IconList
	 */
	private class IconListCellRenderer implements ListCellRenderer<ImageIcon> {
		protected DefaultListCellRenderer	defaultRenderer	= new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends ImageIcon> list, final ImageIcon value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {
			ImageIcon iconName = null;

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof ImageIcon) {
				iconName = value;
				renderer.setBorder(new EmptyBorder(1, 1, 1, 1));
				renderer.setText(iconName.getDescription());
				renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				renderer.setBorder(new EmptyBorder(1, 1, 1, 1));
			}
			return renderer;
		}

	}

}

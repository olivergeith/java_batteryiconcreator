package de.og.batterycreator.gui.panels.iconset;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileFilter;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.og.batterycreator.gui.widgets.OverviewPanel;

public class IconSetMultiSelector extends JList<IconSet> {
	private static final long serialVersionUID = -2767025548199058416L;
	private static final Logger LOGGER = LoggerFactory.getLogger(IconSetMultiSelector.class);

	private final OverviewPanel overPane = new OverviewPanel();
	private final Vector<String> filenamesAndPath = new Vector<String>();
	private final Vector<IconSet> iconSets = new Vector<IconSet>();

	private final String rootDir;
	private final String setTypeName;

	public IconSetMultiSelector(final String setTypeName, final String rootDir) {
		super();
		this.rootDir = rootDir;
		this.setTypeName = setTypeName;
		initUI();
	}

	/**
	 * @return the overviewPanel
	 */
	public JPanel getOverviewPanel() {
		return overPane;
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

	/**
	 * @return the filenamesAndPath
	 */
	public Vector<String> getAllFilenamesAndPath() {
		return filenamesAndPath;
	}

	private void initUI() {
		LOGGER.info("Loading Custom " + setTypeName + " Icon Sets!");

		addSetsFromFilesystem();
		setCellRenderer(new MyCellRenderer());
		setToolTipText("Choose your " + setTypeName + " Iconset");
		// overPane.add(this, BorderLayout.WEST);
		addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(final ListSelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// if (getModel().getSize() > 0)
		// setSelectedIndex(0);
	}

	/**
	 * 
	 */
	private void addSetsFromFilesystem() {
		final File dir = new File(rootDir);
		if (!dir.exists())
			dir.mkdirs();
		// find subdirs with icon sets
		final File[] setDirs = findCustomDirs(dir);
		if (setDirs != null) {
			for (final File setDir : setDirs) {
				final IconSet set = new IconSet(setDir);
				iconSets.add(set);
			}
		}
		setListData(iconSets);
	}

	/**
	 * Renderer for WifiCreator-Combo
	 */
	private class MyCellRenderer implements ListCellRenderer<IconSet> {
		private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends IconSet> list, final IconSet value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof IconSet) {
				if (isSelected)
					renderer.setBackground(Color.darkGray.darker());
				else
					renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				final IconSet set = value;
				renderer.setIcon(set.getOverviewStripe());
			}
			return renderer;
		}

	}

	public void createAllImages(final int size) {
		// final ImageIcon icon = (ImageIcon) getSelectedItem();
		// if (!icon.equals(nada)) {
		// final int index = getSelectedIndex();
		// final IconSet set = iconSets.elementAt(index - 1);
		//
		// final IconSetDeployer depl = new IconSetDeployer(set, setTypeName);
		// depl.createAllImages(size);
		// filenamesAndPath = depl.getAllFilenamesAndPath();
		// } else {
		// filenamesAndPath = new Vector<String>();
		// }
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
		final IconSetMultiSelector list = new IconSetMultiSelector("Toggle", "./custom/toggles/");
		final JScrollPane scroller = new JScrollPane();
		scroller.add(list);
		scroller.getViewport().setView(list);

		f.add(scroller, BorderLayout.WEST);

		f.setVisible(true);
	}
}

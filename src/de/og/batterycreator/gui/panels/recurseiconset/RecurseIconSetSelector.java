package de.og.batterycreator.gui.panels.recurseiconset;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.og.batterycreator.gui.iconstore.IconStore;

public class RecurseIconSetSelector extends JPanel {
	private static final Logger LOGGER = LoggerFactory.getLogger(RecurseIconSetSelector.class);
	private static final long serialVersionUID = -2767025548199058416L;

	private final JList<ImageIcon> list = new JList<ImageIcon>();
	private final JComboBox<RecurseIconSet> combo = new JComboBox<RecurseIconSet>();
	// private final OverviewPanel overPane = new OverviewPanel();
	private final Vector<RecurseIconSet> sets = new Vector<RecurseIconSet>();
	private RecurseIconSet selectedSet;
	private final String rootDir = "./custom/MORPH";
	private static final String NADA = "No MORPH";
	private final JLabel attention = new JLabel();

	public RecurseIconSetSelector() {
		super();
		initUI();
	}

	// /**
	// * @return the overviewPanel
	// */
	// public JPanel getOverviewPanel() {
	// return overPane;
	// }

	private File[] findCustomDirs(final File dir) {
		if (dir.isDirectory()) {
			final File[] subdirs = dir.listFiles(new FileFilter() {
				@Override
				public boolean accept(final File file) {
					return file.isDirectory();
				}
			});
			return subdirs;
		}
		return null;
	}

	private void initUI() {
		attention.setText(createLableHtml());
		attention.setBorder(new EmptyBorder(5, 5, 5, 5));

		// Icon Liste
		final JScrollPane scroller = new JScrollPane();
		list.setBackground(Color.black);
		list.setCellRenderer(new IconListCellRenderer());
		scroller.add(list);
		scroller.getViewport().setView(list);
		scroller.setPreferredSize(new Dimension(750, 500));

		combo.addItem(new RecurseIconSet(NADA));
		combo.setMaximumSize(new Dimension(400, 40));
		addSetsFromFilesystem();
		combo.setRenderer(new MyCellRenderer());
		combo.setToolTipText("Choose your Iconset");
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final RecurseIconSet selected = (RecurseIconSet) combo.getSelectedItem();
				final int index = combo.getSelectedIndex();
				list.removeAll();
				list.repaint();
				if (index > 0) {
					selectedSet = selected;
					// overPane.setText(selected.getContentHTML());
					list.setListData(selected.getIcons());

				} else {
					// overPane.setText("   Choose Icon-Set from Dropdownbox");
					list.setListData(new Vector<ImageIcon>());
					selectedSet = null;
				}
				list.repaint();
			}
		});
		if (combo.getItemCount() > 0)
			combo.setSelectedIndex(0);

		setLayout(new BorderLayout());
		this.add(attention, BorderLayout.EAST);
		this.add(scroller, BorderLayout.CENTER);
		this.add(makeButtonBar(), BorderLayout.NORTH);
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
	 * 
	 */
	private void addSetsFromFilesystem() {
		LOGGER.info("Loading Custom Recurse Icon Sets!");
		final File dir = new File(rootDir);
		if (!dir.exists())
			dir.mkdirs();
		// find subdirs with icon sets
		final File[] setDirs = findCustomDirs(dir);
		if (setDirs != null) {
			for (final File setDir : setDirs) {
				LOGGER.debug(" Found: " + setDir.getName());
				final RecurseIconSet set = new RecurseIconSet(setDir.getPath());
				sets.add(set);
				combo.addItem(set);
				// addItem(set.getRepresentivIcon());
			}
		}
	}

	/**
	 * Renderer for WifiCreator-Combo
	 */
	private class MyCellRenderer implements ListCellRenderer<RecurseIconSet> {
		private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends RecurseIconSet> list, final RecurseIconSet value, final int index,
				final boolean isSelected, final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof RecurseIconSet) {
				if (isSelected)
					renderer.setBackground(Color.darkGray.darker());
				else
					renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				if (value.toString().equals(NADA)) {
					renderer.setIcon(IconStore.nothingIcon);
				}
			}
			return renderer;
		}

	}

	private String createLableHtml() {
		String html = "<html>";

		html += "<font size=5 color=red>";
		html += "<b>" + "Attention !!" + "</b><br><hr>";
		html += "</font>";

		html += "<font size=2 color=black>";
		html += "These Themes/Morphs are added to the flashable zip as they are!<br>";
		html += "Add your filesets to ./custom/MORPH/'your_theme_name'/...<br>";
		html += "The folderstructure in this fileset-folders needs to match your rom<br><br>";
		html += "Select the new theme 'your_theme_name' here in the dropdownbox<br>";
		html += "...and add it to the flashable zip!<br><br>";
		html += "See my example in ./custom/MORPH<br>";
		html += "<br>Make sure you know what you are doing here! Don't brick your device!!!<br>";
		html += "I'm not responsable if you damage your device!!!<br>";
		html += "<hr>";
		html += "</font>";

		html += "</html>";
		return html;
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
		final RecurseIconSetSelector combo = new RecurseIconSetSelector();
		f.add(combo, BorderLayout.CENTER);

		f.setVisible(true);
	}

	/**
	 * @return the selectedSet
	 */
	public RecurseIconSet getSelectedSet() {
		return selectedSet;
	}

	/**
	 * Renderer für IconList
	 */
	private class IconListCellRenderer implements ListCellRenderer<ImageIcon> {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

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

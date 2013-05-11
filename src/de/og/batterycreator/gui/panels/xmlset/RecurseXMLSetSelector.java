package de.og.batterycreator.gui.panels.xmlset;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.overview.OverviewPanel;

public class RecurseXMLSetSelector extends JPanel {
	private static final long serialVersionUID = -2767025548199058416L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RecurseXMLSetSelector.class);

	private final JComboBox<RecurseXMLSet> combo = new JComboBox<RecurseXMLSet>();
	private final OverviewPanel overPane = new OverviewPanel();
	private final Vector<RecurseXMLSet> sets = new Vector<RecurseXMLSet>();
	private RecurseXMLSet selectedSet;
	private final String rootDir = "./custom/MORPH_XML";
	private static final String NADA = "No XML's";
	private final JLabel attention = new JLabel();
	private final JTextArea infoArea = new JTextArea();

	public RecurseXMLSetSelector() {
		super();
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
		infoArea.setBackground(new Color(166, 245, 147));

		combo.addItem(new RecurseXMLSet(NADA));
		combo.setMaximumSize(new Dimension(400, 40));
		combo.setMaximumRowCount(10);
		addSetsFromFilesystem();
		combo.setRenderer(new MyCellRenderer());
		combo.setToolTipText("Choose your Fileset");
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				final RecurseXMLSet selected = (RecurseXMLSet) combo.getSelectedItem();
				final int index = combo.getSelectedIndex();
				if (index > 0) {
					selectedSet = selected;
					overPane.setText(selected.getContentHTML());
					readInfoFile(selected);
				} else {
					overPane.setText("   Choose XML-Set from Dropdownbox");
					selectedSet = null;
					infoArea.setText("");
				}
			}
		});
		if (combo.getItemCount() > 0)
			combo.setSelectedIndex(0);

		setLayout(new BorderLayout());

		final JPanel p = new JPanel(new BorderLayout());

		this.add(attention, BorderLayout.EAST);
		p.add(overPane, BorderLayout.CENTER);
		p.add(infoArea, BorderLayout.SOUTH);
		this.add(p, BorderLayout.CENTER);
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
		LOGGER.info("Loading Custom XML Sets!");
		final File dir = new File(rootDir);
		if (!dir.exists())
			dir.mkdirs();
		// find subdirs with icon sets
		final File[] setDirs = findCustomDirs(dir);
		if (setDirs != null) {
			for (final File setDir : setDirs) {
				LOGGER.debug(" Found: " + setDir.getName());
				final RecurseXMLSet set = new RecurseXMLSet(setDir.getPath());
				sets.add(set);
				combo.addItem(set);
				// addItem(set.getRepresentivIcon());
			}
		}
	}

	/**
	 * Renderer for WifiCreator-Combo
	 */
	private class MyCellRenderer implements ListCellRenderer<RecurseXMLSet> {
		private final DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends RecurseXMLSet> list, final RecurseXMLSet value, final int index,
				final boolean isSelected, final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof RecurseXMLSet) {
				// if (isSelected)
				// renderer.setBackground(Color.darkGray.darker());
				// else
				// renderer.setBackground(Color.black);
				// renderer.setForeground(Color.white);
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

		html += "<font size=3 color=black>";
		html += "These XML-sets are added to the flashable zip as they are!<br>";
		html += "Add your xmlsets to ./custom/MORPH_XML/'your_fileset_name'/...<br>";
		html += "The folderstructure in this xmlset-folders needs to match your rom<br><br>";
		html += "---- For example: you want to flash an XML to /system/app/SystemUI.apk/res/drawable,<br>";
		html += "---- you need to create the following structure:<br>";
		html += "---- <font size=3 color=green><b>./custom/MORPH_XML/'your_xmlset_name'/system/app/SystemUI.apk/res/drawable/abc.xml</b></font><br>";
		html += "See my examples in ./custom/MORPH_XML/...<br><br>";
		html += "<font size=4 color=red>Attention these XMLs need to be compiled!!! ... NO plain XML's here!!!</font> <br>";
		html += "Don't ask me how to do that!!!<br>";
		html += "-> Just one hint: use apktool to de- and recompile!<br><br>";
		html += "<font size=4 color=red>Make sure you know what you are doing here! <br>";
		html += "<b>This is the most dangerous feature in this tool!</b></font><br>";
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
		final RecurseXMLSetSelector combo = new RecurseXMLSetSelector();
		f.add(combo, BorderLayout.CENTER);

		f.setVisible(true);
	}

	/**
	 * @return the selectedSet
	 */
	public RecurseXMLSet getSelectedSet() {
		return selectedSet;
	}

	private void readInfoFile(final RecurseXMLSet selected) {
		final File info = selected.findInfoFile();
		if (info == null) {
			infoArea.setText("");
			return;
		} else {
			try {

				final FileReader fr = new FileReader(info.getPath());
				final BufferedReader reader = new BufferedReader(fr);
				infoArea.read(reader, null);

			} catch (final IOException ioe) {
				LOGGER.error("{}", ioe);
			}
		}

	}

}

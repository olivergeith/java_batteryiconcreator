package de.og.batterycreator.gui.widgets;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TemplateChooser extends JComboBox<String> {
	private static final long serialVersionUID = 2831442001617871077L;
	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateChooser.class);
	private static final String CUSTOM_DIR = "./template/";
	private final Vector<File> templates = new Vector<File>();

	public TemplateChooser() {
		super();
		initUI();
	}

	private void initUI() {
		fillVector();
		setMaximumRowCount(8);
		setSelectedIndex(0);
	}

	private void fillVector() {
		LOGGER.info("Loading Templates");

		final File dir = new File(CUSTOM_DIR);
		if (!dir.exists())
			dir.mkdirs();
		if (dir.exists() && dir.isDirectory()) {
			final File[] files = dir.listFiles(new FilenameFilter() {

				public boolean accept(final File dir, final String name) {
					return name.toLowerCase().endsWith(".zip");
				}
			});
			for (final File fi : files) {
				LOGGER.info(" Found Template: " + fi.getPath());
				templates.add(fi);
				addItem(fi.getPath().replace('\\', '/'));
			}
		}
	}

	/**
	 * For testing purposes !!!
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		final JFrame f = new JFrame();
		f.setTitle("Hallo Emmy!!!!!!!");
		f.setBounds(200, 200, 600, 800);
		f.setLayout(new BorderLayout());
		final TemplateChooser hs = new TemplateChooser();
		f.add(hs, BorderLayout.CENTER);

		f.setVisible(true);
		f.pack();
	}
}

package de.og.batterycreator.gui.widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import og.basics.gui.html.HTMLFileDisplay;

public class HelpSelector extends HTMLFileDisplay {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2831442001617871077L;

	private static final String CUSTOM_DIR = "./help/";

	private final Vector<File> helpfiles = new Vector<File>();

	JComboBox<String> combo = new JComboBox<String>();

	public HelpSelector() {
		super();
		initUI();
	}

	private void initUI() {
		fillHelpfileVector();
		add(combo, BorderLayout.NORTH);
		setPreferredSize(new Dimension(200, 300));
		combo.setMaximumRowCount(8);

		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				updateHtmlPane();
			}

		});
		combo.setSelectedIndex(0);
	}

	private void updateHtmlPane() {
		final int index = combo.getSelectedIndex();
		final File help = helpfiles.get(index);
		displayFile(help);
	}

	private void fillHelpfileVector() {
		System.out.println("Loading HelpPages");

		final File dir = new File(CUSTOM_DIR);
		if (!dir.exists())
			dir.mkdirs();
		if (dir.exists() && dir.isDirectory()) {
			final File[] helps = dir.listFiles(new FilenameFilter() {

				public boolean accept(final File dir, final String name) {
					return name.toLowerCase().endsWith(".htm");
				}
			});
			for (final File help : helps) {
				helpfiles.add(help);
				combo.addItem(help.getName().replaceAll(".htm", ""));
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
		final HelpSelector hs = new HelpSelector();
		f.add(hs, BorderLayout.CENTER);

		f.setVisible(true);
	}
}

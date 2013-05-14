package de.og.batterycreator.gui.widgets.xoriconselector;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XorIconSelector extends JComboBox<ImageIcon> {
	private static final Logger		LOGGER				= LoggerFactory.getLogger(XorIconSelector.class);
	private static final long		serialVersionUID	= -7712530632645291404L;

	private static final ImageIcon	icon01				= new ImageIcon(XorIconSelector.class.getResource("icon1.png"));

	public XorIconSelector() {
		super();
		initUI();
	}

	private void initUI() {
		addItem(icon01);
		LOGGER.info("Loading Custom XOr Icons!");
		addAdditionalIconsFromFilesystem();

		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// final ImageIcon icon = (ImageIcon) getSelectedItem();
			}
		});
	}

	private void addAdditionalIconsFromFilesystem() {
		final File dir = new File("./custom/xorcircle");
		if (dir.exists() && dir.isDirectory()) {
			final File[] pngs = dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(final File dir, final String name) {
					return name.toLowerCase().endsWith(".png");
				}
			});
			for (final File fi : pngs) {
				addItem(new ImageIcon(fi.getPath()));
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
		f.setBounds(200, 200, 200, 200);
		f.setLayout(new BorderLayout());
		final XorIconSelector combo = new XorIconSelector();
		combo.setSelectedIndex(0);
		f.add(combo, BorderLayout.CENTER);

		f.setVisible(true);
	}
}

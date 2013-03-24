package de.og.batterycreator.gui.widgets.chargeiconselector;

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

public class ChargeIconSelector extends JComboBox<ImageIcon> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChargeIconSelector.class);
	private static final long serialVersionUID = -7712530632645291404L;

	private static final ImageIcon charge01 = new ImageIcon(ChargeIconSelector.class.getResource("charge01.png"));
	private static final ImageIcon charge02 = new ImageIcon(ChargeIconSelector.class.getResource("charge02.png"));
	private static final ImageIcon charge03 = new ImageIcon(ChargeIconSelector.class.getResource("charge03.png"));
	private static final ImageIcon charge04 = new ImageIcon(ChargeIconSelector.class.getResource("charge04.png"));
	private static final ImageIcon charge10 = new ImageIcon(ChargeIconSelector.class.getResource("charge10.png"));
	private static final ImageIcon charge11 = new ImageIcon(ChargeIconSelector.class.getResource("charge11.png"));
	private static final ImageIcon charge12 = new ImageIcon(ChargeIconSelector.class.getResource("charge12.png"));
	private static final ImageIcon charge20 = new ImageIcon(ChargeIconSelector.class.getResource("charge20.png"));
	private static final ImageIcon charge21 = new ImageIcon(ChargeIconSelector.class.getResource("charge21.png"));
	private static final ImageIcon charge22 = new ImageIcon(ChargeIconSelector.class.getResource("charge22.png"));
	private static final ImageIcon charge23 = new ImageIcon(ChargeIconSelector.class.getResource("charge23.png"));
	private static final ImageIcon charge24 = new ImageIcon(ChargeIconSelector.class.getResource("charge24.png"));
	private static final ImageIcon charge25 = new ImageIcon(ChargeIconSelector.class.getResource("charge25.png"));
	private static final ImageIcon charge26 = new ImageIcon(ChargeIconSelector.class.getResource("charge26.png"));
	private static final ImageIcon charge30 = new ImageIcon(ChargeIconSelector.class.getResource("charge30.png"));
	private static final ImageIcon charge31 = new ImageIcon(ChargeIconSelector.class.getResource("charge31.png"));
	private static final ImageIcon charge32 = new ImageIcon(ChargeIconSelector.class.getResource("charge32.png"));
	private static final ImageIcon charge40 = new ImageIcon(ChargeIconSelector.class.getResource("charge40.png"));
	private static final ImageIcon charge41 = new ImageIcon(ChargeIconSelector.class.getResource("charge41.png"));
	private static final ImageIcon charge42 = new ImageIcon(ChargeIconSelector.class.getResource("charge42.png"));

	public ChargeIconSelector() {
		super();
		initUI();
	}

	private void initUI() {
		addItem(charge20);
		addItem(charge21);
		addItem(charge22);
		addItem(charge23);
		addItem(charge24);
		addItem(charge25);
		addItem(charge26);
		addItem(charge01);
		addItem(charge02);
		addItem(charge03);
		addItem(charge04);
		addItem(charge30);
		addItem(charge31);
		addItem(charge32);
		addItem(charge10);
		addItem(charge11);
		addItem(charge12);
		addItem(charge40);
		addItem(charge41);
		addItem(charge42);
		LOGGER.info("Loading Custom Charge Icons!");
		addAdditionalIconsFromFilesystem();

		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// final ImageIcon icon = (ImageIcon) getSelectedItem();
			}
		});
	}

	private void addAdditionalIconsFromFilesystem() {
		final File dir = new File("./custom/charge");
		if (dir.exists() && dir.isDirectory()) {
			final File[] pngs = dir.listFiles(new FilenameFilter() {

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
		final ChargeIconSelector combo = new ChargeIconSelector();
		combo.setSelectedIndex(0);
		f.add(combo, BorderLayout.CENTER);

		f.setVisible(true);
	}
}

package de.og.batterycreator.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import og.basics.gui.html.HTMLFileDisplay;
import og.basics.gui.image.StaticImageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.creators.IconProviderInterface;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.overview.OverviewCreator;
import de.og.batterycreator.gui.widgets.overview.OverviewPanel;

public class LockHandlePanel extends JPanel implements IconProviderInterface {
	private static final Logger		LOGGER				= LoggerFactory.getLogger(LockHandlePanel.class);

	JComboBox<ImageIcon>			combo				= new JComboBox<ImageIcon>();

	private static final String		PROVIDER_NAME		= "Lockhandle";
	private static final String		CUSTOM_DIR			= "./custom/lockhandles/";
	// private static final String CUSTOM_DIR = "H:/_IconLibrary/Lockrings/";
	private static final String		CUSTOM_OUT_DIR		= "./pngs/deploy/lock/";

	private static final long		serialVersionUID	= -7712530632645291404L;
	private final ImageIcon			nada				= IconStore.nothingIcon;
	private static final ImageIcon	origlock			= new ImageIcon(LockHandlePanel.class.getResource("ic_lockscreen_handle_normal.png"));
	private static final ImageIcon	androidlock			= new ImageIcon(LockHandlePanel.class.getResource("ic_lock_android.png"));
	private static final ImageIcon	entelock			= new ImageIcon(LockHandlePanel.class.getResource("ic_lock_ente.png"));

	private final Vector<ImageIcon>	handleList			= new Vector<ImageIcon>();
	protected OverviewPanel			overPane			= new OverviewPanel();
	private ImageIcon				overview;
	private final Vector<String>	filenamesAndPath	= new Vector<String>();

	public LockHandlePanel() {
		super();
		initUI();
	}

	private void initUI() {
		fillIconVector();
		for (final ImageIcon icon : handleList) {
			if (!icon.equals(nada)) {
				final BufferedImage bimg = StaticImageHelper.resizeAdvanced2Height(StaticImageHelper.convertImageIcon(icon), 64);
				combo.addItem(new ImageIcon(bimg));
			} else {
				combo.addItem(icon);
			}
		}
		combo.setPreferredSize(new Dimension(200, 40));
		combo.setMaximumSize(new Dimension(200, 40));
		combo.setMaximumRowCount(8);
		combo.setRenderer(new MyCellRenderer());
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				// final int sizeTo =
				// configPane.getSettings().getLockHandleSize();
				// writeSelectedIcon(sizeTo);
				updateOverview();
			}

		});
		combo.setSelectedIndex(0);
		setLayout(new BorderLayout());

		// Tabbed Pane
		final JTabbedPane tabPane = new JTabbedPane();

		// battTabPane.setTabPlacement(JTabbedPane.LEFT);
		tabPane.addTab("Overview", IconStore.overIcon, overPane, "Get an Overview of your icons");
		// Adding Howto, if Helpfile exists !
		final File howto = new File("./custom/lockhandles/howto.htm");
		if (howto.exists()) {
			tabPane.addTab("HowTo & Help", IconStore.helpIcon, new HTMLFileDisplay(howto), "Some things you might want to know :-)");
		}

		this.add(tabPane, BorderLayout.CENTER);
		final JToolBar tollBar = makeButtonBar();

		this.add(tollBar, BorderLayout.NORTH);
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

	private void updateOverview() {
		final ImageIcon icon = getSelectedIcon();
		if (!icon.equals(nada)) {
			overPane.setOverview(icon);
			overPane.setText("   This will be your lockring");
		} else {
			overPane.setText("   Choose Lockring from Dropdownbox");
			overPane.setOverview(overview, 30);
		}

	}

	public void createAllImages(final RomSettings romSettings) {
		filenamesAndPath.removeAllElements();
		final File outf = new File(CUSTOM_OUT_DIR + romSettings.getLockHandleFileName());
		final ImageIcon icon = getSelectedIcon();
		if (!icon.equals(nada)) {
			LOGGER.info("Creating Lockhandle");
			final File dir = new File(CUSTOM_OUT_DIR);
			if (!dir.exists())
				dir.mkdirs();
			final BufferedImage img = StaticImageHelper.resize2Height(StaticImageHelper.convertImageIcon(icon), romSettings.getLockHandleSize());
			StaticImageHelper.writePNG(img, outf);
			filenamesAndPath.addElement(outf.getPath());
		} else {
			outf.delete();
		}
	}

	private ImageIcon getSelectedIcon() {
		final int index = combo.getSelectedIndex();
		return handleList.get(index);
	}

	/**
	 * @return the filenamesAndPath
	 */
	@Override
	public Vector<String> getAllFilenamesAndPath() {
		return filenamesAndPath;
	}

	private Vector<ImageIcon> fillIconVector() {
		LOGGER.info("Loading Custom Lockhandles!");
		handleList.add(nada);
		handleList.add(origlock);
		handleList.add(androidlock);
		handleList.add(entelock);
		final File dir = new File(CUSTOM_DIR);
		if (!dir.exists())
			dir.mkdirs();
		if (dir.exists() && dir.isDirectory()) {
			final File[] pngs = dir.listFiles(new FilenameFilter() {

				public boolean accept(final File dir, final String name) {
					return name.toLowerCase().endsWith(".png") && !name.toLowerCase().startsWith("over");
				}
			});
			for (final File fi : pngs) {
				handleList.add(new ImageIcon(fi.getPath()));
			}
			overview = OverviewCreator.createOverviewIcon(handleList.subList(1, handleList.size() - 1), "Lockrings");
			writeOverview(overview, "Lockrings");
		}
		return handleList;
	}

	private void writeOverview(final ImageIcon over, final String name) {
		final File rootDir = new File(CUSTOM_DIR);
		final String filename = rootDir.getPath() + File.separator + "overview_" + name + ".png";
		final File overFile = new File(filename);
		if (!overFile.exists())
			StaticImageHelper.writePNG(over, overFile);
	}

	/**
	 * Renderer for WifiCreator-Combo
	 */
	private class MyCellRenderer implements ListCellRenderer<ImageIcon> {
		protected DefaultListCellRenderer	defaultRenderer	= new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends ImageIcon> list, final ImageIcon value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof ImageIcon) {
				if (isSelected)
					renderer.setBackground(Color.darkGray.darker());
				else
					renderer.setBackground(Color.black);
				renderer.setForeground(Color.white);
				final ImageIcon icon = value;
				renderer.setIcon(icon);
				if (icon.equals(nada)) {
					renderer.setText("No Lockhandle");
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
		f.setTitle("Hallo Emmy!!!!!!!");
		f.setBounds(200, 200, 300, 80);
		f.setLayout(new BorderLayout());
		final LockHandlePanel combo = new LockHandlePanel();
		f.add(combo, BorderLayout.CENTER);

		f.setVisible(true);
	}

	@Override
	public String getProviderName() {
		return PROVIDER_NAME;
	}

	@Override
	public boolean isActiv() {
		final ImageIcon icon = (ImageIcon) combo.getSelectedItem();
		if (!icon.equals(nada))
			return true;
		return false;
	}

}

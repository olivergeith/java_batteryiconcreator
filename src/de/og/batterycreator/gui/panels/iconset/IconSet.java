package de.og.batterycreator.gui.panels.iconset;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import og.basics.gui.image.StaticImageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.cfg.GlobalSettings;
import de.og.batterycreator.gui.widgets.overview.OverviewCreator;
import de.og.batterycreator.gui.widgets.overview.StripeviewCreator;

/**
 * {@link IconSet} is a collection of Icons in One Folder that are flashed
 * alltogether. This Class generates Vectors of filenamens, icons. It creates
 * overviews (small and original size. And it creates stripes of the first few
 * icons for showing in Comboboxes for example
 * 
 * @author Oliver
 * 
 */
public class IconSet {

	private final Vector<String>	filenamesAndPath	= new Vector<String>();
	private final Vector<ImageIcon>	icons				= new Vector<ImageIcon>();

	private ImageIcon				overview;
	private ImageIcon				iconStripe;
	private final File				rootDir;

	/**
	 * the Logger for this Class
	 */
	private static final Logger		LOG					= LoggerFactory.getLogger(IconSet.class);

	public IconSet(final File rootDir) {
		super();
		this.rootDir = rootDir;
		init();
	}

	public String getName() {
		return rootDir.getName();
	}

	private void init() {
		final File[] pngs = findPNGs(rootDir);
		if (pngs.length > 0) {
			for (final File png : pngs) {
				final ImageIcon icon = new ImageIcon(png.getPath());
				icon.setDescription(png.getName());
				icons.add(icon);
				filenamesAndPath.add(png.getPath());
			}
			overview = OverviewCreator.createOverviewIcon(icons, getName());
			iconStripe = StripeviewCreator.createResizedStripeIcon(icons, getName(), 5, 32);
			writeOverview(overview, getName());
		}
	}

	public static File[] findPNGs(final File dir) {
		final File[] pngs = dir.listFiles(new FilenameFilter() {

			public boolean accept(final File dir, final String name) {
				return name.toLowerCase().endsWith(".png") && !name.toLowerCase().startsWith("over");
			}
		});
		return pngs;
	}

	private void writeOverview(final ImageIcon over, final String name) {
		final String filename = rootDir.getPath() + File.separator + "overview_" + name + ".png";
		final File overFile = new File(filename);
		if (!overFile.exists() || GlobalSettings.INSTANCE.isAlwaysWriteOverview()) {
			LOG.info("  Writing overview to filesystem {}", overFile.getPath());
			StaticImageHelper.writePNG(over, overFile);
		}
	}

	/**
	 * @return the filenamesAndPath
	 */
	public Vector<String> getAllFilenamesIncludingPath() {
		return filenamesAndPath;
	}

	/**
	 * @return the overview
	 */
	public ImageIcon getOverview() {
		return overview;
	}

	/**
	 * @return a representiv Icon
	 */
	public ImageIcon getRepresentivIcon() {
		if (icons.size() >= 2)
			return icons.get(1);
		if (icons.size() == 1)
			return icons.get(0);
		else
			return null;
	}

	/**
	 * @return the overviewStripe
	 */
	public ImageIcon getOverviewStripe() {
		return iconStripe;
	}

	public Vector<ImageIcon> getIcons() {
		return icons;
	}

	@Override
	public String toString() {
		return getName();
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
		// final IconSet set = new IconSet(new
		// File("./custom/weather/(AOKP) RR Weather HTC Home Weather"));
		final IconSet set = new IconSet(new File("./custom/lockhandles"));
		f.add(new JLabel(set.getOverview()), BorderLayout.CENTER);
		f.add(new JLabel(set.getOverviewStripe()), BorderLayout.NORTH);

		f.setVisible(true);
	}
}

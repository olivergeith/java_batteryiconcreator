package de.og.batterycreator.creators.wifi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.cfg.WifiSignalSettings;
import de.og.batterycreator.creators.AbstractCreator;
import de.og.batterycreator.gui.widgets.overview.SignalWifiOverviewCreator;

/**
 * @author Oliver
 * 
 */
public abstract class AbstractWifiCreator extends AbstractCreator {
	private static final Logger	LOGGER	= LoggerFactory.getLogger(AbstractWifiCreator.class);

	public AbstractWifiCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	// ###############################################################################
	// Abstracte Methoden
	// ###############################################################################
	public abstract ImageIcon createImage(int level, boolean fully);

	public abstract ImageIcon createInOutImage(boolean in, boolean out);

	protected WifiSignalSettings	settings	= new WifiSignalSettings();

	protected Color getConnectColor(final boolean fully) {
		Color col = getSettings().getColor();
		if (fully == true)
			col = getSettings().getColorFully();
		return col;
	}

	// ###############################################################################
	// Creating Images
	// ###############################################################################
	@Override
	public void createAllImages() {
		LOGGER.info("Wifi: Creating Icons!");
		iconMap.removeAllElements();
		filenames.removeAllElements();
		filenamesAndPath.removeAllElements();
		createImages();
		createFullyImages();
		createInOutImages();
		overview = createOverview();
	}

	private void createFullyImages() {
		for (int i = 0; i < 5; i++) {
			filenames.add(getFileName(i, true));
			filenamesAndPath.add(getFilenameAndPath(i, true));
			iconMap.add(createImage(i, true));
		}
	}

	private void createImages() {
		for (int i = 0; i < 5; i++) {
			filenamesAndPath.add(getFilenameAndPath(i, false));
			filenames.add(getFileName(i, false));
			iconMap.add(createImage(i, false));
		}

	}

	private void createInOutImages() {
		filenames.add(romSettings.getFileWifiIn());
		iconMap.add(createInOutImage(true, false));
		filenames.add(romSettings.getFileWifiOut());
		iconMap.add(createInOutImage(false, true));
		filenames.add(romSettings.getFileWifiInOut());
		iconMap.add(createInOutImage(true, true));

		filenamesAndPath.add(getPath() + File.separator + romSettings.getFileWifiIn());
		filenamesAndPath.add(getPath() + File.separator + romSettings.getFileWifiOut());
		filenamesAndPath.add(getPath() + File.separator + romSettings.getFileWifiInOut());

	}

	// ###############################################################################
	// Filename for percentage Images
	// ###############################################################################
	protected String getFileNameInOut(final boolean in, final boolean out) {
		if (in && out)
			return romSettings.getFileWifiInOut();
		if (in && !out)
			return romSettings.getFileWifiIn();
		if (!in && out)
			return romSettings.getFileWifiOut();
		return "";
	}

	protected String getFileName(final int level, final boolean fully) {
		String filename;
		if (!fully)
			filename = romSettings.getFileWifiPattern() + level + ".png";
		else
			filename = romSettings.getFileWifiPattern() + level + romSettings.getFileWifiEXtensionFully() + ".png";

		// Sonderbehandlung für null image
		if (fully == true && level == 0)
			filename = romSettings.getFileWifiPattern() + "null.png";

		return filename;
	}

	private String getFilenameAndPath(final int level, final boolean fully) {
		final String filename = getPath() + File.separator + getFileName(level, fully);
		return filename;
	}

	// ###############################################################################
	// Creating Overview
	// ###############################################################################
	@Override
	public ImageIcon createOverview() {
		LOGGER.info("Wifi: Creating Overview!");
		final BufferedImage over = SignalWifiOverviewCreator.createOverview(iconMap, getCreatorName());
		if (over != null)
			writeOverviewFile(over);
		return new ImageIcon(over);
	}

	protected Polygon createUpDownTriangle(final int x, final int y, final int w, final int h) {
		final Polygon p = new Polygon();
		p.addPoint(x, y);
		p.addPoint(x + w, y);
		p.addPoint(x + w / 2, y + h);
		return p;
	}

	protected Polygon createLeftRightTriangle(final int x, final int y, final int w, final int h) {
		final Polygon p = new Polygon();
		p.addPoint(x, y);
		p.addPoint(x, y + w);
		p.addPoint(x + h, y + w / 2);
		return p;
	}

	@Override
	public ImageIcon getOverviewIcon() {
		return overview;
	}

	// ###############################################################################
	// Grafics2D
	// ###############################################################################
	protected Graphics2D initGrafics2D(final BufferedImage img) {
		return initGrafics2D(img, false);
	}

	protected Graphics2D initGrafics2D(final BufferedImage img, final boolean forceTransparent) {
		final Graphics2D g2d = img.createGraphics();
		g2d.setFont(getSettings().getFont());
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (!forceTransparent) {
			if (!getSettings().isTransparentBackground()) {
				g2d.setColor(getSettings().getBackgroundColor());
				g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
			}
		}
		return g2d;
	}

	/**
	 * @return the settings
	 */
	public WifiSignalSettings getSettings() {
		return settings;
	}

	/**
	 * @param settings
	 *            the settings to set
	 */
	public void setSettings(final WifiSignalSettings settings) {
		this.settings = settings;
	}

	@Override
	public String getPath() {
		return "./pngs/renderer/wifi/" + toString();
	}

}

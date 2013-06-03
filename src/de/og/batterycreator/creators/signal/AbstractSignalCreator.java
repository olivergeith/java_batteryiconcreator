package de.og.batterycreator.creators.signal;

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
public abstract class AbstractSignalCreator extends AbstractCreator {
	private static final Logger	LOGGER	= LoggerFactory.getLogger(AbstractSignalCreator.class);

	public AbstractSignalCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	protected static final int		NULL_LEVEL	= 5;
	protected WifiSignalSettings	settings	= new WifiSignalSettings();

	// ###############################################################################
	// Abstracte Methoden
	// ###############################################################################
	public abstract ImageIcon createImage(int level, boolean fully);

	public abstract ImageIcon createInOutImage(boolean in, boolean out);

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
		LOGGER.info("Signal: Creating Icons!");
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
		for (int i = 0; i <= NULL_LEVEL; i++) {
			filenamesAndPath.add(getFilenameAndPath(i, false));
			filenames.add(getFileName(i, false));
			iconMap.add(createImage(i, false));
		}

	}

	private void createInOutImages() {
		filenames.add(romSettings.getFileSignalIn());
		iconMap.add(createInOutImage(true, false));
		filenames.add(romSettings.getFileSignalOut());
		iconMap.add(createInOutImage(false, true));
		filenames.add(romSettings.getFileSignalInOut());
		iconMap.add(createInOutImage(true, true));

		filenamesAndPath.add(getPath() + File.separator + romSettings.getFileSignalIn());
		filenamesAndPath.add(getPath() + File.separator + romSettings.getFileSignalOut());
		filenamesAndPath.add(getPath() + File.separator + romSettings.getFileSignalInOut());

	}

	// ###############################################################################
	// Filename for percentage Images
	// ###############################################################################
	protected String getFileNameInOut(final boolean in, final boolean out) {
		if (in && out)
			return romSettings.getFileSignalInOut();
		if (in && !out)
			return romSettings.getFileSignalIn();
		if (!in && out)
			return romSettings.getFileSignalOut();
		return "";
	}

	protected String getFileName(final int level, final boolean fully) {
		String filename;
		if (!fully)
			filename = romSettings.getFileSignalPattern() + level + ".png";
		else
			filename = romSettings.getFileSignalPattern() + level + romSettings.getFileSignalEXtensionFully() + ".png";

		// Sonderbehandlung für null image
		if (level == NULL_LEVEL)
			filename = romSettings.getFileSignalPattern() + "null.png";

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
		LOGGER.info("Signal: Creating Overview!");
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
		return "./pngs/renderer/signal/" + toString();
	}

}

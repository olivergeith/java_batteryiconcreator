package de.og.batterycreator.creators;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;

import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.cfg.RomSettings;

public abstract class AbstractCreator implements IconProviderInterface {

	protected final Vector<ImageIcon> iconMap = new Vector<ImageIcon>();
	protected final Vector<String> filenames = new Vector<String>();
	protected final Vector<String> filenamesAndPath = new Vector<String>();
	protected ImageIcon overview = null;
	protected RomSettings romSettings = new RomSettings();

	public AbstractCreator(final RomSettings romSettings) {
		this.romSettings = romSettings;
	}

	// ###############################################################################
	// Abstracte Methoden
	// ###############################################################################
	@Override
	public abstract String toString();

	public abstract void createAllImages();

	public abstract ImageIcon createOverview();

	@Override
	public void createAllImages(final RomSettings romSettings) {
		createAllImages();
	}

	// ###############################################################################
	// RomSettings
	// ###############################################################################
	public void setRomSettings(final RomSettings settings) {
		romSettings = settings;
	}

	// ###############################################################################
	// Writing Stuff to Filesystem
	// ###############################################################################
	protected BufferedImage writeFile(final String filename, final BufferedImage img) {
		// getting filename
		final File file = new File(getPath() + File.separator + filename);
		return writeFile(file, img);
	}

	protected BufferedImage writeFile(final File file, BufferedImage img) {
		// Pfad anlegen falls nicht vorhanden
		final File pa = new File(getPath() + File.separator);
		pa.mkdirs();
		// resize ?
		if (romSettings.getBattIconSize() != img.getHeight()) {
			// do the resizing before save
			if (romSettings.isUseAdvancedResize())
				img = StaticImageHelper.resizeAdvanced2Height(img, romSettings.getBattIconSize());
			else
				img = StaticImageHelper.resize2Height(img, romSettings.getBattIconSize());
		}
		// the writing
		StaticImageHelper.writePNG(img, file);
		return img;
	}

	protected void writeOverviewFile(final BufferedImage overview) {
		final File file = new File(getPath() + File.separator + "overview_" + getCreatorName() + ".png");
		StaticImageHelper.writePNG(overview, file);
	}

	// ###############################################################################
	// All filenames and Icons
	// ###############################################################################
	public Vector<String> getAllFilenamesAndPath() {
		return filenamesAndPath;
	}

	public Vector<String> getFilenames() {
		return filenames;
	}

	public Vector<ImageIcon> getIcons() {
		return iconMap;
	}

	// ###############################################################################
	// primitive Getter
	// ###############################################################################
	public String getPath() {
		return "./pngs/" + toString();
	}

	public String getCreatorName() {
		return toString();
	}

	public String getProviderName() {
		return toString();
	}

	// protected Graphics2D initGrafics2D(final BufferedImage img) {
	// return initGrafics2D(img, false);
	// }

	// //
	// ###############################################################################
	// // Grafics2D
	// //
	// ###############################################################################
	// protected Graphics2D initGrafics2D(final BufferedImage img, final boolean
	// forceTransparent) {
	// final Graphics2D g2d = img.createGraphics();
	// g2d.setFont(romSettings.getFont());
	// g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	// RenderingHints.VALUE_ANTIALIAS_ON);
	// g2d.setStroke(new BasicStroke(romSettings.getStrokewidth()));
	// if (!forceTransparent) {
	// if (!romSettings.isTransparentBackground()) {
	// g2d.setColor(romSettings.getBackgroundColor());
	// g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
	// }
	// }
	// return g2d;
	// }

	@Override
	public boolean isActiv() {
		return true;
	}

}

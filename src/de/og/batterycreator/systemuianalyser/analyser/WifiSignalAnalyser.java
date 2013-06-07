package de.og.batterycreator.systemuianalyser.analyser;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.systemuianalyser.data.WifiSignalType;

public class WifiSignalAnalyser {

	/**
	 * the Logger for this Class
	 */
	private static final Logger					LOG		= LoggerFactory.getLogger(WifiSignalAnalyser.class);
	private final File							extractDir;
	private final Map<String, WifiSignalType>	typeMap	= new HashMap<String, WifiSignalType>();

	public WifiSignalAnalyser(final File extractDir) {
		this.extractDir = extractDir;
	}

	public void analyse() {
		findAllIconsInDirTree(extractDir);
		finalizeResults();
	}

	private void finalizeResults() {
		LOG.info("##################################################");
		LOG.info(" Wifi&Signal Analyser");
		LOG.info("##################################################");
		for (final WifiSignalType type : typeMap.values()) {
			LOG.info(" Found: " + type.toDebugString());
		}
		LOG.info("##################################################");
	}

	private void findAllIconsInDirTree(final File file) {
		if (file.isDirectory()) {
			final File[] subDirs = findSubDirs(file);
			for (int i = 0; i < subDirs.length; i++) {
				findAllIconsInDirTree(subDirs[i]);
			}
			final File[] files = findPNGs(file);
			for (final File png : files) {
				final ImageIcon icon = new ImageIcon(png.getPath());
				final File parent = new File(png.getParent());
				final String drawableFolder = parent.getName();
				addToWifiType(png.getName(), icon, drawableFolder);
			}
		}
	}

	private void addToWifiType(final String filename, final ImageIcon icon, final String drawableFolder) {
		WifiSignalType type = typeMap.get(drawableFolder);
		if (type == null) {
			type = new WifiSignalType(drawableFolder);
			typeMap.put(drawableFolder, type);
		}
		icon.setDescription(filename);
		type.addIcon(icon);
	}

	public Map<String, WifiSignalType> getWifiTypeMap() {
		return typeMap;
	}

	private static File[] findPNGs(final File dir) {
		final File[] pngs = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(final File dir, final String name) {
				return name.toLowerCase().endsWith(".png") //
						&& (name.toLowerCase().startsWith(WifiSignalType.WIFI_PREFIX) //
						|| name.toLowerCase().startsWith(WifiSignalType.SIGNAL_PREFIX));
			}
		});
		return pngs;
	}

	/**
	 * @param file
	 * @return
	 */
	private File[] findSubDirs(final File file) {
		// Liste aller Subdirs
		final File[] subDirs = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(final File pathname) {
				return pathname.isDirectory();
			}
		});
		return subDirs;
	}
}

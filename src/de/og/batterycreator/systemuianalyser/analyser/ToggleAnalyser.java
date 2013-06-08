package de.og.batterycreator.systemuianalyser.analyser;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.systemuianalyser.data.ToggleType;

public class ToggleAnalyser {

	/**
	 * the Logger for this Class
	 */
	private static final Logger				LOG		= LoggerFactory.getLogger(ToggleAnalyser.class);
	private final File						extractDir;
	private final Map<String, ToggleType>	typeMap	= new HashMap<String, ToggleType>();

	public ToggleAnalyser(final File extractDir) {
		this.extractDir = extractDir;
	}

	public void analyse() {
		findAllIconsInDirTree(extractDir);
		finalizeResults();
	}

	private void finalizeResults() {
		LOG.info("##################################################");
		LOG.info(" Toggle Analyser");
		LOG.info("##################################################");
		for (final ToggleType type : typeMap.values()) {
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
				LOG.info("Found Toggle: " + png.getPath());
				final ImageIcon icon = new ImageIcon(png.getPath());
				final File parent = new File(png.getParent());
				final String drawableFolder = parent.getName();
				addToType(png.getName(), icon, drawableFolder);
			}
		}
	}

	private void addToType(final String filename, final ImageIcon icon, final String drawableFolder) {
		String pattern;
		if (filename.startsWith(ToggleType.TOGGLE_PREFIX))
			pattern = ToggleType.TOGGLE_PREFIX;
		else if ((filename.startsWith(ToggleType.TOGGLE_PREFIX_4_2)))
			pattern = ToggleType.TOGGLE_PREFIX_4_2;
		else if ((filename.startsWith(ToggleType.TOGGLE_PREFIX_SAMSUNG)))
			pattern = ToggleType.TOGGLE_PREFIX_SAMSUNG;
		else
			return;

		final String mapKey = drawableFolder + "/" + pattern;
		ToggleType type = typeMap.get(mapKey);
		if (type == null) {
			type = new ToggleType(drawableFolder, mapKey);
			typeMap.put(mapKey, type);
		}
		icon.setDescription(filename);
		type.addIcon(icon);
	}

	public Map<String, ToggleType> getToggleMap() {
		return typeMap;
	}

	private static File[] findPNGs(final File dir) {
		final File[] pngs = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(final File dir, final String name) {
				return name.toLowerCase().endsWith(".png") //
						&& (name.toLowerCase().startsWith(ToggleType.TOGGLE_PREFIX) //
								|| name.toLowerCase().startsWith(ToggleType.TOGGLE_PREFIX_4_2)//
						|| name.toLowerCase().startsWith(ToggleType.TOGGLE_PREFIX_SAMSUNG)//
						) //
						&& !name.toLowerCase().contains(ToggleType.TOGGLE_NOT_CONTAINS1);
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

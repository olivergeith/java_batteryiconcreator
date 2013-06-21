package de.og.batterycreator.systemuianalyser.analyser;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.systemuianalyser.data.NotifyIconType;

public class NotifyAnalyser {

	/**
	 * the Logger for this Class
	 */
	private static final Logger					LOG		= LoggerFactory.getLogger(NotifyAnalyser.class);
	private final File							extractDir;
	private final Map<String, NotifyIconType>	typeMap	= new HashMap<String, NotifyIconType>();

	public NotifyAnalyser(final File extractDir) {
		this.extractDir = extractDir;
	}

	public void analyse() {
		findAllIconsInDirTree(extractDir);
		finalizeResults();
	}

	private void finalizeResults() {
		LOG.info("##################################################");
		LOG.info(" Notify Icon Analyser");
		LOG.info("##################################################");
		for (final NotifyIconType type : typeMap.values()) {
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
				LOG.debug("Found Toggle: " + png.getPath());
				final ImageIcon icon = new ImageIcon(png.getPath());
				final File parent = new File(png.getParent());
				final String drawableFolder = parent.getName();
				addToType(png.getName(), icon, drawableFolder);
			}
		}
	}

	private void addToType(final String filename, final ImageIcon icon, final String drawableFolder) {
		String pattern;
		if (filename.startsWith(NotifyIconType.PREFIX_IC))
			pattern = NotifyIconType.PREFIX_IC;
		else if ((filename.startsWith(NotifyIconType.PREFIX_STAT)))
			pattern = NotifyIconType.PREFIX_STAT;
		else
			return;

		final String mapKey = drawableFolder + "/" + pattern;
		NotifyIconType type = typeMap.get(mapKey);
		if (type == null) {
			type = new NotifyIconType(drawableFolder, mapKey);
			typeMap.put(mapKey, type);
		}
		icon.setDescription(filename);
		type.addIcon(icon);
	}

	public Map<String, NotifyIconType> getIconMap() {
		return typeMap;
	}

	private static File[] findPNGs(final File dir) {
		final File[] pngs = dir.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(final File dir, final String name) {
				return name.toLowerCase().endsWith(".png") //
						&& (name.toLowerCase().startsWith(NotifyIconType.PREFIX_IC) || name.toLowerCase().startsWith(NotifyIconType.PREFIX_STAT));
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

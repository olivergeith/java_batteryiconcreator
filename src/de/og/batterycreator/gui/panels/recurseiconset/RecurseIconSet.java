package de.og.batterycreator.gui.panels.recurseiconset;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tom
 * 
 */
public class RecurseIconSet {
	private static final Logger LOGGER = LoggerFactory.getLogger(RecurseIconSet.class);

	private final File dir;

	private final Vector<String> allPathInZip = new Vector<String>();
	private final Vector<String> filenamesAndPath = new Vector<String>();
	private final Vector<ImageIcon> icons = new Vector<ImageIcon>();

	@Override
	public String toString() {
		return dir.getName();
	}

	public RecurseIconSet(final String startDir) {

		dir = new File(startDir);
		// Den Verzeichnisbaum rekursiv traversieren...
		LOGGER.debug("#######################################################");
		LOGGER.debug("Scanning the Folder " + dir.getPath());
		LOGGER.debug("#######################################################");
		allPathInZip.removeAllElements();
		filenamesAndPath.removeAllElements();
		findAllFilesInDirTree(dir);
		LOGGER.debug("#######################################################");
	}

	/**
	 * @param file
	 */
	private void findAllFilesInDirTree(final File file) {
		if (file.isDirectory()) {
			final File[] subDirs = findSubDirs(file);
			for (int i = 0; i < subDirs.length; i++) {
				findAllFilesInDirTree(subDirs[i]);
			}
			final File[] files = findPNGs(file);
			for (final File png : files) {
				String pathInZip = "MORPH" + png.getParent().substring(dir.getPath().length());
				pathInZip = pathInZip.replace('\\', '/') + "/";
				LOGGER.debug(png.getPath() + " ---> " + pathInZip);
				allPathInZip.add(pathInZip);
				filenamesAndPath.add(png.getPath());
				final ImageIcon icon = new ImageIcon(png.getPath());
				icon.setDescription(pathInZip + png.getName());
				icons.add(icon);
			}
		}
	}

	/**
	 * @param file
	 * @return
	 */
	private File[] findSubDirs(final File file) {
		// Liste aller Subdirs
		final File[] subDirs = file.listFiles(new FileFilter() {

			public boolean accept(final File pathname) {
				return pathname.isDirectory();
			}
		});
		return subDirs;
	}

	public static File[] findPNGs(final File dir) {
		final File[] pngs = dir.listFiles(new FilenameFilter() {

			public boolean accept(final File dir, final String name) {
				return name.toLowerCase().endsWith(".png") && !name.toLowerCase().startsWith("over");
			}
		});
		return pngs;
	}

	/**
	 * @return the filenamesAndPath
	 */
	public Vector<String> getFilenamesAndPath() {
		return filenamesAndPath;
	}

	/**
	 * @return the allPathInZip
	 */
	public Vector<String> getAllPathInZip() {
		return allPathInZip;
	}

	/**
	 * @return the icons
	 */
	public Vector<ImageIcon> getIcons() {
		return icons;
	}

	public String getContentHTML() {
		String html = "<html>";

		html += "<font size=5 color=white>";
		html += "<b>" + toString() + "</b><br><hr>";
		html += "</font>";

		html += "<font size=3 color=white>";
		for (final String file : filenamesAndPath) {
			html += file + "<br>";
		}
		html += "<hr>";
		html += "</font>";

		html += "</html>";
		return html;
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) throws Exception {
		new RecurseIconSet("./custom/MORPH/RR312_Theme01");
	}

}
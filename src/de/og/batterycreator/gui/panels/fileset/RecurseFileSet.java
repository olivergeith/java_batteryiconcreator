package de.og.batterycreator.gui.panels.fileset;

import java.io.File;
import java.io.FileFilter;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tom
 * 
 */
public class RecurseFileSet {
	private static final Logger LOGGER = LoggerFactory.getLogger(RecurseFileSet.class);

	private final File dir;

	private final Vector<String> allPathInZip = new Vector<String>();
	private final Vector<String> filenamesAndPath = new Vector<String>();

	@Override
	public String toString() {
		return dir.getName();
	}

	public RecurseFileSet(final String startDir) {

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
			final File[] files = findFiles(file);
			for (final File f : files) {
				String pathInZip = "XTRAS" + f.getParent().substring(dir.getPath().length());
				// String pathInZip = "XTRAS" + f.getParent();
				pathInZip = pathInZip.replace('\\', '/') + "/";
				LOGGER.debug(f.getPath() + " ---> " + pathInZip);
				allPathInZip.add(pathInZip);
				filenamesAndPath.add(f.getPath());
				LOGGER.debug(f.getParent() + " ---> " + pathInZip);
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

	/**
	 * @param file
	 * @return
	 */
	private File[] findFiles(final File file) {
		final File[] files = file.listFiles(new FileFilter() {

			@Override
			public boolean accept(final File pathname) {
				return pathname.isFile();
			}
		});
		return files;
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
		new RecurseFileSet("./custom/XTRAS/Android42Camera");
	}

}
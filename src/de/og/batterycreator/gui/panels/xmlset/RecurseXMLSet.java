package de.og.batterycreator.gui.panels.xmlset;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tom
 * 
 */
public class RecurseXMLSet {

	private static final Logger LOGGER = LoggerFactory.getLogger(RecurseXMLSet.class);

	private final File dir;

	private final Vector<String> allPathInZip = new Vector<String>();
	private final Vector<File> xmlFiles = new Vector<File>();
	private final Vector<String> filenamesAndPath = new Vector<String>();

	@Override
	public String toString() {
		return dir.getName();
	}

	public RecurseXMLSet(final String startDir) {

		dir = new File(startDir);
		// Den Verzeichnisbaum rekursiv traversieren...
		LOGGER.debug("#######################################################");
		LOGGER.debug("Scanning the Folder " + dir.getPath());
		LOGGER.debug("#######################################################");
		allPathInZip.removeAllElements();
		xmlFiles.removeAllElements();
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
			final File[] files = findXMLs(file);
			for (final File xml : files) {
				String pathInZip = "MORPH" + xml.getParent().substring(dir.getPath().length());
				pathInZip = pathInZip.replace('\\', '/') + "/";
				LOGGER.debug(xml.getPath() + " ---> " + pathInZip);
				allPathInZip.add(pathInZip);
				xmlFiles.add(xml);
				filenamesAndPath.add(xml.getPath());
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

	public static File[] findXMLs(final File dir) {
		final File[] pngs = dir.listFiles(new FilenameFilter() {

			public boolean accept(final File dir, final String name) {
				return name.toLowerCase().endsWith(".xml");
			}
		});
		return pngs;
	}

	public File findInfoFile() {
		final String path = dir.getPath() + "/info.txt";
		final File info = new File(path);
		if (info.exists())
			return info;
		else
			return null;

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

		html += "<font size=3 color=white><pre>";
		for (int i = 0; i < xmlFiles.size(); i++) {
			final File fi = xmlFiles.get(i);
			final String morph = allPathInZip.get(i);
			html += morph + " -- " + fi.getName() + "<br>";
		}
		html += "<hr>";
		html += "</pre></font>";

		html += "</html>";
		return html;
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) throws Exception {
		LOGGER.info("Starting");
		new RecurseXMLSet("./custom/MORPH_XML/(AOKP) ChargeAnimationCircleMod_FullCircle");
	}

}
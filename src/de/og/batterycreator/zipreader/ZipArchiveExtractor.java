package de.og.batterycreator.zipreader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipArchiveExtractor {

	/**
	 * the Logger for this Class
	 */
	private static final Logger	LOG	= LoggerFactory.getLogger(ZipArchiveExtractor.class);

	public static void extractArchive(final File archive, final File destDir) throws Exception {
		if (!destDir.exists()) {
			destDir.mkdirs();
		} else {
			deleteDirRecurse(destDir);
			destDir.mkdirs();
		}

		// Zipfile in Entries zerpflücken
		final ZipFile zipFile = new ZipFile(archive);
		final Enumeration<? extends ZipEntry> entries = zipFile.entries();
		final byte[] buffer = new byte[16384];
		int len;
		while (entries.hasMoreElements()) {
			final ZipEntry entry = entries.nextElement();
			final String entryFileName = entry.getName();
			LOG.info(" - Extracting ZipEntry: {}", entryFileName);

			// Zielordner anlegen falls noch nicht geschehen!
			final File dir = buildDirectoryHierarchyFor(entryFileName, destDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			// Datei schreiben
			if (!entry.isDirectory()) {
				final BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(destDir, entryFileName)));
				final BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
				while ((len = bis.read(buffer)) > 0) {
					bos.write(buffer, 0, len);
				}
				bos.flush();
				bos.close();
				bis.close();
			}
		}
		zipFile.close();
	}

	private static File buildDirectoryHierarchyFor(final String entryName, final File destDir) {
		final int lastIndex = entryName.lastIndexOf('/');
		// final String entryFileName = entryName.substring(lastIndex + 1);
		final String internalPathToEntry = entryName.substring(0, lastIndex + 1);
		return new File(destDir, internalPathToEntry);
	}

	public static void deleteDirRecurse(final File path) {
		for (final File file : path.listFiles()) {
			if (file.isDirectory())
				deleteDirRecurse(file);
			file.delete();
		}
		path.delete();
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) throws Exception {
		ZipArchiveExtractor.extractArchive(new File("C:/Users/geith.KIEL/Downloads/xyz.zip"), new File("c:/tmp/xyz"));
	}

}

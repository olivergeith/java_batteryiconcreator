package de.og.batterycreator.zipcreator;

import java.util.Vector;

public class ZipElement {

	private String filenameWithPath;
	private String pathInArchiv;

	public ZipElement(final String filenameWithPath, final String pathInArchiv) {
		this.pathInArchiv = pathInArchiv;
		this.filenameWithPath = filenameWithPath;
	}

	public static Vector<ZipElement> getZipElementVector(final Vector<String> elements, final String pathInArchiv) {

		final Vector<ZipElement> vec = new Vector<ZipElement>();
		if (elements != null && pathInArchiv != null && pathInArchiv.length() > 0) {
			for (final String ele : elements) {
				vec.add(new ZipElement(ele, pathInArchiv));
			}
		}
		return vec;
	}

	/**
	 * @return the filenameWithPath
	 */
	public String getFilenameWithPath() {
		return filenameWithPath;
	}

	/**
	 * @param filenameWithPath
	 *            the filenameWithPath to set
	 */
	public void setFilenameWithPath(final String filenameWithPath) {
		this.filenameWithPath = filenameWithPath;
	}

	/**
	 * @return the pathInArchiv
	 */
	public String getPathInArchiv() {
		return pathInArchiv;
	}

	/**
	 * @param pathInArchiv
	 *            the pathInArchiv to set
	 */
	public void setPathInArchiv(final String pathInArchiv) {
		this.pathInArchiv = pathInArchiv;
	}

}

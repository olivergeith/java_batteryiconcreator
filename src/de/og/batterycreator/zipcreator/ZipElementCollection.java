package de.og.batterycreator.zipcreator;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipElementCollection {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZipElementCollection.class);

	final Vector<ZipElement> zipelEments = new Vector<ZipElement>();

	public ZipElementCollection() {
	}

	/**
	 * Adds files to the collection, that will end all in one path
	 * 
	 * @param elements
	 * @param pathInArchiv
	 */
	public void addElement(final String ele, final String pathInArchiv) {

		if (ele != null && ele.length() > 0 && pathInArchiv != null && pathInArchiv.length() > 0) {
			LOGGER.debug("Adding Element: " + ele + " to path: " + pathInArchiv);
			zipelEments.add(new ZipElement(ele, pathInArchiv));
		}
	}

	/**
	 * Adds files to the collection, that will end all in one path
	 * 
	 * @param elements
	 * @param pathInArchiv
	 */
	public void addElements(final Vector<String> elements, final String pathInArchiv) {

		if (elements != null && pathInArchiv != null && pathInArchiv.length() > 0) {
			for (final String ele : elements) {
				LOGGER.debug("Adding Element: " + ele + " to path: " + pathInArchiv);
				zipelEments.add(new ZipElement(ele, pathInArchiv));
			}
		}
	}

	/**
	 * Adds files to the collection all with an indevidual path
	 * 
	 * @param elements
	 * @param pathInArchiv
	 */
	public void addElements(final Vector<String> elements, final Vector<String> pathInArchiv) {

		if (elements != null && pathInArchiv != null) {
			if (elements.size() == pathInArchiv.size()) {
				for (int i = 0; i < elements.size(); i++) {
					LOGGER.debug("Adding Element: " + elements.get(i) + " to path: " + pathInArchiv.get(i));
					zipelEments.add(new ZipElement(elements.get(i), pathInArchiv.get(i)));
				}
			}
		}
	}

	public Vector<ZipElement> getZipelEments() {
		return zipelEments;
	}

}

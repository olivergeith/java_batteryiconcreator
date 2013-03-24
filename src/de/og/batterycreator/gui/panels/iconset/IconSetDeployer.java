package de.og.batterycreator.gui.panels.iconset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;

import og.basics.gui.image.StaticImageHelper;

public class IconSetDeployer {
	private static final String CUSTOM_OUT_DIR = "./pngs/deploy/";

	public static final int NO_RESIZING = 0;
	private final IconSet iconSet;
	private final Vector<String> filenamesAndPath = new Vector<String>();

	private final String typeName;

	public IconSetDeployer(final IconSet iconSet, final String typeName) {
		this.iconSet = iconSet;
		this.typeName = typeName;
	}

	public String getProviderName() {
		return iconSet.getName();
	}

	public Vector<String> getAllFilenamesAndPath() {
		return filenamesAndPath;
	}

	/**
	 * if size == 0 then there is no resizing !!!
	 * 
	 * @see de.og.batterycreator.creators.IconProviderInterface#createAllImages(int)
	 */
	public void createAllImages(final int size) {
		final Vector<String> files = iconSet.getAllFilenamesIncludingPath();
		filenamesAndPath.removeAllElements();
		if (files != null) {
			for (final String f : files) {
				final File icoFile = new File(f);
				final ImageIcon icon = new ImageIcon(f);

				final BufferedImage buff;
				if (size > NO_RESIZING) {
					buff = StaticImageHelper.resize2Height(StaticImageHelper.convertImageIcon(icon), size);
				} else {
					buff = StaticImageHelper.convertImageIcon(icon);
				}
				final File outdir = new File(CUSTOM_OUT_DIR + typeName + File.separator + iconSet.getName() + File.separator);
				if (!outdir.exists())
					outdir.mkdirs();
				final File outf = new File(outdir.getPath() + File.separator + icoFile.getName());
				filenamesAndPath.add(outf.getPath());
				StaticImageHelper.writePNG(buff, outf);
			}
		}
	}
}

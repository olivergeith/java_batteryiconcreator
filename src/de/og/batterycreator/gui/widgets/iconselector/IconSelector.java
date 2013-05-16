package de.og.batterycreator.gui.widgets.iconselector;

import java.awt.Dimension;
import java.beans.Transient;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class IconSelector extends JComboBox<ImageIcon> {
	private static final Logger	LOGGER				= LoggerFactory.getLogger(IconSelector.class);
	private static final long	serialVersionUID	= -7712530632645291404L;
	private int					height				= 38;
	private final String		customPath;

	public IconSelector(final String customPath, final int height) {
		super();
		this.customPath = customPath;
		setPreferedHeight(height);
		initUI();
	}

	abstract public void fillStaticIcons();

	public void initUI() {
		LOGGER.info("Loading Custom Icons from {}", customPath);
		fillStaticIcons();
		addAdditionalIconsFromFilesystem();
	}

	@Override
	@Transient
	public Dimension getPreferredSize() {
		final Dimension dim = super.getPreferredSize();
		dim.setSize(dim.width, height);
		return dim;
	}

	public void setPreferedHeight(final int height) {
		this.height = height;
	}

	private void addAdditionalIconsFromFilesystem() {
		final File dir = new File(customPath);
		if (dir.exists() && dir.isDirectory()) {
			final File[] pngs = dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(final File dir, final String name) {
					return name.toLowerCase().endsWith(".png");
				}
			});
			for (final File fi : pngs) {
				addItem(new ImageIcon(fi.getPath()));
			}
		}

	}

	/**
	 * @return the customPath
	 */
	public String getCustomPath() {
		return customPath;
	}
}

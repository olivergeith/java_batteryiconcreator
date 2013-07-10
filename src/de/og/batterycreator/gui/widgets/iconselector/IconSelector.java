package de.og.batterycreator.gui.widgets.iconselector;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Transient;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.gui.iconstore.IconStore;

public abstract class IconSelector extends JComboBox<ImageIcon> {
	private static final Logger	LOGGER				= LoggerFactory.getLogger(IconSelector.class);
	private static final long	serialVersionUID	= -7712530632645291404L;
	private int					height				= 38;
	private final String		customPath;

	private final JButton		refreshButton		= new JButton(IconStore.refresh_small);
	private final JToolBar		toolBar				= new JToolBar();

	public IconSelector(final String customPath, final int height) {
		super();
		this.customPath = customPath;
		setPreferedHeight(height);
		initUI();
	}

	abstract public void fillStaticIcons();

	public void initUI() {
		toolBar.setFloatable(false);
		toolBar.add(this);
		toolBar.add(refreshButton);
		refreshButton.setToolTipText("Reload Icons from Filesystem");
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				refresh();
			}
		});
		LOGGER.info("Loading Custom Icons from {}", customPath);
		fillStaticIcons();
		addAdditionalIconsFromFilesystem();
	}

	@Override
	public void setEnabled(final boolean b) {
		super.setEnabled(b);
		toolBar.setEnabled(b);
		refreshButton.setEnabled(b);
	}

	protected void refresh() {
		final int index = getSelectedIndex();
		setSelectedIndex(0);
		removeAllItems();
		// for (int i = getItemCount() - 1; i > 0; i--) {
		// removeItemAt(i);
		// }
		LOGGER.info("Loading Custom Icons from {}", customPath);
		fillStaticIcons();
		addAdditionalIconsFromFilesystem();
		if (index < getItemCount())
			setSelectedIndex(index);
		else
			setSelectedIndex(0);
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

	public JToolBar getToolBar() {
		return toolBar;
	}

}

package de.og.batterycreator.gui.widgets;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;

public class MorphPathWidget extends JTextField {
	private static final long	serialVersionUID	= -3686797491233353351L;

	private boolean				usePreload			= false;
	private boolean				useVRTheme			= false;
	private final String		apkPath;

	public MorphPathWidget(final String apkPath) {
		this.apkPath = apkPath;
		initUI();
	}

	private String createMorphPath() {
		String path = "";
		if (useVRTheme)
			path += "vrtheme/";
		else
			path += "MORPH/";
		if (usePreload)
			path += "preload/symlink/";
		path += apkPath;
		setText(path);
		return path;
	}

	private void initUI() {
		setForeground(Color.blue.darker());
		setEditable(false);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				if (e.getClickCount() == 3) {
					setEditable(!isEditable());
				}
			}
		});
		setToolTipText("Trippleclick me to make me editable...Only for experts! :-)");
		createMorphPath();
	}

	public boolean isUsePreload() {
		return usePreload;
	}

	public void setUsePreload(final boolean usePreload) {
		this.usePreload = usePreload;
		createMorphPath();
	}

	public boolean isUseVRTheme() {
		return useVRTheme;
	}

	public void setUseVRTheme(final boolean useVRTheme) {
		this.useVRTheme = useVRTheme;
		createMorphPath();
	}

}

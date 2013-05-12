package de.og.batterycreator.gui.widgets;

import java.awt.Color;
import javax.swing.JLabel;

public class MorphPathWidget extends JLabel {
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
		this.setText(path);
		return path;
	}

	private void initUI() {
		this.setForeground(Color.blue.darker());
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

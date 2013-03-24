package de.og.batterycreator.gui.widgets;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OverviewPanel extends JPanel {
	private static final long serialVersionUID = -2956273745014471932L;

	private final JLabel overviewLabel = new JLabel();

	public OverviewPanel() {
		setLayout(new BorderLayout());
		setBackground(Color.black);
		overviewLabel.setForeground(Color.white);
		add(overviewLabel, BorderLayout.CENTER);
	}

	public void setOverview(final ImageIcon overview) {
		overviewLabel.setIcon(overview);
	}

	public void setText(final String txt) {
		overviewLabel.setText(txt);
	}
}

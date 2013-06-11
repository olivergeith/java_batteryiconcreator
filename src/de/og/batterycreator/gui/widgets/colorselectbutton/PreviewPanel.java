package de.og.batterycreator.gui.widgets.colorselectbutton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import og.basics.util.StaticExecutor;
import de.og.batterycreator.cfg.SettingsPersistor;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.customcolors.NamedColor;
import de.og.batterycreator.gui.widgets.customcolors.NamedColorDialog;

public class PreviewPanel extends JPanel {
	private static final long	serialVersionUID	= 8925771293231176113L;
	private final JLabel		previewLabel		= new JLabel();

	public PreviewPanel(final JColorChooser chooser) {
		super();
		initUI();
	}

	private void initUI() {
		setLayout(new BorderLayout());
		final JButton saveButton = new JButton("Save Color to user defined Colors", IconStore.colorIcon);
		final JButton manageButton = new JButton("Manage user defined Colors", IconStore.folder2Icon);
		saveButton.setToolTipText("Save Color to userdefined Colors (visible in at next start of this colorchooser-dialog)");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				save();
			}
		});
		manageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				manage();
			}
		});
		previewLabel.setText("XXXXXXXXXX");
		previewLabel.setSize(previewLabel.getPreferredSize());
		previewLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 48));
		previewLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));

		final JToolBar bar = new JToolBar();
		bar.setFloatable(false);
		bar.add(saveButton);
		bar.add(manageButton);

		add(bar, BorderLayout.SOUTH);
		add(previewLabel, BorderLayout.CENTER);
	}

	protected void manage() {
		StaticExecutor.openFolder(SettingsPersistor.COLOR_DIR);
	}

	protected void save() {
		final Color col = previewLabel.getForeground();
		final NamedColor ncol = NamedColorDialog.showDialog(null, new NamedColor(col, ""));
		if (ncol != null) {
			SettingsPersistor.writeColor(ncol);
		}

	}

	@Override
	public void setBackground(final Color color) {
		super.setBackground(color);
		if (previewLabel != null && color != null) {
			previewLabel.setBackground(color);
			previewLabel.setForeground(color);
		}
	}

	@Override
	public void setForeground(final Color color) {
		super.setForeground(color);
		if (previewLabel != null && color != null) {
			previewLabel.setBackground(color);
			previewLabel.setForeground(color);
		}
	}
}

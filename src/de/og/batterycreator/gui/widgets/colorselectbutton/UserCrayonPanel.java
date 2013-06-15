package de.og.batterycreator.gui.widgets.colorselectbutton;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import og.basics.util.StaticExecutor;
import de.og.batterycreator.cfg.SettingsPersistor;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.gui.widgets.customcolors.NamedColor;
import de.og.batterycreator.gui.widgets.customcolors.NamedColorDialog;

public class UserCrayonPanel extends CrayonPanel {
	private static final long		serialVersionUID	= 1L;
	private static final ImageIcon	icon				= new ImageIcon(CrayonPanel.class.getResource("crayons.png"));

	public UserCrayonPanel() {
		super();
		guiInit();
	}

	private void guiInit() {
		final JButton saveButton = new JButton(IconStore.colorSave);
		final JButton manageButton = new JButton(IconStore.colorManage);
		manageButton.setToolTipText("Go to the folder, where the colors are stored and delete them there!");
		saveButton.setToolTipText("Save current color to 'User defined Colors'");
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
		final JToolBar bar = getToolBar();
		bar.setVisible(true);
		bar.add(saveButton);
		bar.add(manageButton);

	}

	protected void manage() {
		StaticExecutor.openFolder(SettingsPersistor.COLOR_DIR);
	}

	protected void save() {
		final Color col = getCurrentColor();
		final NamedColor ncol = NamedColorDialog.showDialog(null, new NamedColor(col, ""));
		if (ncol != null) {
			SettingsPersistor.writeColor(ncol);
			createCrayon(ncol.getName(), ncol.getColor());
			updateUI();
		}

	}

	@Override
	protected void addColors() {
		final Vector<NamedColor> colors = SettingsPersistor.readAllNamedColors();
		for (final NamedColor col : colors) {
			createCrayon(col.getName(), col.getColor());
		}
	}

	@Override
	public String getDisplayName() {
		return "User defined Colors";
	}

	@Override
	protected ImageIcon getIcon() {
		return icon;
	}

	@Override
	public Icon getSmallDisplayIcon() {
		return IconStore.colorManage;
	}

	@Override
	public Icon getLargeDisplayIcon() {
		return IconStore.colorManage;
	}

}

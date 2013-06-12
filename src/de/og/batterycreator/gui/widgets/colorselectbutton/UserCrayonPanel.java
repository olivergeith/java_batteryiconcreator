package de.og.batterycreator.gui.widgets.colorselectbutton;

import java.util.Vector;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.SettingsPersistor;
import de.og.batterycreator.gui.widgets.customcolors.NamedColor;

public class UserCrayonPanel extends CrayonPanel {
	private static final long		serialVersionUID	= 1L;
	private static final ImageIcon	icon				= new ImageIcon(CrayonPanel.class.getResource("crayons.png"));

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

}

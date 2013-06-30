package de.og.batterycreator.gui.widgets.colorselectbutton;

import java.awt.Color;
import javax.swing.ImageIcon;

public class NadmajCrayonPanel extends CrayonPanel {
	private static final long		serialVersionUID	= 1L;

	private static final ImageIcon	icon				= new ImageIcon(CrayonPanel.class.getResource("nadmaj.png"));

	@Override
	protected void addColors() {
		createCrayon("White", new Color(255, 255, 255));
		createCrayon("Red", new Color(255, 51, 51));
		createCrayon("Purple", new Color(204, 0, 204));
		createCrayon("Pink", new Color(255, 153, 153));
		createCrayon("Orange", new Color(255, 153, 0));
		createCrayon("Light Green", new Color(142, 205, 0));
		createCrayon("Light Blue", new Color(51, 181, 229));
		createCrayon("Green", new Color(0, 204, 51));
		createCrayon("Cyan", new Color(0, 255, 255));
		createCrayon("Yellow Gold", new Color(255, 204, 0));
	}

	@Override
	public String getDisplayName() {
		return "NadMaj Simplistics";
	}

	@Override
	protected ImageIcon getIcon() {
		return icon;
	}

	@Override
	protected String getUrl() {
		return "http://forum.xda-developers.com/showpost.php?p=38735293&postcount=5";
	}

}

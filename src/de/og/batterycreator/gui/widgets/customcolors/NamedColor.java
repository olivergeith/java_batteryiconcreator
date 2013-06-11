package de.og.batterycreator.gui.widgets.customcolors;

import java.awt.Color;

public class NamedColor {
	private final String	name;
	private final Color		color;

	public NamedColor(final Color color, final String name) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return name;
	}

}

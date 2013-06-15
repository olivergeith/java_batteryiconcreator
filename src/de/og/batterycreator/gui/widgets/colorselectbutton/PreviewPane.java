package de.og.batterycreator.gui.widgets.colorselectbutton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class PreviewPane extends JLabel {
	private static final long	serialVersionUID	= -7740855618285717792L;
	Color						curColor;

	public PreviewPane(final JColorChooser chooser) {
		curColor = chooser.getColor();
		final ColorSelectionModel model = chooser.getSelectionModel();
		model.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent evt) {
				final ColorSelectionModel model = (ColorSelectionModel) evt.getSource();
				curColor = model.getSelectedColor();
			}
		});
		setPreferredSize(new Dimension(50, 50));
	}

	@Override
	public void paint(final Graphics g) {
		g.setColor(curColor);
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
	}
}
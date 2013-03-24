package de.og.batterycreator.gui.widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderAndLabel extends JSlider {

	private static final long serialVersionUID = -5143738798997636241L;
	private final JLabel valueLabel = new JLabel();

	public JLabel getValueLabel() {
		return valueLabel;
	}

	Dimension prefsize = new Dimension(130, 25);

	public SliderAndLabel(final int min, final int max) {
		super(min, max);
		initUI();
	}

	private void initUI() {
		setLayout(new BorderLayout());
		setPreferredSize(prefsize);
		setSize(prefsize);
		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				valueLabel.setText("" + getValue());
			}
		});
		valueLabel.setBorder(new BevelBorder(1));
	}

	@Override
	public void setValue(final int value) {
		super.setValue(value);
		valueLabel.setText("" + value);

	}
}

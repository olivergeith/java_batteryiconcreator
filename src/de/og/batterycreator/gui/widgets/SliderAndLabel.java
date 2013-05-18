package de.og.batterycreator.gui.widgets;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderAndLabel extends JSlider {

	private static final long	serialVersionUID	= -5143738798997636241L;
	private final JLabel		valueLabel			= new JLabel();

	private final JToolBar		toolbar				= new JToolBar();

	public JToolBar getToolbar() {
		return toolbar;
	}

	public JLabel getValueLabel() {
		return valueLabel;
	}

	Dimension	prefsize	= new Dimension(30, 25);

	public SliderAndLabel(final int min, final int max) {
		super(min, max);
		initUI();
	}

	private void initUI() {
		valueLabel.setPreferredSize(prefsize);
		valueLabel.setSize(prefsize);
		valueLabel.setMinimumSize(prefsize);
		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				valueLabel.setText("" + getValue());
			}
		});
		toolbar.setFloatable(false);
		toolbar.add(this);
		toolbar.add(valueLabel);
		valueLabel.setBorder(new BevelBorder(1));
	}

	@Override
	public void setValue(final int value) {
		super.setValue(value);
		valueLabel.setText("" + value);

	}

	@Override
	public void setEnabled(final boolean arg0) {
		super.setEnabled(arg0);
		if (valueLabel != null)
			valueLabel.setEnabled(arg0);
		if (toolbar != null)
			toolbar.setEnabled(arg0);
	}

}

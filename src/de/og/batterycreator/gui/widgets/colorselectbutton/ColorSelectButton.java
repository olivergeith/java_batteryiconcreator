/**
 * 
 */
package de.og.batterycreator.gui.widgets.colorselectbutton;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 * This Button opens a ColorChooser and sets its onw Background to the choosen
 * color.
 * 
 * @author geith
 * 
 */
public class ColorSelectButton extends JButton {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1405509065947842554L;

	/**
	 * 
	 */
	public ColorSelectButton() {
		initUI();
	}

	/**
	 * @param arg0
	 */
	public ColorSelectButton(final Icon arg0) {
		super(arg0);
		initUI();
	}

	/**
	 * @param arg0
	 */
	public ColorSelectButton(final String arg0) {
		super(arg0);
		initUI();
	}

	public ColorSelectButton(final String arg0, final String tooltip) {
		super(arg0);
		setToolTipText(tooltip);
		initUI();
	}

	/**
	 * @param arg0
	 */
	public ColorSelectButton(final Action arg0) {
		super(arg0);
		initUI();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ColorSelectButton(final String arg0, final Icon arg1) {
		super(arg0, arg1);
		initUI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#setBackground(java.awt.Color)
	 */
	@Override
	public void setBackground(final Color bg) {
		super.setBackground(bg);
		final int howdark = bg.getRed() + bg.getGreen() + bg.getBlue();
		if (howdark < (3 * 255) / 2)
			setForeground(Color.white);
		else
			setForeground(Color.black);
	}

	private void initUI() {
		// setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				bringUpColorChooser2();
			}
		});
	}

	public void setColor(final Color col) {
		setBackground(col);
	}

	public Color getColor() {
		return getBackground();
	}

	private void bringUpColorChooser2() {
		final JColorChooser colorChooser = new JColorChooser();

		final JPanel previewPanel = new PreviewPanel(colorChooser);

		// final JLabel previewLabel = new JLabel();
		// previewLabel.setText("xxx");
		// previewLabel.setSize(previewLabel.getPreferredSize());
		// previewLabel.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 48));
		// previewLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
		colorChooser.setPreviewPanel(previewPanel);

		// Override the chooser panels with our own

		final AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();

		final AbstractColorChooserPanel[] panelsnew = new AbstractColorChooserPanel[panels.length + 4];

		for (int i = 0; i < panels.length; i++) {
			panelsnew[i] = panels[i];
		}
		panelsnew[panels.length] = new CrayonPanel();
		panelsnew[panels.length + 1] = new KrozCrayonPanel();
		panelsnew[panels.length + 2] = new MorphologyCrayonPanel();
		panelsnew[panels.length + 3] = new UserCrayonPanel();

		colorChooser.setChooserPanels(panelsnew);

		final ActionListener okActionListener = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent actionEvent) {
				System.out.println(colorChooser.getColor());
				setBackground(colorChooser.getColor());
				informListeners();
			}
		};

		final ActionListener cancelActionListener = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent actionEvent) {
				System.out.println("Cancel Button");
			}
		};

		final JDialog dialog = JColorChooser.createDialog(this, "Change Color", true, colorChooser, okActionListener, cancelActionListener);

		dialog.setVisible(true);
	}

	// ##################################################################################
	// ColorChangeListener
	// ##################################################################################
	/**
	 * the listener who want to know about changes in Orgdata
	 */
	private final List<ColorChangeListener>	changeListeners	= new ArrayList<ColorChangeListener>();

	/**
	 * 
	 * @return the List of {@link ColorChangeListener}
	 */
	public List<ColorChangeListener> getColorChangeListeners() {
		synchronized (changeListeners) {
			return changeListeners;
		}
	}

	/**
	 * Informing the subscribed listeners about updates
	 */
	protected void informListeners() {
		for (final ColorChangeListener listener : getColorChangeListeners()) {
			try {
				listener.onChange(getColor());
			} catch (final RuntimeException exc) {
				System.out.println("dataReceived failed for listener " + listener);
			}
		}
	}

	/**
	 * Adding {@link ColorChangeListener}
	 * 
	 * @param listener
	 *            the {@link ColorChangeListener}
	 */
	public void addColorChangeListener(final ColorChangeListener listener) {
		synchronized (changeListeners) {
			changeListeners.add(listener);
		}
	}

	/**
	 * Removing {@link ColorChangeListener}
	 * 
	 * @param listener
	 *            the {@link ColorChangeListener}
	 */
	public void removeColorChangeListener(final ColorChangeListener listener) {
		synchronized (changeListeners) {
			changeListeners.remove(listener);
		}
	}

}

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
import og.basics.util.StaticColorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Button opens a ColorChooser and sets its onw Background to the choosen
 * color.
 * 
 * @author geith
 * 
 */
public class ColorSelectButton extends JButton {

	private static final long	serialVersionUID	= 1405509065947842554L;

	/**
	 * the Logger for this Class
	 */
	private static final Logger	LOG					= LoggerFactory.getLogger(ColorSelectButton.class);

	public ColorSelectButton() {
		initUI();
	}

	public ColorSelectButton(final Icon arg0) {
		super(arg0);
		initUI();
	}

	public ColorSelectButton(final String arg0) {
		super(arg0);
		initUI();
	}

	public ColorSelectButton(final String arg0, final String tooltip) {
		super(arg0);
		setToolTipText(tooltip);
		initUI();
	}

	public ColorSelectButton(final Action arg0) {
		super(arg0);
		initUI();
	}

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
		if (StaticColorHelper.isdark(bg))
			setForeground(Color.white);
		else
			setForeground(Color.black);
	}

	private void initUI() {
		// setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				bringUpColorChooser();
			}
		});
	}

	public void setColor(final Color col) {
		setBackground(col);
	}

	public Color getColor() {
		return getBackground();
	}

	private void bringUpColorChooser() {
		final JColorChooser colorChooser = new JColorChooser();
		// final PreviewPane previewPanel = new PreviewPane(colorChooser);
		// colorChooser.setPreviewPanel(previewPanel);
		colorChooser.addChooserPanel(new CrayonPanel());
		colorChooser.addChooserPanel(new KrozCrayonPanel());
		colorChooser.addChooserPanel(new MorphologyCrayonPanel());
		colorChooser.addChooserPanel(new UserCrayonPanel());

		final ActionListener okActionListener = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent actionEvent) {
				// System.out.println(colorChooser.getColor());
				setBackground(colorChooser.getColor());
				informListeners();
			}
		};

		final ActionListener cancelActionListener = new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent actionEvent) {
				LOG.debug("Cancel Button");
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
				LOG.error("dataReceived failed for listener {}", listener);
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

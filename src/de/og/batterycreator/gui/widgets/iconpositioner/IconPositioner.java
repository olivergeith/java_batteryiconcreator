package de.og.batterycreator.gui.widgets.iconpositioner;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class IconPositioner extends JToolBar implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {
	private static final long				serialVersionUID	= 1L;

	private final IconPositionerCanvasLabel	canvas				= new IconPositionerCanvasLabel();
	private final Point						mousePos			= new Point();
	private boolean							dragging			= false;

	private final Model						modelX;
	private final Model						modelY;
	// private final JLabel labelX = new JLabel();
	// private final JLabel labelY = new JLabel();

	private final JSpinner					spinnerX			= new JSpinner();
	private final JSpinner					spinnerY			= new JSpinner();

	private boolean							isListener			= false;

	/**
	 * Konstruktor
	 * 
	 * @param model
	 */
	public IconPositioner(final int min, final int max) {
		modelX = new Model(0, min, max, 1);
		modelY = new Model(0, min, max, 1);
		initGui();
	}

	private void initGui() {
		spinnerX.setModel(modelX);
		spinnerX.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				repaintCanvas();
			}
		});
		spinnerY.setModel(modelY);
		spinnerY.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent arg0) {
				repaintCanvas();
			}
		});
		setUpMousListener(true);
		this.add(canvas);
		this.add(new JLabel(" X:"));
		this.add(spinnerX);
		this.add(new JLabel(" Y:"));
		this.add(spinnerY);
		setFloatable(false);
	}

	public void setPosition(final int x, final int y) {
		modelX.setValue(x);
		modelY.setValue(y);
		repaintCanvas();
	}

	public Point getPosition() {
		return new Point(modelX.getIntValue(), modelY.getIntValue());
	}

	/**
	 * Konvertierungsmethode, um von einer Mouse-X-Koordinate auf ein IntValue
	 * zu kommen
	 * 
	 * @param mouseX
	 * @return
	 */
	private int convertMouseX2ModelValue(final int mouseX) {
		final int value = modelX.getIntMin() + mouseX * (modelX.getIntMax() - modelX.getIntMin()) / canvas.getWidth();
		return value;
	}

	/**
	 * Konvertierungsmethode, um von einer Mouse-X-Koordinate auf ein IntValue
	 * zu kommen
	 * 
	 * @param mouseY
	 * @return
	 */
	private int convertMouseY2ModelValue(final int mouseY) {
		final int value = modelY.getIntMin() + mouseY * (modelY.getIntMax() - modelY.getIntMin()) / canvas.getHeight();
		return value;
	}

	/**
	 * Konvertierungsmethode, um von einem IntValue eauf eine Mouse-X-Koordinate
	 * zu kommen
	 * 
	 * @param value
	 * @return
	 */
	private int convertModelValue2MouseX(final int value) {
		final int mouseX = (value - modelX.getIntMin()) * canvas.getWidth() / (modelX.getIntMax() - modelX.getIntMin());
		return mouseX;
	}

	/**
	 * Konvertierungsmethode, um von einem IntValue eauf eine Mouse-X-Koordinate
	 * zu kommen
	 * 
	 * @param value
	 * @return
	 */
	private int convertModelValue2MouseY(final int value) {
		final int mouseY = (value - modelY.getIntMin()) * canvas.getHeight() / (modelY.getIntMax() - modelY.getIntMin());
		return mouseY;
	}

	/**
	 * Updatemethode, die beim Mouse-draggen aufgerufen wird
	 */
	public void updateComps(final MouseEvent e) {
		mousePos.move(e.getX(), e.getY());
		modelX.setValue(convertMouseX2ModelValue(e.getX()));
		modelY.setValue(convertMouseY2ModelValue(e.getY()));
		repaintCanvas();
	}

	/**
	 * Methode um das Vanvas zu repainten, wenn von aussen das Value geändert
	 * wurde
	 * 
	 */
	private void repaintCanvas() {
		final int x = convertModelValue2MouseX(modelX.getIntValue());
		final int y = convertModelValue2MouseY(modelY.getIntValue());
		// labelX.setText("X=" + modelX.getIntValue());
		// labelY.setText("Y=" + modelY.getIntValue());
		informListeners(modelX.getIntValue(), modelY.getIntValue());
		canvas.setPosition(new Point(x, y));
		canvas.repaint();
	}

	@Override
	public void setEnabled(final boolean enabled) {
		super.setEnabled(enabled);
		spinnerX.setEnabled(enabled);
		spinnerY.setEnabled(enabled);
		canvas.setEnabled(enabled);
		setUpMousListener(enabled);
		repaintCanvas();
	}

	private void setUpMousListener(final boolean enabled) {
		if (enabled) {
			if (!isListener) {
				canvas.addMouseMotionListener(this);
				canvas.addMouseListener(this);
				addMouseWheelListener(this);
				addComponentListener(this);
				isListener = true;
			}
		} else {
			canvas.removeMouseMotionListener(this);
			canvas.removeMouseListener(this);
			removeMouseWheelListener(this);
			removeComponentListener(this);
			isListener = false;
		}
	}

	/**
	 * MouseListener
	 */
	@Override
	public void mouseClicked(final MouseEvent e) {
		if (e.getClickCount() == 2) {
			// doubleclicked
			setPosition(0, 0);
		}
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		if (canvas.isTouched(e.getX(), e.getY())) {
			setDragging(true);
			updateComps(e);
		}
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		if (isDragging()) {
			setDragging(false);
			updateComps(e);
		}
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		if (isDragging()) {
			updateComps(e);
		}
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
	}

	/**
	 * wird gerade gedragged?
	 * 
	 * @return
	 */
	private boolean isDragging() {
		return dragging;
	}

	/**
	 * DraggingMode an- und abschalten
	 * 
	 * @param dragging
	 */
	private void setDragging(final boolean dragging) {
		this.dragging = dragging;
	}

	/**
	 * MousWheelListener
	 */
	@Override
	public void mouseWheelMoved(final MouseWheelEvent e) {
		final int rotation = e.getWheelRotation();
		if ((e.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK) {
			if (rotation > 0)
				modelX.stepDown();
			else
				modelX.stepUp();
		} else {
			if (rotation > 0)
				modelY.stepDown();
			else
				modelY.stepUp();
		}
		repaintCanvas();
	}

	/**
	 * ComponentListener
	 */
	@Override
	public void componentHidden(final ComponentEvent e) {
	}

	@Override
	public void componentMoved(final ComponentEvent e) {
	}

	@Override
	public void componentResized(final ComponentEvent e) {
		repaintCanvas();
	}

	@Override
	public void componentShown(final ComponentEvent e) {
	}

	// ##################################################################################
	// Listener
	// ##################################################################################
	/**
	 * the listener who want to know about changes in Orgdata
	 */
	private final List<IconPositionerListener>	changeListeners	= new ArrayList<IconPositionerListener>();

	/**
	 * 
	 * @return the List of {@link IconPositionerListener}
	 */
	public List<IconPositionerListener> getChangeListeners() {
		synchronized (changeListeners) {
			return changeListeners;
		}
	}

	/**
	 * Informing the subscribed listeners about updates
	 */
	protected void informListeners(final int x, final int y) {
		for (final IconPositionerListener listener : getChangeListeners()) {
			try {
				listener.positionChanged(x, y);
			} catch (final RuntimeException exc) {
				System.out.println("positionChanged failed for listener " + listener);
			}
		}
	}

	/**
	 * Adding {@link IconPositionerListener}
	 * 
	 * @param listener
	 *            the {@link IconPositionerListener}
	 */
	public void addIconPositionerListener(final IconPositionerListener listener) {
		synchronized (changeListeners) {
			changeListeners.add(listener);
		}
	}

	/**
	 * Removing {@link IconPositionerListener}
	 * 
	 * @param listener
	 *            the {@link IconPositionerListener}
	 */
	public void removeIconPositionerListener(final IconPositionerListener listener) {
		synchronized (changeListeners) {
			changeListeners.remove(listener);
		}
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		final JFrame f = new JFrame("Positioner", null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(150, 75);
		f.setLayout(new BorderLayout());
		final IconPositioner spindial = new IconPositioner(-20, 20);
		spindial.setPosition(5, 5);
		f.getContentPane().add(spindial, BorderLayout.CENTER);
		f.setVisible(true);
	}

}

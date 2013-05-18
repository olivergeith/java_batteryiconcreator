package de.og.batterycreator.gui.widgets.iconpositioner;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Zeichnet das eigentliche Display
 * 
 * @author Geith
 * 
 */
class IconPositionerCanvasLabel extends JLabel {
	private static final long		serialVersionUID	= 1L;

	public static final ImageIcon	back				= new ImageIcon(IconPositionerCanvasLabel.class.getResource("back.png"));

	private Point					position			= new Point(1, 1);
	public static Color				ZEIGER_COLOR		= Color.RED;
	private final Rectangle			touchRect			= new Rectangle();

	/**
	 * definiert, wie sich die isTouched-Mehtode verhält Wenn false, wird nur
	 * eine Selection des Zeigers erlaubt ansosnten kann man überall hinklicken
	 */
	private boolean					quickedit			= true;

	public IconPositionerCanvasLabel() {
		this.setSize(new Dimension(back.getIconWidth(), back.getIconWidth()));
		setIcon(back);
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		drawHorizontalZeigerInstrument(g, 0);
	}

	private void drawHorizontalZeigerInstrument(final Graphics g, final int yPosition) {
		final Graphics2D g2d = (Graphics2D) g;

		// Touch rect berechnen
		touchRect.setLocation(position.x - 3, position.y - 3);
		touchRect.setSize(6, 6);

		// Fadenkreuz
		g2d.setColor(new Color(0, 0, 0, 40));
		g2d.fillRect(position.x - 1, 0, 2, getHeight());
		g2d.fillRect(0, position.y, getWidth(), 2);

		// Shadow under knob
		g2d.setColor(new Color(0, 0, 0, 15));
		g2d.fillRect(touchRect.x, touchRect.y, touchRect.width + 3, touchRect.height + 3);
		g2d.setColor(new Color(0, 0, 0, 60));
		g2d.fillRect(touchRect.x + 1, touchRect.y + 1, touchRect.width + 1, touchRect.height + 1);

		// Zeiger + schatten
		g2d.setColor(Color.red);
		g2d.fillRect(touchRect.x, touchRect.y, touchRect.width, touchRect.height);
		g2d.setColor(Color.darkGray);
		g2d.drawRect(touchRect.x - 1, touchRect.y - 1, touchRect.width + 1, touchRect.height + 1);

	}

	/**
	 * Gibt zurück, ob das das Panel angefasst wurde Im Quickedit-Mode kommt
	 * immer true zurück. Ansonsten nur, wenn der Zeiger getouched ist.
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isTouched(final int x, final int y) {
		if (isQuickedit())
			return true;
		return touchRect.contains(x, y);
	}

	/**
	 * 
	 * @param zeigerPosX
	 */
	public void setPosition(final int x, final int y) {
		if (x > getWidth())
			position.x = getWidth();
		else if (x < 1)
			position.x = 1;
		else
			position.x = x;

		if (y > getHeight())
			position.y = getHeight();
		else if (y < 1)
			position.y = 1;
		else
			position.y = y;
	}

	/**
	 * Ist das Canvas im Qickedit-Mode?
	 * 
	 * @return
	 */
	public boolean isQuickedit() {
		return quickedit;
	}

	/**
	 * Setzt den Quickedit-Mode true...das Bedienung ist erlaubt ohne vorher den
	 * Zeiger anzufassen false ...der Zeiger muss angefasst werden um den Wert
	 * zu ändern
	 * 
	 * @param quickedit
	 */
	public void setQuickedit(final boolean quickedit) {
		this.quickedit = quickedit;
	}

	/**
	 * @return the position
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(final Point position) {
		this.position = position;
	}

}

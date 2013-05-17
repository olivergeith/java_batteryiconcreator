package de.og.batterycreator.gui.widgets.iconpositioner;

import java.awt.Color;
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
		// this.setSize(dim);
		setIcon(back);
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		drawHorizontalZeigerInstrument(g, 0);
	}

	private void drawHorizontalZeigerInstrument(final Graphics g, final int yPosition) {
		final Graphics2D g2d = (Graphics2D) g;

		// g2d.drawImage(back.getImage(), 0, 0, null);
		// hintergrund
		// final Color c1 = new Color(192, 200, 255, 255);
		// final Color c2 = new Color(255, 255, 255, 255);
		// final GradientPaint gradientFill = new GradientPaint(0, 0, c1,
		// getWidth(), getHeight(), c2);
		// g2d.setPaint(gradientFill);
		// g2d.fillRect(0, 0, getWidth(), getHeight());

		// rahmen + schatten
		// g2d.setColor(new Color(128, 128, 128, 25));
		// g2d.fillRect(0, 0, 3, getHeight());
		// g2d.fillRect(3, 0, getWidth(), 3);

		// Zeiger + schatten
		touchRect.setLocation(position.x - 2, position.y - 2);
		touchRect.setSize(4, 4);
		g2d.setColor(Color.red);
		g2d.fillRect(touchRect.x, touchRect.y, touchRect.width, touchRect.height);

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

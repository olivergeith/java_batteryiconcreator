package de.og.batterycreator.gui.widgets.customcolors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class NamedColorDialog extends JDialog {
	private static final long	serialVersionUID	= 2359150242293472883L;
	private final JButton		okButton			= new JButton("OK");
	private final JButton		cancelButton		= new JButton("Cancel");
	private final JTextField	nameField			= new JTextField();
	private final JToolBar		toolbarBottom		= new JToolBar();

	private NamedColor			color;

	public NamedColorDialog(final Frame arg0, final NamedColor color) {
		super(arg0);
		this.color = color;
		initUI();
	}

	public NamedColorDialog(final Dialog arg0, final NamedColor color) {
		super(arg0);
		this.color = color;
		initUI();
	}

	public NamedColorDialog(final Window arg0, final NamedColor color) {
		super(arg0);
		this.color = color;
		initUI();
	}

	private void initUI() {
		setTitle("Enter Name for Color");
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		this.getContentPane().setBackground(color.getColor());
		nameField.setText(color.getName());
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(300, 100));
		setSize(new Dimension(300, 200));
		setBounds(200, 300, 300, 200);
		toolbarBottom.setFloatable(false);
		toolbarBottom.add(nameField);
		toolbarBottom.add(okButton);
		toolbarBottom.add(cancelButton);
		nameField.setPreferredSize(new Dimension(100, 25));
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				ok();
			}
		});
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				cancel();
			}
		});
		nameField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(final KeyEvent e) {
				validateControlls();
			}

			@Override
			public void keyReleased(final KeyEvent e) {
				validateControlls();
			}

			@Override
			public void keyPressed(final KeyEvent e) {
				validateControlls();
			}
		});

		add(toolbarBottom, BorderLayout.NORTH);
		validateControlls();
		pack();
	}

	private void validateControlls() {
		okButton.setEnabled(nameField.getText().length() > 0);

	}

	protected void cancel() {
		color = null;
		setVisible(false);
	}

	protected void ok() {
		color = new NamedColor(color.getColor(), nameField.getText());
		setVisible(false);
	}

	public static NamedColor showDialog(final Window parent, final NamedColor color) {
		final NamedColorDialog f = new NamedColorDialog(parent, color);
		f.setVisible(true);
		return f.getColor();
	}

	/**
	 * For testing purposes !!!
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		final NamedColor color = showDialog((Window) null, new NamedColor(Color.red, "Rot"));
		// final NamedColorDialog f = new NamedColorDialog((Window) null, new
		// NamedColor(Color.red, "Rot"));
		// f.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		// f.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// f.setVisible(true);
		System.out.println("Closed --> Color = " + color);
	}

	public NamedColor getColor() {
		return color;
	}

}

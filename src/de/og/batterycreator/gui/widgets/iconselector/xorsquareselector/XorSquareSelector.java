package de.og.batterycreator.gui.widgets.iconselector.xorsquareselector;

import javax.swing.ImageIcon;
import de.og.batterycreator.gui.widgets.iconselector.IconSelector;

public class XorSquareSelector extends IconSelector {

	private static final long		serialVersionUID	= -7712530632645291404L;

	public static final ImageIcon	icon01				= new ImageIcon(XorSquareSelector.class.getResource("icon1.png"));
	public static final ImageIcon	icon02				= new ImageIcon(XorSquareSelector.class.getResource("icon2.png"));
	private final static String		customPath			= "./custom/xorSquare";

	public XorSquareSelector(final int height) {
		super(customPath, height);
	}

	@Override
	public void fillStaticIcons() {
		addItem(icon01);
		addItem(icon02);
	}

	// /**
	// * For testing purposes !!!
	// *
	// * @param args
	// */
	// public static void main(final String[] args) {
	//
	// final JFrame f = new JFrame();
	// f.setTitle("Hallo Emmy!!!!!!!");
	// f.setBounds(200, 200, 200, 200);
	// f.setLayout(new BorderLayout());
	// final CopyOfXorIconSelector combo = new CopyOfXorIconSelector(36);
	// combo.setSelectedIndex(0);
	// f.add(combo, BorderLayout.CENTER);
	//
	// f.setVisible(true);
	// }

}

package de.og.batterycreator.gui.widgets.iconselector.chargeiconselector;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.gui.widgets.iconselector.IconSelector;

public class ChargeIconSelector extends IconSelector {
	private static final Logger		LOGGER				= LoggerFactory.getLogger(ChargeIconSelector.class);
	private static final long		serialVersionUID	= -7712530632645291404L;

	private final static String		customPath			= "./custom/charge";

	private static final ImageIcon	charge01			= new ImageIcon(ChargeIconSelector.class.getResource("charge1.png"));
	private static final ImageIcon	charge02			= new ImageIcon(ChargeIconSelector.class.getResource("charge2.png"));
	private static final ImageIcon	charge03			= new ImageIcon(ChargeIconSelector.class.getResource("charge3.png"));
	private static final ImageIcon	charge04			= new ImageIcon(ChargeIconSelector.class.getResource("charge4.png"));
	private static final ImageIcon	charge05			= new ImageIcon(ChargeIconSelector.class.getResource("charge5.png"));
	private static final ImageIcon	charge06			= new ImageIcon(ChargeIconSelector.class.getResource("charge6.png"));
	private static final ImageIcon	charge07			= new ImageIcon(ChargeIconSelector.class.getResource("charge7.png"));

	public ChargeIconSelector(final int height) {
		super(customPath, height);
		setRenderer(new IconListCellRenderer());
	}

	@Override
	public void fillStaticIcons() {
		addItem(charge01);
		addItem(charge02);
		addItem(charge03);
		addItem(charge04);
		addItem(charge05);
		addItem(charge06);
		addItem(charge07);
		LOGGER.info("Loading Custom Charge Icons!");
	}

	/**
	 * Renderer für IconList
	 */
	private class IconListCellRenderer implements ListCellRenderer<ImageIcon> {
		protected DefaultListCellRenderer	defaultRenderer	= new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends ImageIcon> list, final ImageIcon value, final int index, final boolean isSelected,
				final boolean cellHasFocus) {

			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof ImageIcon) {
				renderer.setBorder(new EmptyBorder(1, 1, 1, 1));
				renderer.setBackground(Color.gray);
				renderer.setForeground(Color.white);
				renderer.setBorder(new EmptyBorder(1, 1, 1, 1));
			}
			return renderer;
		}

	}

}

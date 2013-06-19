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

	private static final ImageIcon	charge01			= new ImageIcon(ChargeIconSelector.class.getResource("charge01.png"));
	private static final ImageIcon	charge02			= new ImageIcon(ChargeIconSelector.class.getResource("charge02.png"));
	private static final ImageIcon	charge03			= new ImageIcon(ChargeIconSelector.class.getResource("charge03.png"));
	private static final ImageIcon	charge04			= new ImageIcon(ChargeIconSelector.class.getResource("charge04.png"));
	private static final ImageIcon	charge10			= new ImageIcon(ChargeIconSelector.class.getResource("charge10.png"));
	private static final ImageIcon	charge11			= new ImageIcon(ChargeIconSelector.class.getResource("charge11.png"));
	private static final ImageIcon	charge12			= new ImageIcon(ChargeIconSelector.class.getResource("charge12.png"));
	private static final ImageIcon	charge20			= new ImageIcon(ChargeIconSelector.class.getResource("charge20.png"));
	private static final ImageIcon	charge21			= new ImageIcon(ChargeIconSelector.class.getResource("charge21.png"));
	private static final ImageIcon	charge22			= new ImageIcon(ChargeIconSelector.class.getResource("charge22.png"));
	private static final ImageIcon	charge23			= new ImageIcon(ChargeIconSelector.class.getResource("charge23.png"));
	private static final ImageIcon	charge24			= new ImageIcon(ChargeIconSelector.class.getResource("charge24.png"));
	private static final ImageIcon	charge25			= new ImageIcon(ChargeIconSelector.class.getResource("charge25.png"));
	private static final ImageIcon	charge26			= new ImageIcon(ChargeIconSelector.class.getResource("charge26.png"));
	private static final ImageIcon	charge30			= new ImageIcon(ChargeIconSelector.class.getResource("charge30.png"));
	private static final ImageIcon	charge31			= new ImageIcon(ChargeIconSelector.class.getResource("charge31.png"));
	private static final ImageIcon	charge32			= new ImageIcon(ChargeIconSelector.class.getResource("charge32.png"));
	private static final ImageIcon	charge40			= new ImageIcon(ChargeIconSelector.class.getResource("charge40.png"));
	private static final ImageIcon	charge41			= new ImageIcon(ChargeIconSelector.class.getResource("charge41.png"));
	private static final ImageIcon	charge42			= new ImageIcon(ChargeIconSelector.class.getResource("charge42.png"));

	public ChargeIconSelector(final int height) {
		super(customPath, height);
		setRenderer(new IconListCellRenderer());
	}

	@Override
	public void fillStaticIcons() {
		addItem(charge20);
		addItem(charge21);
		addItem(charge22);
		addItem(charge23);
		addItem(charge24);
		addItem(charge25);
		addItem(charge26);
		addItem(charge01);
		addItem(charge02);
		addItem(charge03);
		addItem(charge04);
		addItem(charge30);
		addItem(charge31);
		addItem(charge32);
		addItem(charge10);
		addItem(charge11);
		addItem(charge12);
		addItem(charge40);
		addItem(charge41);
		addItem(charge42);
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

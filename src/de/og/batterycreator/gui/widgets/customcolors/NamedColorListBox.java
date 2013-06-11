package de.og.batterycreator.gui.widgets.customcolors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Vector;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.cfg.SettingsPersistor;

public class NamedColorListBox extends JList<NamedColor> {
	private static final long	serialVersionUID	= 1L;
	private static final Logger	LOGGER				= LoggerFactory.getLogger(NamedColorListBox.class);

	public NamedColorListBox() {
		initUI();

	}

	private void initUI() {
		setCellRenderer(new MyListCellRenderer());
		final Vector<NamedColor> colors = SettingsPersistor.readAllNamedColors();
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setListData(colors);
	}

	/**
	 * Renderer for Combo
	 */
	private class MyListCellRenderer implements ListCellRenderer<NamedColor> {
		protected DefaultListCellRenderer	defaultRenderer	= new DefaultListCellRenderer();

		@Override
		public Component getListCellRendererComponent(final JList<? extends NamedColor> list, final NamedColor value, final int index,
				final boolean isSelected, final boolean cellHasFocus) {
			final JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (value instanceof NamedColor) {
				final NamedColor col = value;
				if (renderer.getIcon() == null) {
					if (col != null) {
						renderer.setBackground(col.getColor());
					}
				}
			}
			return renderer;
		}
	}

	/**
	 * For testing purposes !!!
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		final JFrame f = new JFrame();
		f.setTitle("Hallo Emmy!!!!!!!");
		f.setBounds(200, 200, 800, 80);
		f.setLayout(new BorderLayout());
		final NamedColorListBox combo = new NamedColorListBox();
		combo.setSelectedIndex(0);
		f.add(combo, BorderLayout.CENTER);

		f.setVisible(true);
	}

}

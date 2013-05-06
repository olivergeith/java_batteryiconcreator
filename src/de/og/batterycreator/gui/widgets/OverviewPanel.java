package de.og.batterycreator.gui.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import og.basics.gui.image.StaticImageHelper;

public class OverviewPanel extends JPanel {
	private static final long	serialVersionUID	= -2956273745014471932L;

	private final JLabel		overviewLabel		= new JLabel();
	private ImageIcon			overview;
	private final JSlider		slider				= new JSlider(10, 200);
	private BufferedImage		overbuff;
	private final JLabel		zoomLabel			= new JLabel("Zoom");

	public OverviewPanel(final int zoomValue) {
		initUI(zoomValue);
	}

	public OverviewPanel() {
		initUI(100);
	}

	private void initUI(final int zoomValue) {
		setLayout(new BorderLayout());
		setBackground(Color.black);
		// overviewLabel.setBackground(Color.black);
		overviewLabel.setForeground(Color.white);
		slider.setValue(zoomValue);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(5);
		// slider.setSnapToTicks(true);
		slider.setOrientation(JSlider.VERTICAL);
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				showOverview();
			}
		});
		// Scrollable Overview!
		final JPanel p = new JPanel(new BorderLayout());
		p.setBackground(Color.black);
		p.add(overviewLabel, BorderLayout.CENTER);
		final JScrollPane scroller = new JScrollPane();
		scroller.add(p);
		scroller.getViewport().setView(p);
		// scroller.setBackground(Color.black);
		add(scroller, BorderLayout.CENTER);
		final JPanel toolbar = new JPanel(new BorderLayout());
		toolbar.setBorder(new EmptyBorder(3, 3, 3, 3));
		toolbar.add(zoomLabel, BorderLayout.NORTH);
		toolbar.add(slider, BorderLayout.CENTER);
		add(toolbar, BorderLayout.EAST);
	}

	public void setOverview(final ImageIcon overview) {
		setOverview(overview, 100);
	}

	public void setOverview(final ImageIcon overview, final boolean autozoom) {
		if (autozoom)
			setOverview(overview, calcAutoZoom(overview));
		else
			setOverview(overview);
	}

	public void setOverview(final ImageIcon overview, final int zoom) {
		slider.setValue(zoom);
		this.overview = overview;
		overbuff = StaticImageHelper.convertImageIcon(overview);
		showOverview();
	}

	public void setText(final String txt) {
		overviewLabel.setText(txt);
	}

	private void showOverview() {
		if (overview != null) {
			final int height100 = overview.getIconHeight();
			final int value = slider.getValue();
			final int height = Math.round(height100 / 100f * value);
			final BufferedImage buff = StaticImageHelper.resize2Height(overbuff, height);
			overviewLabel.setIcon(new ImageIcon(buff));
			zoomLabel.setText(createZoomLabelText(value));
		}
	}

	/**
	 * Calculates the Zoomfactor so that the height og the overview matches the
	 * height of the panel!
	 * 
	 * @param overview
	 * @return
	 */
	private int calcAutoZoom(final ImageIcon overview) {
		int zoom = 100;
		final int overpaneHeight = getHeight();
		// Wenn der overview höher ist als das Overview Panel
		if (overview.getIconHeight() > overpaneHeight) {
			// dann passen wir es genau an!
			zoom = Math.round(100f * overpaneHeight / overview.getIconHeight());
			// kleiner als 12 % sollte es nie werden
			if (zoom < 12)
				zoom = 12;
			// ein bißchen weniger... kleiner Offset, dass es sicher
			// passt!
			zoom -= 2;
		}
		return zoom;
	}

	private String createZoomLabelText(final int value) {
		String html = "<html>";

		html += "<font size=4 color=blue>";
		html += "<b>Zoom</b><br><hr>";
		html += "</font>";

		html += "<font size=3 color=black>";
		html += value + "%";
		html += "</font>";

		html += "</html>";
		return html;
	}

}

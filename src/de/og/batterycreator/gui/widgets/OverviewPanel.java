package de.og.batterycreator.gui.widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import og.basics.gui.image.StaticImageHelper;

public class OverviewPanel extends JPanel {
	private static final long	serialVersionUID	= -2956273745014471932L;

	private final JLabel		overviewLabel		= new JLabel();
	private ImageIcon			overview;
	private final JSlider		slider				= new JSlider(10, 200);
	private BufferedImage		overbuff;

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
		toolbar.add(new JLabel("Zoom"), BorderLayout.NORTH);
		toolbar.add(slider, BorderLayout.CENTER);
		add(toolbar, BorderLayout.EAST);
	}

	public void setOverview(final ImageIcon overview) {
		setOverview(overview, 100);
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
		}
	}
}

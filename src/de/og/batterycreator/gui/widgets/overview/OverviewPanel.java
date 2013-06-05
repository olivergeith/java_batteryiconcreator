package de.og.batterycreator.gui.widgets.overview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import og.basics.gui.image.StaticImageHelper;
import de.og.batterycreator.gui.iconstore.IconStore;

public class OverviewPanel extends JPanel {
	private static final long	serialVersionUID	= -2956273745014471932L;

	private final JLabel		overviewLabel		= new JLabel();
	private ImageIcon			overview;
	private final JSlider		slider				= new JSlider(10, 200);
	private BufferedImage		overbuff;
	private final JLabel		zoomLabel			= new JLabel("Zoom");
	private final JButton		autofitButton		= new JButton();
	private final JButton		auto100Button		= new JButton();

	// private final JColorSelectButton bgColor = new JColorSelectButton("",
	// "BackgroundColor of Overview");
	// public static final ImageIcon color = new
	// ImageIcon(AnimatorBar.class.getResource("color.png"));

	public OverviewPanel(final int zoomValue) {
		initUI(zoomValue);
	}

	public OverviewPanel() {
		initUI(100);
	}

	private void initUI(final int zoomValue) {
		setLayout(new BorderLayout());
		setBackground(Color.black);
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

		// Doppelclick auf ZoomLabel --> 100%
		zoomLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent arg0) {
				if (arg0.getClickCount() == 2) {
					setOverview(overview, 100);
				}

			}
		});
		autofitButton.setToolTipText("Will auto-fit large overview into the overview-panel. Will not zoom more than 100%");
		autofitButton.setIcon(IconStore.zoomfit);
		autofitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				if (overview != null) {
					setOverview(overview, true);
				}

			}
		});

		auto100Button.setToolTipText("Zoom to 100%");
		auto100Button.setIcon(IconStore.zoom100);
		auto100Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (overview != null) {
					setOverview(overview, 100);
				}

			}
		});

		final JPanel p = new JPanel(new BorderLayout());
		// bgColor.setColor(Color.black);
		// bgColor.setIcon(color);
		// bgColor.addColorChangeListener(new ColorChangeListener() {
		// @Override
		// public void onChange(final Color col) {
		// p.setBackground(col);
		// overviewLabel.setForeground(bgColor.getForeground());
		// }
		// });

		// Scrollable Overview!
		p.setBackground(Color.black);
		p.add(overviewLabel, BorderLayout.CENTER);
		final JScrollPane scroller = new JScrollPane();
		scroller.add(p);
		scroller.getViewport().setView(p);
		// scroller.setBackground(Color.black);
		add(scroller, BorderLayout.CENTER);
		final JPanel leftBar = new JPanel(new BorderLayout());
		leftBar.setBorder(new EmptyBorder(3, 3, 3, 3));
		leftBar.add(zoomLabel, BorderLayout.NORTH);
		leftBar.add(slider, BorderLayout.CENTER);
		final JToolBar toolbar = new JToolBar();
		toolbar.setOrientation(JToolBar.VERTICAL);
		toolbar.setFloatable(false);
		toolbar.add(autofitButton);
		toolbar.add(auto100Button);
		// toolbar.add(bgColor);
		leftBar.add(toolbar, BorderLayout.SOUTH);
		add(leftBar, BorderLayout.EAST);
	}

	public void setOverview(final ImageIcon overview, final boolean autozoom) {
		if (autozoom)
			setOverview(overview, calcAutoZoom(overview));
		else
			setOverview(overview);
	}

	public void setOverview(final ImageIcon overview) {
		setOverview(overview, 100);
	}

	public void setOverview(final ImageIcon overview, final int zoom) {
		this.overview = overview;
		if (overview != null) {
			slider.setValue(zoom);
			overbuff = StaticImageHelper.convertImageIcon(overview);
		}
		showOverview();
	}

	public void setText(final String txt) {
		overviewLabel.setText(txt);
	}

	private void showOverview() {
		if (overview != null && overbuff != null) {
			final int height100 = overview.getIconHeight();
			final int value = slider.getValue();
			final int height = Math.round(height100 / 100f * value);
			final BufferedImage buff = StaticImageHelper.resize2Height(overbuff, height);
			overviewLabel.setIcon(new ImageIcon(buff));
			zoomLabel.setText(createZoomLabelText(value));
		} else {
			overviewLabel.setIcon(null);
			zoomLabel.setText(createZoomLabelText(100));
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
		int zoom1 = 100;
		int zoom2 = 100;
		final int overpaneHeight = getHeight();
		final int overpaneWidth = getWidth();
		// Wenn der overview höher ist als das Overview Panel
		if (overview.getIconHeight() > overpaneHeight) {
			// dann passen wir es genau an!
			zoom1 = Math.round(100f * overpaneHeight / overview.getIconHeight());
			// kleiner als 10 % sollte es nie werden
			if (zoom1 < 14)
				zoom1 = 14;
			// ein bißchen weniger... kleiner Offset, dass es sicherer
			// passt!
			zoom1 -= 4;
		}
		// Now checking width
		if (overview.getIconWidth() > overpaneWidth) {
			// dann passen wir es genau an!
			zoom2 = Math.round(100f * overpaneWidth / overview.getIconWidth());
			// kleiner als 10 % sollte es nie werden
			if (zoom2 < 18)
				zoom2 = 18;
			// ein bißchen weniger... kleiner Offset, dass es sicherer
			// passt!
			zoom2 -= 8;
		}
		// System.out.println("Zoom1=" + zoom1);
		// System.out.println("Zoom2=" + zoom2);
		if (zoom1 < zoom2)
			return zoom1;
		return zoom2;
	}

	private String createZoomLabelText(final int value) {
		String html = "<html>";

		html += "<font size=4 color=blue>";
		html += "<b>Zoom</b><br><hr>";
		html += "</font>";
		if (value == 100)
			html += "<font size=3 color=green>";
		else
			html += "<font size=3 color=black>";
		html += value + "%";
		html += "</font>";

		html += "</html>";
		return html;
	}

}

package de.og.batterycreator.gui.widgets.animator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import og.basics.util.timer.AbstractTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.gui.widgets.colorselectbutton.ColorChangeListener;
import de.og.batterycreator.gui.widgets.colorselectbutton.ColorSelectButton;

public class AnimatorBar extends JToolBar {
	private static final long			serialVersionUID	= -7723769368206156309L;
	private List<ImageIcon>				icons;
	private final JLabel				animationLabel		= new JLabel();
	private final JButton				startButton			= new JButton();
	private final JButton				stopButton			= new JButton();
	private AbstractTrigger				trigger;
	private int							index				= 0;
	private static final Logger			LOG					= LoggerFactory.getLogger(AnimatorBar.class);
	private final ColorSelectButton	bgColor				= new ColorSelectButton("", "BackgroundColor of Animator");
	final JPanel						aniPanel			= new JPanel(new BorderLayout());
	private int							delayTime			= 80;

	public static final ImageIcon		start				= new ImageIcon(AnimatorBar.class.getResource("start.png"));
	public static final ImageIcon		stop				= new ImageIcon(AnimatorBar.class.getResource("stop.png"));
	public static final ImageIcon		pause				= new ImageIcon(AnimatorBar.class.getResource("pause.png"));
	public static final ImageIcon		color				= new ImageIcon(AnimatorBar.class.getResource("color.png"));

	/**
	 * @param delayTime
	 *            in millies
	 */
	public AnimatorBar(final int delayTime) {
		this.delayTime = delayTime;
		initUI();
	}

	public AnimatorBar() {
		initUI();
	}

	private void initUI() {

		bgColor.setColor(Color.black);
		bgColor.setIcon(color);
		bgColor.addColorChangeListener(new ColorChangeListener() {
			@Override
			public void onChange(final Color col) {
				aniPanel.setBackground(col);
			}
		});

		aniPanel.setBackground(Color.black);
		aniPanel.setBorder(new EmptyBorder(2, 4, 2, 2));
		aniPanel.add(animationLabel, BorderLayout.CENTER);

		startButton.setEnabled(false);
		startButton.setToolTipText("Start animation");
		startButton.setIcon(start);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				startAnimation();
			}

		});
		stopButton.setEnabled(false);
		stopButton.setToolTipText("Stop animation");
		stopButton.setIcon(stop);
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				stopAnimation();
			}
		});
		// Bar zusammenbauen
		setFloatable(false);
		add(startButton);
		add(stopButton);
		addSeparator();
		add(aniPanel);
		addSeparator();
		add(bgColor);
	}

	private void startAnimation() {
		index = 0;
		// StopButton anschalten
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				stopButton.setEnabled(true);
				startButton.setEnabled(false);
			}
		});

		trigger = new AbstractTrigger(delayTime) {
			@Override
			public void trigger(final int triggercount) {
				animationLabel.setIcon(icons.get(index));
				index++;
				// am ende neu beginnen
				if (index == icons.size())
					index = 0;
			}
		};
		trigger.start();
	}

	private void stopAnimation() {
		if (trigger != null) {
			trigger.stopTriggering();
			trigger = null;
		}
		// StopButton anschalten
		stopButton.setEnabled(false);
		startButton.setEnabled(true);
	}

	/**
	 * @return the icons
	 */
	public List<ImageIcon> getIcons() {
		return icons;
	}

	/**
	 * @param icons
	 *            the icons to set
	 */
	public void setIcons(final List<ImageIcon> icons) {
		LOG.debug("Icons Set: {}", icons);
		stopAnimation();
		index = 0;
		this.icons = icons;
		if (getIcons() != null && getIcons().size() > 0) {
			stopButton.setEnabled(false);
			startButton.setEnabled(true);
			animationLabel.setIcon(this.icons.get(0));
		} else {
			stopButton.setEnabled(false);
			startButton.setEnabled(false);
			animationLabel.setIcon(null);
		}

	}

	/**
	 * @return the delayTime in millies
	 */
	public int getDelayTime() {
		return delayTime;
	}

	/**
	 * @param delayTime
	 *            the delayTime to set in millies
	 */
	public void setDelayTime(final int delayTime) {
		this.delayTime = delayTime;
	}
}

package de.og.batterycreator.gui.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import de.og.batterycreator.cfg.RomPreset;

public class DrawableComboBox extends JComboBox<String> {
	private static final long serialVersionUID = 1L;
	// systemui
	private final SliderAndLabel sliderBattSize = new SliderAndLabel(25, 70);
	private final SliderAndLabel sliderToggleSize = new SliderAndLabel(32, 100);
	// frameworkres
	private final SliderAndLabel sliderLockSize = new SliderAndLabel(130, 250);
	private final SliderAndLabel sliderWeatherSize = new SliderAndLabel(50, 200);
	private final SliderAndLabel sliderNotificationSize = new SliderAndLabel(2, 8);

	public DrawableComboBox() {

		initUI();

	}

	private void initUI() {
		addItem(RomPreset.DRAWABLE_XHDPI);
		addItem(RomPreset.DRAWABLE_HDPI);
		addItem(RomPreset.DRAWABLE_600DP_XHDPI);
		addItem(RomPreset.DRAWABLE_720DP_XHDPI);
		addItem(RomPreset.DRAWABLE_600DP_HDPI);
		addItem(RomPreset.DRAWABLE_720DP_HDPI);
		setEditable(false);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				if (getSelectedItem().equals(RomPreset.DRAWABLE_XHDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_XHDPI);
					sliderLockSize.setValue(RomPreset.LOCK_XHDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_XHDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_XHDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_XHDPI);

				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_HDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_HDPI);
					sliderLockSize.setValue(RomPreset.LOCK_HDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_HDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_HDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_HDPI);

				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_720DP_XHDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_720DP_XHDPI);
					sliderLockSize.setValue(RomPreset.LOCK_720P_XHDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_720DP_XHDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_720DP_XHDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_720DP_XHDPI);

				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_600DP_XHDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_600DP_XHDPI);
					sliderLockSize.setValue(RomPreset.LOCK_600DP_XHDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_600DP_XHDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_600DP_XHDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_600DP_XHDPI);

				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_720DP_HDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_720DP_HDPI);
					sliderLockSize.setValue(RomPreset.LOCK_720P_HDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_720DP_HDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_720DP_HDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_720DP_HDPI);

				} else if (getSelectedItem().equals(RomPreset.DRAWABLE_600DP_HDPI)) {
					sliderBattSize.setValue(RomPreset.BATT_ICON_HEIGHT_600DP_HDPI);
					sliderLockSize.setValue(RomPreset.LOCK_600DP_HDPI);
					sliderWeatherSize.setValue(RomPreset.WEATHER_600DP_HDPI);
					sliderToggleSize.setValue(RomPreset.TOGGLE_600DP_HDPI);
					sliderNotificationSize.setValue(RomPreset.NOTIFICATION_600DP_HDPI);
				}
			}
		});
	}

	/**
	 * @return the slider
	 */
	public SliderAndLabel getSliderBattSize() {
		return sliderBattSize;
	}

	/**
	 * @return the sliderLockSize
	 */
	public SliderAndLabel getSliderLockSize() {
		return sliderLockSize;
	}

	/**
	 * @return the sliderWeatherSize
	 */
	public SliderAndLabel getSliderWeatherSize() {
		return sliderWeatherSize;
	}

	/**
	 * @return the sliderToggleSize
	 */
	public SliderAndLabel getSliderToggleSize() {
		return sliderToggleSize;
	}

	/**
	 * @return the sliderNotificationSize
	 */
	public SliderAndLabel getSliderNotificationSize() {
		return sliderNotificationSize;
	}

}

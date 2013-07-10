package de.og.batterycreator.gui.cfg;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import og.basics.jgoodies.JGoodiesHelper;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import de.og.batterycreator.cfg.HSBSettings;
import de.og.batterycreator.gui.image.StaticFilterHelper;
import de.og.batterycreator.gui.widgets.SliderAndLabel;
import de.og.batterycreator.gui.widgets.iconselector.textureselector.TextureSelector;

/**
 * Hier gehts darum {@link HSBSettings} zu bearbeiten und ein lokales Preview
 * davon anzuzeigen
 * 
 * @author Oliver
 */
public class HSBSettingsPanel extends JPanel {
	private static final long		serialVersionUID		= -8325905127474322209L;

	private final SliderAndLabel	sliderHueShift			= new SliderAndLabel(0, 100);
	private final SliderAndLabel	sliderSaturationShift	= new SliderAndLabel(-100, 100);
	private final SliderAndLabel	sliderBrightnessShift	= new SliderAndLabel(-100, 100);
	private final JLabel			preview					= new JLabel();
	private HSBSettings				hsbSettings				= new HSBSettings(0, 0, 0);
	private ImageIcon				texture					= TextureSelector.icon01;

	public HSBSettingsPanel() {
		sliderHueShift.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				setHuePreviewImage();
			}
		});
		sliderBrightnessShift.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				setHuePreviewImage();
			}
		});
		sliderSaturationShift.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				setHuePreviewImage();
			}
		});

		this.setLayout(new BorderLayout());
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("Hue"), cc.xyw(2, ++row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Saturation"), cc.xyw(4, row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Brightness"), cc.xyw(6, row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Preview"), cc.xyw(8, row, 1));
		builder.add(sliderHueShift.getToolbar(), cc.xyw(2, ++row, 1));
		builder.add(sliderSaturationShift.getToolbar(), cc.xyw(4, row, 1));
		builder.add(sliderBrightnessShift.getToolbar(), cc.xyw(6, row, 1));
		builder.add(preview, cc.xyw(8, row, 1));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		this.add(builder.getPanel(), BorderLayout.CENTER);
	}

	private void setHuePreviewImage() {
		final BufferedImage imghue = getHuePreviewImage(texture);
		preview.setIcon(new ImageIcon(imghue));
	}

	private BufferedImage getHuePreviewImage(ImageIcon tex) {
		// ImageIcon tex = (ImageIcon) textureSelector.getSelectedItem();
		if (tex == null) {
			tex = TextureSelector.icon01;
		}
		return StaticFilterHelper.getHSBImage(tex, sliderHueShift.getValue(), sliderBrightnessShift.getValue(), sliderSaturationShift.getValue());
	}

	public HSBSettings getHsbSettings() {
		hsbSettings.hue = sliderHueShift.getValue();
		hsbSettings.brightness = sliderBrightnessShift.getValue();
		hsbSettings.saturation = sliderSaturationShift.getValue();
		return hsbSettings;
	}

	public void setHsbSettings(final HSBSettings hsbSettings) {
		this.hsbSettings = hsbSettings;
		sliderHueShift.setValue(hsbSettings.hue);
		sliderBrightnessShift.setValue(hsbSettings.brightness);
		sliderSaturationShift.setValue(hsbSettings.saturation);
	}

	public void setTexture(final ImageIcon texture) {
		this.texture = texture;
		setHuePreviewImage();
	}

}

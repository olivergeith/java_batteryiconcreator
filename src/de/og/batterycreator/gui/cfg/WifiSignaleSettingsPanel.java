package de.og.batterycreator.gui.cfg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import og.basics.gui.icon.CommonIconProvider;
import og.basics.gui.jfontchooser.JFontChooserButton;
import og.basics.jgoodies.JGoodiesHelper;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.og.batterycreator.cfg.SettingsPersistor;
import de.og.batterycreator.cfg.WifiSignalSettings;
import de.og.batterycreator.gui.widgets.colorselectbutton.ColorSelectButton;

public class WifiSignaleSettingsPanel extends SettingsPanel {
	private static final long serialVersionUID = 1L;

	private WifiSignalSettings settings = new WifiSignalSettings();
	private final String fontSizes[] = {
			"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32"
	};

	private final JCheckBox cboxTransparentBgrnd = createCheckbox("Transparent Background (switchOff = experimental !)",
			"Use this, when your statusbar Background is not black!");
	private final ColorSelectButton backgroundColor = new ColorSelectButton("Background Color", "Color if not transparent");
	private final ColorSelectButton iconColorInactiv = new ColorSelectButton("Inactiv", "Color for inactiv Iconelements");
	private final ColorSelectButton inColor = new ColorSelectButton("ColorDataIn", "Color when Data comes in ;-)");
	private final ColorSelectButton outColor = new ColorSelectButton("ColorDataOut", "Color when Data comes in ;-)");
	private final ColorSelectButton color = new ColorSelectButton("Connected", "Color when connected");
	private final ColorSelectButton colorFully = new ColorSelectButton("Fully Connected", "Color when fully connected");
	private final JFontChooserButton fontButton = new JFontChooserButton("Choose Font", fontSizes);

	// Construktor
	public WifiSignaleSettingsPanel() {
		initComponents();
		myInit();
		setSettings(settings);
	}

	private void initComponents() {
	}

	private void myInit() {
		setLayout(new BorderLayout());
		this.add(createTabPaneWifiColors(), BorderLayout.CENTER);
		makeButtonBar();
	}

	public JPanel createTabPaneWifiColors() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createGroupLabel("Colors"), cc.xyw(2, ++row, 7));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(iconColorInactiv, cc.xyw(2, ++row, 1));
		builder.add(cboxTransparentBgrnd, cc.xyw(2, ++row, 3));
		builder.add(backgroundColor, cc.xyw(6, row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Colors for Connection"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Colors for Data Activity"), cc.xyw(6, row, 3));
		builder.add(color, cc.xyw(2, ++row, 1));
		builder.add(colorFully, cc.xyw(4, row, 1));
		builder.add(inColor, cc.xyw(6, row, 1));
		builder.add(outColor, cc.xyw(8, row, 1));
		builder.add(fontButton, cc.xyw(2, ++row, 3));

		final JPanel cfp = builder.getPanel();
		return cfp;
	}

	public void setSettings(final WifiSignalSettings settings) {
		if (settings != null) {
			this.settings = settings;
			iconColorInactiv.setColor(settings.getColorInActiv());
			backgroundColor.setColor(settings.getBackgroundColor());
			cboxTransparentBgrnd.setSelected(settings.isTransparentBackground());
			inColor.setColor(settings.getInColor());
			outColor.setColor(settings.getOutColor());
			color.setColor(settings.getColor());
			colorFully.setColor(settings.getColorFully());
			fontButton.setFont(settings.getFont());
			validateControls();
			this.repaint();
		}
	}

	public WifiSignalSettings getSettings() {
		settings.setColorInActiv(iconColorInactiv.getColor());
		settings.setBackgroundColor(backgroundColor.getColor());
		settings.setTransparentBackground(cboxTransparentBgrnd.isSelected());
		settings.setInColor(inColor.getColor());
		settings.setOutColor(outColor.getColor());
		settings.setColor(color.getColor());
		settings.setColorFully(colorFully.getColor());
		settings.setFont(fontButton.getFont());
		return settings;
	}

	@Override
	protected void validateControls() {
		// transparent Backround special behaviour
		backgroundColor.setEnabled(!cboxTransparentBgrnd.isSelected());
		backgroundColor.setVisible(!cboxTransparentBgrnd.isSelected());
		if (!backgroundColor.isEnabled()) {
			backgroundColor.setBackground(Color.black);
		}
	}

	/**
	 * Creating buttonbar
	 */
	private void makeButtonBar() {
		final LoadSettingsAktion loadAktion = new LoadSettingsAktion("Load Settings for selected Creator", CommonIconProvider.BUTTON_ICON_OPEN);
		final SaveSettingsAktion saveAktion = new SaveSettingsAktion("Save Settings for selected Creator", CommonIconProvider.BUTTON_ICON_SAVE);
		final JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.add(loadAktion);
		toolBar.add(saveAktion);
		add(toolBar, BorderLayout.NORTH);
	}

	/**
	 * Load Settings Action
	 * 
	 */
	private class LoadSettingsAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public LoadSettingsAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			setSettings(SettingsPersistor.loadWifiSettings());
		}
	}

	/**
	 * Save Settings Action
	 * 
	 */
	private class SaveSettingsAktion extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SaveSettingsAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		public void actionPerformed(final ActionEvent arg0) {
			SettingsPersistor.saveWifiSettings("MyWifiSignalSettings", getSettings());
		}
	}

}

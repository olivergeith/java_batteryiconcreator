package de.og.batterycreator.gui.cfg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import og.basics.gui.icon.CommonIconProvider;
import og.basics.gui.image.StaticImageHelper;
import og.basics.gui.jfontchooser.JFontChooserButton;
import og.basics.gui.widgets.hidepanel.HidePanel;
import og.basics.jgoodies.JGoodiesHelper;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jhlabs.image.HSBAdjustFilter;
import de.og.batterycreator.cfg.BattSettings;
import de.og.batterycreator.cfg.SettingsPersistor;
import de.og.batterycreator.creators.batt.AbstractIconCreator;
import de.og.batterycreator.gui.widgets.SliderAndLabel;
import de.og.batterycreator.gui.widgets.colorselectbutton.ColorSelectButton;
import de.og.batterycreator.gui.widgets.iconpositioner.IconPositioner;
import de.og.batterycreator.gui.widgets.iconpositioner.IconPositionerListener;
import de.og.batterycreator.gui.widgets.iconselector.chargeiconselector.ChargeIconSelector;
import de.og.batterycreator.gui.widgets.iconselector.textureselector.TextureSelector;
import de.og.batterycreator.gui.widgets.iconselector.xorcircleselector.XorCircleSelector;
import de.og.batterycreator.gui.widgets.iconselector.xorsquareselector.XorSquareSelector;

public class BattSettingsPanel extends SettingsPanel {
	private static final long			serialVersionUID					= 1L;

	private BattSettings				settings;
	private final String				fontSizes[]							= {
			"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35",
			"36", "38", "39", "40", "41", "42", "43", "44", "45"
																			};

	private final ColorSelectButton		fontColor							= new ColorSelectButton("Main Color", "Color when normal battery-level");
	private final ColorSelectButton		fontColorLowBatt					= new ColorSelectButton("LowBatt", "Color when low battery");
	private final ColorSelectButton		fontColorMedBatt					= new ColorSelectButton("MedBatt", "Color when Med battery");
	private final ColorSelectButton		fontColorCharge						= new ColorSelectButton("Charge Color", "Color when charging");
	private final ChargeIconSelector	chargeIconSeletor					= new ChargeIconSelector(36);

	private final JCheckBox				cboxDropShadowFont					= createCheckbox("DropShadow Font", "DropShadow behind PercentageText");
	private final JCheckBox				cboxDropShadowIcon					= createCheckbox("DropShadow ChargeIcon", "DropShadow behind Charge-Icon");
	private final ColorSelectButton		dropShadowColor						= new ColorSelectButton("DropShadow Color",
																					"Color of Shadow behind Percentage-Text");
	private final IconPositioner		dropShadowPos						= new IconPositioner(-5, 5);
	private final SliderAndLabel		sliderDropShadowOpacity				= new SliderAndLabel(0, 6);
	private final SliderAndLabel		sliderDropShadowBlurryness			= new SliderAndLabel(1, 6);

	private final XorCircleSelector		xorIconSelector						= new XorCircleSelector(36);
	private final XorSquareSelector		xorSquareIconSelector				= new XorSquareSelector(36);

	private final JCheckBox				cboxUseChargeColor					= createCheckbox("Use charge color",
																					"Use ChargeColor (green), else use normal battery colors");

	private final JCheckBox				cboxTransparentBgrnd				= createCheckbox("Transparent Background (switchOff = experimental !)",
																					"Use this, when your statusbar Background is not black!");
	private final ColorSelectButton		backgroundColor						= new ColorSelectButton("Background Color", "Color if not transparent");

	private final ColorSelectButton		extraColor1							= new ColorSelectButton("Extra1", "Extra Color 1");
	private final ColorSelectButton		extraColor2							= new ColorSelectButton("Extra2", "Extra Color 2");

	private final ColorSelectButton		iconColor							= new ColorSelectButton("Main Color", "Color when normal battery-level");
	private final ColorSelectButton		iconColorLowBatt					= new ColorSelectButton("LowBatt", "Color when low battery");
	private final ColorSelectButton		iconColorMedBatt					= new ColorSelectButton("MedBatt", "Color when Med battery");
	private final ColorSelectButton		iconColorInactiv					= new ColorSelectButton("Inactiv", "Color for inactiv Iconelements");
	private final ColorSelectButton		iconColorCharge						= new ColorSelectButton("Charge Color", "Color when charging");

	private final ColorSelectButton		pulsingChargeGlowColorButton		= new ColorSelectButton("Glow Color", "Glow Color when charging");
	private final JCheckBox				cboxPulsingChargeGlow				= createCheckbox("Pulsing ChargeGlow",
																					"Pulsing glow Animation behind Charge-Icon or number");
	private final SliderAndLabel		pulsingChargGlowRadiusSlider		= new SliderAndLabel(10, 50);

	private final SliderAndLabel		sliderStroke						= new SliderAndLabel(1, 10);
	private final JCheckBox				cboxFlip							= createCheckbox("Flip Icon", "Mirror's the Icon...ony has effect on a few styls!");
	private final JCheckBox				cboxZeiger							= createCheckbox("Draw Zeiger", "Draw a Zeiger...ony has effect on a few styls!");
	private final JCheckBox				cboxNoBG							= createCheckbox("No Backgr.", "Removes the -normally gray- background!");
	private final SliderAndLabel		sliderBattGradientLevel				= new SliderAndLabel(1, 5);

	private final JCheckBox				cboxColoredFont						= createCheckbox("Low battery Colors", "...");
	private final JCheckBox				cboxColoredIcon						= createCheckbox("Low battery Colors", "...");
	private final JCheckBox				cboxShowFont						= createCheckbox("Show percentages", "...");
	private final JCheckBox				cboxShowChargeSymbol				= createCheckbox("Charge-Icon", "Show Charge-Icon when charging");
	private final JCheckBox				cboxUseGradientMediumLevels			= createCheckbox("Gradient",
																					"Use Gradient Colors between Low and Medium Batterylevels");
	private final JCheckBox				cboxUseGradientNormalLevels			= createCheckbox("Gradient",
																					"Use Gradient Colors between Medium and 100% Batterylevels");
	private final JCheckBox				cboxGlow							= createCheckbox("Glow behind Font", "Glow behind percentages (when not on charge)");
	private final JCheckBox				cboxGlowForCharge					= createCheckbox("Glow behind Charge-Icon", "Glow behind Charge-Icon!");
	private final SliderAndLabel		sliderGlowRadius					= new SliderAndLabel(10, 50);
	private final JCheckBox				cboxShowAdditionalFontOnCharge		= createCheckbox("Add Font on Charge too", "Have a percentage-text and charge-icon");

	private final SliderAndLabel		sliderLowBatt						= new SliderAndLabel(0, 30);
	private final SliderAndLabel		sliderMedBatt						= new SliderAndLabel(0, 100);

	private final IconPositioner		iconPos								= new IconPositioner(-30, 30);
	private final IconPositioner		glowPos								= new IconPositioner(-30, 30);
	private final IconPositioner		fontPos								= new IconPositioner(-30, 30);
	private final IconPositioner		pulsingChargeGlowPos				= new IconPositioner(-30, 30);
	private final JCheckBox				cboxMoveIconWithText				= createCheckbox("Lock to Font-Position-Offset",
																					"Move Charge-Icon with Font-Position");
	private final JCheckBox				cboxMoveGlowWithText				= createCheckbox("Lock to Font-Position-Offset", "Move Glow with Font-Position");
	private final JCheckBox				cboxMovePulsingChargeGlowWithText	= createCheckbox("Lock to Font-Position-Offset",
																					"Move pulsing ChargeGlow with Font-Position");

	private final SliderAndLabel		sliderReduceOn100					= new SliderAndLabel(-5, 0);

	private final SliderAndLabel		sliderResizeChargeSymbol			= new SliderAndLabel(15, 40);

	private final JFontChooserButton	fontButton							= new JFontChooserButton("Font", fontSizes);

	private final JCheckBox				cboxAddPercent						= createCheckbox("Add '%'", "Add '%' behind numbers");

	private final ButtonGroup			radiogroup							= new ButtonGroup();
	private final JRadioButton			cboxNoFilling						= createRadioButton("No special fill", "No special filling");
	private final JRadioButton			cboxBattGradient					= createRadioButton("BattGradient", "Gradients within Iconcolor (Level below)");
	private final JRadioButton			cboxLinearGradient					= createRadioButton("LinearGradient",
																					"Linear Gradients within Icon (BattGradients will have no effect then!");
	private final JRadioButton			cboxTexture							= createRadioButton("Use Texture", "Use Texture instead of colors");

	private final TextureSelector		textureSelector						= new TextureSelector(36);
	private final JComboBox<String>		textureFilterTypeCombo				= new JComboBox<String>();
	private final SliderAndLabel		sliderHueShift						= new SliderAndLabel(0, 100);
	private final JLabel				huePreviewLabel						= new JLabel();

	// Construktor
	public BattSettingsPanel() {
		initComponents();
		myInit();
	}

	private void initComponents() {
		radiogroup.add(cboxNoFilling);
		radiogroup.add(cboxBattGradient);
		radiogroup.add(cboxTexture);
		radiogroup.add(cboxLinearGradient);
		cboxNoFilling.setSelected(true);

		textureFilterTypeCombo.addItem("None");
		textureFilterTypeCombo.addItem("Colorize Texture");
		textureFilterTypeCombo.addItem("Hue shift");
		textureFilterTypeCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				validateControls();
			}
		});
		sliderHueShift.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent e) {
				final BufferedImage imghue = getHuePreviewImage();
				huePreviewLabel.setIcon(new ImageIcon(imghue));
			}

		});

		sliderLowBatt.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				final int low = sliderLowBatt.getValue();
				final int med = sliderMedBatt.getValue();
				if (low > med)
					sliderMedBatt.setValue(low);
			}
		});

		sliderMedBatt.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent arg0) {
				final int low = sliderLowBatt.getValue();
				final int med = sliderMedBatt.getValue();
				if (med < low)
					sliderLowBatt.setValue(med);
			}
		});

		fontPos.addIconPositionerListener(new IconPositionerListener() {

			@Override
			public void positionChanged(final int x, final int y) {
				// validateControls();
				if (cboxMoveGlowWithText.isSelected() == true) {
					glowPos.setPosition(x, y);
				}
				if (cboxMoveIconWithText.isSelected() == true) {
					iconPos.setPosition(x, y);
				}
				if (cboxMovePulsingChargeGlowWithText.isSelected() == true) {
					pulsingChargeGlowPos.setPosition(x, y);
				}
			}
		});
	}

	private BufferedImage getHuePreviewImage() {
		ImageIcon tex = (ImageIcon) textureSelector.getSelectedItem();
		if (tex == null) {
			tex = TextureSelector.icon01;
		}
		final HSBAdjustFilter huefilter = new HSBAdjustFilter();
		final Float factor = sliderHueShift.getValue() / 100f;
		huefilter.setHFactor(factor);
		final BufferedImage imghue = huefilter.filter(StaticImageHelper.convertImageIcon(tex), null);
		return imghue;
	}

	private void myInit() {
		setLayout(new BorderLayout());

		final JScrollPane cfgScroller = new JScrollPane();
		final JPanel cfg = createTabPaneBattSettings();
		cfgScroller.add(cfg);
		cfgScroller.getViewport().setView(cfg);
		cfgScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(cfgScroller, BorderLayout.CENTER);
		makeButtonBar();
	}

	private JPanel createTabPaneBattSettings() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;
		builder.add(createCfgPanePercentages(), cc.xyw(1, ++row, 9));
		builder.add(createGlowPane(), cc.xyw(1, ++row, 9));
		builder.add(createDropShadowPane(), cc.xyw(1, ++row, 9));

		builder.add(createCfgPaneChargeIcon(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPanePulsingChargeGlow(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPaneIconColors(), cc.xyw(1, ++row, 9));
		// builder.add(createCfgPaneThresholds(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPaneFilling(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPaneMisc(), cc.xyw(1, ++row, 9));
		final JPanel cfp = builder.getPanel();
		return cfp;
	}

	private JPanel createCfgPanePercentages() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(cboxShowFont, cc.xyw(2, ++row, 1));
		builder.add(cboxAddPercent, cc.xyw(4, row, 1));
		builder.add(cboxColoredFont, cc.xyw(6, row, 3));

		builder.add(fontColor, cc.xyw(2, ++row, 1));
		builder.add(fontColorCharge, cc.xyw(4, row, 1));
		builder.add(fontColorLowBatt, cc.xyw(6, row, 1));
		builder.add(fontColorMedBatt, cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createBlackLabel("Font"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Reduce font on 100%"), cc.xyw(4, row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Font-Position-Offsets (drag red square)"), cc.xyw(6, row, 3));
		builder.add(fontButton, cc.xyw(2, ++row, 1));
		builder.add(sliderReduceOn100.getToolbar(), cc.xyw(4, row, 1));
		builder.add(fontPos, cc.xyw(6, row, 3));

		final JPanel hide = new HidePanel("Percentages...", builder.getPanel());
		return hide;
	}

	private JPanel createGlowPane() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(cboxGlow, cc.xyw(2, ++row, 3));
		builder.add(cboxGlowForCharge, cc.xyw(6, row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Glow Radius: "), cc.xyw(4, ++row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Glow Offset (drag red square)"), cc.xyw(6, row, 3));

		builder.add(sliderGlowRadius.getToolbar(), cc.xyw(4, ++row, 1));
		builder.add(glowPos, cc.xyw(6, row, 3));

		builder.add(cboxMoveGlowWithText, cc.xyw(6, ++row, 3));

		final JPanel hide = new HidePanel("Glow", builder.getPanel(), false);
		return hide;
	}

	private JPanel createDropShadowPane() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(cboxDropShadowFont, cc.xyw(2, ++row, 3));
		builder.add(cboxDropShadowIcon, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createBlackLabel("DropShadow Blurring"), cc.xyw(2, ++row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("DropShadow Intensity"), cc.xyw(4, row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("DropShadow Offset (drag red square)"), cc.xyw(6, row, 3));
		builder.add(sliderDropShadowBlurryness.getToolbar(), cc.xyw(2, ++row, 1));
		builder.add(sliderDropShadowOpacity.getToolbar(), cc.xyw(4, row, 1));
		builder.add(dropShadowPos, cc.xyw(6, row, 3));
		builder.add(dropShadowColor, cc.xyw(2, ++row, 1));

		final JPanel hide = new HidePanel("DropShadow", builder.getPanel(), false);
		return hide;
	}

	private JPanel createCfgPaneChargeIcon() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(cboxShowChargeSymbol, cc.xyw(2, ++row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Charge-Icon size"), cc.xyw(4, row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Charge-Icon Offset (drag red square)"), cc.xyw(6, row, 3));
		builder.add(chargeIconSeletor, cc.xyw(2, ++row, 1));
		builder.add(sliderResizeChargeSymbol.getToolbar(), cc.xyw(4, row, 1));
		builder.add(iconPos, cc.xyw(6, row, 3));
		builder.add(cboxShowAdditionalFontOnCharge, cc.xyw(2, ++row, 3));
		builder.add(cboxMoveIconWithText, cc.xyw(6, row, 3));

		final JPanel hide = new HidePanel("Charge-Icon...", builder.getPanel());
		return hide;
	}

	private JPanel createCfgPanePulsingChargeGlow() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(cboxPulsingChargeGlow, cc.xyw(2, ++row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Glow Radius"), cc.xyw(4, row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("ChargeGlow Offset (drag red square)"), cc.xyw(6, row, 3));
		builder.add(pulsingChargeGlowColorButton, cc.xyw(2, ++row, 1));
		builder.add(pulsingChargGlowRadiusSlider.getToolbar(), cc.xyw(4, row, 1));
		builder.add(pulsingChargeGlowPos, cc.xyw(6, row, 3));
		builder.add(cboxMovePulsingChargeGlowWithText, cc.xyw(6, ++row, 3));

		final JPanel hide = new HidePanel("Pulsing Charge Glow", builder.getPanel(), false);
		return hide;
	}

	private JPanel createCfgPaneIconColors() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(iconColorInactiv, cc.xyw(2, ++row, 1));
		builder.add(cboxUseChargeColor, cc.xyw(4, row, 1));
		builder.add(cboxColoredIcon, cc.xyw(6, row, 3));
		builder.add(iconColor, cc.xyw(2, ++row, 1));
		builder.add(iconColorCharge, cc.xyw(4, row, 1));
		builder.add(iconColorLowBatt, cc.xyw(6, row, 1));
		builder.add(iconColorMedBatt, cc.xyw(8, row, 1));
		builder.add(cboxTransparentBgrnd, cc.xyw(2, ++row, 5));
		builder.add(backgroundColor, cc.xyw(8, row, 1));

		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createGroupLabel("Thresholds..."), cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("...for Low Battery-Levels"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("...for Med Battery-Levels"), cc.xyw(6, row, 3));
		builder.add(sliderLowBatt.getToolbar(), cc.xyw(2, ++row, 1));
		builder.add(cboxUseGradientMediumLevels, cc.xyw(4, row, 1));
		builder.add(sliderMedBatt.getToolbar(), cc.xyw(6, row, 1));
		builder.add(cboxUseGradientNormalLevels, cc.xyw(8, row, 1));

		final JPanel hide = new HidePanel("Icon Colors...", builder.getPanel());
		return hide;
	}

	private JPanel createCfgPaneFilling() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(cboxNoFilling, cc.xyw(2, ++row, 1));
		builder.add(cboxTexture, cc.xyw(4, row, 1));
		builder.add(cboxBattGradient, cc.xyw(6, row, 1));
		builder.add(cboxLinearGradient, cc.xyw(8, row, 1));
		builder.add(textureSelector, cc.xyw(4, ++row, 1));
		builder.add(sliderBattGradientLevel.getToolbar(), cc.xyw(6, row, 1));

		builder.addSeparator("Texture Options", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createGroupLabel("Filter"), cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Hue-Level"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Hue-Preview"), cc.xyw(6, row, 3));
		builder.add(textureFilterTypeCombo, cc.xyw(2, ++row, 1));
		builder.add(sliderHueShift.getToolbar(), cc.xyw(4, row, 1));
		builder.add(huePreviewLabel, cc.xyw(6, row, 1));

		builder.addSeparator("Background Icons", cc.xyw(2, ++row, 7));
		builder.add(xorIconSelector, cc.xyw(2, ++row, 1));
		builder.add(xorSquareIconSelector, cc.xyw(4, row, 1));

		final JPanel hide = new HidePanel("Special Paint Options (only work in some batteries)", builder.getPanel());
		return hide;
	}

	private JPanel createCfgPaneMisc() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(cboxNoBG, cc.xyw(2, ++row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Stroke Width"), cc.xyw(4, row, 1));
		builder.add(cboxZeiger, cc.xyw(6, row, 1));

		builder.add(cboxFlip, cc.xyw(2, ++row, 1));
		builder.add(sliderStroke.getToolbar(), cc.xyw(4, row, 1));

		builder.add(JGoodiesHelper.createBlackLabel("Extra Colors"), cc.xyw(2, ++row, 1));
		builder.add(extraColor1, cc.xyw(2, ++row, 1));
		builder.add(extraColor2, cc.xyw(4, row, 1));

		final JPanel hide = new HidePanel("Misc Options (only work in some batteries)", builder.getPanel());
		return hide;
	}

	public void setSettings(final BattSettings settings) {
		if (settings != null) {
			this.settings = settings;
			cboxMoveIconWithText.setSelected(settings.isMoveIconWithText());
			cboxMoveGlowWithText.setSelected(settings.isMoveGlowWithText());
			cboxMovePulsingChargeGlowWithText.setSelected(settings.isMoveChargeGlowWithText());
			cboxShowAdditionalFontOnCharge.setSelected(settings.isShowAdditionalFontOnCharge());

			cboxNoFilling.setSelected(true);
			fontColor.setColor(settings.getFontColor());
			fontColorLowBatt.setColor(settings.getFontColorLowBatt());
			fontColorMedBatt.setColor(settings.getFontColorMedBatt());
			fontColorCharge.setColor(settings.getFontChargeColor());

			dropShadowColor.setColor(settings.getDropShadowColor());
			dropShadowPos.setPosition(settings.getDropShadowOffsetX(), settings.getDropShadowOffsetY());
			cboxDropShadowFont.setSelected(settings.isDropShadowFont());
			cboxDropShadowIcon.setSelected(settings.isDropShadowIcon());
			sliderDropShadowOpacity.setValue(settings.getDropShadowOpacity());
			sliderDropShadowBlurryness.setValue(settings.getDropShadowBlurryness());

			extraColor1.setColor(settings.getExtraColor1());
			extraColor2.setColor(settings.getExtraColor2());

			iconColor.setColor(settings.getIconColor());
			iconColorLowBatt.setColor(settings.getIconColorLowBatt());
			iconColorMedBatt.setColor(settings.getIconColorMedBatt());
			iconColorInactiv.setColor(settings.getIconColorInActiv());
			iconColorCharge.setColor(settings.getIconChargeColor());

			backgroundColor.setColor(settings.getBackgroundColor());
			cboxTransparentBgrnd.setSelected(settings.isTransparentBackground());

			cboxFlip.setSelected(settings.isFlip());
			cboxZeiger.setSelected(settings.isDrawZeiger());
			cboxBattGradient.setSelected(settings.isBattGradient());
			sliderBattGradientLevel.setValue(settings.getBattGradientLevel());
			cboxNoBG.setSelected(settings.isNoBG());
			sliderStroke.setValue(settings.getStrokewidth());

			cboxShowFont.setSelected(settings.isShowFont());
			cboxAddPercent.setSelected(settings.isAddPercent());
			cboxShowChargeSymbol.setSelected(settings.isShowChargeSymbol());
			cboxColoredFont.setSelected(settings.isColoredFont());
			cboxColoredIcon.setSelected(settings.isColoredIcon());
			cboxUseChargeColor.setSelected(settings.isUseChargeColor());

			cboxPulsingChargeGlow.setSelected(settings.isChargeGlow());
			pulsingChargeGlowColorButton.setColor(settings.getIconChargeGlowColor());
			pulsingChargGlowRadiusSlider.setValue(settings.getChargeGlowRadius());
			pulsingChargeGlowPos.setPosition(settings.getChargeGlowOffsetX(), settings.getChargeGlowOffsetY());

			cboxGlow.setSelected(settings.isGlow());
			sliderGlowRadius.setValue(settings.getGlowRadius());
			cboxGlowForCharge.setSelected(settings.isGlowForCharge());

			sliderMedBatt.setValue(settings.getMedBattTheshold());
			sliderLowBatt.setValue(settings.getLowBattTheshold());

			if (settings.getChargeIcon() != null)
				chargeIconSeletor.setSelectedItem(settings.getChargeIcon());
			else
				chargeIconSeletor.setSelectedIndex(3);

			if (settings.getXorIcon() != null)
				xorIconSelector.setSelectedItem(settings.getXorIcon());
			else
				xorIconSelector.setSelectedIndex(0);

			if (settings.getXorSquareIcon() != null)
				xorSquareIconSelector.setSelectedItem(settings.getXorSquareIcon());
			else
				xorSquareIconSelector.setSelectedIndex(0);

			cboxUseGradientMediumLevels.setSelected(settings.isUseGradiantForMediumColor());
			cboxUseGradientNormalLevels.setSelected(settings.isUseGradiantForNormalColor());
			fontButton.setFont(settings.getFont());

			iconPos.setPosition(settings.getIconXOffset(), settings.getIconYOffset());
			fontPos.setPosition(settings.getFontXOffset(), settings.getFontYOffset());
			glowPos.setPosition(settings.getGlowXOffset(), settings.getGlowYOffset());

			sliderReduceOn100.setValue(settings.getReduceFontOn100());

			sliderResizeChargeSymbol.setValue(settings.getResizeChargeSymbolHeight());
			// cboxResizeChargeSymbol.setSelected(settings.isResizeChargeSymbol());

			cboxLinearGradient.setSelected(settings.isLinearGradient());
			cboxTexture.setSelected(settings.isUseTexture());
			if (settings.getTextureIcon() != null)
				textureSelector.setSelectedItem(settings.getTextureIcon());
			else
				textureSelector.setSelectedIndex(0);

			textureFilterTypeCombo.setSelectedIndex(settings.getTextureFilterType());
			sliderHueShift.setValue(settings.getHueShift());

			this.repaint();
			validateControls();
		}
	}

	public BattSettings getSettings() {
		settings.setFontColor(fontColor.getColor());
		settings.setFontColorLowBatt(fontColorLowBatt.getColor());
		settings.setFontColorMedBatt(fontColorMedBatt.getColor());
		settings.setFontChargeColor(fontColorCharge.getColor());

		settings.setDropShadowColor(dropShadowColor.getColor());
		settings.setDropShadowFont(cboxDropShadowFont.isSelected());
		settings.setDropShadowIcon(cboxDropShadowIcon.isSelected());
		settings.setDropShadowOffsetX(dropShadowPos.getPosition().x);
		settings.setDropShadowOffsetY(dropShadowPos.getPosition().y);
		settings.setDropShadowOpacity(sliderDropShadowOpacity.getValue());
		settings.setDropShadowBlurryness(sliderDropShadowBlurryness.getValue());

		settings.setExtraColor1(extraColor1.getColor());
		settings.setExtraColor2(extraColor2.getColor());

		settings.setIconColor(iconColor.getColor());
		settings.setIconColorLowBatt(iconColorLowBatt.getColor());
		settings.setIconColorMedBatt(iconColorMedBatt.getColor());
		settings.setIconColorInActiv(iconColorInactiv.getColor());
		settings.setIconChargeColor(iconColorCharge.getColor());

		settings.setBackgroundColor(backgroundColor.getColor());
		settings.setTransparentBackground(cboxTransparentBgrnd.isSelected());

		settings.setFlip(cboxFlip.isSelected());
		settings.setDrawZeiger(cboxZeiger.isSelected());
		settings.setBattGradient(cboxBattGradient.isSelected());
		settings.setBattGradientLevel(sliderBattGradientLevel.getValue());
		settings.setNoBG(cboxNoBG.isSelected());
		settings.setStrokewidth(sliderStroke.getValue());

		settings.setShowFont(cboxShowFont.isSelected());
		settings.setAddPercent(cboxAddPercent.isSelected());
		settings.setShowChargeSymbol(cboxShowChargeSymbol.isSelected());
		settings.setColoredFont(cboxColoredFont.isSelected());
		settings.setColoredIcon(cboxColoredIcon.isSelected());
		settings.setUseChargeColor(cboxUseChargeColor.isSelected());

		settings.setChargeGlow(cboxPulsingChargeGlow.isSelected());
		settings.setIconChargeGlowColor(pulsingChargeGlowColorButton.getColor());
		settings.setChargeGlowRadius(pulsingChargGlowRadiusSlider.getValue());
		settings.setChargeGlowOffsetX(pulsingChargeGlowPos.getPosition().x);
		settings.setChargeGlowOffsetY(pulsingChargeGlowPos.getPosition().y);

		settings.setGlow(cboxGlow.isSelected());
		settings.setGlowRadius(sliderGlowRadius.getValue());
		settings.setGlowForCharge(cboxGlowForCharge.isSelected());

		settings.setMedBattTheshold(sliderMedBatt.getValue());
		settings.setLowBattTheshold(sliderLowBatt.getValue());

		settings.setChargeIcon((ImageIcon) chargeIconSeletor.getSelectedItem());
		settings.setXorIcon((ImageIcon) xorIconSelector.getSelectedItem());
		settings.setXorSquareIcon((ImageIcon) xorSquareIconSelector.getSelectedItem());
		settings.setUseGradiantForMediumColor(cboxUseGradientMediumLevels.isSelected());
		settings.setUseGradiantForNormalColor(cboxUseGradientNormalLevels.isSelected());

		settings.setFont(fontButton.getFont());
		settings.setFontXOffset(fontPos.getPosition().x);
		settings.setFontYOffset(fontPos.getPosition().y);
		settings.setReduceFontOn100(sliderReduceOn100.getValue());

		settings.setGlowXOffset(glowPos.getPosition().x);
		settings.setGlowYOffset(glowPos.getPosition().y);
		settings.setMoveIconWithText(cboxMoveIconWithText.isSelected());
		settings.setMoveGlowWithText(cboxMoveGlowWithText.isSelected());
		settings.setMoveChargeGlowWithText(cboxMovePulsingChargeGlowWithText.isSelected());
		settings.setShowAdditionalFontOnCharge(cboxShowAdditionalFontOnCharge.isSelected());

		settings.setIconXOffset(iconPos.getPosition().x);
		settings.setIconYOffset(iconPos.getPosition().y);

		// settings.setResizeChargeSymbol(cboxResizeChargeSymbol.isSelected());
		settings.setResizeChargeSymbolHeight(sliderResizeChargeSymbol.getValue());

		settings.setLinearGradient(cboxLinearGradient.isSelected());

		settings.setUseTexture(cboxTexture.isSelected());
		settings.setTextureIcon((ImageIcon) textureSelector.getSelectedItem());
		settings.setTextureFilterType(textureFilterTypeCombo.getSelectedIndex());
		settings.setHueShift(sliderHueShift.getValue());

		return settings;
	}

	@Override
	protected void validateControls() {
		cboxAddPercent.setEnabled(cboxShowFont.isSelected());
		fontColor.setEnabled(cboxShowFont.isSelected());
		fontColorMedBatt.setEnabled(cboxShowFont.isSelected() && cboxColoredFont.isSelected());
		fontColorLowBatt.setEnabled(cboxShowFont.isSelected() && cboxColoredFont.isSelected());
		fontColorCharge.setEnabled(cboxShowFont.isSelected());
		cboxColoredFont.setEnabled(cboxShowFont.isSelected());
		iconColorMedBatt.setEnabled(cboxColoredIcon.isSelected());
		iconColorLowBatt.setEnabled(cboxColoredIcon.isSelected());
		iconColorCharge.setEnabled(cboxUseChargeColor.isSelected());
		chargeIconSeletor.setEnabled(cboxShowChargeSymbol.isSelected());
		fontButton.setEnabled(cboxShowFont.isSelected());
		// iconPos.setEnabled(cboxShowChargeSymbol.isSelected());
		fontPos.setEnabled(cboxShowFont.isSelected());
		sliderReduceOn100.setEnabled(cboxShowFont.isSelected());
		sliderResizeChargeSymbol.setEnabled(cboxShowChargeSymbol.isSelected());
		// transparent Backround special behaviour
		backgroundColor.setEnabled(!cboxTransparentBgrnd.isSelected());
		backgroundColor.setVisible(!cboxTransparentBgrnd.isSelected());
		if (!backgroundColor.isEnabled()) {
			backgroundColor.setBackground(Color.black);
		}

		sliderBattGradientLevel.setEnabled(cboxBattGradient.isSelected());

		pulsingChargGlowRadiusSlider.setEnabled(cboxPulsingChargeGlow.isSelected());
		pulsingChargeGlowColorButton.setEnabled(cboxPulsingChargeGlow.isSelected());

		cboxDropShadowFont.setEnabled(cboxShowFont.isSelected());
		cboxDropShadowIcon.setEnabled(cboxShowChargeSymbol.isSelected());

		final boolean enableDropshadowOptions = (cboxDropShadowFont.isEnabled() && cboxDropShadowFont.isSelected())
				|| (cboxDropShadowIcon.isEnabled() && cboxDropShadowIcon.isSelected());
		sliderDropShadowOpacity.setEnabled(enableDropshadowOptions);
		sliderDropShadowBlurryness.setEnabled(enableDropshadowOptions);
		dropShadowColor.setEnabled(enableDropshadowOptions);
		dropShadowPos.setEnabled(enableDropshadowOptions);
		// texturestuff
		textureSelector.setEnabled(cboxTexture.isSelected());
		textureFilterTypeCombo.setEnabled(cboxTexture.isSelected());
		sliderHueShift.setEnabled(cboxTexture.isSelected() && textureFilterTypeCombo.getSelectedIndex() == BattSettings.TEXTURE_FILTER_HUE_SHIFT);
		huePreviewLabel.setVisible(cboxTexture.isSelected() && textureFilterTypeCombo.getSelectedIndex() == BattSettings.TEXTURE_FILTER_HUE_SHIFT);
		// Glow
		sliderGlowRadius.setEnabled(cboxGlow.isSelected() || cboxGlowForCharge.isSelected());
		cboxMoveGlowWithText.setEnabled(cboxGlow.isSelected() || cboxGlowForCharge.isSelected());
		glowPos.setEnabled(!cboxMoveGlowWithText.isSelected() && (cboxGlow.isSelected() || cboxGlowForCharge.isSelected()));
		if (cboxMoveGlowWithText.isSelected() == true) {
			glowPos.setPosition(fontPos.getPosition().x, fontPos.getPosition().y);
		}

		cboxShowAdditionalFontOnCharge.setEnabled(cboxShowChargeSymbol.isSelected());
		cboxMoveIconWithText.setEnabled(cboxShowChargeSymbol.isSelected());
		iconPos.setEnabled(!cboxMoveIconWithText.isSelected() && cboxShowChargeSymbol.isSelected());
		if (cboxMoveIconWithText.isSelected() == true) {
			iconPos.setPosition(fontPos.getPosition().x, fontPos.getPosition().y);
		}
		cboxMovePulsingChargeGlowWithText.setEnabled(cboxPulsingChargeGlow.isSelected());
		pulsingChargeGlowPos.setEnabled(!cboxMovePulsingChargeGlowWithText.isSelected() && cboxPulsingChargeGlow.isSelected());
		if (cboxMovePulsingChargeGlowWithText.isSelected() == true) {
			pulsingChargeGlowPos.setPosition(fontPos.getPosition().x, fontPos.getPosition().y);
		}

	}

	public void enableSupportedFeatures(final AbstractIconCreator cre) {
		cboxFlip.setEnabled(cre.supportsFlip());
		sliderStroke.setEnabled(cre.supportsStrokeWidth());
		cboxNoBG.setEnabled(cre.supportsNoBg());
		cboxBattGradient.setEnabled(cre.supportsBattGradient());
		sliderBattGradientLevel.setEnabled(cre.supportsBattGradient());
		extraColor1.setEnabled(cre.supportsExtraColor1());
		extraColor2.setEnabled(cre.supportsExtraColor2());
		xorIconSelector.setEnabled(cre.supportsXOrIcon());
		xorSquareIconSelector.setEnabled(cre.supportsXOrSquareIcon());
		cboxLinearGradient.setEnabled(cre.supportsLinearGradient());
		textureSelector.setEnabled(cre.supportsTexture());
		cboxTexture.setEnabled(cre.supportsTexture());
		cboxZeiger.setEnabled(cre.supportsZeiger());
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
		private static final long	serialVersionUID	= 1L;

		public LoadSettingsAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			setSettings(SettingsPersistor.loadBattSettings());
		}
	}

	/**
	 * Save Settings Action
	 * 
	 */
	private class SaveSettingsAktion extends AbstractAction {
		private static final long	serialVersionUID	= 1L;

		public SaveSettingsAktion(final String arg0, final Icon arg1) {
			super(arg0, arg1);
		}

		@Override
		public void actionPerformed(final ActionEvent arg0) {
			SettingsPersistor.saveBattSettings("MyBattSettings", getSettings());
		}
	}

}

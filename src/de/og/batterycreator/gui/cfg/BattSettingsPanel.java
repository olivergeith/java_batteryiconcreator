package de.og.batterycreator.gui.cfg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import og.basics.gui.Jcolorselectbutton.JColorSelectButton;
import og.basics.gui.icon.CommonIconProvider;
import og.basics.gui.jfontchooser.JFontChooserButton;
import og.basics.gui.widgets.hidepanel.HidePanel;
import og.basics.jgoodies.JGoodiesHelper;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import de.og.batterycreator.cfg.BattSettings;
import de.og.batterycreator.cfg.SettingsPersistor;
import de.og.batterycreator.gui.widgets.SliderAndLabel;
import de.og.batterycreator.gui.widgets.iconpositioner.IconPositioner;
import de.og.batterycreator.gui.widgets.iconselector.chargeiconselector.ChargeIconSelector;
import de.og.batterycreator.gui.widgets.iconselector.xorcircleselector.XorCircleSelector;
import de.og.batterycreator.gui.widgets.iconselector.xorsquareselector.XorSquareSelector;

public class BattSettingsPanel extends SettingsPanel {
	private static final long			serialVersionUID			= 1L;

	private BattSettings				settings;
	private final String				fontSizes[]					= {
			"12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32"
																	};

	private final JColorSelectButton	fontColor					= new JColorSelectButton("Main Color", "Color when normal battery-level");
	private final JColorSelectButton	fontColorLowBatt			= new JColorSelectButton("LowBatt", "Color when low battery");
	private final JColorSelectButton	fontColorMedBatt			= new JColorSelectButton("MedBatt", "Color when Med battery");
	private final JColorSelectButton	fontColorCharge				= new JColorSelectButton("Charge Color", "Color when charging");
	private final ChargeIconSelector	chargeIconSeletor			= new ChargeIconSelector(36);
	private final XorCircleSelector		xorIconSelector				= new XorCircleSelector(36);
	private final XorSquareSelector		xorSquareIconSelector		= new XorSquareSelector(36);

	private final JCheckBox				cboxUseChargeColor			= createCheckbox("Use charge color",
																			"Use ChargeColor (green), else use normal battery colors");

	private final JCheckBox				cboxTransparentBgrnd		= createCheckbox("Transparent Background (switchOff = experimental !)",
																			"Use this, when your statusbar Background is not black!");
	private final JColorSelectButton	backgroundColor				= new JColorSelectButton("Background Color", "Color if not transparent");

	private final JColorSelectButton	extraColor1					= new JColorSelectButton("Extra1", "Extra Color 1");
	private final JColorSelectButton	extraColor2					= new JColorSelectButton("Extra2", "Extra Color 2");

	private final JColorSelectButton	iconColor					= new JColorSelectButton("Main Color", "Color when normal battery-level");
	private final JColorSelectButton	iconColorLowBatt			= new JColorSelectButton("LowBatt", "Color when low battery");
	private final JColorSelectButton	iconColorMedBatt			= new JColorSelectButton("MedBatt", "Color when Med battery");
	private final JColorSelectButton	iconColorInactiv			= new JColorSelectButton("Inactiv", "Color for inactiv Iconelements");
	private final JColorSelectButton	iconColorCharge				= new JColorSelectButton("Charge Color", "Color when charging");

	private final JColorSelectButton	iconColorGlowCharge			= new JColorSelectButton("Charge Glow Color", "Glow Color when charging");
	private final JCheckBox				cboxChargeGlow				= createCheckbox("ChargeGlow", "Pulsing glow Animation behind charge symbol or number");
	private final SliderAndLabel		sliderChargGlowRadius		= new SliderAndLabel(10, 50);

	private final SliderAndLabel		sliderStroke				= new SliderAndLabel(1, 10);
	private final JCheckBox				cboxFlip					= createCheckbox("Flip Icon", "Mirror's the Icon...ony has effect on a few styls!");
	private final JCheckBox				cboxNoBG					= createCheckbox("No Backgr.", "Removes the -normally gray- background!");
	private final JCheckBox				cboxBattGradient			= createCheckbox("Gradient", "Gradients within Iconcolor");
	private final SliderAndLabel		sliderBattGradientLevel		= new SliderAndLabel(1, 5);

	private final JCheckBox				cboxColoredFont				= createCheckbox("Low battery Colors", "...");
	private final JCheckBox				cboxColoredIcon				= createCheckbox("Low battery Colors", "...");
	private final JCheckBox				cboxShowFont				= createCheckbox("Show %numb", "...");
	private final JCheckBox				cboxShowChargeSymbol		= createCheckbox("Charge-Symbol", "Show Charge-Symbol when charging");
	private final JCheckBox				cboxUseGradientMediumLevels	= createCheckbox("Gradient for Medium levels",
																			"Use Gradient Colors between Low and Med Batterylevels");
	private final JCheckBox				cboxUseGradientNormalLevels	= createCheckbox("Gradient for Normal levels",
																			"Use Gradient Colors between Med and 100% Batterylevels");
	private final JCheckBox				cboxGlow					= createCheckbox("Glow", "Glow behind percentages");
	private final SliderAndLabel		sliderGlowRadius			= new SliderAndLabel(10, 50);
	private final JCheckBox				cboxGlowForChargeToo		= createCheckbox("Also for Charge", "Glow behind charge icon too!");

	private final SliderAndLabel		sliderLowBatt				= new SliderAndLabel(0, 30);
	private final SliderAndLabel		sliderMedBatt				= new SliderAndLabel(0, 100);

	// private final SliderAndLabel sliderFontXOffset = new SliderAndLabel(-20,
	// 20);
	// private final SliderAndLabel sliderFontYOffset = new SliderAndLabel(-20,
	// 20);
	// private final SliderAndLabel slidericonXOffset = new SliderAndLabel(-20,
	// 20);
	// private final SliderAndLabel slidericonYOffset = new SliderAndLabel(-20,
	// 20);

	private final IconPositioner		iconPos						= new IconPositioner(-20, 20);
	private final IconPositioner		fontPos						= new IconPositioner(-20, 20);

	private final SliderAndLabel		sliderReduceOn100			= new SliderAndLabel(-5, 0);

	private final SliderAndLabel		sliderResizeChargeSymbol	= new SliderAndLabel(15, 40);
	private final JCheckBox				cboxResizeChargeSymbol		= createCheckbox("resize Charge Symbol to: (pixel)",
																			" Resize the Charge Symbol to make it fit better");

	private final JFontChooserButton	fontButton					= new JFontChooserButton("Choose Font", fontSizes);

	private final JCheckBox				cboxAddPercent				= createCheckbox("Add '%'", "Add '%' behind numbers");

	// Construktor
	public BattSettingsPanel() {
		initComponents();
		myInit();
	}

	private void initComponents() {
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
	}

	private void myInit() {
		setLayout(new BorderLayout());

		final JScrollPane cfgScroller = new JScrollPane();
		final JPanel cfg = createTabPaneBattSettings();
		cfgScroller.add(cfg);
		cfgScroller.getViewport().setView(cfg);
		// cfgScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
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
		builder.add(createCfgPaneIcons(), cc.xyw(1, ++row, 9));
		builder.add(createCfgPaneThresholds(), cc.xyw(1, ++row, 9));
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
		// builder.add(JGoodiesHelper.createGroupLabel("Percentages..."),
		// cc.xyw(2, ++row, 7));
		// builder.addSeparator("", cc.xyw(2, ++row, 7));

		builder.add(cboxShowFont, cc.xyw(2, ++row, 1));
		builder.add(cboxAddPercent, cc.xyw(4, row, 1));
		builder.add(cboxColoredFont, cc.xyw(6, row, 3));

		builder.add(fontColor, cc.xyw(2, ++row, 1));
		builder.add(fontColorCharge, cc.xyw(4, row, 1));
		builder.add(fontColorLowBatt, cc.xyw(6, row, 1));
		builder.add(fontColorMedBatt, cc.xyw(8, row, 1));

		builder.add(JGoodiesHelper.createBlackLabel("Font Position Offsets (drag red square"), cc.xyw(6, ++row, 3));
		builder.add(fontButton, cc.xyw(2, ++row, 3));
		builder.add(fontPos, cc.xyw(6, row, 3));

		builder.add(JGoodiesHelper.createBlackLabel("Reduce font on 100% by <x> pixel"), cc.xyw(2, ++row, 3));
		builder.add(sliderReduceOn100, cc.xyw(2, ++row, 1));
		builder.add(sliderReduceOn100.getValueLabel(), cc.xyw(4, row, 1));

		builder.add(JGoodiesHelper.createBlackLabel("Glow behind"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Glow Radius"), cc.xyw(4, row, 3));
		builder.add(cboxGlow, cc.xyw(2, ++row, 1));
		builder.add(sliderGlowRadius, cc.xyw(4, row, 1));
		builder.add(sliderGlowRadius.getValueLabel(), cc.xyw(6, row, 1));
		builder.add(cboxGlowForChargeToo, cc.xyw(8, row, 1));

		final JPanel hide = new HidePanel("Percentages...", builder.getPanel());
		return hide;
	}

	private JPanel createCfgPaneIcons() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(JGoodiesHelper.createBlackLabel("ChargeIcon Position Offsets (drag red square"), cc.xyw(6, row, 3));
		builder.add(cboxShowChargeSymbol, cc.xyw(2, ++row, 1));
		builder.add(chargeIconSeletor, cc.xyw(4, row, 1));
		builder.add(iconPos, cc.xyw(6, row, 3));

		builder.add(cboxResizeChargeSymbol, cc.xyw(2, ++row, 3));
		builder.add(sliderResizeChargeSymbol, cc.xyw(6, row, 1));
		builder.add(sliderResizeChargeSymbol.getValueLabel(), cc.xyw(8, row, 1));

		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Pulsing glow behind Chargesymbol"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("Glow Radius"), cc.xyw(6, row, 3));
		builder.add(cboxChargeGlow, cc.xyw(2, ++row, 1));
		builder.add(iconColorGlowCharge, cc.xyw(4, row, 1));
		builder.add(sliderChargGlowRadius, cc.xyw(6, row, 1));
		builder.add(sliderChargGlowRadius.getValueLabel(), cc.xyw(8, row, 1));

		builder.addSeparator("", cc.xyw(2, ++row, 7));

		builder.add(iconColorInactiv, cc.xyw(2, ++row, 1));
		builder.add(cboxUseChargeColor, cc.xyw(4, row, 1));
		builder.add(cboxColoredIcon, cc.xyw(6, row, 3));
		builder.add(iconColor, cc.xyw(2, ++row, 1));
		builder.add(iconColorCharge, cc.xyw(4, row, 1));
		builder.add(iconColorLowBatt, cc.xyw(6, row, 1));
		builder.add(iconColorMedBatt, cc.xyw(8, row, 1));
		builder.add(cboxTransparentBgrnd, cc.xyw(2, ++row, 5));
		builder.add(backgroundColor, cc.xyw(8, row, 1));

		final JPanel hide = new HidePanel("Charge Icon...", builder.getPanel());
		return hide;
	}

	private JPanel createCfgPaneThresholds() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;
		// builder.add(JGoodiesHelper.createGroupLabel("Thresholds..."),
		// cc.xyw(2, ++row, 7));
		// builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("...for Low Battery-Levels"), cc.xyw(2, ++row, 3));
		builder.add(JGoodiesHelper.createBlackLabel("...for Med Battery-Levels"), cc.xyw(6, row, 3));
		builder.add(sliderLowBatt, cc.xyw(2, ++row, 1));
		builder.add(sliderLowBatt.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(sliderMedBatt, cc.xyw(6, row, 1));
		builder.add(sliderMedBatt.getValueLabel(), cc.xyw(8, row, 1));
		builder.add(cboxUseGradientMediumLevels, cc.xyw(2, ++row, 3));
		builder.add(cboxUseGradientNormalLevels, cc.xyw(6, row, 3));

		final JPanel hide = new HidePanel("Thresholds...", builder.getPanel());
		return hide;
	}

	private JPanel createCfgPaneMisc() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu, 64dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;
		// builder.add(JGoodiesHelper.createGroupLabel("Misc Options (only work in some renderes)"),
		// cc.xyw(2, ++row, 7));
		// builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Stroke Width"), cc.xyw(6, ++row, 3));
		builder.add(cboxFlip, cc.xyw(2, ++row, 1));
		builder.add(cboxNoBG, cc.xyw(4, row, 1));
		builder.add(sliderStroke, cc.xyw(6, row, 1));
		builder.add(sliderStroke.getValueLabel(), cc.xyw(8, row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Gradient within battery"), cc.xyw(2, ++row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Gradient Level"), cc.xyw(4, row, 1));
		builder.add(cboxBattGradient, cc.xyw(2, ++row, 1));
		builder.add(sliderBattGradientLevel, cc.xyw(4, row, 1));
		builder.add(sliderBattGradientLevel.getValueLabel(), cc.xyw(6, row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Extra Colors"), cc.xyw(2, ++row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Background Icons"), cc.xyw(6, row, 1));
		builder.add(extraColor1, cc.xyw(2, ++row, 1));
		builder.add(extraColor2, cc.xyw(4, row, 1));
		builder.add(xorIconSelector, cc.xyw(6, row, 1));
		builder.add(xorSquareIconSelector, cc.xyw(8, row, 1));

		final JPanel hide = new HidePanel("Misc Options (only work in some renderes)", builder.getPanel());
		return hide;
	}

	public void setSettings(final BattSettings settings) {
		if (settings != null) {
			this.settings = settings;
			fontColor.setColor(settings.getFontColor());
			fontColorLowBatt.setColor(settings.getFontColorLowBatt());
			fontColorMedBatt.setColor(settings.getFontColorMedBatt());
			fontColorCharge.setColor(settings.getFontChargeColor());

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

			cboxChargeGlow.setSelected(settings.isChargeGlow());
			iconColorGlowCharge.setColor(settings.getIconChargeGlowColor());
			sliderChargGlowRadius.setValue(settings.getChargeGlowRadius());

			cboxGlow.setSelected(settings.isGlow());
			sliderGlowRadius.setValue(settings.getGlowRadius());
			cboxGlowForChargeToo.setSelected(settings.isGlowForChargeToo());

			sliderMedBatt.setValue(settings.getMedBattTheshold());
			sliderLowBatt.setValue(settings.getLowBattTheshold());

			if (settings.getChargeIcon() != null)
				chargeIconSeletor.setSelectedItem(settings.getChargeIcon());
			else
				chargeIconSeletor.setSelectedIndex(1);

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

			sliderReduceOn100.setValue(settings.getReduceFontOn100());

			sliderResizeChargeSymbol.setValue(settings.getResizeChargeSymbolHeight());
			cboxResizeChargeSymbol.setSelected(settings.isResizeChargeSymbol());

			validateControls();
			this.repaint();
		}
	}

	public BattSettings getSettings() {
		settings.setFontColor(fontColor.getColor());
		settings.setFontColorLowBatt(fontColorLowBatt.getColor());
		settings.setFontColorMedBatt(fontColorMedBatt.getColor());
		settings.setFontChargeColor(fontColorCharge.getColor());

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

		settings.setChargeGlow(cboxChargeGlow.isSelected());
		settings.setIconChargeGlowColor(iconColorGlowCharge.getColor());
		settings.setChargeGlowRadius(sliderChargGlowRadius.getValue());

		settings.setGlow(cboxGlow.isSelected());
		settings.setGlowRadius(sliderGlowRadius.getValue());
		settings.setGlowForChargeToo(cboxGlowForChargeToo.isSelected());

		cboxChargeGlow.setSelected(settings.isChargeGlow());
		iconColorGlowCharge.setColor(settings.getIconChargeGlowColor());
		sliderChargGlowRadius.setValue(settings.getChargeGlowRadius());

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

		settings.setIconXOffset(iconPos.getPosition().x);
		settings.setIconYOffset(iconPos.getPosition().y);

		settings.setResizeChargeSymbol(cboxResizeChargeSymbol.isSelected());
		settings.setResizeChargeSymbolHeight(sliderResizeChargeSymbol.getValue());

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
		iconPos.setEnabled(cboxShowChargeSymbol.isSelected());
		fontPos.setEnabled(cboxShowFont.isSelected());
		sliderReduceOn100.setEnabled(cboxShowFont.isSelected());
		sliderResizeChargeSymbol.setEnabled(cboxShowChargeSymbol.isSelected() && cboxResizeChargeSymbol.isSelected());
		cboxResizeChargeSymbol.setEnabled(cboxShowChargeSymbol.isSelected());
		// transparent Backround special behaviour
		backgroundColor.setEnabled(!cboxTransparentBgrnd.isSelected());
		backgroundColor.setVisible(!cboxTransparentBgrnd.isSelected());
		if (!backgroundColor.isEnabled()) {
			backgroundColor.setBackground(Color.black);
		}

		sliderChargGlowRadius.setEnabled(cboxChargeGlow.isSelected());
		iconColorGlowCharge.setEnabled(cboxChargeGlow.isSelected());

		sliderGlowRadius.setEnabled(cboxGlow.isSelected());
		cboxGlowForChargeToo.setEnabled(cboxGlow.isSelected());
	}

	public void enableSupportedFeatures(final boolean supportsFlip, final boolean suppoertsStrokewidth, final boolean noBG, final boolean battGradient,
			final boolean extra1, final boolean extra2, final boolean xorBackground, final boolean xorSquareBackground) {
		cboxFlip.setEnabled(supportsFlip);
		sliderStroke.setEnabled(suppoertsStrokewidth);
		cboxNoBG.setEnabled(noBG);
		cboxBattGradient.setEnabled(battGradient);
		sliderBattGradientLevel.setEnabled(battGradient);
		extraColor1.setEnabled(extra1);
		extraColor2.setEnabled(extra2);
		// xorIconSelector.setVisible(xorBackground);
		// xorSquareIconSelector.setVisible(xorSquareBackground);
		xorIconSelector.setEnabled(xorBackground);
		xorSquareIconSelector.setEnabled(xorSquareBackground);
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

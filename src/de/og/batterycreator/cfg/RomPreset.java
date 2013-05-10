package de.og.batterycreator.cfg;

import java.util.Vector;

public class RomPreset {

	// Konstanten Filenames
	public static final String			LOCKHANDLE_FILENAME_DEFAULT				= "ic_lockscreen_handle_normal.png";

	public static final String			MORPHPATH_SYSTEMUI						= "MORPH/system/app/SystemUI.apk/res/";
	public static final String			MORPHPATH_SYSTEMUI_SAMSUNG_JB			= "MORPH/preload/symlink/system/app/SystemUI.apk/res/";
	public static final String			MORPHPATH_FRAMEWORK						= "MORPH/system/framework/framework-res.apk/res/";
	public static final String			MORPHPATH_FRAMEWORK_SAMSUNG_JB			= "MORPH/preload/symlink/system/framework/framework-res.apk/res/";
	public static final String			MORPHPATH_LIDROID						= "MORPH/system/framework/lidroid-res.apk/res/";
	public static final String			MORPHPATH_MMS							= "MORPH/system/app/Mms.apk/res/";

	public static final String			WIFI_ICON_NAME							= "stat_sys_wifi_signal_";
	public static final String			WIFI_ICON_EXTENSION_FULLY				= "_fully";
	public static final String			WIFI_ICON_NAME_IN						= "stat_sys_wifi_in.png";
	public static final String			WIFI_ICON_NAME_OUT						= "stat_sys_wifi_out.png";
	public static final String			WIFI_ICON_NAME_INOUT					= "stat_sys_wifi_inout.png";

	public static final String			SIGNAL_ICON_NAME						= "stat_sys_signal_";
	public static final String			SIGNAL_ICON_EXTENSION_FULLY				= "_fully";
	public static final String			SIGNAL_ICON_NAME_IN						= "stat_sys_signal_in.png";
	public static final String			SIGNAL_ICON_NAME_OUT					= "stat_sys_signal_out.png";
	public static final String			SIGNAL_ICON_NAME_INOUT					= "stat_sys_signal_inout.png";

	public static final String			NOTIFICATION_BG_FILENME					= "notification_panel_bg.9.png";

	public static final String			BATT_ICON_CHARGE_NAME_AOKP				= "stat_sys_battery_circle_charge_anim";
	public static final String			BATT_ICON_NAME_AOKP						= "stat_sys_battery_circle_";
	public static final String			BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY	= "stat_sys_battery_charge_anim";
	public static final String			BATT_ICON_NAME_STOCK_ICS_JKAY			= "stat_sys_battery_";
	public static final String			BATT_ICON_CHARGE_NAME_ROOTBOX_ALT		= "stat_sys_battery_altcircle_charge_anim";
	public static final String			BATT_ICON_NAME_ROOTBOX_ALT				= "stat_sys_battery_altcircle_";
	public static final String			BATT_ICON_CHARGE_NAME_ROOTBOX_SQUARE	= "stat_sys_battery_square_charge_anim";
	public static final String			BATT_ICON_NAME_ROOTBOX_SQUARE			= "stat_sys_battery_square_";

	// is in frameworkres...
	public static final int				WEATHER_XHDPI							= 128;
	public static final int				WEATHER_MDPI							= 66;
	public static final int				WEATHER_HDPI							= 98;
	public static final int				WEATHER_720DP_XHDPI						= 162;
	public static final int				WEATHER_720DP_HDPI						= 144;
	public static final int				WEATHER_600DP_XHDPI						= 162;
	public static final int				WEATHER_600DP_HDPI						= 144;

	public static final int				EMO_XHDPI								= 44;
	public static final int				EMO_MDPI								= 22;
	public static final int				EMO_HDPI								= 33;
	public static final int				EMO_720DP_XHDPI							= 66;
	public static final int				EMO_720DP_HDPI							= 55;
	public static final int				EMO_600DP_XHDPI							= 66;
	public static final int				EMO_600DP_HDPI							= 55;

	public static final int				LOCK_MDPI								= 108;
	public static final int				LOCK_XHDPI								= 216;
	public static final int				LOCK_HDPI								= 162;
	public static final int				LOCK_600DP_HDPI							= 183;
	public static final int				LOCK_720P_HDPI							= 193;
	public static final int				LOCK_600DP_XHDPI						= 183;
	public static final int				LOCK_720P_XHDPI							= 193;

	// in systemUI
	public static final int				TOGGLE_720DP_XHDPI						= 96;
	public static final int				TOGGLE_600DP_XHDPI						= 84;
	public static final int				TOGGLE_720DP_HDPI						= 96;
	public static final int				TOGGLE_600DP_HDPI						= 84;
	public static final int				TOGGLE_XHDPI							= 64;
	public static final int				TOGGLE_MDPI								= 40;
	public static final int				TOGGLE_HDPI								= 48;
	public static final int				TOGGLE_XHDPI_S3							= 72;

	public static final int				NOTIFICATION_MDPI						= 2;
	public static final int				NOTIFICATION_HDPI						= 3;
	public static final int				NOTIFICATION_720DP_HDPI					= 3;
	public static final int				NOTIFICATION_600DP_HDPI					= 3;
	public static final int				NOTIFICATION_XHDPI						= 4;
	public static final int				NOTIFICATION_720DP_XHDPI				= 4;
	public static final int				NOTIFICATION_600DP_XHDPI				= 4;

	public static final int				BATT_ICON_HEIGHT_MDPI					= 18;
	public static final int				BATT_ICON_HEIGHT_XHDPI					= 36;
	public static final int				BATT_ICON_HEIGHT_HDPI					= 27;
	public static final int				BATT_ICON_HEIGHT_HDPI_S3				= 38;
	public static final int				BATT_ICON_HEIGHT_XHDPI_S3				= 50;
	public static final int				BATT_ICON_HEIGHT_720DP_XHDPI			= 48;
	public static final int				BATT_ICON_HEIGHT_600DP_XHDPI			= 43;
	public static final int				BATT_ICON_HEIGHT_720DP_HDPI				= 48;
	public static final int				BATT_ICON_HEIGHT_600DP_HDPI				= 43;

	public static final String			DRAWABLE_MDPI							= "drawable-mdpi";
	public static final String			DRAWABLE_XHDPI							= "drawable-xhdpi";
	public static final String			DRAWABLE_HDPI							= "drawable-hdpi";
	public static final String			DRAWABLE_HDPI_S3						= "drawable-hdpi";
	public static final String			DRAWABLE_720DP_XHDPI					= "drawable-sw720dp-xhdpi";
	public static final String			DRAWABLE_600DP_XHDPI					= "drawable-sw600dp-xhdpi";
	public static final String			DRAWABLE_720DP_HDPI						= "drawable-sw720dp-hdpi";
	public static final String			DRAWABLE_600DP_HDPI						= "drawable-sw600dp-hdpi";

	public static final String			templateS2								= "./template/template.zip";
	public static final String			templateS3Nexus							= "./template/templateS3Nexus.zip";
	public static final String			templateS3Nexus43						= "./template/templateS3Nexus-4.3.zip";

	public static final String			APPLY									= "Apply Settings for Rom...";

	private static Vector<RomPreset>	presets									= new Vector<RomPreset>();

	static {
		presets.add(new RomPreset(APPLY, DRAWABLE_HDPI, BATT_ICON_HEIGHT_HDPI, DRAWABLE_HDPI, BATT_ICON_NAME_AOKP, BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI,
				NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI, EMO_HDPI, templateS2, MORPHPATH_SYSTEMUI, MORPHPATH_FRAMEWORK));

		presets.add(new RomPreset("Default (hdpi AOKP)", DRAWABLE_HDPI, BATT_ICON_HEIGHT_HDPI, DRAWABLE_HDPI, BATT_ICON_NAME_AOKP, BATT_ICON_CHARGE_NAME_AOKP,
				LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI, EMO_HDPI, templateS2, MORPHPATH_SYSTEMUI, MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("Default (xhdpi AOKP)", DRAWABLE_XHDPI, BATT_ICON_HEIGHT_XHDPI, DRAWABLE_XHDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_XHDPI, EMO_XHDPI, templateS2, MORPHPATH_SYSTEMUI,
				MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("Default (mhdpi AOKP)", DRAWABLE_MDPI, BATT_ICON_HEIGHT_MDPI, DRAWABLE_MDPI, BATT_ICON_NAME_AOKP, BATT_ICON_CHARGE_NAME_AOKP,
				LOCK_MDPI, NOTIFICATION_MDPI, TOGGLE_MDPI, false, WEATHER_MDPI, EMO_MDPI, templateS2, MORPHPATH_SYSTEMUI, MORPHPATH_FRAMEWORK));

		// Rootbox
		presets.add(new RomPreset("(S2 i9100) ### RootBox 3.x", DRAWABLE_HDPI, BATT_ICON_HEIGHT_HDPI, DRAWABLE_HDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI, EMO_HDPI, templateS2, MORPHPATH_SYSTEMUI,
				MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("(S2 i9100) ### RootBox 3.x (Speedometer)", DRAWABLE_HDPI, BATT_ICON_HEIGHT_HDPI, DRAWABLE_HDPI, BATT_ICON_NAME_ROOTBOX_ALT,
				BATT_ICON_CHARGE_NAME_ROOTBOX_ALT, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI, EMO_HDPI, templateS2, MORPHPATH_SYSTEMUI,
				MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("(S2 i9100) ### RootBox 3.x (Square)", DRAWABLE_HDPI, BATT_ICON_HEIGHT_HDPI, DRAWABLE_HDPI, BATT_ICON_NAME_ROOTBOX_SQUARE,
				BATT_ICON_CHARGE_NAME_ROOTBOX_SQUARE, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI, EMO_HDPI, templateS2, MORPHPATH_SYSTEMUI,
				MORPHPATH_FRAMEWORK));

		// Resurrection
		presets.add(new RomPreset("(S2 i9100) Resurrection Remix 3.1.3 and above", DRAWABLE_XHDPI, BATT_ICON_HEIGHT_XHDPI, DRAWABLE_XHDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_XHDPI, EMO_XHDPI, templateS2, MORPHPATH_SYSTEMUI,
				MORPHPATH_FRAMEWORK));

		// other
		presets.add(new RomPreset("(S2 i9100) JellyBam 4.x - 5.x", DRAWABLE_HDPI, BATT_ICON_HEIGHT_HDPI, DRAWABLE_HDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI, EMO_HDPI, templateS2, MORPHPATH_SYSTEMUI,
				MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("(S2 i9100) Chameleon v3.0 AOKP hybrid", DRAWABLE_HDPI, BATT_ICON_HEIGHT_HDPI, DRAWABLE_HDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI, EMO_HDPI, templateS2, MORPHPATH_SYSTEMUI,
				MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("(S2 i9100) Original AOKP", DRAWABLE_HDPI, BATT_ICON_HEIGHT_HDPI, DRAWABLE_HDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI, EMO_HDPI, templateS2, MORPHPATH_SYSTEMUI,
				MORPHPATH_FRAMEWORK));
		// presets.add(new RomPreset("(S2 i9100) Neat Rom", DRAWABLE_HDPI,
		// BATT_ICON_HEIGHT_HDPI, DRAWABLE_HDPI, BATT_ICON_NAME_STOCK_ICS_JKAY,
		// BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY, LOCK_HDPI, NOTIFICATION_HDPI,
		// TOGGLE_HDPI, false, WEATHER_HDPI, EMO_HDPI, templateS2,
		// MORPHPATH_SYSTEMUI_SAMSUNG_JB));

		presets.add(new RomPreset("(S2 i9100) HydraH2O ICS 1.x", DRAWABLE_HDPI, BATT_ICON_HEIGHT_HDPI, DRAWABLE_HDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI, EMO_HDPI, templateS2, MORPHPATH_SYSTEMUI,
				MORPHPATH_FRAMEWORK));

		presets.add(new RomPreset("(S2 i9100) Original CM9/CM10/10.1 + 1%-Mod!!!", DRAWABLE_HDPI, BATT_ICON_HEIGHT_HDPI, DRAWABLE_HDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI, EMO_HDPI, templateS2, MORPHPATH_SYSTEMUI,
				MORPHPATH_FRAMEWORK));

		presets.add(new RomPreset("(S2 i9100) Stock ROM incl. JKay", DRAWABLE_HDPI, BATT_ICON_HEIGHT_HDPI, DRAWABLE_HDPI, BATT_ICON_NAME_STOCK_ICS_JKAY,
				BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI, EMO_HDPI, templateS2, MORPHPATH_SYSTEMUI,
				MORPHPATH_FRAMEWORK));

		presets.add(new RomPreset("(S2 i9100) Presets for 1%Mod from shayne77 Mod-Tool", DRAWABLE_HDPI, BATT_ICON_HEIGHT_HDPI, DRAWABLE_HDPI,
				BATT_ICON_NAME_STOCK_ICS_JKAY, BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY, LOCK_HDPI, NOTIFICATION_HDPI, TOGGLE_HDPI, false, WEATHER_HDPI, EMO_HDPI,
				templateS2, MORPHPATH_SYSTEMUI, MORPHPATH_FRAMEWORK));

		// Note2 Presets
		presets.add(new RomPreset("(Note II) Lab Rats ROM", DRAWABLE_XHDPI, BATT_ICON_HEIGHT_XHDPI, DRAWABLE_XHDPI, BATT_ICON_NAME_STOCK_ICS_JKAY,
				BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI_S3, true, WEATHER_XHDPI, EMO_XHDPI, templateS3Nexus,
				MORPHPATH_SYSTEMUI, MORPHPATH_FRAMEWORK));

		// S3 Presets
		presets.add(new RomPreset("(S3 i9300) [MORPHOLOGY SOCIETY]", DRAWABLE_HDPI, BATT_ICON_HEIGHT_HDPI_S3, DRAWABLE_HDPI, BATT_ICON_NAME_STOCK_ICS_JKAY,
				BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI_S3, true, WEATHER_XHDPI, EMO_XHDPI, templateS2,
				MORPHPATH_SYSTEMUI, MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("(S3 i9300) Stock [sdcard under /data/media]", DRAWABLE_XHDPI, BATT_ICON_HEIGHT_XHDPI, DRAWABLE_XHDPI,
				BATT_ICON_NAME_STOCK_ICS_JKAY, BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI_S3, true, WEATHER_XHDPI,
				EMO_XHDPI, templateS3Nexus, MORPHPATH_SYSTEMUI, MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("(S3 i9300) Omega Rom", DRAWABLE_XHDPI, BATT_ICON_HEIGHT_XHDPI_S3, DRAWABLE_XHDPI, BATT_ICON_NAME_STOCK_ICS_JKAY,
				BATT_ICON_CHARGE_NAME_STOCK_ICS_JKAY, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_XHDPI, EMO_XHDPI, templateS3Nexus,
				MORPHPATH_SYSTEMUI, MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("(S3 i9300) ### RootBox 3.x ", DRAWABLE_XHDPI, BATT_ICON_HEIGHT_XHDPI, DRAWABLE_XHDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_XHDPI, EMO_XHDPI, templateS3Nexus, MORPHPATH_SYSTEMUI,
				MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("(S3 i9300) ### RootBox 3.x (Speedometer)", DRAWABLE_XHDPI, BATT_ICON_HEIGHT_XHDPI, DRAWABLE_XHDPI,
				BATT_ICON_NAME_ROOTBOX_ALT, BATT_ICON_CHARGE_NAME_ROOTBOX_ALT, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_XHDPI, EMO_XHDPI,
				templateS3Nexus, MORPHPATH_SYSTEMUI, MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("(S3 i9300) ### RootBox 3.x (Square)", DRAWABLE_XHDPI, BATT_ICON_HEIGHT_XHDPI, DRAWABLE_XHDPI, BATT_ICON_NAME_ROOTBOX_SQUARE,
				BATT_ICON_CHARGE_NAME_ROOTBOX_SQUARE, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_XHDPI, EMO_XHDPI, templateS3Nexus,
				MORPHPATH_SYSTEMUI, MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("(S3 i9300/i747) Pacman Rom", DRAWABLE_XHDPI, BATT_ICON_HEIGHT_XHDPI, DRAWABLE_XHDPI, BATT_ICON_NAME_ROOTBOX_SQUARE,
				BATT_ICON_CHARGE_NAME_ROOTBOX_SQUARE, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_XHDPI, EMO_XHDPI, templateS3Nexus43,
				MORPHPATH_SYSTEMUI, MORPHPATH_FRAMEWORK));
		// Galaxy Nexus Presets
		presets.add(new RomPreset("(Galaxy Nexus) ### RootBox 3.x ", DRAWABLE_XHDPI, BATT_ICON_HEIGHT_XHDPI, DRAWABLE_XHDPI, BATT_ICON_NAME_AOKP,
				BATT_ICON_CHARGE_NAME_AOKP, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_XHDPI, EMO_XHDPI, templateS3Nexus, MORPHPATH_SYSTEMUI,
				MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("(Galaxy Nexus) ### RootBox 3.x (Speedometer)", DRAWABLE_XHDPI, BATT_ICON_HEIGHT_XHDPI, DRAWABLE_XHDPI,
				BATT_ICON_NAME_ROOTBOX_ALT, BATT_ICON_CHARGE_NAME_ROOTBOX_ALT, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_XHDPI, EMO_XHDPI,
				templateS3Nexus, MORPHPATH_SYSTEMUI, MORPHPATH_FRAMEWORK));
		presets.add(new RomPreset("(Galaxy Nexus) ### RootBox 3.x (Sqare)", DRAWABLE_XHDPI, BATT_ICON_HEIGHT_XHDPI, DRAWABLE_XHDPI,
				BATT_ICON_NAME_ROOTBOX_SQUARE, BATT_ICON_CHARGE_NAME_ROOTBOX_SQUARE, LOCK_XHDPI, NOTIFICATION_XHDPI, TOGGLE_XHDPI, false, WEATHER_XHDPI,
				EMO_XHDPI, templateS3Nexus, MORPHPATH_SYSTEMUI, MORPHPATH_FRAMEWORK));
	}

	private final String				romName;
	private final String				systemUIDrawableFolder;
	private final String				frameworkDrawableFolder;
	private final String				filePattern;
	private final String				filePatternCharge;
	private final int					weatherSize;
	private final int					emoSize;
	private final int					toggleSize;
	private final int					lockHandleSize;
	private final int					notificationHeight;
	private final int					battsize;
	private final boolean				useLidroid;
	private final String				template;
	private final String				morphPath2SystemUIRes;
	private final String				morphPath2FrameworkRes;

	public RomPreset(final String romName, final String systemUIDrawableFolder, final int battsize, final String frameworkDrawableFolder,
			final String filePattern, final String filePatternCharge, final int lockHandleSize, final int notificationHeight, final int toggleSize,
			final boolean useLidroid, final int weatherSize, final int emoSize, final String template, final String morphPath2SystemUIRes,
			final String morphPath2FrameworkRes) {
		super();
		this.romName = romName;
		this.systemUIDrawableFolder = systemUIDrawableFolder;
		this.battsize = battsize;
		this.frameworkDrawableFolder = frameworkDrawableFolder;
		this.filePattern = filePattern;
		this.filePatternCharge = filePatternCharge;
		this.lockHandleSize = lockHandleSize;
		this.notificationHeight = notificationHeight;
		this.toggleSize = toggleSize;
		this.useLidroid = useLidroid;
		this.weatherSize = weatherSize;
		this.emoSize = emoSize;
		this.template = template;
		this.morphPath2SystemUIRes = morphPath2SystemUIRes;
		this.morphPath2FrameworkRes = morphPath2FrameworkRes;
	}

	public static Vector<RomPreset> getPresets() {
		return presets;
	}

	public String getRomName() {
		return romName;
	}

	public String getSystemUIDrawableFolder() {
		return systemUIDrawableFolder;
	}

	public String getFilePattern() {
		return filePattern;
	}

	public String getFilePatternCharge() {
		return filePatternCharge;
	}

	@Override
	public String toString() {
		return romName;
	}

	public int getLockHandleSize() {
		return lockHandleSize;
	}

	public int getNotificationHeight() {
		return notificationHeight;
	}

	/**
	 * @return the toggleSize
	 */
	public int getToggleSize() {
		return toggleSize;
	}

	/**
	 * @return the weatherSize
	 */
	public int getWeatherSize() {
		return weatherSize;
	}

	/**
	 * @return the frameworkDrawableFolder
	 */
	public String getFrameworkDrawableFolder() {
		return frameworkDrawableFolder;
	}

	/**
	 * @return the battsize
	 */
	public int getBattsize() {
		return battsize;
	}

	/**
	 * @return the useLidroid
	 */
	public boolean isUseLidroid() {
		return useLidroid;
	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	public String getMorphPath2SystemUIRes() {
		return morphPath2SystemUIRes;
	}

	public Object getMorphPath2FrameworkRes() {
		return morphPath2FrameworkRes;
	}

	public int getEmoSize() {
		return emoSize;
	}
}

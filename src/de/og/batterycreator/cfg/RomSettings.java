package de.og.batterycreator.cfg;

import java.io.Serializable;

public class RomSettings implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID			= 6675299018043274221L;

	private boolean				useVRThemeTemplate			= false;
	private boolean				usePreload					= false;
	private boolean				useMMSForEmoticons			= true;
	private boolean				useHtcForLockhandle			= false;

	private String				fileBattPattern				= RomPreset.BATT_ICON_NAME_AOKP;
	private String				fileBattPatternCharge		= RomPreset.BATT_ICON_CHARGE_NAME_AOKP;

	private String				frameworkDrawableFolder		= RomPreset.DRAWABLE_HDPI;
	private String				systemUIDrawableFolder		= RomPreset.DRAWABLE_HDPI;
	private String				lidroidDrawableFolder		= RomPreset.DRAWABLE_HDPI;
	private String				emoticonsDrawableFolder		= RomPreset.DRAWABLE_HDPI;

	private String				morphPath2SystemUIRes		= RomPreset.MORPHPATH_SYSTEMUI;
	private String				folderSystemUIInZip			= morphPath2SystemUIRes + RomPreset.DRAWABLE_HDPI + "/";

	private String				morphPath2Framework			= RomPreset.MORPHPATH_FRAMEWORK;
	private String				folderFrameworkInZip		= morphPath2Framework + RomPreset.DRAWABLE_HDPI + "/";

	private String				morphPath2Lidroid			= RomPreset.MORPHPATH_LIDROID;
	private String				folderLidroidInZip			= morphPath2Lidroid + RomPreset.DRAWABLE_HDPI + "/";

	private String				morphPath2MMS				= RomPreset.MORPHPATH_MMS;
	private String				folderEmoticonsInZip		= morphPath2MMS + RomPreset.DRAWABLE_HDPI + "/";

	private String				morphPath2HTCLock			= RomPreset.MORPHPATH_HTC_LOCK;

	private String				folderLockHandleInZip		= morphPath2Framework + RomPreset.DRAWABLE_HDPI + "/";

	private int					battIconSize				= RomPreset.BATT_ICON_HEIGHT_HDPI;
	private boolean				useAdvancedResize			= true;

	// Lockhandle
	private String				lockHandleFileName			= RomPreset.LOCKHANDLE_FILENAME_DEFAULT;
	private int					lockHandleSize				= RomPreset.LOCK_HDPI;
	// toggle
	private int					toggleSize					= RomPreset.TOGGLE_HDPI;
	private boolean				useLidroid					= false;
	// weather
	private int					weatherSize					= RomPreset.WEATHER_HDPI;
	// EMoticons
	private int					emoSize						= RomPreset.EMO_HDPI;
	// private boolean useMmsForEmoticons = true;

	// Notification
	private String				notificationBGFilename		= RomPreset.NOTIFICATION_BG_FILENME;
	private int					notificationHeight			= RomPreset.NOTIFICATION_HDPI;
	// Signal Stuff
	private String				fileSignalPattern			= RomPreset.SIGNAL_ICON_NAME;
	private String				fileSignalEXtensionFully	= RomPreset.SIGNAL_ICON_EXTENSION_FULLY;
	private String				fileSignalIn				= RomPreset.SIGNAL_ICON_NAME_IN;
	private String				fileSignalOut				= RomPreset.SIGNAL_ICON_NAME_OUT;
	private String				fileSignalInOut				= RomPreset.SIGNAL_ICON_NAME_INOUT;

	// Wifi Stuff
	private String				fileWifiPattern				= RomPreset.WIFI_ICON_NAME;
	private String				fileWifiEXtensionFully		= RomPreset.WIFI_ICON_EXTENSION_FULLY;
	private String				fileWifiIn					= RomPreset.WIFI_ICON_NAME_IN;
	private String				fileWifiOut					= RomPreset.WIFI_ICON_NAME_OUT;
	private String				fileWifiInOut				= RomPreset.WIFI_ICON_NAME_INOUT;

	private String				template					= new String("./template/template.zip");

	/**
	 * @return the filePattern
	 */
	public String getFilePattern() {
		return fileBattPattern;
	}

	/**
	 * @param filePattern
	 *            the filePattern to set
	 */
	public void setFilePattern(final String filePattern) {
		fileBattPattern = filePattern;
	}

	/**
	 * @return the filePatternCharge
	 */
	public String getFilePatternCharge() {
		return fileBattPatternCharge;
	}

	/**
	 * @param filePatternCharge
	 *            the filePatternCharge to set
	 */
	public void setFilePatternCharge(final String filePatternCharge) {
		fileBattPatternCharge = filePatternCharge;
	}

	// ##########################################
	// SystemUI stuff
	// ##########################################
	public void setSystemUIDrawableFolder(final String systemUIDrawableFolder) {
		this.systemUIDrawableFolder = systemUIDrawableFolder;
		setFolderSystemUIInZip();
	}

	public void setMorphPath2SystemUIRes(final String morphPath2SystemUIRes) {
		this.morphPath2SystemUIRes = morphPath2SystemUIRes;
		setFolderSystemUIInZip();
	}

	private void setFolderSystemUIInZip() {
		folderSystemUIInZip = morphPath2SystemUIRes + systemUIDrawableFolder + "/";
	}

	public String getSystemUIDrawableFolder() {
		return systemUIDrawableFolder;
	}

	public String getSystemUIDpi() {
		final int index = systemUIDrawableFolder.indexOf("-");
		if (index > 0)
			return systemUIDrawableFolder.substring(index + 1);
		else
			return "dpi_unknown";
	}

	public String getFolderSystemUIInZip() {
		return folderSystemUIInZip;
	}

	// ##########################################
	// Framework stuff
	// ##########################################
	public void setMorphPath2Framework(final String morphPath2Framework) {
		this.morphPath2Framework = morphPath2Framework;
		setFolderFrameworkInZip();
	}

	public void setFrameworkDrawableFolder(final String frameworkDrawableFolder) {
		this.frameworkDrawableFolder = frameworkDrawableFolder;
		setFolderFrameworkInZip();
		setFolderLockHandleInZip();
	}

	private void setFolderFrameworkInZip() {
		folderFrameworkInZip = morphPath2Framework + frameworkDrawableFolder + "/";
	}

	public String getFrameworkDrawableFolder() {
		return frameworkDrawableFolder;
	}

	public String getFolderFrameworkInZip() {
		return folderFrameworkInZip;
	}

	// ##########################################
	// Lidroid stuff
	// ##########################################
	private void setFolderLidroidInZip() {
		folderLidroidInZip = morphPath2Lidroid + lidroidDrawableFolder + "/";
	}

	public void setLidroidDrawableFolder(final String lidroidDrawableFolder) {
		this.lidroidDrawableFolder = lidroidDrawableFolder;
		setFolderLidroidInZip();
	}

	public String getMorphPath2Lidroid() {
		return morphPath2Lidroid;
	}

	public void setMorphPath2Lidroid(final String morphPath2Lidroid) {
		this.morphPath2Lidroid = morphPath2Lidroid;
		setFolderLidroidInZip();
	}

	public String getFolderLidroidInZip() {
		return folderLidroidInZip;
	}

	public String getLidroidDrawableFolder() {
		return lidroidDrawableFolder;
	}

	public boolean isUseLidroid() {
		return useLidroid;
	}

	public void setUseLidroid(final boolean useLidroid) {
		this.useLidroid = useLidroid;
	}

	// ##########################################
	// Emoticons stuff
	// ##########################################
	private void setFolderEmoticonsInZip() {
		if (useMMSForEmoticons)
			folderEmoticonsInZip = morphPath2MMS + emoticonsDrawableFolder + "/";
		else
			folderEmoticonsInZip = morphPath2Framework + emoticonsDrawableFolder + "/";

	}

	public String getFolderLockHandleInZip() {
		return folderLockHandleInZip;
	}

	// ##########################################
	// Lock stuff
	// ##########################################
	private void setFolderLockHandleInZip() {
		if (useHtcForLockhandle)
			folderLockHandleInZip = morphPath2HTCLock + frameworkDrawableFolder + "/";
		else
			folderLockHandleInZip = morphPath2Framework + frameworkDrawableFolder + "/";

	}

	public String getMorphPath2HTCLock() {
		return morphPath2HTCLock;
	}

	public void setMorphPath2HTCLock(final String morphPath2HTCLock) {
		this.morphPath2HTCLock = morphPath2HTCLock;
		setFolderLockHandleInZip();
	}

	public String getFolderEmoticonsInZip() {
		return folderEmoticonsInZip;
	}

	public String getEmoticonsDrawableFolder() {
		return emoticonsDrawableFolder;
	}

	public void setEmoticonsDrawableFolder(final String emoticonsDrawableFolder) {
		this.emoticonsDrawableFolder = emoticonsDrawableFolder;
		setFolderEmoticonsInZip();
	}

	public String getMorphPath2MMS() {
		return morphPath2MMS;
	}

	public void setMorphPath2MMS(final String morphPath2Emoticons) {
		morphPath2MMS = morphPath2Emoticons;
		setFolderEmoticonsInZip();
	}

	public boolean isUseMMSForEmoticons() {
		return useMMSForEmoticons;
	}

	public void setUseMMSForEmoticons(final boolean useMMSForEmoticons) {
		this.useMMSForEmoticons = useMMSForEmoticons;
		setFolderEmoticonsInZip();
	}

	// ##########################################
	// Batt Size
	// ##########################################
	public int getBattIconSize() {
		return battIconSize;
	}

	public void setBattIconSize(final int targetIconSize) {
		battIconSize = targetIconSize;
	}

	/**
	 * @return the useAdvancedResize
	 */
	public boolean isUseAdvancedResize() {
		return useAdvancedResize;
	}

	/**
	 * @param useAdvancedResize
	 *            the useAdvancedResize to set
	 */
	public void setUseAdvancedResize(final boolean useAdvancedResize) {
		this.useAdvancedResize = useAdvancedResize;
	}

	/**
	 * @return the filePattern
	 */
	public String getFileWifiPattern() {
		return fileWifiPattern;
	}

	/**
	 * @param filePattern
	 *            the filePattern to set
	 */
	public void setFileWifiPattern(final String filePattern) {
		fileWifiPattern = filePattern;
	}

	/**
	 * @return the fileEXtensionFully
	 */
	public String getFileWifiEXtensionFully() {
		return fileWifiEXtensionFully;
	}

	/**
	 * @param fileEXtensionFully
	 *            the fileEXtensionFully to set
	 */
	public void setFileWifiEXtensionFully(final String fileEXtensionFully) {
		fileWifiEXtensionFully = fileEXtensionFully;
	}

	/**
	 * @return the fileIn
	 */
	public String getFileWifiIn() {
		return fileWifiIn;
	}

	/**
	 * @param fileIn
	 *            the fileIn to set
	 */
	public void setFileWifiIn(final String fileIn) {
		fileWifiIn = fileIn;
	}

	/**
	 * @return the fileOut
	 */
	public String getFileWifiOut() {
		return fileWifiOut;
	}

	/**
	 * @param fileOut
	 *            the fileOut to set
	 */
	public void setFileWifiOut(final String fileOut) {
		fileWifiOut = fileOut;
	}

	/**
	 * @return the fileInOut
	 */
	public String getFileWifiInOut() {
		return fileWifiInOut;
	}

	/**
	 * @param fileInOut
	 *            the fileInOut to set
	 */
	public void setFileWifiInOut(final String fileInOut) {
		fileWifiInOut = fileInOut;
	}

	/**
	 * @return the lockHandleFileName
	 */
	public String getLockHandleFileName() {
		return lockHandleFileName;
	}

	/**
	 * @param lockHandleFileName
	 *            the lockHandleFileName to set
	 */
	public void setLockHandleFileName(final String lockHandleFileName) {
		this.lockHandleFileName = lockHandleFileName;
	}

	/**
	 * @return the lockHandleSize
	 */
	public int getLockHandleSize() {
		return lockHandleSize;
	}

	/**
	 * @param lockHandleSize
	 *            the lockHandleSize to set
	 */
	public void setLockHandleSize(final int lockHandleSize) {
		this.lockHandleSize = lockHandleSize;
	}

	/**
	 * @return the fileSignalInOut
	 */
	public String getFileSignalInOut() {
		return fileSignalInOut;
	}

	/**
	 * @param fileSignalInOut
	 *            the fileSignalInOut to set
	 */
	public void setFileSignalInOut(final String fileSignalInOut) {
		this.fileSignalInOut = fileSignalInOut;
	}

	/**
	 * @return the fileSignalOut
	 */
	public String getFileSignalOut() {
		return fileSignalOut;
	}

	/**
	 * @param fileSignalOut
	 *            the fileSignalOut to set
	 */
	public void setFileSignalOut(final String fileSignalOut) {
		this.fileSignalOut = fileSignalOut;
	}

	/**
	 * @return the fileSignalIn
	 */
	public String getFileSignalIn() {
		return fileSignalIn;
	}

	/**
	 * @param fileSignalIn
	 *            the fileSignalIn to set
	 */
	public void setFileSignalIn(final String fileSignalIn) {
		this.fileSignalIn = fileSignalIn;
	}

	/**
	 * @return the fileSignalEXtensionFully
	 */
	public String getFileSignalEXtensionFully() {
		return fileSignalEXtensionFully;
	}

	/**
	 * @param fileSignalEXtensionFully
	 *            the fileSignalEXtensionFully to set
	 */
	public void setFileSignalEXtensionFully(final String fileSignalEXtensionFully) {
		this.fileSignalEXtensionFully = fileSignalEXtensionFully;
	}

	/**
	 * @return the fileSignalPattern
	 */
	public String getFileSignalPattern() {
		return fileSignalPattern;
	}

	/**
	 * @param fileSignalPattern
	 *            the fileSignalPattern to set
	 */
	public void setFileSignalPattern(final String fileSignalPattern) {
		this.fileSignalPattern = fileSignalPattern;
	}

	/**
	 * @return the notificationBGFilename
	 */
	public String getNotificationBGFilename() {
		return notificationBGFilename;
	}

	/**
	 * @param notificationBGFilename
	 *            the notificationBGFilename to set
	 */
	public void setNotificationBGFilename(final String notificationBGFilename) {
		this.notificationBGFilename = notificationBGFilename;
	}

	/**
	 * @return the notificationHeight
	 */
	public int getNotificationHeight() {
		return notificationHeight;
	}

	/**
	 * @param notificationHeight
	 *            the notificationHeight to set
	 */
	public void setNotificationHeight(final int notificationHeight) {
		this.notificationHeight = notificationHeight;
	}

	/**
	 * @return the toggleSize
	 */
	public int getToggleSize() {
		return toggleSize;
	}

	/**
	 * @param toggleSize
	 *            the toggleSize to set
	 */
	public void setToggleSize(final int toggleSize) {
		this.toggleSize = toggleSize;
	}

	/**
	 * @return the weatherSize
	 */
	public int getWeatherSize() {
		return weatherSize;
	}

	/**
	 * @param weatherSize
	 *            the weatherSize to set
	 */
	public void setWeatherSize(final int weatherSize) {
		this.weatherSize = weatherSize;
	}

	/**
	 * @return the template
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * @param template
	 *            the template to set
	 */
	public void setTemplate(final String template) {
		this.template = template;
	}

	public String getMorphPath2SystemUIRes() {
		return morphPath2SystemUIRes;
	}

	public String getMorphPath2FrameworkRes() {
		return morphPath2Framework;
	}

	public int getEmoSize() {
		return emoSize;
	}

	public void setEmoSize(final int emoSize) {
		this.emoSize = emoSize;
	}

	public boolean isUseVRThemeTemplate() {
		return useVRThemeTemplate;
	}

	public void setUseVRThemeTemplate(final boolean useVRThemeTemplate) {
		this.useVRThemeTemplate = useVRThemeTemplate;
	}

	public boolean isUsePreload() {
		return usePreload;
	}

	public void setUsePreload(final boolean usePreload) {
		this.usePreload = usePreload;
	}

	public boolean isUseHtcForLockhandle() {
		return useHtcForLockhandle;
	}

	public void setUseHtcForLockhandle(final boolean useHtcForLockhandle) {
		this.useHtcForLockhandle = useHtcForLockhandle;
		setFolderLockHandleInZip();
	}

}

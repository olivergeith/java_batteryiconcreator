package de.og.batterycreator.cfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import og.basics.gui.file.FileDialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsPersistor {
	private static final Logger	LOGGER					= LoggerFactory.getLogger(SettingsPersistor.class);
	private static final String	SETTINGS_ROMS_EXTENSION	= ".rcfg";
	private static final String	SETTINGS_BATT_EXTENSION	= ".bcfg";
	private static final String	SETTINGS_WIFI_EXTENSION	= ".wcfg";
	private static final String	SETTINGS_DIR			= "./stylSettings/";
	private static final String	SETTINGS_DIR_GLOBAL		= "./settings/";

	// ###############################################################################
	// Persisting Settings
	// ###############################################################################
	public static void saveRomSettings(final String name, final RomSettings settings) {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR);
			if (!pa.exists())
				pa.mkdirs();
			final String filename = SETTINGS_DIR + name + SETTINGS_ROMS_EXTENSION;
			final File saveFile = FileDialogs.saveFile(pa, new File(filename), SETTINGS_ROMS_EXTENSION, "RomSettings");
			if (saveFile != null) {
				final FileOutputStream file = new FileOutputStream(saveFile);
				final ObjectOutputStream o = new ObjectOutputStream(file);
				o.writeObject(settings);
				o.close();
			}
		} catch (final IOException e) {
			System.err.println(e);
		}
	}

	public static RomSettings loadRomSettings() {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR);
			if (!pa.exists())
				pa.mkdirs();

			// final String filename = "./stylSettings/" + getName() + ".cfg";
			final File loadFile = FileDialogs.chooseFile(pa, SETTINGS_ROMS_EXTENSION, "RomSettings");
			if (loadFile != null) {
				final FileInputStream file = new FileInputStream(loadFile);
				final ObjectInputStream o = new ObjectInputStream(file);
				final RomSettings settings = (RomSettings) o.readObject();
				o.close();
				return settings;
			}
		} catch (final IOException e) {
			System.err.println(e);
		} catch (final ClassNotFoundException e) {
			System.err.println(e);
		}
		return null;
	}

	// ###############################################################################
	// Persisting Settings
	// ###############################################################################
	public static void saveBattSettings(final String name, final BattSettings settings) {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR);
			if (!pa.exists())
				pa.mkdirs();
			final String filename = SETTINGS_DIR + name + SETTINGS_BATT_EXTENSION;
			final File saveFile = FileDialogs.saveFile(pa, new File(filename), SETTINGS_BATT_EXTENSION, "Battery Settings");
			if (saveFile != null) {
				final FileOutputStream file = new FileOutputStream(saveFile);
				final ObjectOutputStream o = new ObjectOutputStream(file);
				o.writeObject(settings);
				o.close();
			}
		} catch (final IOException e) {
			System.err.println(e);
		}
	}

	public static BattSettings loadBattSettings() {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR);
			if (!pa.exists())
				pa.mkdirs();

			// final String filename = "./stylSettings/" + getName() + ".cfg";
			final File loadFile = FileDialogs.chooseFile(pa, SETTINGS_BATT_EXTENSION, "Battery Settings");
			if (loadFile != null) {
				final FileInputStream file = new FileInputStream(loadFile);
				final ObjectInputStream o = new ObjectInputStream(file);
				final BattSettings settings = (BattSettings) o.readObject();
				o.close();
				return settings;
			}
		} catch (final IOException e) {
			System.err.println(e);
		} catch (final ClassNotFoundException e) {
			System.err.println(e);
		}
		return null;
	}

	// ###############################################################################
	// Persisting Settings
	// ###############################################################################
	public static void saveWifiSettings(final String name, final WifiSignalSettings settings) {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR);
			if (!pa.exists())
				pa.mkdirs();
			final String filename = SETTINGS_DIR + name + SETTINGS_WIFI_EXTENSION;
			final File saveFile = FileDialogs.saveFile(pa, new File(filename), SETTINGS_WIFI_EXTENSION, "Wifi/Signal Settings");
			if (saveFile != null) {
				final FileOutputStream file = new FileOutputStream(saveFile);
				final ObjectOutputStream o = new ObjectOutputStream(file);
				o.writeObject(settings);
				o.close();
			}
		} catch (final IOException e) {
			System.err.println(e);
		}
	}

	public static WifiSignalSettings loadWifiSettings() {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR);
			if (!pa.exists())
				pa.mkdirs();

			// final String filename = "./stylSettings/" + getName() + ".cfg";
			final File loadFile = FileDialogs.chooseFile(pa, SETTINGS_WIFI_EXTENSION, "Wifi/Signaly Settings");
			if (loadFile != null) {
				final FileInputStream file = new FileInputStream(loadFile);
				final ObjectInputStream o = new ObjectInputStream(file);
				final WifiSignalSettings settings = (WifiSignalSettings) o.readObject();
				o.close();
				return settings;
			}
		} catch (final IOException e) {
			System.err.println(e);
		} catch (final ClassNotFoundException e) {
			System.err.println(e);
		}
		return null;
	}

	// ###############################################################################
	// Persisting Settings
	// ###############################################################################
	public static void saveGlobalSettings(final GlobalSettings settings) {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR_GLOBAL);
			if (!pa.exists())
				pa.mkdirs();
			final String filename = SETTINGS_DIR_GLOBAL + "GlobalSettings.gcfg";
			LOGGER.info("Saving Global Settings: {}", filename);
			final File saveFile = new File(filename);
			if (saveFile != null) {
				final FileOutputStream file = new FileOutputStream(saveFile);
				final ObjectOutputStream o = new ObjectOutputStream(file);
				o.writeObject(settings);
				o.close();
			}
		} catch (final IOException e) {
			LOGGER.error("Error saving Global Settings: " + e);
		}
	}

	public static GlobalSettings loadGlobalSettings() {
		try {
			// Pfad anlegen falls nicht vorhanden
			final File pa = new File(SETTINGS_DIR_GLOBAL);
			if (!pa.exists())
				pa.mkdirs();

			final String filename = SETTINGS_DIR_GLOBAL + "GlobalSettings.gcfg";
			LOGGER.info("Loading Global Settings: {}", filename);
			final File loadFile = new File(filename);
			if (loadFile != null) {
				final FileInputStream file = new FileInputStream(loadFile);
				final ObjectInputStream o = new ObjectInputStream(file);
				final GlobalSettings settings = (GlobalSettings) o.readObject();
				o.close();
				return settings;
			}
		} catch (final IOException e) {
			LOGGER.warn("Loading Global Settings not possible (file not found): IOException");
		} catch (final ClassNotFoundException e) {
			LOGGER.error("Error loading Global Settings: ClassNotFoundException");
		}
		return null;
	}
}

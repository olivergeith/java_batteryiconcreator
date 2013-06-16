package de.og.batterycreator.systemuianalyser.gui;

import java.awt.Component;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import og.basics.gui.image.StaticImageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.cfg.GlobalSettings;
import de.og.batterycreator.gui.iconstore.IconStore;
import de.og.batterycreator.main.IconCreatorFrame;
import de.og.batterycreator.systemuianalyser.data.BatteryType;
import de.og.batterycreator.systemuianalyser.data.ToggleType;
import de.og.batterycreator.systemuianalyser.data.WifiSignalType;

public class IconExporter {

	private static final Logger	LOG		= LoggerFactory.getLogger(IconExporter.class);
	private final Component		parent;
	private String				romName	= "MyRomName";

	public IconExporter(final Component parent) {
		this.parent = parent;
	}

	// #######################################################
	// toggles
	// #######################################################
	public void exportToggleIconSet(final ToggleType type) {
		if (type != null) {
			final String iconSetFolderName = askRomNameGenerateFolderNameForIconSet("Toggles", type.getDpi());
			if ((iconSetFolderName != null) && (iconSetFolderName.length() > 0)) {
				exportToggleIconSet(type, iconSetFolderName);
			}
		}
	}

	private void exportToggleIconSet(final ToggleType type, final String iconSetFolderName) {
		if (type != null) {
			// outfolder anlegen
			final String outFolder = getOutFolder(GlobalSettings.INSTANCE.getToggleCustomPath(), iconSetFolderName);
			// loop über alle icons
			for (final ImageIcon icon : type.getIcons()) {
				//
				final String filenameandPath = outFolder + icon.getDescription();
				LOG.info("Saving: {}", filenameandPath);
				StaticImageHelper.writePNG(icon, new File(filenameandPath));
			}
			// Erfolg vermelden
			showQuittung(outFolder, "Toggles");
		}
	}

	// #######################################################
	// Wifi
	// #######################################################
	public void exportWifiSignalIconSet(final WifiSignalType type) {
		if (type != null) {
			final String iconSetFolderName = askRomNameGenerateFolderNameForIconSet("Signal&Wifi", type.getDpi());
			if ((iconSetFolderName != null) && (iconSetFolderName.length() > 0)) {
				exportWifiIconSet(type, iconSetFolderName);
			}
		}
	}

	private void exportWifiIconSet(final WifiSignalType type, final String iconSetFolderName) {
		if (type != null) {
			// outfolder anlegen
			final String outFolder = getOutFolder(GlobalSettings.INSTANCE.getSignalWifiCustomPath(), iconSetFolderName);
			// loop über alle icons
			for (final ImageIcon icon : type.getIcons()) {
				//
				final String filenameandPath = outFolder + icon.getDescription();
				LOG.info("Saving: {}", filenameandPath);
				StaticImageHelper.writePNG(icon, new File(filenameandPath));
			}
			// Erfolg vermelden
			showQuittung(outFolder, "Signal & Wifi");
		}
	}

	// #######################################################
	// Battery
	// #######################################################
	public void exportBatteryIconSet(final BatteryType type) {
		if (type != null) {
			final String iconSetFolderName = askRomNameGenerateFolderNameForIconSet(type.getPattern(), type.getDpi());
			if (iconSetFolderName != null) {
				exportBatteryIconSet(type, iconSetFolderName);
			}
		}
	}

	private void exportBatteryIconSet(final BatteryType type, final String iconSetFolderName) {
		if (type != null) {
			// outfolder anlegen
			final String outFolder = getOutFolder(GlobalSettings.INSTANCE.getBattCustomPath(), iconSetFolderName);
			// loop über alle icons
			for (final ImageIcon icon : type.getIcons()) {
				//
				final String filenameandPath = outFolder + icon.getDescription();
				LOG.info("Saving: {}", filenameandPath);
				StaticImageHelper.writePNG(icon, new File(filenameandPath));
			}
			// loop über alle chargeicons
			for (final ImageIcon icon : type.getIconsCharge()) {
				//
				final String filenameandPath = outFolder + icon.getDescription();
				LOG.info("Saving: {}", filenameandPath);
				StaticImageHelper.writePNG(icon, new File(filenameandPath));
			}
			// Erfolg vermelden
			showQuittung(outFolder, "Batteries");
		}
	}

	/**
	 * returns the filder for Type!
	 * 
	 * @param ident
	 * @param dpi
	 * @return
	 */
	private String askRomNameGenerateFolderNameForIconSet(final String ident, final String dpi) {
		final String name = (String) JOptionPane.showInputDialog(parent, //
				"Exporting and creating an 'Icon-Set --> " + ident + "' for you\n\n"//
						+ "- The Icon-Set will be named <MyRomName>_" + ident + "_(" + dpi + ")\n\n"//
						+ "What is the name of your Rom ?\n" //
				, "Exporting Icons", JOptionPane.PLAIN_MESSAGE, IconStore.iconsetsIcon, null, romName);

		if (name != null && name.length() > 0) {
			// RomName merken und foldername zurückgeben!
			romName = name;
			return name + "_" + ident + "_(" + dpi + ")";
		}
		return null;
	}

	private String getOutFolder(final String customPath, final String typeFolderName) {
		final String outFolder = customPath + typeFolderName + "/";
		final File folder = new File(outFolder);
		folder.mkdirs();
		return outFolder;
	}

	private void showQuittung(final String outFolder, final String setType) {
		JOptionPane.showMessageDialog(parent, //
				"Icons have been saved to:\n" + //
						outFolder + "\n\n" + //
						"Please restart " + IconCreatorFrame.APP_NAME + " to have this new Icon-Set available in 'Icon-Sets --> " + setType + "'\n", //
				"Exporting Icons",//
				JOptionPane.INFORMATION_MESSAGE);

	}

	public void exportIconSet(final List<ImageIcon> icons, final String exportPath, final String iconSetName) {
		if (icons != null) {
			// outfolder anlegen
			final String outFolder = getOutFolder(exportPath, iconSetName);
			// loop über alle icons
			for (final ImageIcon icon : icons) {
				//
				final String filenameandPath = outFolder + icon.getDescription();
				LOG.info("Saving: {}", filenameandPath);
				StaticImageHelper.writePNG(icon, new File(filenameandPath));
			}
			// Erfolg vermelden
			showQuittung(outFolder, "Batteries");
		}
	}

}

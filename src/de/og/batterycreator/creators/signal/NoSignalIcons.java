package de.og.batterycreator.creators.signal;

import java.util.Vector;

import javax.swing.ImageIcon;

import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.gui.iconstore.IconStore;

public class NoSignalIcons extends AbstractSignalCreator {

	public static String name = "No Signal Icons";
	private final ImageIcon nada = IconStore.nothingIcon;

	public NoSignalIcons(final RomSettings romSettings) {
		super(romSettings);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Vector<String> getAllFilenamesAndPath() {
		return new Vector<String>();
	}

	@Override
	public void createAllImages() {
	}

	@Override
	public ImageIcon createOverview() {
		return nada;
	}

	@Override
	public ImageIcon createImage(final int level, final boolean fully) {
		return nada;
	}

	@Override
	public ImageIcon createInOutImage(final boolean in, final boolean out) {
		return nada;
	}

	@Override
	public Vector<String> getFilenames() {
		return new Vector<String>();
	}

	@Override
	public Vector<ImageIcon> getIcons() {
		return new Vector<ImageIcon>();
	}

	@Override
	public ImageIcon getOverviewIcon() {
		return nada;
	}

}

package de.og.batterycreator.creators.batt;

import java.util.Vector;
import javax.swing.ImageIcon;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.gui.iconstore.IconStore;

public class NoBattIcons extends AbstractIconCreator {
	public static String	name	= "No Battery Icons";
	private final ImageIcon	nada	= IconStore.nothingIcon;

	public NoBattIcons(final RomSettings romSettings) {
		super(romSettings);
	}

	@Override
	public ImageIcon createImage(final int percentage, final boolean charge) {
		return nada;
	}

	@Override
	public void createAllImages() {
	}

	@Override
	public ImageIcon createOverview() {
		return nada;
	}

	@Override
	public ImageIcon createSmallOverview() {
		return nada;
	}

	@Override
	public Vector<String> getAllFilenamesAndPath() {
		return new Vector<String>();
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
	public String toString() {
		return name;
	}

	@Override
	public ImageIcon getOverviewIcon() {
		return nada;
	}

	@Override
	public ImageIcon getOverviewSmallIcon() {
		return nada;
	}

}

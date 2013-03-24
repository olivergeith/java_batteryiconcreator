package de.og.batterycreator.creators;

import java.util.Vector;

import de.og.batterycreator.cfg.RomSettings;

public interface IconProviderInterface {

	public String getProviderName();

	public Vector<String> getAllFilenamesAndPath();

	// public void createAllImages();

	public void createAllImages(final RomSettings romSettings);

	public boolean isActiv();

}

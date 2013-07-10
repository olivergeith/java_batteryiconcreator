package de.og.batterycreator.cfg;

import java.io.Serializable;

public class HSBSettings implements Serializable {

	private static final long	serialVersionUID	= -2336629424986910888L;
	public int					hue					= 0;
	public int					saturation			= 0;
	public int					brightness			= 0;

	public HSBSettings(final int hue, final int saturation, final int brightness) {
		this.hue = hue;
		this.saturation = saturation;
		this.brightness = brightness;
	}

}

package de.og.batterycreator.systemuianalyser.data;

public abstract class IconType {

	private final String	drawableFolder;
	private String			dpi;

	public IconType(final String drawableFolder) {
		this.drawableFolder = drawableFolder;
		cutOfDrawable();
	}

	public abstract String toDebugString();

	private void cutOfDrawable() {
		final int index = drawableFolder.indexOf("-");
		setDpi(drawableFolder.substring(index + 1));
	}

	public String getDrawableFolder() {
		return drawableFolder;
	}

	public String getDpi() {
		return dpi;
	}

	private void setDpi(final String dpi) {
		this.dpi = dpi;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((drawableFolder == null) ? 0 : drawableFolder.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final IconType other = (IconType) obj;
		if (drawableFolder == null) {
			if (other.drawableFolder != null)
				return false;
		} else if (!drawableFolder.equals(other.drawableFolder))
			return false;
		return true;
	}

}

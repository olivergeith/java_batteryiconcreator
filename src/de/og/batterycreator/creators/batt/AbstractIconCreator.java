package de.og.batterycreator.creators.batt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.ImageIcon;
import og.basics.grafics.Draw2DFunktions;
import og.basics.gui.image.StaticImageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.og.batterycreator.cfg.BattSettings;
import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.creators.AbstractCreator;
import de.og.batterycreator.main.IconCreatorFrame;

/**
 * @author Oliver
 * 
 */
public abstract class AbstractIconCreator extends AbstractCreator {

	private static final Logger	LOGGER	= LoggerFactory.getLogger(AbstractIconCreator.class);

	public AbstractIconCreator(final RomSettings romSettings) {
		super(romSettings);
	}

	protected BattSettings	settings	= new BattSettings();

	// ###############################################################################
	// Abstracte Methoden
	// ###############################################################################
	public abstract ImageIcon createImage(final int percentage, final boolean charge);

	public boolean isNativeXXHDPI() {
		return false;
	}

	// ###############################################################################
	// Special features
	// ###############################################################################
	public boolean supportsGradient() {
		return false;
	}

	public boolean supportsFlip() {
		return false;
	}

	public boolean supportsNoBg() {
		return false;
	}

	public boolean supportsStrokeWidth() {
		return false;
	}

	public boolean supportsExtraColor1() {
		return false;
	}

	public boolean supportsExtraColor2() {
		return false;
	}

	public boolean supportsXOrIcon() {
		return false;
	}

	// ###############################################################################
	// Creating Images
	// ###############################################################################
	@Override
	public void createAllImages() {
		iconMap.removeAllElements();
		filenames.removeAllElements();
		filenamesAndPath.removeAllElements();
		LOGGER.info("Battery: Creating Icons!");
		createImages();
		LOGGER.info("Battery: Creating ChargeIcons!");
		createChargeImages();
		LOGGER.info("Battery: Creating Overview!");
		overview = createOverview();
	}

	private void createChargeImages() {
		for (int i = 0; i <= 100; i++) {
			filenamesAndPath.add(getFilenameAndPath(i, true));
			filenames.add(getFileName(i, true));
			iconMap.add(createImage(i, true));
		}
		filenamesAndPath.add(getFilenameAndPathFull(true));

	}

	private void createImages() {
		for (int i = 0; i <= 100; i++) {
			filenamesAndPath.add(getFilenameAndPath(i, false));
			filenames.add(getFileName(i, false));
			iconMap.add(createImage(i, false));
		}
		filenamesAndPath.add(getFilenameAndPathFull(false));
	}

	protected void drawPercentage(final Graphics2D g2d, final int percentage, final boolean charge, final BufferedImage img) {
		drawGlow(g2d, percentage, charge, img);
		drawGlowOnChargeAnimation(g2d, percentage, charge, img);
		if (settings.isShowFont()) {
			int yoff = 8;
			if (charge && settings.isShowChargeSymbol()) {
				drawChargeIcon(g2d, img);
			} else {
				// Sonderbehandlung bei 100% --> Schrift kleiner machen
				if (percentage == 100 && settings.getReduceFontOn100() < 0) {
					final Font font = settings.getFont();
					final Font newfont = new Font(font.getName(), font.getStyle(), font.getSize() + settings.getReduceFontOn100());
					g2d.setFont(newfont);
					// offset extra berechnen proportional zur verkleinerten
					// Font
					yoff = 8 + Math.round(settings.getReduceFontOn100() / 2f);
				}
				final FontMetrics metrix = g2d.getFontMetrics();
				// Farbe für Schrift
				g2d.setColor(settings.getActivFontColor(percentage, charge));
				String str = "" + percentage;
				if (settings.isAddPercent())
					str += "%";
				final Rectangle2D strRect = metrix.getStringBounds(str, g2d);
				final int strxpos = 1 + settings.getFontXOffset() + (int) (Math.round(img.getWidth() / 2) - Math.round(strRect.getWidth() / 2));
				final int strypos = img.getHeight() / 2 + yoff + settings.getFontYOffset();

				g2d.drawString(str, strxpos, strypos);
				// Schrift wieder normal machen!!!
				g2d.setFont(settings.getFont());
			}
		} else if (charge && settings.isShowChargeSymbol()) {
			drawChargeIcon(g2d, img);
		}
	}

	/**
	 * Draws a glow behind a charge Symbol or number
	 * 
	 * @param g2d
	 * @param percentage
	 * @param charge
	 * @param img
	 */
	private void drawGlowOnChargeAnimation(final Graphics2D g2d, final int percentage, final boolean charge, final BufferedImage img) {
		if (settings.isChargeGlow() && charge == true) {
			int centertranparenz = 170;

			final int mod = percentage % 10;
			switch (mod) {
				case 0:
					centertranparenz = centertranparenz * 10 / 10;
					break;
				case 1:
					centertranparenz = centertranparenz * 8 / 10;
					break;
				case 2:
					centertranparenz = centertranparenz * 6 / 10;
					break;
				case 3:
					centertranparenz = centertranparenz * 4 / 10;
					break;
				case 4:
					centertranparenz = centertranparenz * 2 / 10;
					break;
				case 5:
					centertranparenz = centertranparenz * 0 / 10;
					break;
				case 6:
					centertranparenz = centertranparenz * 2 / 10;
					break;
				case 7:
					centertranparenz = centertranparenz * 4 / 10;
					break;
				case 8:
					centertranparenz = centertranparenz * 6 / 10;
					break;
				case 9:
					centertranparenz = centertranparenz * 8 / 10;
					break;
				default:
					centertranparenz = centertranparenz * 10 / 10;
					break;
			}

			// centertranparenz = centertranparenz / steps * mod;

			// getting the Colors right...
			final Color col = settings.getIconChargeGlowColor();
			final Color col2 = new Color(col.getRed(), col.getGreen(), col.getBlue(), centertranparenz);
			final Color col3 = new Color(col.getRed(), col.getGreen(), col.getBlue(), 0);

			// creating paint
			final Point2D center = new Point2D.Float(img.getWidth() / 2, img.getHeight() / 2);
			final float radius = settings.getChargeGlowRadius();
			final float[] dist = {
					0.0f, 1.0f
			};
			final Color[] colors = {
					col2, col3
			};
			final RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);

			// painting
			g2d.setPaint(p);
			g2d.fillArc(-10, -10, img.getWidth() + 20, img.getHeight() + 20, 0, 360);
		}
	}

	/**
	 * Draws a glow behind a charge Symbol or number
	 * 
	 * @param g2d
	 * @param percentage
	 * @param charge
	 * @param img
	 */
	private void drawGlow(final Graphics2D g2d, final int percentage, final boolean charge, final BufferedImage img) {
		if (settings.isGlow()) {
			// aus aussteigen, wenn gerade charge, aber bei cahrge kein glow
			// soll!
			if (charge == true && settings.isGlowForChargeToo() == false) {
				return;
			}
			final int centertranparenz = 190;
			// getting the Colors right...
			final Color col = settings.getActivIconColor(percentage, charge);
			final Color col2 = new Color(col.getRed(), col.getGreen(), col.getBlue(), centertranparenz);
			final Color col3 = new Color(col.getRed(), col.getGreen(), col.getBlue(), 0);

			// creating paint
			final Point2D center = new Point2D.Float(img.getWidth() / 2, img.getHeight() / 2);
			final float radius = settings.getGlowRadius();
			final float[] dist = {
					0.0f, 1.0f
			};
			final Color[] colors = {
					col2, col3
			};
			final RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);

			// painting
			g2d.setPaint(p);
			final int centerX = settings.getFontXOffset() + img.getWidth() / 2;
			final int centerY = settings.getFontYOffset() + img.getHeight() / 2;
			final int r = img.getHeight();
			Draw2DFunktions.fillCircle(g2d, centerX, centerY, r, 0, 360);
		}
	}

	/**
	 * Creates a darker Color of the given color...used for gradients
	 * 
	 * @param col1
	 * @return
	 */
	public Color getBattGardientSecondColor(final Color col1) {
		Color col2 = col1;
		for (int i = 0; i <= settings.getBattGradientLevel(); i++)
			col2 = col2.darker();
		return col2;
	}

	private void drawChargeIcon(final Graphics2D g2d, final BufferedImage img) {
		final ImageIcon chargeIcon = settings.getChargeIcon();
		if (chargeIcon != null) {
			// Resize Charge Icon
			BufferedImage resizedChargeIcon = new BufferedImage(chargeIcon.getIconWidth(), chargeIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g = resizedChargeIcon.createGraphics();
			g.drawImage(chargeIcon.getImage(), 0, 0, null);
			if (settings.isResizeChargeSymbol())
				resizedChargeIcon = StaticImageHelper.resize2Height(resizedChargeIcon, settings.getResizeChargeSymbolHeight());

			final int w = resizedChargeIcon.getWidth();
			final int h = resizedChargeIcon.getHeight();
			final int x = 1 + settings.getIconXOffset() + img.getWidth() / 2 - w / 2;
			final int y = img.getHeight() / 2 - h / 2 + settings.getIconYOffset();
			g2d.drawImage(resizedChargeIcon, x, y, null);
		}
	}

	// ###############################################################################
	// Writing Stuff to Filesystem
	// ###############################################################################
	protected BufferedImage writeFile(final int percentage, final boolean charge, BufferedImage img) {
		// Filename zusammenbasteln
		final String filename = getFileName(percentage, charge);
		img = writeFile(filename, img);
		// Sonderbehandlung um das Full Image zu schreiben
		if (percentage == 100) {
			writeFileFull(charge, img);
		}
		return img;
	}

	private void writeFileFull(final boolean charge, final BufferedImage img) {
		final File file = new File(getFilenameAndPathFull(charge));
		// hier schreiben wir direct, weil img schon resized ist!
		StaticImageHelper.writePNG(img, file);
	}

	// ###############################################################################
	// Filename for Full Image
	// ###############################################################################
	public String getFilenameAndPathFull(final boolean charge) {
		return getPath() + File.separator + getFileNameFull(charge);
	}

	private String getFileNameFull(final boolean charge) {
		String filename;
		if (charge == false)
			filename = romSettings.getFilePattern() + "full.png";
		else
			filename = romSettings.getFilePatternCharge() + "full.png";
		return filename;
	}

	// ###############################################################################
	// Filename for percentage Images
	// ###############################################################################
	public String getFileName(final int percentage, final boolean charge) {
		String filename;
		if (charge == false)
			filename = romSettings.getFilePattern() + percentage + ".png";
		else
			filename = romSettings.getFilePatternCharge() + percentage + ".png";
		return filename;
	}

	public String getFilenameAndPath(final int percentage, final boolean charge) {
		final String filename = getPath() + File.separator + getFileName(percentage, charge);
		return filename;
	}

	// ###############################################################################
	// Creating Overview
	// ###############################################################################
	@Override
	public ImageIcon createOverview() {
		LOGGER.info("Battery: Creating Overview!");
		if (iconMap != null && iconMap.size() > 100) {
			final ImageIcon img1 = iconMap.get(0);
			final int iw = img1.getIconWidth();
			final int ih = img1.getIconHeight();
			final int w = iw * 20 + 21;
			final int offsetOben = 50;
			final int offsetUnten = 35;
			final int h = ih * 11 + 12 + offsetOben + offsetUnten;

			final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			final Graphics2D g2d = over.createGraphics();
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 19));
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setColor(Color.black);
			g2d.fillRect(0, 0, w, h);
			g2d.setColor(Color.white);
			g2d.drawString(getCreatorName(), 2, 20);
			g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
			g2d.setColor(Color.gray);
			g2d.drawString("Created with ''The Battery Icon Creator'' V" + IconCreatorFrame.VERSION_NR + " by OlliG", 2, 32);
			g2d.drawString("http://forum.xda-developers.com/showthread.php?t=1918500", 2, h - offsetUnten + 20);
			g2d.setColor(Color.white);
			g2d.fillRect(0, 40, w, 2);
			g2d.fillRect(0, h - offsetUnten, w, 2);
			g2d.fillRect(0, h - 2, w, 2);

			for (int z = 0; z < 10; z++) {
				for (int e = 0; e < 10; e++) {
					final int index = z * 10 + e;
					final ImageIcon img = iconMap.elementAt(index);
					g2d.drawImage(img.getImage(), 1 + e * (iw + 1), 1 + z * (ih + 1) + offsetOben, null);
				}
			}
			final ImageIcon img = iconMap.elementAt(100);
			g2d.drawImage(img.getImage(), 1 + 0 * iw, 1 + 10 * (ih + 1) + offsetOben, null);

			// // Charge Icons
			for (int z = 0; z < 10; z++) {
				for (int e = 0; e < 10; e++) {
					final int index = 101 + z * 10 + e;
					final ImageIcon imgc = iconMap.elementAt(index);
					g2d.drawImage(imgc.getImage(), 1 + 10 * (iw + 1) + e * (iw + 1), 1 + z * (ih + 1) + offsetOben, null);
				}
			}
			final ImageIcon img100c = iconMap.elementAt(201);
			g2d.drawImage(img100c.getImage(), 1 + 10 * (iw + 1) + 0 * iw, 1 + 10 * (ih + 1) + offsetOben, null);

			writeOverviewFile(over);
			return new ImageIcon(over);
		}
		return null;
	}

	// ###############################################################################
	// Grafics2D
	// ###############################################################################
	protected Graphics2D initGrafics2D(final BufferedImage img) {
		return initGrafics2D(img, false);
	}

	protected Graphics2D initGrafics2D(final BufferedImage img, final boolean forceTransparent) {
		final Graphics2D g2d = img.createGraphics();
		g2d.setFont(settings.getFont());
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(settings.getStrokewidth()));
		if (!forceTransparent) {
			if (!settings.isTransparentBackground()) {
				g2d.setColor(settings.getBackgroundColor());
				g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
			}
		}
		return g2d;
	}

	public ImageIcon getOverviewIcon() {
		return overview;
	}

	/**
	 * @return the settings
	 */
	public BattSettings getBattSettings() {
		return settings;
	}

	/**
	 * @param settings
	 *            the settings to set
	 */
	public void setBattSettings(final BattSettings settings) {
		this.settings = settings;
	}

	@Override
	public String getPath() {
		return "./pngs/renderer/batt/" + toString();
	}

}

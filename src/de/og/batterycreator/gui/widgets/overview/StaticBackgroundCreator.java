package de.og.batterycreator.gui.widgets.overview;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class StaticBackgroundCreator {

	private static Graphics2D initGraphics(final BufferedImage over) {
		final Graphics2D g2d = over.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		return g2d;
	}

	protected static BufferedImage drawBackgroundGlassFrame(final int w, final int h) {

		final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGraphics(over);

		// Zurück auf normales paint
		g2d.setPaintMode();

		// Background
		final int radius = 8;

		// GlasFrame
		Color col1 = new Color(92, 92, 92, 128);
		Color col2 = new Color(128, 128, 128, 128);
		GradientPaint gradientFill = new GradientPaint(0, 0, col1, 0, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(1, 1, w - 2, h - 2, radius + 3, radius + 3);

		col1 = new Color(64, 64, 64, 220);
		col2 = new Color(32, 32, 32, 128);
		gradientFill = new GradientPaint(0, 0, col1, w, h, col2);
		g2d.setPaint(gradientFill);
		g2d.drawRoundRect(1, 1, w - 2, h - 2, radius + 3, radius + 3);

		// Reflektion
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f));
		col1 = new Color(255, 255, 255, 192);
		col2 = new Color(255, 255, 255, 16);
		gradientFill = new GradientPaint(0, 0, col2, 0, (int) Math.round(h * 0.66666), col1);
		g2d.setPaint(gradientFill);
		final Polygon p = new Polygon();
		p.addPoint(1, 1);
		p.addPoint(w - 1, 1);
		p.addPoint(w - 1, (int) Math.round(h * 0.33333));
		p.addPoint(1, (int) Math.round(h * 0.66666));
		g2d.fillPolygon(p);
		// Zurück auf normales paint
		g2d.setPaintMode();

		// innerer rand
		col1 = new Color(92, 92, 92, 255);
		col2 = new Color(32, 32, 32, 255);
		gradientFill = new GradientPaint(0, 0, col2, 0, h, col1);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(10, 10, w - 20, h - 20, radius, radius);
		// fläche
		col1 = new Color(40, 40, 40, 255);
		col2 = new Color(8, 8, 8, 255);
		gradientFill = new GradientPaint(0, 0, col1, 0, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(11, 11, w - 22, h - 22, radius, radius);

		g2d.dispose();
		return over;
	}

	protected static BufferedImage drawBackgroundFrame(final int w, final int h) {
		final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGraphics(over);
		// Zurück auf normales paint
		g2d.setPaintMode();
		// Background
		final int radius = 20;
		final int offsetOben = 40;

		g2d.setPaint(Color.gray);
		g2d.fillRoundRect(1, 1, w - 2, h - 2, radius + 6, radius + 6);

		Color col1 = new Color(32, 32, 32, 255);
		Color col2 = new Color(92, 92, 92, 255);
		GradientPaint gradientFill = new GradientPaint(0, h / 2, col1, 0, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(2, 2, w - 4, h - 4, radius + 6, radius + 6);

		col1 = new Color(64, 64, 64, 255);
		col2 = new Color(92, 92, 92, 128);
		gradientFill = new GradientPaint(0, 0, col1, 0, h / 2, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(8, 8, w - 16, h - 16, radius + 2, radius + 2);

		// Reflektion
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f));

		col1 = new Color(192, 192, 192, 255);
		col2 = new Color(32, 32, 32, 128);
		gradientFill = new GradientPaint(0, 0, col2, 0, 2 * offsetOben, col1);
		g2d.setPaint(gradientFill);
		g2d.fillArc(-50, -offsetOben, w + 100, 3 * offsetOben, 0, 360);
		g2d.setPaint(new Color(255, 255, 255, 30));
		g2d.drawArc(-50, -offsetOben, w + 100, 3 * offsetOben, 0, 360);
		// Zurück auf normales paint
		g2d.setPaintMode();

		// innerer rand
		col1 = new Color(64, 64, 64, 64);
		col2 = new Color(32, 32, 32, 255);
		gradientFill = new GradientPaint(0, 0, col2, 0, h, col1);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(10, 10, w - 20, h - 20, radius, radius);
		// fläche
		col1 = new Color(32, 32, 32, 255);
		col2 = new Color(16, 16, 16, 255);
		gradientFill = new GradientPaint(0, 0, col1, w / 2, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(11, 11, w - 22, h - 22, radius, radius);
		g2d.dispose();
		return over;
	}

	protected static BufferedImage drawBackgroundFrame2(final int w, final int h) {
		final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGraphics(over);
		// Zurück auf normales paint
		g2d.setPaintMode();
		// Background
		final int radius = 2;

		Color col1 = new Color(32, 32, 32, 255);
		Color col2 = new Color(92, 92, 92, 255);
		GradientPaint gradientFill = new GradientPaint(0, h / 2, col1, 0, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRect(1, 1, w - 2, h - 2);

		// fläche
		col1 = new Color(192, 192, 192, 128);
		col2 = new Color(32, 32, 32, 200);
		gradientFill = new GradientPaint(0, 0, col1, w / 2, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(10, 10, w - 20, h - 20, radius, radius);
		// innerer rand
		col1 = new Color(32, 32, 32, 255);
		col2 = new Color(16, 16, 16, 255);
		gradientFill = new GradientPaint(0, 0, col2, w, 0, col1);
		g2d.setPaint(gradientFill);
		g2d.drawRoundRect(11, 11, w - 22, h - 22, radius, radius);

		// Reflektion
		// g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
		// 1f));
		//
		// col1 = new Color(32, 32, 32, 200);
		// col2 = new Color(32, 32, 32, 0);
		// gradientFill = new GradientPaint(0, 0, col1, w, 0, col2);
		// g2d.setPaint(gradientFill);
		// g2d.fillArc(-10, -20, w * 2, h * 2, 0, 360);
		// Zurück auf normales paint
		g2d.setPaintMode();

		g2d.dispose();
		return over;
	}

	// #########################################################################
	// Backgrounds
	// #########################################################################

	protected static BufferedImage drawBackground01(final int w, final int h, final int offsetOben, final int offsetUnten) {
		final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGraphics(over);

		// Zurück auf normales paint
		g2d.setPaintMode();
		// Background
		g2d.setColor(new Color(128, 128, 128, 255));
		g2d.fillRoundRect(1, 1, w - 2, h - 2, 20, 20);
		g2d.setColor(new Color(32, 32, 32));
		g2d.fillRoundRect(3, 3, w - 6, h - 6, 20, 20);
		// Composite COlor setzen
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f));
		final Color col1 = new Color(255, 255, 255, 25);
		final Color col2 = new Color(255, 255, 255, 10);
		final GradientPaint gradientFill = new GradientPaint(-20, -30, col1, w + 40, -30, col2);
		g2d.setPaint(gradientFill);
		g2d.fillArc(-20, -offsetOben, w + 40, 2 * offsetOben, 0, 360);
		g2d.setPaint(new Color(255, 255, 255, 30));
		g2d.drawArc(-20, -offsetOben, w + 40, 2 * offsetOben, 0, 360);
		g2d.dispose();
		return over;
	}

	protected static BufferedImage drawBackgroundTV(final int w, final int h, final boolean smallframe) {
		final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGraphics(over);
		// Zurück auf normales paint
		g2d.setPaintMode();
		// Background
		final int radius = 8;

		Color col1 = new Color(92, 92, 92, 255);
		Color col2 = new Color(32, 32, 32, 255);
		GradientPaint gradientFill = new GradientPaint(0, 0, col1, 0, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(1, 1, w - 2, h - 2, radius + 3, radius + 3);

		col1 = new Color(64, 64, 64, 255);
		col2 = new Color(32, 32, 32, 255);
		gradientFill = new GradientPaint(0, 0, col1, w, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(3, 3, w - 6, h - 6, radius + 3, radius + 3);

		// Reflektion
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f));
		col1 = new Color(192, 192, 192, 192);
		col2 = new Color(32, 32, 32, 0);
		gradientFill = new GradientPaint(0, 0, col2, w / 2, 0, col1);
		g2d.setPaint(gradientFill);
		final Polygon p = new Polygon();
		p.addPoint(3, 3);
		p.addPoint(3, h - 3);
		p.addPoint((int) Math.round(w * 0.33333), h - 3);
		p.addPoint((int) Math.round(w * 0.66666), 3);
		g2d.fillPolygon(p);
		// Zurück auf normales paint
		g2d.setPaintMode();

		// innerer rand
		col1 = new Color(92, 92, 92, 255);
		col2 = new Color(32, 32, 32, 255);
		gradientFill = new GradientPaint(0, 0, col2, 0, h, col1);
		g2d.setPaint(gradientFill);
		if (smallframe) {
			g2d.fillRoundRect(10, 10, w - 20, h - 20, radius, radius);
		} else {
			g2d.fillRoundRect(10, 32, w - 20, h - 42, radius, radius);
		}
		// fläche
		col1 = new Color(32, 32, 32, 255);
		col2 = new Color(16, 16, 16, 255);
		gradientFill = new GradientPaint(0, 0, col1, w / 2, h, col2);
		g2d.setPaint(gradientFill);
		if (smallframe) {
			g2d.fillRoundRect(11, 11, w - 22, h - 22, radius, radius);
		} else {
			g2d.fillRoundRect(11, 33, w - 22, h - 44, radius, radius);
		}
		g2d.dispose();
		return over;
	}

	protected static BufferedImage drawBackgroundOldStyle(final int w, final int h, final int offsetOben, final int offsetUnten) {
		final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGraphics(over);
		// Zurück auf normales paint
		g2d.setPaintMode();

		// Background
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, w, h);

		// deviderlines
		g2d.setColor(Color.white);
		g2d.fillRect(0, offsetOben - 2, w, 2);
		g2d.fillRect(0, h - offsetUnten, w, 2);
		g2d.fillRect(0, h - 2, w, 2);
		g2d.dispose();
		return over;
	}

	protected static BufferedImage drawBackgroundBigTV(final int w, final int h, final int offsetOben) {
		final BufferedImage over = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = initGraphics(over);
		// Zurück auf normales paint
		g2d.setPaintMode();
		// Background
		final int radius = 12;

		Color col1 = new Color(92, 92, 92, 255);
		Color col2 = new Color(32, 32, 32, 255);
		GradientPaint gradientFill = new GradientPaint(0, 0, col1, 0, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(1, 1, w - 2, h - 2, radius + 3, radius + 3);

		col1 = new Color(64, 64, 64, 255);
		col2 = new Color(32, 32, 32, 255);
		gradientFill = new GradientPaint(0, 0, col1, w, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(3, 3, w - 6, h - 6, radius + 3, radius + 3);

		// Reflektion
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1f));
		col1 = new Color(192, 192, 192, 192);
		col2 = new Color(32, 32, 32, 0);
		gradientFill = new GradientPaint(0, 0, col2, w / 2, 0, col1);
		g2d.setPaint(gradientFill);
		final Polygon p = new Polygon();
		p.addPoint(3, 3);
		p.addPoint(3, h - 3);
		p.addPoint((int) Math.round(w * 0.33333), h - 3);
		p.addPoint((int) Math.round(w * 0.66666), 3);
		g2d.fillPolygon(p);
		// Zurück auf normales paint
		g2d.setPaintMode();

		// innerer rand
		col1 = new Color(92, 92, 92, 255);
		col2 = new Color(32, 32, 32, 255);
		gradientFill = new GradientPaint(0, 0, col2, 0, h, col1);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(15, offsetOben, w - 30, h - offsetOben - 15, radius, radius);
		// fläche
		col1 = new Color(32, 32, 32, 255);
		col2 = new Color(16, 16, 16, 255);
		gradientFill = new GradientPaint(0, 0, col1, w / 2, h, col2);
		g2d.setPaint(gradientFill);
		g2d.fillRoundRect(16, offsetOben + 1, w - 32, h - offsetOben - 17, radius, radius);

		g2d.dispose();
		return over;
	}

}

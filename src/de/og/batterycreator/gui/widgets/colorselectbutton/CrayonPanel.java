package de.og.batterycreator.gui.widgets.colorselectbutton;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import og.basics.util.StaticColorHelper;
import og.basics.util.StaticExecutor;
import de.og.batterycreator.main.IconCreatorFrame;

/* Used by ColorChooserDemo2.java. */
public class CrayonPanel extends AbstractColorChooserPanel implements ActionListener {
	private static final long		serialVersionUID	= 2389524499578003673L;
	private final List<JButton>		buttons				= new ArrayList<JButton>();
	private final JPanel			buttonPanel			= new JPanel(new GridLayout(0, 2));
	private static final ImageIcon	icon				= new ImageIcon(CrayonPanel.class.getResource("crayons2.png"));
	private final JLabel			logoLabel			= new JLabel();
	private final JLabel			urlLabel			= new JLabel();
	private final JLabel			titleLabel			= new JLabel();

	@Override
	public void updateChooser() {
		// final Color color = getColorFromModel();
		// System.out.println("updateModel " + color);
		// for (final JButton button : buttons) {
		// if (button.getBackground().equals(color))
		// button.setSelected(true);
		// }
	}

	protected JButton createCrayon(final String name, final Color col) {
		final JButton crayon = new JButton();
		crayon.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
		crayon.setActionCommand(name);
		crayon.addActionListener(this);
		crayon.setText(name);
		crayon.setHorizontalAlignment(JButton.HORIZONTAL);
		crayon.setBackground(col);
		// optimizing foregroundcolor for better readability
		if (StaticColorHelper.isdark(col))
			crayon.setForeground(Color.white);
		else
			crayon.setForeground(Color.black);

		buttons.add(crayon);
		buttonPanel.add(crayon);
		return crayon;
	}

	@Override
	protected void buildChooser() {
		setLayout(new BorderLayout());

		titleLabel.setText(getDisplayName());
		titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));
		titleLabel.setForeground(Color.blue.darker());

		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		logoLabel.setIcon(getIcon());
		logoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				StaticExecutor.openUrlInExternalBrowser(getUrl());
			}
		});
		logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		urlLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		urlLabel.setText(getUrl());
		urlLabel.setForeground(Color.blue.darker());
		urlLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(final MouseEvent e) {
				StaticExecutor.openUrlInExternalBrowser(getUrl());
			}
		});

		add(buttonPanel, BorderLayout.CENTER);
		add(titleLabel, BorderLayout.NORTH);
		add(logoLabel, BorderLayout.WEST);
		add(urlLabel, BorderLayout.SOUTH);

		addColors();
	}

	protected String getUrl() {
		return IconCreatorFrame.HOMEPAGE_URL;
	}

	protected void addColors() {
		createCrayon("yellow", Color.yellow);
		createCrayon("orange", Color.orange);
		createCrayon("red", Color.red);
		createCrayon("green", Color.green.darker());
		createCrayon("Samsung green", new Color(142, 205, 0));
		createCrayon("AOKP blue", new Color(39, 135, 173));
		createCrayon("AOKP blue (light)", new Color(51, 181, 229));
		createCrayon("Dark Gray", Color.darkGray);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		Color newColor = null;
		final JButton toggle = (JButton) e.getSource();
		newColor = toggle.getBackground();
		getColorSelectionModel().setSelectedColor(newColor);
	}

	@Override
	public String getDisplayName() {
		return "Rom Fumbler Colors";
	}

	@Override
	public Icon getSmallDisplayIcon() {
		return null;
	}

	@Override
	public Icon getLargeDisplayIcon() {
		return null;
	}

	protected ImageIcon getIcon() {
		return icon;
	}
}
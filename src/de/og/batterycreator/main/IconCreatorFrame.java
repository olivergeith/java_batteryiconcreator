package de.og.batterycreator.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import og.basics.gui.LToolBar;
import og.basics.gui.about.UniversalAboutDialog;
import og.basics.gui.about.VersionDetails;
import og.basics.gui.icon.CommonIconProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.og.batterycreator.gui.IconCreatingPanelNew;
import de.og.batterycreator.gui.iconstore.IconStore;

public class IconCreatorFrame extends JFrame {
	private static final Logger LOGGER = LoggerFactory.getLogger(IconCreatorFrame.class);

	private final JButton aboutButton = new JButton(CommonIconProvider.BUTTON_ICON_INFO);
	private final JButton exitButton = new JButton(CommonIconProvider.BUTTON_ICON_EXIT);
	private static final String APP_NAME = "'The Rom Fumbler'";
	public static final String VERSION_NR = "22.0";
	private static final String VERSION_DATE = "xx.yyy.2013";
	private static final long serialVersionUID = 1L;
	private static IconCreatorFrame frame;
	private final IconCreatingPanelNew iconCreatingPanel = new IconCreatingPanelNew(this);
	LToolBar toolBar = iconCreatingPanel.getToolBar();

	public static void main(final String[] args) {
		LOGGER.info("Starting " + APP_NAME + " " + VERSION_NR + "(" + VERSION_DATE + ")");
		frame = new IconCreatorFrame();

	}

	public IconCreatorFrame() {
		super();
		setTitle(APP_NAME + " ----- Version " + VERSION_NR + "   (" + VERSION_DATE + ")");
		setIconImage(IconStore.logoIcon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 100, 300, 1280);
		initUI();
		LOGGER.info("Showing up Frame!");
		setVisible(true);
		pack();
	}

	/**
	 * Gui Init
	 */
	private void initUI() {

		aboutButton.setToolTipText("About this supercool App :-)!");
		aboutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				showAbout();
			}
		});
		exitButton.setToolTipText("Exit this supercool App!");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				System.exit(0);
			}
		});

		getContentPane().setLayout(new BorderLayout());
		makeMenuAndButtonBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		getContentPane().add(iconCreatingPanel, BorderLayout.CENTER);
	}

	private void makeMenuAndButtonBar() {
		toolBar.add(exitButton, 0);
		toolBar.add(aboutButton, 1);
	}

	public void showAbout() {
		final VersionDetails details = new VersionDetails();
		details.setApplicationname(APP_NAME);
		details.setCompany("www.geith-online.de");
		details.setVersion(VERSION_NR);
		details.setDate(VERSION_DATE);
		details.setLogo(IconStore.logoIcon);
		details.setCopyright("by Oliver Geith");
		details.setDescription(getDescription());
		final UniversalAboutDialog aboutDialog = new UniversalAboutDialog(frame, details);
		aboutDialog.setVisible(true);
	}

	public String getDescription() {
		String str = "This application can... ";
		str += "...create icons for batteries...<br>";
		str += "...modify the wifi and signal icons <br>";
		str += "...modify toggle icons <br>";
		str += "...modify notification background <br>";
		str += "...create flashable zips!<br>";
		str += "...and much more advanced stuff...Check it out!!<br>";
		return str;
	}

}

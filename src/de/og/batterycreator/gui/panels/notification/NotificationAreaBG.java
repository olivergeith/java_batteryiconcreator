package de.og.batterycreator.gui.panels.notification;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import og.basics.gui.image.StaticImageHelper;
import og.basics.jgoodies.JGoodiesHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import de.og.batterycreator.cfg.RomSettings;
import de.og.batterycreator.creators.IconProviderInterface;
import de.og.batterycreator.gui.widgets.SliderAndLabel;

public class NotificationAreaBG extends JPanel implements IconProviderInterface {
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationAreaBG.class);

	private static final String PROVIDER_NAME = "Transparent NotificationArea";
	private static final long serialVersionUID = 7145187966709405687L;
	private static final String CUSTOM_OUT_DIR = "./pngs/notificationbg/";

	private static final ImageIcon background = new ImageIcon(NotificationAreaBG.class.getResource("background2.png"));
	private final Vector<String> filenamesAndPath = new Vector<String>();
	private final JCheckBox activBox = new JCheckBox("Include transparent Notification Area");
	private final SliderAndLabel transparencySlider = new SliderAndLabel(0, 100);
	private final JLabel overview = new JLabel(background);
	private final JLabel hint = new JLabel(createLableHtml());

	private ImageIcon notificationBG;

	public NotificationAreaBG() {
		initUI();

	}

	private String createLableHtml() {
		String html = "<html>";

		html += "<font size=5 color=blue>";
		html += "<b>" + "Attention !!" + "</b><br><hr>";
		html += "</font>";

		html += "<font size=3 color=black>";
		html += "There seeems to be some other half transparent png, that is <br>";
		html += "above or on top the background png! <br>";
		html += "So the background is not as transparent as you see it here. <br> <br>";
		html += "A transparency of 80 would result in an almost black notification area! <br>";
		html += "A transparency of 50 is still pretty dark!<br>";
		html += "A transparency of 0 will never be completely transparent! <br> <br>";
		html += "(Speaking for Resurrection Remix ROM...<br>";
		html += "...don't know about other ROM's)<br>";
		html += "<hr>";
		html += "</font>";

		html += "</html>";
		return html;
	}

	private void initUI() {
		setLayout(new BorderLayout());
		activBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				transparencySlider.setEnabled(activBox.isSelected());
				overview.setVisible(activBox.isSelected());
				hint.setVisible(activBox.isSelected());
				updateGui();
				if (!activBox.isSelected()) {
					filenamesAndPath.removeAllElements();
				}
			}
		});
		activBox.setSelected(false);
		transparencySlider.setEnabled(false);
		transparencySlider.setValue(75);
		overview.setVisible(false);
		hint.setVisible(false);
		transparencySlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent e) {
				updateGui();
			}

		});
		final JPanel gui = createSettingsPanel();
		add(gui, BorderLayout.CENTER);

	}

	public JPanel createSettingsPanel() {
		// -----------------------------------------1-----2------3-----4------5-----6------7-----8-----9------10----11
		final FormLayout layout = new FormLayout("2dlu, 64dlu, 2dlu, 16dlu, 2dlu, 32dlu, 2dlu,256dlu, 2dlu",
				"p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p,p");
		final CellConstraints cc = new CellConstraints();
		final PanelBuilder builder = new PanelBuilder(layout);
		int row = 1;

		builder.add(activBox, cc.xyw(2, ++row, 7));
		builder.add(JGoodiesHelper.createBlackLabel("Transparency"), cc.xyw(2, ++row, 3));
		builder.add(transparencySlider, cc.xyw(2, ++row, 1));
		builder.add(transparencySlider.getValueLabel(), cc.xyw(4, row, 1));
		builder.add(JGoodiesHelper.createBlackLabel("Preview"), cc.xyw(2, ++row, 3));
		builder.addSeparator("", cc.xyw(2, ++row, 7));
		builder.add(overview, cc.xyw(2, ++row, 5));
		builder.add(hint, cc.xyw(8, row, 1));

		final JPanel cfp = builder.getPanel();
		return cfp;
	}

	public void updateGui() {
		// createAllImages();
		createPreview();
	}

	@Override
	public void createAllImages(final RomSettings romSettings) {
		// Create a graphics contents on the buffered image
		final int h = romSettings.getNotificationHeight();
		final int w = h * 10;
		final BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = img.createGraphics();
		final File outf = new File(CUSTOM_OUT_DIR + romSettings.getNotificationBGFilename());
		final File dir = new File(CUSTOM_OUT_DIR);

		final Color col = new Color(0f, 0f, 0f, transparencySlider.getValue() / 100f);
		g2d.setColor(col);

		g2d.fillRect(0, 0, img.getWidth(), img.getHeight());

		// Filewriting
		if (!dir.exists())
			dir.mkdirs();

		StaticImageHelper.writePNG(img, outf);
		filenamesAndPath.removeAllElements();
		// Damit es nbei einem create() von aussen nicht zum füllen des vectors
		// kommt...
		if (activBox.isSelected()) {
			filenamesAndPath.addElement(outf.getPath());
			LOGGER.info("Creating notification" + outf.getPath());
		}
		notificationBG = new ImageIcon(img);
	}

	protected void createPreview() {

		final int w = background.getIconWidth();
		final int h = background.getIconHeight();
		final BufferedImage hintergrund = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g2d = hintergrund.createGraphics();
		g2d.drawImage(background.getImage(), 0, 0, null);

		final Color col = new Color(0f, 0f, 0f, transparencySlider.getValue() / 100f);
		g2d.setColor(col);

		g2d.fillRect(0, 0, w, h);
		overview.setIcon(new ImageIcon(hintergrund));

	}

	@Override
	public String getProviderName() {
		return PROVIDER_NAME;
	}

	@Override
	public String toString() {
		return PROVIDER_NAME;
	}

	@Override
	public Vector<String> getAllFilenamesAndPath() {
		if (activBox.isSelected()) {
			return filenamesAndPath;
		} else
			return new Vector<String>();
	}

	/**
	 * For testing purposes !!!
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {

		final JFrame f = new JFrame();
		f.setTitle("Hallo Emmy!!!!!!!");
		f.setBounds(200, 200, 300, 80);
		f.setLayout(new BorderLayout());

		final NotificationAreaBG bg = new NotificationAreaBG();
		f.add(bg, BorderLayout.CENTER);

		f.setVisible(true);
		f.pack();
	}

	/**
	 * @return the bg
	 */
	public ImageIcon getBg() {
		return notificationBG;
	}

	/**
	 * @param bg
	 *            the bg to set
	 */
	public void setBg(final ImageIcon bg) {
		notificationBG = bg;
	}

	@Override
	public boolean isActiv() {
		return activBox.isSelected();
	}

}

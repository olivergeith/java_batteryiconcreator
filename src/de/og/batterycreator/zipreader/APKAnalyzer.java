package de.og.batterycreator.zipreader;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import og.basics.gui.file.FileDialogs;
import og.basics.gui.tracepanel.TracePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APKAnalyzer extends JDialog {
	private static final long	serialVersionUID	= -1605180725450582074L;
	private File				zipFile				= new File("SystemUI.apk");
	// private final TracePanel tracer = new TracePanel(new
	// DefaultTextFileSaveHandler("./logs", "Analyse.log", ".log",
	// "Analyser Logfile"));
	private final TracePanel	tracer				= new TracePanel();
	private final JButton		choose				= new JButton("Choose SystemUI.apk");

	/**
	 * the Logger for this Class
	 */
	private static final Logger	LOG					= LoggerFactory.getLogger(APKAnalyzer.class);

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final APKAnalyzer a = new APKAnalyzer(null, "RomPreset creator (by analysing SystemUI.apk)");
		a.setVisible(true);
		a.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

	public APKAnalyzer(final Window arg0, final String arg1) {
		super(arg0, arg1, ModalityType.APPLICATION_MODAL);
		guiInit();
	}

	private void guiInit() {
		setPreferredSize(new Dimension(600, 400));
		setMinimumSize(new Dimension(600, 400));
		choose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent arg0) {
				try {
					analyse();
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});

		setLayout(new BorderLayout());
		this.add(choose, BorderLayout.NORTH);
		this.add(tracer, BorderLayout.CENTER);
	}

	public File chooseZip() {
		final File choosenFile = FileDialogs.chooseFileFree(zipFile, ".apk", "apk-File");
		if (choosenFile != null) {
			zipFile = choosenFile;
			return zipFile;
		}
		return null;
	}

	public void analyse() throws Exception {
		final File zipFile = chooseZip();
		traceinfo("Analysing: " + zipFile);
		if (zipFile != null) {
			traceinfo("Analysing: " + zipFile.getName());
			final File extractDir = new File("./analyzer/" + zipFile.getName());
			// Entpacken
			traceinfo("Extracting to: " + extractDir.getPath());
			ZipArchiveExtractor.extractArchive(zipFile, extractDir);

			// Batterien suchen

			// Wifi icons suchen

			// signal Icons suchen

			// aufräumen
			traceinfo("Cleaning up: " + extractDir.getPath());
			ZipArchiveExtractor.deleteDirRecurse(extractDir);
		}
	}

	/**
	 * @return the zipFile
	 */
	public File getZipFile() {
		return zipFile;
	}

	/**
	 * @param zipFile
	 *            the zipFile to set
	 */
	public void setZipFile(final File zipFile) {
		this.zipFile = zipFile;
	}

	private void traceinfo(final String text) {
		tracer.appendInfoText(text);
		LOG.info(text);
	}

	private void tracesuccess(final String text) {
		tracer.appendSuccessText(text);
		LOG.info(text);
	}

	private void traceerror(final String text) {
		tracer.appendErrorText(text);
		LOG.error(text);
	}
}

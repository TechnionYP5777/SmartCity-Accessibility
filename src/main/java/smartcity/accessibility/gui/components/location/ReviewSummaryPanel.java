package smartcity.accessibility.gui.components.location;

import javax.swing.JPanel;

import smartcity.accessibility.gui.components.JMultilineLabel;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;

import javax.swing.JButton;
import javax.swing.JProgressBar;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ReviewSummaryPanel extends JPanel {

	private static int MAX_CHARS = 100;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3831232049823976748L;

	/**
	 * Create the panel.
	 */
	public ReviewSummaryPanel(Review r) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 233, 139, 0 };
		gridBagLayout.rowHeights = new int[] { 32, 23, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		JMultilineLabel lblTest = new JMultilineLabel(getSummary(r.getContent()));
		GridBagConstraints gbc_lblTest = new GridBagConstraints();
		gbc_lblTest.fill = GridBagConstraints.BOTH;
		gbc_lblTest.insets = new Insets(0, 0, 0, 5);
		gbc_lblTest.gridheight = 2;
		gbc_lblTest.gridy = gbc_lblTest.gridx = 0;
		add(lblTest, gbc_lblTest);
		lblTest.setVisible(true);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setMinimum(Score.getMinScore());
		progressBar.setMaximum(Score.getMaxScore());
		progressBar.setValue(r.getRating().getScore());
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.insets = new Insets(0, 0, 5, 0);
		gbc_progressBar.gridx = 1;
		gbc_progressBar.gridy = 0;
		add(progressBar, gbc_progressBar);
		progressBar.setVisible(true);

		JButton btnSeeFullReview = new JButton("See Full Review");
		GridBagConstraints gbc_btnSeeFullReview = new GridBagConstraints();
		gbc_btnSeeFullReview.anchor = GridBagConstraints.NORTH;
		gbc_btnSeeFullReview.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSeeFullReview.gridy = gbc_btnSeeFullReview.gridx = 1;
		add(btnSeeFullReview, gbc_btnSeeFullReview);
		btnSeeFullReview.setVisible(true);

	}

	private static String getSummary(String text) {
		text = text.replaceAll("\r\n|\r|\n", " ");
		if (text.length() > MAX_CHARS)
			text = text.substring(0, MAX_CHARS) + "...";
		return text;
	}
}

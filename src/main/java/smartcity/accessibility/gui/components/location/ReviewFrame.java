package smartcity.accessibility.gui.components.location;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import smartcity.accessibility.database.ReviewManager;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.gui.Application;
import smartcity.accessibility.gui.components.JMultilineLabel;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;

public class ReviewFrame implements MouseListener {

	private JFrame frame;
	private JButton btnDownvote;
	private JButton btnUpvote;
	private Review review;
	private JLabel lblUpvoteCount;
	private JLabel lblDownvoteCount;

	/**
	 * Create the application.
	 */
	public ReviewFrame(Review r) {
		review = r;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				ReviewManager.updateReview(review);
			}

			@Override
			public void windowClosing(WindowEvent arg0) {

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {

			}

			@Override
			public void windowIconified(WindowEvent arg0) {

			}

			@Override
			public void windowOpened(WindowEvent arg0) {

			}

		});

		JLabel lblReview = new JLabel("Review", SwingConstants.CENTER);
		lblReview.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblReview.setBounds(133, 11, 146, 31);
		frame.getContentPane().add(lblReview);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(133, 42, 146, 14);
		progressBar.setMinimum(Score.getMinScore());
		progressBar.setMaximum(Score.getMaxScore());
		progressBar.setValue(review.getRating().getScore());
		frame.getContentPane().add(progressBar);

		JMultilineLabel txtReview = new JMultilineLabel(review.getContent());
		txtReview.setBackground(new Color(255, 255, 255));

		JScrollPane scrollPane = new JScrollPane(txtReview);
		scrollPane.setBounds(10, 67, 414, 250);
		scrollPane.setVisible(true);
		frame.getContentPane().add(scrollPane);

		JLabel lblUpvotes = new JLabel("Upvotes", SwingConstants.CENTER);
		lblUpvotes.setBounds(10, 328, 89, 25);
		frame.getContentPane().add(lblUpvotes);

		JLabel lblDownvotes = new JLabel("Downvotes", SwingConstants.CENTER);
		lblDownvotes.setBounds(335, 328, 89, 25);
		frame.getContentPane().add(lblDownvotes);

		lblUpvoteCount = new JLabel(Integer.toString(review.getUpvotes()), SwingConstants.CENTER);
		lblUpvoteCount.setForeground(Color.GREEN);
		lblUpvoteCount.setBounds(10, 364, 89, 14);
		frame.getContentPane().add(lblUpvoteCount);

		lblDownvoteCount = new JLabel(Integer.toString(review.getDownvotes()), SwingConstants.CENTER);
		lblDownvoteCount.setForeground(Color.RED);
		lblDownvoteCount.setBounds(335, 364, 89, 14);
		frame.getContentPane().add(lblDownvoteCount);

		btnUpvote = new JButton("Upvote");
		btnUpvote.setBackground(Color.GREEN);
		btnUpvote.setBounds(10, 411, 89, 23);
		btnUpvote.addMouseListener(this);
		frame.getContentPane().add(btnUpvote);

		btnDownvote = new JButton("Downvote");
		btnDownvote.setBackground(Color.RED);
		btnDownvote.setBounds(335, 411, 89, 23);
		btnDownvote.addMouseListener(this);
		frame.getContentPane().add(btnDownvote);

		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() == btnUpvote)
			try {
				review.upvote(Application.appUser);
			} catch (UnauthorizedAccessException e1) {
				e1.printStackTrace();
			}
		if (arg0.getSource() == btnDownvote)
			try {
				review.downvote(Application.appUser);
			} catch (UnauthorizedAccessException e) {
				e.printStackTrace();
			}
		lblDownvoteCount.setText(Integer.toString(review.getDownvotes()));
		lblUpvoteCount.setText(Integer.toString(review.getUpvotes()));
		frame.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent __) {

	}

	@Override
	public void mouseExited(MouseEvent __) {

	}

	@Override
	public void mousePressed(MouseEvent __) {

	}

	@Override
	public void mouseReleased(MouseEvent __) {

	}
}

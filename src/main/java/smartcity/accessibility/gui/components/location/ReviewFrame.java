package smartcity.accessibility.gui.components.location;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import smartcity.accessibility.gui.Application;
import smartcity.accessibility.gui.components.JMultilineLabel;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;

public class ReviewFrame implements MouseListener {

	private JFrame frame;
	private JButton btnDownvote;
	private JButton btnUpvote;
	private Review review;

	/**
	 * Create the application.
	 */
	public ReviewFrame(Review r) {
		review = r;
		initialize(r);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Review r) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblReview = new JLabel("Review", SwingConstants.CENTER);
		lblReview.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblReview.setBounds(133, 11, 146, 31);
		frame.getContentPane().add(lblReview);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(133, 42, 146, 14);
		progressBar.setMinimum(Score.getMinScore());
		progressBar.setMaximum(Score.getMaxScore());
		progressBar.setValue(r.getRating().getScore());
		frame.getContentPane().add(progressBar);

		JMultilineLabel txtReview = new JMultilineLabel(r.getContent());
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

		JLabel lblUpvoteCount = new JLabel(Integer.toString(r.getUpvotes()), SwingConstants.CENTER);
		lblUpvoteCount.setForeground(Color.GREEN);
		lblUpvoteCount.setBounds(10, 364, 89, 14);
		frame.getContentPane().add(lblUpvoteCount);

		JLabel lblDownvoteCount = new JLabel(Integer.toString(r.getDownvotes()), SwingConstants.CENTER);
		lblDownvoteCount.setForeground(Color.RED);
		lblDownvoteCount.setBounds(335, 364, 89, 14);
		frame.getContentPane().add(lblDownvoteCount);

		btnUpvote = new JButton("Upvote");
		btnUpvote.setBackground(Color.GREEN);
		btnUpvote.setBounds(10, 411, 89, 23);
		frame.getContentPane().add(btnUpvote);

		btnDownvote = new JButton("Downvote");
		btnDownvote.setBackground(Color.RED);
		btnDownvote.setBounds(335, 411, 89, 23);
		frame.getContentPane().add(btnDownvote);
		
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource() == btnUpvote)
			review.upvote(Application.appUser);
		if(arg0.getSource() == btnDownvote)
			review.downvote(Application.appUser);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}

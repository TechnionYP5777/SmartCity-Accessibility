package smartcity.accessibility.gui.components.location;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import smartcity.accessibility.database.AbstractReviewManager;
import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.gui.Application;
import smartcity.accessibility.gui.components.JMultilineLabel;
import smartcity.accessibility.gui.components.RatingStar;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;
import smartcity.accessibility.socialnetwork.User.Privilege;

public class ReviewFrame implements MouseListener, ChangeListener {

	private JFrame frame;
	private JButton btnDownvote;
	private JButton btnUpvote;
	private Review review;
	private Location location;
	private JLabel lblUpvoteCount;
	private JLabel lblDownvoteCount;
	private JButton btnDelete;
	private JCheckBox chckbxPinned;

	public ReviewFrame(Review r, Location loc) {
		review = r;
		location = loc;
		initialize();
	}

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
				AbstractReviewManager.instance().updateReview(review, null);
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

		int numOfScores = Math.abs(Score.getMaxScore());
		RatingStar rs = new RatingStar(numOfScores);
		rs.setBounds(133, 42, 146, 30);
		rs.setRate(review.getRating().getScore());
		rs.setClickable(false);
		frame.getContentPane().add(rs);

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

		if (Privilege.commentReviewPrivilegeLevel(Application.appUser)) {
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

		}

		if (Application.appUser.getUsername().equals(review.getUser())
				|| Privilege.deletePrivilegeLevel(Application.appUser)) {
			btnDelete = new JButton("Delete");
			btnDelete.setBounds(335, 17, 89, 23);
			btnDelete.addMouseListener(this);
			frame.getContentPane().add(btnDelete);

		}

		chckbxPinned = new JCheckBox("Pinned");
		chckbxPinned.setBounds(335, 42, 89, 23);
		chckbxPinned.setEnabled(Privilege.pinPrivilegeLevel(Application.appUser));
		chckbxPinned.addChangeListener(this);
		frame.getContentPane().add(chckbxPinned);

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
			} catch (UnauthorizedAccessException ¢) {
				¢.printStackTrace();
			}
		lblDownvoteCount.setText(Integer.toString(review.getDownvotes()));
		lblUpvoteCount.setText(Integer.toString(review.getUpvotes()));
		if (arg0.getSource() == btnDelete) {

			if (Application.appUser.canDeleteReview(review)) {
				AbstractReviewManager.instance().deleteReview(review, b -> { });
				location.deleteReview(review);
			}
			frame.dispose();
		}
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

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (arg0.getSource() == chckbxPinned)
			if (Application.appUser.canPinReview())
				review.setPinned(chckbxPinned.isSelected());


	}
}

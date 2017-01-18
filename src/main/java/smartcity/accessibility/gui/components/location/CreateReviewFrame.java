package smartcity.accessibility.gui.components.location;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import org.parse4j.ParseException;

import smartcity.accessibility.database.ReviewManager;
import smartcity.accessibility.gui.Application;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;

public class CreateReviewFrame implements MouseListener {

	private JFrame frame;
	private JButton btnCreate;
	private Location location;
	private JSlider slider;
	private JTextArea textArea;

	/**
	 * Create the application.
	 */
	public CreateReviewFrame(Location l) {
		location = l;
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

		JLabel lblSignupForm = new JLabel("Review Form");
		lblSignupForm.setFont(new Font("Dialog", Font.BOLD, 15));
		lblSignupForm.setBounds(151, 25, 166, 15);
		frame.getContentPane().add(lblSignupForm);

		JLabel lblUsername = new JLabel("Rating: ");
		lblUsername.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblUsername.setBounds(12, 61, 101, 15);
		frame.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Content:");
		lblPassword.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblPassword.setBounds(12, 121, 101, 15);
		frame.getContentPane().add(lblPassword);

		slider = new JSlider();
		slider.setMaximum(Score.getMaxScore());
		slider.setMinimum(Score.getMinScore());
		slider.setBounds(123, 50, 282, 26);
		frame.getContentPane().add(slider);

		textArea = new JTextArea();
		textArea.setFont(new Font("Courier New", Font.PLAIN, 11));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(123, 123, 282, 270);
		scrollPane.setVisible(true);
		frame.getContentPane().add(scrollPane);

		btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnCreate.setBounds(153, 425, 117, 25);
		btnCreate.addMouseListener(this);
		frame.getContentPane().add(btnCreate);

		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() != btnCreate)
			return;
		try {
			location.addReview((new Review(location, slider.getValue(), textArea.getText(), Application.appUser)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		frame.dispose();

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
}

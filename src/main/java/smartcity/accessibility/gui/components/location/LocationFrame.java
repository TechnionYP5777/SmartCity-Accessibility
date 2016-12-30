package smartcity.accessibility.gui.components.location;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.mapmanagement.Coordinates;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class LocationFrame {

	private JFrame frame;
	private Location loc;
	private JButton btnAddReview;

	public static void main(String[] args) throws InterruptedException {
		new LocationFrame(new LatLng());

	}

	/**
	 * Create the application.
	 */
	public LocationFrame(LatLng loc) {
		this.loc = LocationManager.getLocation(loc);
		System.out.println(this.loc);
		// this.loc.getReviews().get(0).
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblLocation = new JLabel("Location", SwingConstants.CENTER);
		lblLocation.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLocation.setBounds(156, 11, 112, 28);
		frame.getContentPane().add(lblLocation);

		JPanel jp = new JPanel();
		// jp.setSize(400,350);
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		for (int i = 0; i < 100; ++i) {
			ReviewSummaryPanel rsp = new ReviewSummaryPanel(
					new Review(new Coordinates(new LatLng()), Score.getMaxScore() - 1,
							"this is a veryasdasdasdasdaaszdfasdfasdfasdfasdfasdfasdfasdf \n very long \n content \n omg \n this is it\na\nb\nc\na\na\na\na\na\na\na\na\na\na\na\na\na\na\na\na",
							new UserImpl("a", "b", User.Privilege.Admin)));
			rsp.setVisible(true);
			jp.add(rsp);
			jp.add(new JSeparator(SwingConstants.HORIZONTAL));
		}

		JScrollPane scrollPane = new JScrollPane(jp);

		scrollPane.setBounds(25, 50, 400, 350);
		scrollPane.setVisible(true);
		frame.getContentPane().add(scrollPane);

		btnAddReview = new JButton("Add Review");
		btnAddReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAddReview.setBounds(156, 427, 112, 23);
		frame.getContentPane().add(btnAddReview);
		frame.setVisible(true);
	}
}

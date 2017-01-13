package smartcity.accessibility.gui.components.location;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.stream.IntStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.gui.Application;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.navigation.JxMapsConvertor;
import smartcity.accessibility.navigation.Navigation;
import smartcity.accessibility.navigation.exception.CommunicationFailed;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;
import smartcity.accessibility.socialnetwork.User;

public class LocationFrame implements MouseListener {

	private JFrame frame;
	private Location loc;
	private JButton btnAddReview;
	private JButton btnNavigate;
	private JButton btnRefresh;

	/**
	 * Create the application.
	 */
	public LocationFrame(Location loc) {
		this.loc = loc;
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
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				//alex according to git if the location isn't in the db i should save it so there is throwned exception --Assaf
				LocationManager.updateLocation(loc);

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

		JLabel lblLocation = new JLabel("Location", SwingConstants.CENTER);
		lblLocation.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLocation.setBounds(156, 11, 112, 28);
		frame.getContentPane().add(lblLocation);

		JPanel jp = new JPanel();
		// jp.setSize(400,350);
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		for (Review r : loc.getPinnedReviews()) {
			ReviewSummaryPanel rsp = new ReviewSummaryPanel(r, loc);
			rsp.setVisible(true);
			jp.add(rsp);
			jp.add(new JSeparator(SwingConstants.HORIZONTAL));
		}
		for (Review r : loc.getReviews()) {
			ReviewSummaryPanel rsp = new ReviewSummaryPanel(r, loc);
			rsp.setVisible(true);
			jp.add(rsp);
			jp.add(new JSeparator(SwingConstants.HORIZONTAL));
		}

		JScrollPane scrollPane = new JScrollPane(jp);

		scrollPane.setBounds(25, 50, 400, 350);
		scrollPane.setVisible(true);
		frame.getContentPane().add(scrollPane);

		if (User.Privilege.addReviewPrivilegeLevel(Application.appUser)) {
			btnAddReview = new JButton("Add Review");
			btnAddReview.addMouseListener(this);
			btnAddReview.setBounds(25, 437, 112, 23);
			frame.getContentPane().add(btnAddReview);

		}

		btnNavigate = new JButton("Navigate");
		btnNavigate.setBounds(336, 437, 89, 23);
		btnNavigate.addMouseListener(this);
		frame.getContentPane().add(btnNavigate);

		btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(179, 437, 89, 23);
		btnRefresh.addMouseListener(this);
		frame.getContentPane().add(btnRefresh);
		frame.setVisible(true);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == btnAddReview)
			new CreateReviewFrame(loc);
		if (e.getSource() == btnNavigate)
			activateNavigation();
		if (e.getSource() != btnRefresh)
			return;
		frame.dispose();
		new LocationFrame(loc);

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
	
	/**
	 * @author yael
	 */
	public void activateNavigation(){
		Location src = new Location(Application.currLocation.getPosition());
		Location dst = loc;
		Object[] range = IntStream.rangeClosed(Score.getMinScore(), Score.getMaxScore()).mapToObj(n -> Integer.valueOf(n)).toArray();
		Integer accessibilityThreshold = (Integer)JOptionPane.showInputDialog(frame,
                "Insert the accessibility threshold for the navigation:\n",
                "choose accessibilityThreshold",
                JOptionPane.PLAIN_MESSAGE, null, range, 5);
		(new Thread() {
			@Override
			public void run() {
				try {
					LatLng[] shapePoints = Navigation.showRoute(src, dst, accessibilityThreshold);
					JxMapsConvertor.displayRoute(Application.mapView, shapePoints);
					if(!src.getCoordinates().equals(shapePoints[0]))
						JxMapsConvertor.addStartLine(Application.mapView, src.getCoordinates(), shapePoints[0]);
					if(!dst.getCoordinates().equals(shapePoints[shapePoints.length-1]))
						JxMapsConvertor.addEndLine(Application.mapView, dst.getCoordinates(), shapePoints[shapePoints.length-1]);
				} catch (CommunicationFailed e) {
					JOptionPane.showMessageDialog(frame,  "Navigation failed to conncet to servers. \n please check your internet connection and try again.",
						    "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}).start();
	}
}

package smartcity.accessibility.gui.components.location;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.gui.Application;
import smartcity.accessibility.mapmanagement.Coordinates;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

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

		JLabel lblLocation = new JLabel("Location", SwingConstants.CENTER);
		lblLocation.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLocation.setBounds(156, 11, 112, 28);
		frame.getContentPane().add(lblLocation);

		JPanel jp = new JPanel();
		// jp.setSize(400,350);
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		for(Review r: loc.getPinnedReviews()){
			ReviewSummaryPanel rsp = new ReviewSummaryPanel(r);
			rsp.setVisible(true);
			jp.add(rsp);
			jp.add(new JSeparator(SwingConstants.HORIZONTAL));
		}
		for(Review r: loc.getReviews()){
			ReviewSummaryPanel rsp = new ReviewSummaryPanel(r);
			rsp.setVisible(true);
			jp.add(rsp);
			jp.add(new JSeparator(SwingConstants.HORIZONTAL));
		}
		
		for (int i = 0; i < 10; ++i) {
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

		if(Application.appUser.getPrivilege()==User.Privilege.RegularUser){
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
		if(e.getSource()==btnAddReview)
			new CreateReviewFrame(loc);
		if(e.getSource()==btnNavigate)
			System.out.println("Navigate");
		if(e.getSource()==btnRefresh){
			frame.dispose();
			new LocationFrame(loc);
		}
			
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

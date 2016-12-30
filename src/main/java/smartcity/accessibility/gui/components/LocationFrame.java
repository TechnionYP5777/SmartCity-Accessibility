package smartcity.accessibility.gui.components;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.mapmanagement.Location;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class LocationFrame {

	private JFrame frame;
	private Location loc;
	
	public static void main(String[] args) throws InterruptedException{
		LocationFrame f =new LocationFrame(new LatLng());
		//f.wait();
	}

	/**
	 * Create the application.
	 */
	public LocationFrame(LatLng loc) {
		this.loc = LocationManager.getLocation(loc);
		//this.loc.getReviews().get(0).
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setBounds(182, 28, 70, 15);
		frame.getContentPane().add(lblLocation);
		
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(0,1));
		for(int i=0;i<100;i++){
			JButton temp = new JButton("a");
			temp.setVisible(true);
			
			jp.add(temp);
		}
		
		JScrollPane scrollPane = new JScrollPane(jp);
		
		scrollPane.setBounds(12, 50, 411, 167);
		scrollPane.setVisible(true);
		frame.getContentPane().add(scrollPane);
		
		frame.setVisible(true);
	}
}

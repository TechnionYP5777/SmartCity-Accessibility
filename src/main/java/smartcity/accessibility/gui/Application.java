package smartcity.accessibility.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.teamdev.jxmaps.MapMouseEvent;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.MouseEvent;

import smartcity.accessibility.database.DatabaseManager;
import smartcity.accessibility.database.UserManager;
import smartcity.accessibility.exceptions.UserNotFoundException;
import smartcity.accessibility.gui.components.ButtonsPanel;
import smartcity.accessibility.gui.components.MapFrame;
import smartcity.accessibility.gui.components.location.LocationFrame;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;

public class Application {

	public static MapFrame frame;
	public static ExtendedMapView mapView;

	public static final int FRAME_X_SIZE = 1000;
	public static final int FRAME_Y_SIZE = 700;

	public static User appUser = new UserImpl("", "", User.Privilege.DefaultUser);
	public static Marker currLocation;

	public static void main(String[] args) {
		frame = new MapFrame("SmartCity - Accessibility");

		DatabaseManager.initialize();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}

		//new MapViewOptions(MapComponentType.HEAVYWEIGHT).importPlaces();
		mapView = JxMapsFunctionality.getMapView();

		mapView.setSize(FRAME_X_SIZE, FRAME_Y_SIZE - 100);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(mapView, BorderLayout.CENTER);

		JPanel panel2 = new ButtonsPanel();

		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = gbc.gridx = 0;
		gbc.weightx = 0.0;
		gbc.ipady = FRAME_Y_SIZE - 100;
		gbc.ipadx = FRAME_X_SIZE;
		frame.getContentPane().add(panel, gbc);
		frame.setLocationRelativeTo(null);

		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.0;
		frame.getContentPane().add(panel2, gbc);

		frame.pack();
		frame.setLocation(100, 100);
		frame.setVisible(true);

		mapView.waitReady();

		/*
		 * Kolikant
		 */
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent __) {

			}

			@Override
			public void windowIconified(WindowEvent __) {

			}

			@Override
			public void windowDeiconified(WindowEvent __) {

			}

			@Override
			public void windowDeactivated(WindowEvent __) {

			}

			@Override
			public void windowClosing(WindowEvent __) {
				try {
					UserManager.updateAllUserInformation(appUser);
				} catch (UserNotFoundException e1) {
					/*
					 * user was not default and failed to save it's information.
					 * what do we want to do?
					 */
				}
			}

			@Override
			public void windowClosed(WindowEvent __) {

			}

			@Override
			public void windowActivated(WindowEvent __) {

			}
		});

		mapView.getMap().addEventListener("click", new MapMouseEvent() {

			@Override
			public void onEvent(MouseEvent arg0) {
				new LocationFrame(new Location(arg0.latLng()));
			}
		});

		mapView.getMap().addEventListener("rightclick", new MapMouseEvent() {
			@Override
			public void onEvent(MouseEvent arg0) {
				JxMapsFunctionality.onRightClick(arg0.latLng());
			}

		});
		JxMapsFunctionality.addOptionsMenu(JxMapsFunctionality.createOptionsBar());

		JxMapsFunctionality.initMapLocation("Eliezer 10, Haifa, Israel");
	}

}

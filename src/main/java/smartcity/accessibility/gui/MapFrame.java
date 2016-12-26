package smartcity.accessibility.gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.teamdev.jxmaps.MapComponentType;
import com.teamdev.jxmaps.MapViewOptions;

import smartcity.accessibility.database.DatabaseManager;

public class MapFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6388654596262185662L;

	public static MapFrame frame;
	public static GMap mapView;



	public static final int FRAME_X_SIZE = 1000;
	public static final int FRAME_Y_SIZE = 700;

	public MapFrame(String string) {
		super(string);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// setSize(FRAME_X_SIZE, FRAME_Y_SIZE);
		setLayout(new GridBagLayout());
		setVisible(true);
		setResizable(false);

	}

	public static void main(String[] args) {
		frame = new MapFrame("JxMaps - Hello, World!");

		DatabaseManager.initialize();

		MapViewOptions options = new MapViewOptions(MapComponentType.HEAVYWEIGHT);
		options.importPlaces();
		mapView = new GMap(options);
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
		frame.setVisible(true);

	}

	
}

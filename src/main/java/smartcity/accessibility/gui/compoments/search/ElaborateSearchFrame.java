package smartcity.accessibility.gui.compoments.search;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.exceptions.EmptySearchQuery;
import smartcity.accessibility.gui.Application;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.search.SearchQueryResult;
import smartcity.accessibility.socialnetwork.Score;

/*
 * Author Kolikant
 */
public class ElaborateSearchFrame implements MouseListener {

	private static final int startingLoc = 59;
	private static final int jumps = 60;

	private JFrame frame;
	private JTextField locationTypeField;
	private JTextField RadiusField;
	private JTextField CurrentPositionField;
	private JButton btnSearch;
	private JComboBox<Integer> thresholdsBox;

	/**
	 * Create the application.
	 */
	public ElaborateSearchFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 400);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblElaborateSearchForm = new JLabel("Elaborate Search");
		lblElaborateSearchForm.setFont(new Font("Dialog", Font.BOLD, 15));
		lblElaborateSearchForm.setBounds(151, 25, 166, 15);
		frame.getContentPane().add(lblElaborateSearchForm);

		JLabel lblLocationType = new JLabel("Location Type: ");
		lblLocationType.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblLocationType.setBounds(12, startingLoc, 101, 15);
		frame.getContentPane().add(lblLocationType);

		JLabel lblMinAccessability = new JLabel("Min Accessability:");
		lblMinAccessability.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblMinAccessability.setBounds(12, startingLoc + jumps, 101, 15);
		frame.getContentPane().add(lblMinAccessability);

		JLabel lblSearchRadius = new JLabel("Search Radius:");
		lblSearchRadius.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblSearchRadius.setBounds(12, startingLoc + 2 * jumps, 101, 15);
		frame.getContentPane().add(lblSearchRadius);

		JLabel lblCurrentPosition = new JLabel("Current Position:");
		lblCurrentPosition.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblCurrentPosition.setBounds(12, startingLoc + 3 * jumps, 101, 15);
		frame.getContentPane().add(lblCurrentPosition);

		locationTypeField = new JTextField();
		locationTypeField.setBounds(151, startingLoc, 198, 25);
		frame.getContentPane().add(locationTypeField);
		locationTypeField.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.setBounds(151, 303, 117, 25);
		btnSearch.addMouseListener(this);
		frame.getContentPane().add(btnSearch);

		Object[] scores = IntStream.rangeClosed(Score.getMinScore(), Score.getMaxScore())
				.mapToObj(n -> Integer.valueOf(n)).toArray();
		Integer[] acscores = new Integer[scores.length];
		for (int i = 0; i < scores.length; ++i)
			acscores[i] = (Integer) scores[i];
		this.thresholdsBox = new JComboBox<>(acscores);
		thresholdsBox.setBounds(151, startingLoc + jumps, 198, 25);
		frame.getContentPane().add(thresholdsBox);
		thresholdsBox.setVisible(true);
		frame.setVisible(true);

		RadiusField = new JTextField();
		RadiusField.setBounds(151, startingLoc + 2 * jumps, 198, 25);
		frame.getContentPane().add(RadiusField);

		CurrentPositionField = new JTextField();
		CurrentPositionField.setBounds(151, startingLoc + 3 * jumps, 198, 25);
		frame.getContentPane().add(CurrentPositionField);
		CurrentPositionField.setText((Application.currLocation.getPosition() + ""));

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource() != btnSearch)
			return;
		JxMapsFunctionality.ClearMarkers(JxMapsFunctionality.getMapView());
		createAndSearchQuery();

		frame.dispose();
	}

	private SearchQuery createAndSearchQuery() {
		int Threshold, radius;
		try {

			Threshold = thresholdsBox.getSelectedIndex();
			radius = Integer.parseInt(RadiusField.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(Application.frame, "Radius and Thresold must be numbers", "Bad Input",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		LatLng c;
		try {
			String[] latLng = CurrentPositionField.getText().replaceAll("\\[", "").replaceAll("\\]", "").split(",");
			c = new LatLng(Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1]));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Application.frame, "cannot decipher latlng", "Bad Input",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}

		SearchQuery $ = SearchQuery.TypeSearch(locationTypeField.getText());
		SearchQueryResult esr = $.searchByType(new Location(c), radius);

		try {
			esr.convertDummiesToReal();
		} catch (EmptySearchQuery e) {
			JOptionPane.showMessageDialog(Application.frame, e.getMessage(), "Bad Input", JOptionPane.ERROR_MESSAGE);
		}

		try {
			esr.filterLocations(Threshold);
		} catch (EmptySearchQuery e) {
			JOptionPane.showMessageDialog(Application.frame, e.getMessage(), "Bad Input", JOptionPane.ERROR_MESSAGE);
		}

		JxMapsFunctionality.putAllExtendedMarker(JxMapsFunctionality.getMapView(), esr.getLocations());
		return $;
		// NearbyPlacesAttempt.displayResults(locationTypeField.getText(),
		// radius, c, JxMapsFunctionality.getMapView());

		// return SearchQuery.freeTextSearch(locationTypeField.getText());
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

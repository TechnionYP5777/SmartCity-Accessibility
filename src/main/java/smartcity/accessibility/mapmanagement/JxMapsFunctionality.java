package smartcity.accessibility.mapmanagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicButtonUI;

import com.teamdev.jxmaps.ControlPosition;
import com.teamdev.jxmaps.Geocoder;
import com.teamdev.jxmaps.GeocoderCallback;
import com.teamdev.jxmaps.GeocoderRequest;
import com.teamdev.jxmaps.GeocoderResult;
import com.teamdev.jxmaps.GeocoderStatus;
import com.teamdev.jxmaps.InfoWindow;
import com.teamdev.jxmaps.LatLng;
import com.teamdev.jxmaps.Map;
import com.teamdev.jxmaps.MapOptions;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.MapTypeControlOptions;
import com.teamdev.jxmaps.MapViewOptions;
import com.teamdev.jxmaps.Marker;
import com.teamdev.jxmaps.swing.MapView;

import smartcity.accessibility.database.LocationListCallback;
import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.gui.Application;
import smartcity.accessibility.gui.ExtendedMarker;
import smartcity.accessibility.gui.compoments.search.SearchFieldUI;
import smartcity.accessibility.jxMapsFunctionality.OptionsWindow;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.extendedMapView;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.search.SearchQueryResult;

/*
 * Author Kolikant
 */

public abstract class JxMapsFunctionality {

	public static MapView mv;

	public static class extendedMapView extends MapView {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		List<Marker> MarkerList = new ArrayList<Marker>();
		private OptionsWindow optionsWindow;

		public extendedMapView() {
			setOnMapReadyHandler(new MapReadyHandler() {
				@Override
				public void onMapReady(MapStatus arg0) {
					MapOptions options = new MapOptions();
					MapTypeControlOptions controlOptions = new MapTypeControlOptions();
					// Changing position of the map type control
					controlOptions.setPosition(ControlPosition.TOP_RIGHT);
					// Setting map type control options
					options.setMapTypeControlOptions(controlOptions);
					// Setting map options
					getMap().setOptions(options);

				}
			});
		}

		public void stop() {

			System.out.println("Stage is closing");
			// Save file
		}
		
		public void setOptionsWindow(OptionsWindow ow){
			optionsWindow = ow;
		}

		@Override
		public void addNotify() {
			super.addNotify();
		}

	}

	public static void addOptionsMenu(OptionsWindow ow){
		extendedMapView mva = (extendedMapView)mv;
		mva.setOptionsWindow(ow);
	}
	
	public static OptionsWindow createOptionsBar(){
		return new OptionsWindow(mv, new Dimension(350, 40)) {
			@Override
			public void initContent(JWindow contentWindow) {
				JPanel content = new JPanel(new GridBagLayout());
				content.setBackground(Color.white);

				Font robotoPlain13 = new Font("Roboto", 0, 13);
				final JTextField searchField = new JTextField();
				searchField.setText("Eliezer 10, Haifa, Israel");
				searchField.setToolTipText("Enter address or coordinates...");
				searchField.setBorder(BorderFactory.createEmptyBorder());
				searchField.setFont(robotoPlain13);
				searchField.setForeground(new Color(0x21, 0x21, 0x21));
				searchField.setUI(new SearchFieldUI(searchField));

				final JButton searchButton = new JButton();
				searchButton.setBorder(BorderFactory.createEmptyBorder());
				searchButton.setUI(new BasicButtonUI());
				searchButton.setOpaque(false);
				
				Runnable r = new Runnable() {
					@Override
					public void run() {
						System.out.println(searchField.getText()); 
						SearchQuery sq = SearchQuery.adressSearch(searchField.getText());
						SearchQueryResult sqr1= sq.SearchByAddress(mv);
						try {
							sq.waitOnSearch();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						LatLng position1 = sqr1.getCoordinations().get(0).getGeometry().getLocation();
						 JxMapsFunctionality.putMarker((extendedMapView) mv, position1, searchField.getText());
					}
				};
				
				ActionListener searchActionListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						r.run();
					}
				};
				searchButton.addActionListener(searchActionListener);
				searchField.addActionListener(searchActionListener);

				content.add(searchField, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
						GridBagConstraints.HORIZONTAL, new Insets(11, 11, 11, 0), 0, 0));
				content.add(searchButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTHWEST,
						GridBagConstraints.NONE, new Insets(11, 0, 11, 11), 0, 0));

				contentWindow.getContentPane().add(content);
			}

			@Override
			protected void updatePosition() {
				if (parentFrame.isVisible()) {
					Point newLocation = parentFrame.getContentPane().getLocationOnScreen();
					newLocation.translate(56, 11);
					contentWindow.setLocation(newLocation);
					contentWindow.setSize(340, 40);
				}
			}
		};
	}
	
	public static MapView getMapView() {
		return mv != null ? mv : (mv = new extendedMapView());
	}

	public static void DestroyMapView() {
		mv.dispose();
		mv = null;
	}

	public static void ClearMarkers(extendedMapView mv) {
		for (Marker m : mv.MarkerList)
			m.remove();
	}

	public static MapView getMapView(MapViewOptions o) {
		o.importPlaces();
		return mv = new extendedMapView();
	}

	public static void putMarker(extendedMapView mv, LatLng l, String name) {
		waitForMapReady(mv);
		Map map = mv.getMap();
		Marker marker = new Marker(map);
		marker.setPosition(l);
		map.setCenter(l);
		final InfoWindow window = new InfoWindow(map);
		mv.MarkerList.add(marker);
		window.setContent(name);
		window.open(map, marker);
	}

	public static void waitForMapReady(extendedMapView mv) {
		mv.waitReady();
	}

	public static void openFrame(MapView v, String s, double zoom) {
		openFrame(v, s, zoom, 700, 500);
	}

	public static void openFrame(MapView v, String s, double zoom, int x, int y) {
		JFrame frame = new JFrame(s);
		v.getMap().setZoom(zoom);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(v, BorderLayout.CENTER);
		frame.setSize(x, y);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void initMapLocation(MapView mapView, String startAdress) {
		Map map = mapView.getMap();
		map.setZoom(17.0);
		GeocoderRequest request = new GeocoderRequest();
		request.setAddress(startAdress);
		Geocoder g = mapView.getServices().getGeocoder();
		g.geocode(request, new GeocoderCallback(map) {
			@Override
			public void onComplete(GeocoderResult[] rs, GeocoderStatus s) {
				System.out.println(s.name());
				if (s != GeocoderStatus.OK)
					return;
				map.setCenter(rs[0].getGeometry().getLocation());
				onRightClick(mapView, rs[0].getGeometry().getLocation());
			}
		});

	}

	public static void onRightClick(MapView v, LatLng l) {
		Map map = v.getMap();
		if (Application.currLocation != null)
			Application.currLocation.remove();
		Application.currLocation = new Marker(v.getMap());
		Application.currLocation.setPosition(l);
		LocationManager.getLocationsNearPoint(l, new LocationListCallback() {

			@Override
			public void done(List<Location> ls) {
				for (Marker m : Application.markers)
					m.remove();
				Application.markers.clear();
				for (Location loc : ls)
					Application.markers.add(new ExtendedMarker(map, loc));
			}
		});
	}
}
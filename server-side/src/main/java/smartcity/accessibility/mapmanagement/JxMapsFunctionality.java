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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicButtonUI;

import com.teamdev.jxmaps.ControlPosition;
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
import smartcity.accessibility.exceptions.illigalString;
import smartcity.accessibility.gui.Application;
import smartcity.accessibility.gui.ExtendedMarker;
import smartcity.accessibility.gui.components.OptionsWindow;
import smartcity.accessibility.gui.components.SpinningWheel;
import smartcity.accessibility.gui.components.location.LocationFrame;
import smartcity.accessibility.gui.components.search.SearchFieldUI;
import smartcity.accessibility.mapmanagement.Location.LocationSubTypes;
import smartcity.accessibility.mapmanagement.Location.LocationTypes;
import smartcity.accessibility.navigation.JxMapsConvertor;
import smartcity.accessibility.search.NearbyPlacesSearch;
import smartcity.accessibility.search.SearchQuery;
import smartcity.accessibility.search.SearchQueryResult;

/*
 * Author Kolikant
 */

public abstract class JxMapsFunctionality {

	public static ExtendedMapView mv;

	public static class ExtendedMapView extends MapView {

		private static final long serialVersionUID = 1L;
		List<Marker> MarkerList = new ArrayList<Marker>();
		@SuppressWarnings("unused")
		private OptionsWindow optionsWindow;

		public ExtendedMapView(MapViewOptions options) {
			super(options);
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

		public void setOptionsWindow(OptionsWindow ¢) {
			optionsWindow = ¢;
		}

		@Override
		public void addNotify() {
			super.addNotify();
		}

	}

	public static void addOptionsMenu(OptionsWindow ¢) {
		((ExtendedMapView) mv).setOptionsWindow(¢);
	}

	public static OptionsWindow createOptionsBar() {
		return new OptionsWindow(mv, new Dimension(350, 40)) {
			@Override
			public void initContent(JWindow contentWindow) {
				JPanel content = new JPanel(new GridBagLayout());
				content.setBackground(Color.white);

				Font robotoPlain13 = new Font("Roboto", 0, 13);
				final JTextField searchField = new JTextField();
				searchField.setToolTipText("Enter address");
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
						SearchQuery sq;
						try {
							sq = SearchQuery.adressSearch(searchField.getText());
							SearchQueryResult sqr1 = sq.SearchByAddress(mv);
							try {
								sq.waitOnSearch();
							} catch (InterruptedException ¢) {
								¢.printStackTrace();
							}
							if (!sqr1.gotResults())
								JOptionPane.showMessageDialog(Application.frame, "no results were found",
										"search found nothing :(", JOptionPane.INFORMATION_MESSAGE);
							else {
								Location exactAddLoc = sqr1.get(0);
								// LocationManager.getLocation(dummy.getCoordinates(),
								// Location.LocationTypes.Street,
								// Location.LocationSubTypes.Default);
								try {
									Location StreetLoc = getStreetLocationByAdress(searchField.getText());
									JxMapsFunctionality.putExtendedMarkerWithStreet((ExtendedMapView) mv, exactAddLoc,
											StreetLoc, searchField.getText());
								} catch (Exception e) {
									JOptionPane.showMessageDialog(Application.frame,
											"Search Syntax is as follows:\n Country(optional) City(optional) Street StreetNumber",
											"bad search query", JOptionPane.INFORMATION_MESSAGE);
								}

							}
						} catch (illigalString e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				};

				ActionListener searchActionListener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent __) {
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
				if (!parentFrame.isVisible())
					return;
				Point newLocation = parentFrame.getContentPane().getLocationOnScreen();
				newLocation.translate(56, 11);
				contentWindow.setLocation(newLocation);
				contentWindow.setSize(340, 40);
			}
		};
	}

	private static Location getStreetLocationByAdress(String adress) throws illigalString {
		String[] Adress = adress.split(" "),
				StreetRepresenatation = Arrays.copyOfRange(Adress, 0, Math.max(0, Adress.length - 1));
		String StreetAdress = String.join(" ", StreetRepresenatation);
		SearchQuery sq = SearchQuery.adressSearch(StreetAdress);
		SearchQueryResult $ = sq.SearchByAddress(mv);
		try {
			sq.waitOnSearch();
		} catch (InterruptedException ¢) {
			¢.printStackTrace();
		}
		if ($.gotResults())
			return LocationManager.getLocation($.get(0).getCoordinates(), Location.LocationTypes.Street,
					Location.LocationSubTypes.Default);
		JOptionPane.showMessageDialog(Application.frame, "no results were found", "search found nothing :(",
				JOptionPane.INFORMATION_MESSAGE);
			// JxMapsFunctionality.putExtendedMarker((ExtendedMapView) mv,
			// dummy, searchField.getText());
		return null;
	}

	public static ExtendedMapView getMapView() {
		MapViewOptions $ = new MapViewOptions();
		$.importPlaces();
		return mv != null ? mv : (mv = new ExtendedMapView($));
	}

	public static void DestroyMapView() {
		mv.dispose();
		mv = null;
	}

	public static void ClearMarkers(ExtendedMapView mv) {
		for (Marker ¢ : mv.MarkerList)
			¢.remove();
		JxMapsConvertor.removePrevPolyline();
	}

	public static MapView getMapView(MapViewOptions ¢) {
		¢.importPlaces();
		return mv = new ExtendedMapView(¢);
	}

	public static void putMarker(ExtendedMapView mv, LatLng l, String name) {
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

	public static void putMarkerNoJump(ExtendedMapView mv, LatLng l, String name) {
		waitForMapReady(mv);
		Map map = mv.getMap();
		Marker marker = new Marker(map);
		marker.setPosition(l);
		final InfoWindow window = new InfoWindow(map);
		mv.MarkerList.add(marker);
		window.setContent(name);
		window.open(map, marker);
	}

	public static ExtendedMarker putExtendedMarker(ExtendedMapView mv, Location l, String name) {
		ExtendedMarker $ = putExtendedMarker(mv, l);
		Map map = mv.getMap();
		final InfoWindow window = new InfoWindow(map);
		window.setContent(name);
		window.open(map, $);
		return $;
	}

	public static void putAllExtendedMarker(ExtendedMapView mv, List<Location> ls) {
		for (Location loc : ls) {
			ExtendedMarker marker = putExtendedMarker(mv, loc);
			Map map = mv.getMap();
			final InfoWindow window = new InfoWindow(map);
			window.setContent(loc.getName());
			window.open(map, marker);
		}
	}

	public static ExtendedMarker putExtendedMarkerWithStreet(ExtendedMapView mv, Location l, Location sl, String name) {
		waitForMapReady(mv);
		Map map = mv.getMap();
		ExtendedMarker $ = new ExtendedMarker(map, l, sl);
		$.setPosition(l.getCoordinates());
		map.setCenter(l.getCoordinates());
		final InfoWindow window = new InfoWindow(map);
		window.setContent(name);
		window.open(map, $);
		mv.MarkerList.add($);
		return $;
	}

	public static ExtendedMarker putExtendedMarker(ExtendedMapView mv, Location l) {
		waitForMapReady(mv);
		Map map = mv.getMap();
		ExtendedMarker $ = new ExtendedMarker(map, l);
		$.setPosition(l.getCoordinates());
		map.setCenter(l.getCoordinates());
		mv.MarkerList.add($);
		return $;
	}

	public static void waitForMapReady(ExtendedMapView mv) {
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

	public static void initMapLocation(String startAdress) {
		Map map = mv.getMap();
		map.setZoom(17.0);
		GeocoderRequest request = new GeocoderRequest();
		request.setAddress(startAdress);
		mv.getServices().getGeocoder().geocode(request, new GeocoderCallback(map) {
			@Override
			public void onComplete(GeocoderResult[] rs, GeocoderStatus s) {
				System.out.println(s.name());
				if (s != GeocoderStatus.OK)
					return;
				map.setCenter(rs[0].getGeometry().getLocation());
				onRightClick(rs[0].getGeometry().getLocation());
			}
		});

	}

	public static void onRightClick(LatLng l) {
		Map map = mv.getMap();
		if (Application.currLocation != null)
			Application.currLocation.remove();
		Application.currLocation = new Marker(map);
		Application.currLocation.setPosition(l);

		NearbyPlacesSearch.findNearbyPlaces(mv, new LocationBuilder().setCoordinates(l.getLat(), l.getLng()).build(), 1000, Arrays.asList(LocationSubTypes.values()).stream()
				.map(λ -> λ + "").collect(Collectors.toList()), new LocationListCallback() {
					@Override
					public void done(List<Location> ls) {
						ClearMarkers(mv);

						for (Location ¢ : ls) {
							System.out.println(¢.getLocationSubType());
							System.out.println(¢.getCoordinates());
							if (¢.getLocationType() == null || !¢.getLocationType().equals(LocationTypes.Street))
								JxMapsFunctionality.putExtendedMarker(mv, ¢);
						}
						map.setCenter(l);
						map.setZoom(17.0);
						LocationManager.getLocationsNearPoint(l, new LocationListCallback() {

							@Override
							public void done(List<Location> sl) {
								if (sl != null)
									for (Location ¢ : sl)
										if (!ls.contains(¢) && !¢.getCoordinates().equals(l))
											JxMapsFunctionality.putExtendedMarker(mv, ¢);
							}
						}, 10);
					}
				});
		
		
	}

	public static void onClick(LatLng l) {
		SpinningWheel wheel = new SpinningWheel();
		LocationManager.getLocation(l, new LocationListCallback() {

			@Override
			public void done(List<Location> ¢) {
				new LocationFrame(!¢.isEmpty() ? ¢.get(0) : new LocationBuilder().setCoordinates(l.getLat(), l.getLng()).build());
				wheel.dispose();
			}
		});
	}
}
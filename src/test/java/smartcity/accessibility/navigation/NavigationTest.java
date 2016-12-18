package smartcity.accessibility.navigation;

import org.junit.Test;

import smartcity.accessibility.navigation.mapquestcommunication.Latlng;
import smartcity.accessibility.navigation.mapquestcommunication.Route;

public class NavigationTest {

	/**
	 * [[SuppressWarningsSpartan]]
	 */
	@Test
	public void getSimpleRouteFromServers() {
		//TODO this test is temporal for it relay on things that will change!
		Latlng from = new Latlng(39.750307,-104.999472);
		Latlng to =  new Latlng(40.750307,-105.999472);
		@SuppressWarnings("unused")
		Route r = (new Navigation()).getRouteFromMapQuest(from, to, null);
		/*for(Leg leg : r.getLegs()){
			for(Maneuver m : leg.getManeuvers()){
				for(String name : m.getStreet_names()){
					System.out.println(name+"\n");
				}
			}
		}*/
	}

}

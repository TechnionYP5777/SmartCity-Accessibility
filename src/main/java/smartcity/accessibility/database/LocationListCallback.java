package smartcity.accessibility.database;

import java.util.List;

import smartcity.accessibility.mapmanagement.Location;

public interface LocationListCallback {

	void done(List<Location> ls);
}

package smartcity.accessibility.database;

import java.util.List;
import java.util.Map;

public interface Database {
	/**
	 * gets object with id 
	 * @param objectClass
	 * @param id
	 * @return
	 */
	Map<String, Object> get(String objectClass, String id);

	/**
	 * gets the object that matches the fields of baseObject
	 * @param objectClass
	 * @param baseObject
	 * @return
	 */
	List<Map<String, Object>> get(String objectClass, Map<String, Object> baseObject);
	
	/**
	 * gets the object in the radius of the point specified
	 * @param objectClass
	 * @param field name of the field in the database that holds the location
	 * @param latitude
	 * @param longitude
	 * @param radius
	 * @return
	 */
	List<Map<String, Object>> get(String objectClass, String field, double latitude, double longitude, double radius);
	
	/**
	 * gets all the objects from object class
	 * @param objectClass
	 * @return
	 */
	List<Map<String, Object>> getAll(String objectClass);
	
	/**
	 * put object into object class and return newly created objects id
	 * @param objectClass
	 * @param object
	 * @return
	 */
	String put(String objectClass, Map<String, Object> object);
}

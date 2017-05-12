package smartcity.accessibility.database;

import java.util.List;
import java.util.Map;

import smartcity.accessibility.database.exceptions.ObjectNotFoundException;

/**
 * @author KaplanAlexander
 *
 */
public interface Database {
	/**
	 * gets object with id 
	 * @param objectClass
	 * @param id
	 * @return
	 */
	Map<String, Object> get(String objectClass, String id) throws ObjectNotFoundException;

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
	 * @param radius in kilometer
	 * @return
	 */
	List<Map<String, Object>> get(String objectClass, String field, double latitude, double longitude, double radius);
	
	/**
	 * put object into object class and return newly created objects id
	 * @param objectClass
	 * @param object
	 * @return
	 */
	String put(String objectClass, Map<String, Object> object);
	
	/**
	 * delete item with id from objectClass
	 * @param objectClass
	 * @param id
	 * @return
	 */
	boolean delete(String objectClass, String id);
	
	/**
	 * update the object with id with the fields in m
	 * if failed to update for any reason return false
	 * @param objectClass
	 * @param id
	 * @param m
	 * @return
	 */
	boolean update(String objectClass, String id, Map<String, Object> m);
}

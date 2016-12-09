package smartcity.accessibility.gui;

import smartcity.accessibility.database.DatabaseManager;

/*
 * @author KaplanAlexander
 * This is the class we will use to initiate the main menu of the project (a map)
 * and generally intialize the project (connect to database and such);
 */
public class Map {

	public static void main(String[] args) {
		DatabaseManager.initialize();

	}

}

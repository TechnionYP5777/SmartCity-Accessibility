package smartcity.accessibility.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;

@SpringBootApplication
public class Application {
	public static ExtendedMapView mapView;
	
	public static void main(String[] args) {
		mapView = JxMapsFunctionality.getMapView();
		SpringApplication.run(Application.class, args);
	}
}
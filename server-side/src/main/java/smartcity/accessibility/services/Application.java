package smartcity.accessibility.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.inject.Guice;
import com.google.inject.Injector;

import smartcity.accessibility.database.DatabaseModule;
import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.database.ReviewManager;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new DatabaseModule());
		ReviewManager.initialize(injector.getInstance(ReviewManager.class));
		LocationManager.initialize(injector.getInstance(LocationManager.class));
		
		SpringApplication.run(Application.class, args);
	}
}
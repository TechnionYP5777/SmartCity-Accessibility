package smartcity.accessibility.services;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jersey.repackaged.com.google.common.cache.CacheBuilder;
import jersey.repackaged.com.google.common.cache.CacheLoader;
import jersey.repackaged.com.google.common.cache.LoadingCache;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;

@SpringBootApplication
public class Application {
	public static ExtendedMapView mapView;
	public static LoadingCache<String, UserInfo> tokenToSession;

	public static void main(String[] args) {
		resetSessions();
		mapView = JxMapsFunctionality.getMapView();
		SpringApplication.run(Application.class, args);
	}
	
	public static void resetSessions(){
		tokenToSession = CacheBuilder.newBuilder()
	    .concurrencyLevel(4)
	    .maximumSize(10000)
	    .expireAfterWrite(10, TimeUnit.MINUTES)
	    .expireAfterAccess(10, TimeUnit.MINUTES)
	    .build(
	        new CacheLoader<String, UserInfo>() {
				@Override
				public UserInfo load(String token) throws Exception {
					return new UserInfo();
				}
	        });
	}
}
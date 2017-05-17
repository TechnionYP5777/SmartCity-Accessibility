package smartcity.accessibility.services;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



import com.google.inject.Guice;
import com.google.inject.Injector;

import smartcity.accessibility.database.DatabaseModule;
import smartcity.accessibility.database.LocationManager;
import smartcity.accessibility.database.ReviewManager;
import smartcity.accessibility.database.UserProfileManager;
import jersey.repackaged.com.google.common.cache.CacheBuilder;
import jersey.repackaged.com.google.common.cache.CacheLoader;
import jersey.repackaged.com.google.common.cache.LoadingCache;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality;
import smartcity.accessibility.mapmanagement.JxMapsFunctionality.ExtendedMapView;
/**
 * @author yael
 */
@SpringBootApplication
public class Application {
	
	public static LoadingCache<String, UserInfo> tokenToSession;
	public static ExtendedMapView mapView = JxMapsFunctionality.getMapView();;
	
	public Application(){
		resetSessions(); 
	}
	
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new DatabaseModule());
		ReviewManager.initialize(injector.getInstance(ReviewManager.class));
		LocationManager.initialize(injector.getInstance(LocationManager.class));
		UserProfileManager.initialize(injector.getInstance(UserProfileManager.class));
		mapView = JxMapsFunctionality.getMapView();
		SpringApplication.run(Application.class, args);
	}
	
	public static void resetSessions(){
		tokenToSession = CacheBuilder.newBuilder()
	    .concurrencyLevel(4)
	    .maximumSize(10000)
	    .expireAfterWrite(3, TimeUnit.MINUTES)
	    .expireAfterAccess(10, TimeUnit.MINUTES)
	    .build(
	        new CacheLoader<String, UserInfo>() {
				@Override
				public UserInfo load(String token) throws Exception {
					return new UserInfo();
				}
	        });
	}
	
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurerAdapter() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**").allowedOrigins("*");
	            }
	        };
	    }
}
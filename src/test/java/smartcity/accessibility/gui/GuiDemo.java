package smartcity.accessibility.gui;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.parse4j.ParseException;

import com.teamdev.jxmaps.LatLng;

import smartcity.accessibility.exceptions.UnauthorizedAccessException;
import smartcity.accessibility.mapmanagement.Coordinates;
import smartcity.accessibility.mapmanagement.Location;
import smartcity.accessibility.socialnetwork.Review;
import smartcity.accessibility.socialnetwork.Score;
import smartcity.accessibility.socialnetwork.User;
import smartcity.accessibility.socialnetwork.UserImpl;
import smartcity.accessibility.gui.components.location.LocationFrame;

public class GuiDemo {
	
	public Location loc;
	public Review r1;
	public Review r2;
	public Review r3;

	@BeforeClass
	public static void initClass(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void init() throws ParseException, UnauthorizedAccessException{
		User u = new UserImpl("a","b",User.Privilege.Admin);
		loc = new Coordinates(new LatLng());
		r1 = new Review(loc, Score.getMaxScore(),"This is a great review",u);
		r2 = new Review(loc, Score.getMaxScore()-1,"This is a good review",u);
		r3 = new Review(loc, Score.getMinScore(),"This is a horrible review",u);
		try{
			loc.addReview(r1);
		}catch(Exception e){
			
		}
		try{
			loc.addReview(r2);
		}catch(Exception e){
			
		}
		try{
			loc.addReview(r3);
		}catch(Exception e){
			
		}
		try{
			loc.pinReview(u, r1);
		} catch(Exception e){
			
		}
	}
	
	@Ignore
	@Test
	public void LocationFrame(){
		new LocationFrame(loc);
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

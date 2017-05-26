import { Component, ViewChild, ElementRef } from '@angular/core';
import { NavController,ModalController, Events,AlertController } from 'ionic-angular';
import { Geolocation } from '@ionic-native/geolocation';
import { MapClickMenuPage } from '../mapclickmenu/mapclickmenu';
import { LoginService } from '../login/LoginService';
import { UserPagePage } from '../user-page/user-page'; 
import { LoginPage } from '../login/login';
import { ComplexSearchPage } from '../complex-search/complex-search';
import { AdminPage } from '../admin/admin'; 
import { SearchService } from './searchService';
import { navigationManeuverPage } from '../navigation_maneuver/navigation_maneuver';

declare var google;  
 
 
@Component({
  selector: 'page-mapview',
  templateUrl: 'mapview.html'
})
export class MapviewPage {
 
  @ViewChild('map') mapElement: ElementRef;
  map: any;
  markers : any;
  geolocation: Geolocation;
  isLoggedin : any;
  searchQuery: any;
  loginPage = LoginPage;
  adminPage = AdminPage;
  complexSearchPage = ComplexSearchPage;
  output :  any;
  route : any;

  constructor(public navCtrl: NavController,public alertCtrl: AlertController,public modalCtrl: ModalController,public loginService : LoginService, public searchService : SearchService, public events: Events) {
	    this.isLoggedin = this.loginService.isLoggedIn();
		this.output = "";
		this.subscribeToNavigation();
		this.markers = [];
  }
  
    ionViewDidLoad(){
        this.loadMap();
    }
  
	addMarker(LatLngArr){
		for (var i = 0; i < this.markers.length; i++) {
            this.markers[i].setMap(null);
        }
		this.markers = [];
	    for (var i = 0; i < LatLngArr.length; i++) {
			this.markers[i] = LatLngArr[i];
            var coords = LatLngArr[i];
            var latLng = new google.maps.LatLng(coords.lat,coords.lng);
            var marker = new google.maps.Marker({
                position: latLng,
                map: this.map
            });
		    google.maps.event.addListener(marker,'click',(event)=>{ 
			    let clickMenu = this.modalCtrl.create(MapClickMenuPage,{latlng : event.latLng});
			    clickMenu.present();
		    });
        }
    }
	
    callSearch(searchQuery) {
	    this.searchService.search(searchQuery).subscribe(data => {
			this.addMarker([data.coordinates]);
			this.map.setCenter(data.coordinates);
		});
    }
	
	callToComplexSearch() {
			this.navCtrl.push(this.complexSearchPage);
			this.events.subscribe('complexSearch:pressed', (complexSearchResults, initLocation) => {
				for(var i = 1; i < complexSearchResults.length - 1; i++) {
					this.addMarker([complexSearchResults[i].coordinates]);
				}	
				this.map.setCenter(initLocation);
			});
	
	}
		
	userprofile(){
		this.isLoggedin = this.loginService.isLoggedIn();
		if(!this.isLoggedin)
			this.presentAlert("Sorry, it seems you were not active for 10 minutes. Please re-login.");
		else 
		    this.navCtrl.push(UserPagePage);
	}
	
	ionViewDidEnter(){
	    this.isLoggedin = this.loginService.isLoggedIn();
    }
  
	subscribeToNavigation(){
		this.events.subscribe('navigation:done', (navigationResults,loading) => {
			if(this.route != null)
				this.route.setMap(null);
			this.route = new google.maps.Polyline({
			    path: navigationResults.latlng,
			    geodesic: true,
			    strokeColor: '#FF0000',
			    strokeOpacity: 1.0,
			    strokeWeight: 2
			});
			google.maps.event.addListener(this.route,'click',(event)=>{ 
				let clickMenu = this.modalCtrl.create(navigationManeuverPage,navigationResults);
				clickMenu.present();
			});
			this.route.setMap(this.map);
			loading.dismiss();
		});
	}
	presentAlert(str) {
		let alert = this.alertCtrl.create({
		  title: 'Alert',
		  subTitle: str,
		  buttons: ['OK']
		});
		alert.present();
	}


loadMap(){
	this.geolocation = new Geolocation();
    this.geolocation.getCurrentPosition().then((position) => {
		let latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
		let mapOptions = {
			center: latLng,
			zoom: 15,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		}		
		this.map = new google.maps.Map(document.getElementById('map'), mapOptions);
		google.maps.event.addListener(this.map,'click',(event)=>{ 
			let clickMenu = this.modalCtrl.create(MapClickMenuPage,{latlng : event.latLng});
			clickMenu.present();
		} );
		this.addMarker([]);
    }, (err) => {
      console.log(err);
    });	
  }
}
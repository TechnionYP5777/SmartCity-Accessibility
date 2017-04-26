import { Component, ViewChild, ElementRef } from '@angular/core';
import { NavController,ModalController } from 'ionic-angular';
import { Geolocation } from '@ionic-native/geolocation';
import { AlertController } from 'ionic-angular';
import {MapClickMenuPage} from '../mapclickmenu/mapclickmenu';
import { LoginService } from '../login/LoginService';
import { UserPagePage } from '../user-page/user-page'; 
import { LoginPage } from '../login/login';
import { ComplexSearchPage } from '../complex-search/complex-search'; 
import { SearchService } from './searchService';
declare var google;  
 
 
@Component({
  selector: 'page-mapview',
  templateUrl: 'mapview.html'
})
export class MapviewPage {
 
  @ViewChild('map') mapElement: ElementRef;
  map: any;
  marker:any;
  geolocation: Geolocation;
  isLoggedin : any;
  searchQuery: any;
  loginPage = LoginPage;
  userProfile = UserPagePage;
  complexSearchPage = ComplexSearchPage;
  output :  any;
  constructor(public navCtrl: NavController,public alertCtrl: AlertController,public modalCtrl: ModalController,public loginService : LoginService, public searchService : SearchService) {
	    this.isLoggedin = this.loginService.isLoggedIn();
		this.output = "";
  }
  
    ionViewDidLoad(){
        this.loadMap();
    }
  
    callSearch(searchQuery) {
	    this.searchService.search(searchQuery).subscribe(data => {
			this.output = "Location is: " + data.name + " coordinates are: " + data.LatLng;
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
	
    addMarker(){
        var uluru = {lat: 32.779867, lng: 35.016426};
	    var marker = new google.maps.Marker({
		    position: uluru,
            map: this.map
        });
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
		this.addMarker();
    }, (err) => {
      console.log(err);
    });
	
  }
}
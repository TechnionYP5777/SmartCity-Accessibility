import { Component, ViewChild, ElementRef } from '@angular/core';
import { NavController,ModalController, Events,AlertController, LoadingController } from 'ionic-angular';
import { Geolocation } from '@ionic-native/geolocation';
import { MapClickMenuPage } from '../mapclickmenu/mapclickmenu'; 
import { ComplexSearchPage } from '../complex-search/complex-search';
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
  marker_curr_location : any;
  searchQuery: any;
  complexSearchPage = ComplexSearchPage;
  output :  any;
  navigateMarkers : any;
  loading : any;

  constructor(public navCtrl: NavController, public loadingCtrl: LoadingController, public alertCtrl: AlertController,public modalCtrl: ModalController, public searchService : SearchService, public events: Events) {
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
			this.markers[i] = marker;
        }
    }
	
    callSearch(searchQuery) {
		this.presentLoadingCustom();
	    this.searchService.search(searchQuery).subscribe(data => {
			this.addMarker([data.coordinates]);
			this.map.setCenter(data.coordinates);
		});
		this.loading.dismiss();
    }
	
	callToComplexSearch() {
			this.navCtrl.push(this.complexSearchPage);
			let markers_complex = [];
			this.events.subscribe('complexSearch:pressed', (complexSearchResults, initLocation) => {
				for(var i = 0; i < complexSearchResults.length; i++) {
					markers_complex[i] = complexSearchResults[i].coordinates;
				}
				this.addMarker(markers_complex);
				this.map.setCenter(initLocation);
			});
	
	}
  
	subscribeToNavigation(){
		this.events.subscribe('navigation:done', (navigationResults,loading) => {
			if(navigationResults == null){
				loading.dismiss();
				return;
			}
			if(this.navigateMarkers != null){
				this.navigateMarkers[0].setMap(null);
				this.navigateMarkers[1].setMap(null);
				this.navigateMarkers[2].setMap(null);
			}
			var route = new google.maps.Polyline({
			    path: navigationResults.latlng,
			    geodesic: true,
			    strokeColor: '#FF0000',
			    strokeOpacity: 1.0,
			    strokeWeight: 2
			});
            var start_marker = new google.maps.Marker({
                position: navigationResults.latlng[0],
                map: this.map,
				icon: 'assets/icon/start.png'
            });
			var len = navigationResults.latlng.length;
			var end_marker = new google.maps.Marker({
                position: navigationResults.latlng[len-1],
                map: this.map,
				icon: 'assets/icon/end.png'
            });
			route.setMap(this.map);
			this.navigateMarkers = [route, start_marker, end_marker];
			google.maps.event.addListener(route,'click',(event)=>{ 
				let clickMenu = this.modalCtrl.create(navigationManeuverPage,navigationResults);
				clickMenu.present();
			});
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
		
		this.trackUser();
		
    }, (err) => {
      console.log(err);
    });	
  }
  
trackUser() {
	this.geolocation.watchPosition().subscribe((position) => {
		let latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
		
		if(this.marker_curr_location != null)
			this.marker_curr_location.setMap(null);
		
		this.marker_curr_location = new google.maps.Marker({
			map: this.map,
			position: latLng,
			icon: 'assets/icon/curr_location.png'
		});
		
		var infowindow = new google.maps.InfoWindow({
		    content: 'you are here'
	    });
		
		this.marker_curr_location.addListener('click', function() {
            infowindow.open(this.map, this.marker_curr_location);
        });
		
    }, (err) => {
      console.log(err);
    });	
}  

presentLoadingCustom() {
            this.loading = this.loadingCtrl.create({
            spinner: 'bubbles',
		    showBackdrop: false,
		    cssClass: 'loader'
        });
        this.loading.present();
    }
}
import { Component } from '@angular/core';
import { NavController, NavParams , Events, AlertController, LoadingController} from 'ionic-angular';
import {ComplexSearchService} from './complexSearchService';
import { SearchService } from '../mapview/searchService';
import { Geolocation } from '@ionic-native/geolocation';

declare var google;  

/*
  Generated class for the ComplexSearch page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'page-complex-search',
  templateUrl: 'complex-search.html'
})
export class ComplexSearchPage {
  type: string = "restaurant";
  gender: string;
  minRating: number = 1;
  raduis: number;
  loading: any;
  music: string;
  initLoc: string;
  output: any;
  callback: any;
  startLocationCoordinates: any;
  useCurrLoc: any = false;
  geolocation : any;
  constructor(public events: Events, public searchService: SearchService,
				public navCtrl: NavController, public navParams: NavParams,
				public complexSearchService : ComplexSearchService,
				public alertCtrl: AlertController, public loadingCtrl: LoadingController) {
    
  }

  stpSelect() {
    console.log('STP selected');
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ComplexSearchPage');
  }

  callComplexSearch(type, radius, initLoc, minRating) {
	  if (radius < 0) {
			this.presentAlert('radius must be a positive number');
			return;
	  }
	  
	  if (this.useCurrLoc) {
			this.coordsComplexSearch(type, radius, minRating);
	  } else {
			this.addressComplexSearch(type, radius, initLoc, minRating);
	  }
	 

	
   }
   
   coordsComplexSearch(type, radius, minRating) {
		this.presentLoadingCustom();
		this.geolocation = new Geolocation();
		this.geolocation.getCurrentPosition().then((position) => {
			this.startLocationCoordinates = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
			this.complexSearchService.complexSearchCoords(type, radius, position.coords, minRating).subscribe(data => {
				this.events.publish('complexSearch:pressed', data, this.startLocationCoordinates);
			});
			this.loading.dismiss();
			this.navCtrl.pop();
		}, (err) => {
			console.log(err);
		});
		
		
   }
   
   addressComplexSearch(type, radius, initLoc, minRating){
	 if (!initLoc) {
		this.presentAlert('You did not entered any initial location');
		return;
	  }
	  this.presentLoadingCustom();
	  
		this.searchService.search(initLoc).subscribe(
		data => {
			this.startLocationCoordinates = data.coordinates;
		}
		, err => {
			this.handleError(err.json());
		}
	);
	 
	    this.complexSearchService.complexSearchAddress(type, radius, initLoc, minRating).subscribe(data => {
			
			this.events.publish('complexSearch:pressed', data, this.startLocationCoordinates);
        });
		this.loading.dismiss();
		this.navCtrl.pop();
   }
   
   presentAlert(str) {
		let alert = this.alertCtrl.create({
		  title: 'Alert',
		  subTitle: str,
		  buttons: ['OK']
		});
		alert.present();
	}
	
	handleError(err) {
		this.presentAlert("error: " + err.message);
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

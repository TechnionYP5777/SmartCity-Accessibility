import { Component } from '@angular/core';
import { NavController, NavParams , Events, AlertController, LoadingController} from 'ionic-angular';
import {ComplexSearchService} from './complexSearchService';
import { SearchService } from '../mapview/searchService';
import { Geolocation } from '@ionic-native/geolocation';
import { Constants } from "../constants";
import {SpecialConstants} from "../special-constants/special-constants";

declare var google;  

/*
	author: Koral Chapnik
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
				public alertCtrl: AlertController, public loadingCtrl: LoadingController,
				public _constants : SpecialConstants) {
    
  }

  stpSelect() {
    console.log('STP selected');
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ComplexSearchPage');
  }

  callComplexSearch(type, radius, initLoc, minRating) {
	  if (radius <= 0 ) {
			this.presentAlert('radius must be a positive number');
			return;
	  }
	  
	  if (radius == null ) {
			this.presentAlert('please enter a radius');
			return;
	  }
	  
	  if (this.useCurrLoc) {
			this.coordsComplexSearch(type, radius, minRating);
	  } else {
			this.addressComplexSearch(type, radius, initLoc, minRating);
	  }
	 

	
   }
   
   coordsComplexSearch(type, radius, minRating) {
		this.loading = this._constants.createCustomLoading();
		this.loading.present();
		this.geolocation = new Geolocation();
		this.geolocation.getCurrentPosition().then((position) => {
			this.startLocationCoordinates = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
			this.complexSearchService.complexSearchCoords(type, radius, position.coords, minRating).subscribe(data => {
				this.events.publish('complexSearch:pressed', data, this.startLocationCoordinates);
				this.loading.dismiss().catch(() => {});
				
			});
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
	  this.loading = this._constants.createCustomLoading();
      this.loading.present();
	  
		this.searchService.search(initLoc).subscribe(
		data => {
			this.startLocationCoordinates = data.coordinates;
		}
		, err => {
			this.handleError(err.json());
			return;
		}
	);
	 
	    this.complexSearchService.complexSearchAddress(type, radius, initLoc, minRating).subscribe(data => {
			
			this.events.publish('complexSearch:pressed', data, this.startLocationCoordinates);
        });
		this.loading.dismiss().catch(() => {});
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
		if(err.message == null)
			this.presentAlert(Constants.serverNotResponding);
		else 
			this.presentAlert("error: " + err.message);
    }
	
}

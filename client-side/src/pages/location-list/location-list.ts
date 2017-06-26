import { Component } from '@angular/core';
import { AlertController, NavController, NavParams , ModalController, ViewController} from 'ionic-angular';
import { LocationsInRadiusService } from './LocationsInRadiusService';
import { AddLocationPage } from '../add-location/add-location';
import { GetReviewsPage } from '../reviews/reviews'; 
import { SearchService } from '../mapview/searchService';
import { Constants } from "../constants";

@Component({
  selector: 'page-location-list',
  templateUrl: 'location-list.html'
})
export class LocationListPage {
	lat : any;
	lng : any;
	output: any;
	output2: any;
	locations: any;
	location: any;
	pleaseWait: any;
	index: any;
	adress: any;
	
	constructor(public alertCtrl: AlertController, public viewCtrl: ViewController, public modalCtrl: ModalController, public navCtrl: NavController, public navParams: NavParams, public locationsInRadius: LocationsInRadiusService, public searchService : SearchService) {
		this.lat = navParams.get('lat');
		this.lng = navParams.get('lng');	
		this.output = "Calculating...";
		this.pleaseWait = true;
		this.locations = [];
		this.adress = "";
		this.searchService.getAdress(this.lat, this.lng).subscribe(data => {	
			this.adress = data.res;
		});
		this.locationsInRadius.GetLocationsInRadiusFrom(this.lat, this.lng).subscribe(data => {	
			for(var count = 0; count < data.length; count++){
				this.locations[count] = data[count];
			}
		this.output = this.locations;
		this.pleaseWait = false;
		}, err => {
			if(err.error == null)
				this.presentAlert(Constants.serverNotResponding);
			else 
				this.presentAlert("problem in finding nearby locations: "+err.json().message);
			this.viewCtrl.dismiss();
		});
	}

	cc(locationn){
		let clickMenu = this.modalCtrl.create(GetReviewsPage, {lat:locationn.coordinates.lat,lng:locationn.coordinates.lng,type:locationn.locationType,subtype:locationn.locationSubType,name:locationn.name});
		clickMenu.present();
		this.viewCtrl.dismiss();
	}
	
	addLocation(){
		let clickMenu = this.modalCtrl.create(AddLocationPage,{lat:this.lat,lng:this.lng});
		clickMenu.present();
		this.viewCtrl.dismiss();
	}
	
	  presentAlert(string) {
	  let alert = this.alertCtrl.create({
		title: string,
		buttons: ['OK']
	  });
	  alert.present(alert);
	}
}

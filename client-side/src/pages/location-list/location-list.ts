import { Component } from '@angular/core';
import { NavController, NavParams , ModalController, ViewController} from 'ionic-angular';
import { LocationsInRadiusService } from './LocationsInRadiusService';
import { AddLocationPage } from '../add-location/add-location';
import { GetReviewsPage } from '../reviews/reviews'; 

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
	youChose: any;
	index: any;
	constructor(public viewCtrl: ViewController, public modalCtrl: ModalController, public navCtrl: NavController, public navParams: NavParams, public locationsInRadius: LocationsInRadiusService) {
		this.lat = navParams.get('lat');
		this.lng = navParams.get('lng');	
		this.output = "Calculating...";
		this.youChose = "you chose: "
		this.locations = [{"reviews":[],"coordinates":{"lng":0,"lat":0},"name":"please wait...","locationType":"Coordinate","locationSubType":"Default","notPinnedReviews":[],"pinnedReviews":[]}];
		this.locationsInRadius.GetLocationsInRadiusFrom(this.lat, this.lng).subscribe(data => {	
			for(var count = 0; count < data.length; count++){
				this.locations[count] = data[count];
			}
		this.output = this.locations;
		});
	}

	cc(locationn){
		let clickMenu = this.modalCtrl.create(GetReviewsPage, {lat:locationn.coordinates.lat,lng:locationn.coordinates.lng,type:locationn.locationType,subtype:locationn.locationSubType,name:locationn.name});
		clickMenu.present();
		this.viewCtrl.dismiss();
	}
	
	addLocation(){
		this.viewCtrl.dismiss();
		let clickMenu = this.modalCtrl.create(AddLocationPage,{lat:this.lat,lng:this.lng});
		clickMenu.present();
	}
}

import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { LocationsInRadiusService } from './LocationsInRadiusService';

@Component({
  selector: 'page-location-list',
  templateUrl: 'location-list.html'
})
export class LocationListPage {
	lat : any;
	lng : any;
	output: any;
	constructor(public navCtrl: NavController, public navParams: NavParams, public locationsInRadius: LocationsInRadiusService) {
		this.lat = navParams.get('lat');
		this.lng = navParams.get('lng');	
		this.output = "aaaaaaaaaHWERWERWERWER";
		this.locationsInRadius.GetLocationsInRadiusFrom(this.lat, this.lng).subscribe(data => {
			for (var key in data) {
				 console.log("we got:", data[key]);
			}
			this.output = data[key];
		});
	}

}

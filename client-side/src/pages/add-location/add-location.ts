import {Component} from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { AddLocationService } from './AddLocationService';


@Component({
  selector: 'page-add-location',
  templateUrl: 'add-location.html',
})

export class AddLocationPage {
  token : any;
  lat : any;
  lng : any;
  omg : any;
  type: string;
  name: string;
  notDone: any;
  thanks: any;
  
  constructor(public navCtrl: NavController, public navParams: NavParams, public addLocationService: AddLocationService) {
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
	this.omg = "omg!";
	this.notDone = true;
	this.thanks = "Thank You So Much For Contributing To Our Database!"
  }
  
  addToDataBase(){
		this.omg = this.type;
		this.notDone = false;
		this.addLocationService.addLocation(this.name, this.lat, this.lng, this.type);
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AddReviewPage');
  }

}

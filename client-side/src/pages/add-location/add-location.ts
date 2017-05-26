import {Component} from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';


@Component({
  selector: 'page-add-location',
  templateUrl: 'add-location.html',
})

export class AddLocationPage {
  token : any;
  lat : any;
  lng : any;
  omg : any;
  
  constructor(public navCtrl: NavController, public navParams: NavParams) {
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
	this.omg = "omg!";
  }
  
  addToDataBase(){
	  this.omg = "click";
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AddReviewPage');
  }

}

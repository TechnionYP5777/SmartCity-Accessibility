import {Component} from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {HomePage} from '../home/home';
import { LoginService } from '../login/LoginService';


@Component({
  selector: 'page-add-location',
  templateUrl: 'add-location.html',
})

export class AddLocationPage {
  token : any;
  lat : any;
  lng : any;
  
  constructor(public navCtrl: NavController, public navParams: NavParams) {
    this.token = window.sessionStorage.getItem('token');
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AddReviewPage');
  }

}

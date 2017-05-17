import {Component} from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {HomePage} from '../home/home';
import {GetReviewsService} from './ReviewsService';

@Component({
  selector: 'page-get-reviews',
  templateUrl: 'get-reviews.html',
})

export class GetReviewsPage {
  lat : any;
  lng : any;
  
  constructor(public navCtrl: NavController, public navParams: NavParams, public getreviewsservice: GetReviewsService) {
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
  }
  

  ionViewDidLoad() {
    console.log('ionViewDidLoad GetReviewPage');
  }

}

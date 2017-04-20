import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {HomePage} from '../home/home';
import {AddReviewService} from './AddReviewService';

/*
  Generated class for the AddReview page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'page-add-review',
  templateUrl: 'add-review.html'
})
export class AddReviewPage {
  lat : any;
  lng : any;
  
  reviewinfo = {
		 review: '',
		 score: ''
	};
  
  constructor(public navCtrl: NavController, public navParams: NavParams, public addreviewservice: AddReviewService) {
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
  }
  
  addreview(rev) {
     console.log(rev.review);
     console.log(rev.score);
   }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AddReviewPage');
  }

}

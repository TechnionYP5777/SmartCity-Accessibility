import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

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
  constructor(public navCtrl: NavController, public navParams: NavParams) {
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AddReviewPage');
  }

}

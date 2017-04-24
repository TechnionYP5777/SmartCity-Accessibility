import {Component} from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {HomePage} from '../home/home';
import {AddReviewService} from './AddReviewService';
import {Rating} from './rating';


@Component({
  selector: 'page-add-review',
  templateUrl: 'add-review.html',
  directives: [Rating]
})

export class AddReviewPage {
  private rate:number = 3;
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
  
  onUpdate(value) {
    this.rate = value;
  }
  
  addreview(rev) {
     console.log(rev.review);
     console.log(rev.score);
   }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AddReviewPage');
  }

}

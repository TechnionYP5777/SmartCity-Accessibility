import {Component} from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {AddReviewService} from './AddReviewService';
import { LoginService } from '../login/LoginService';



@Component({
  selector: 'page-add-review',
  templateUrl: 'add-review.html',
})

export class AddReviewPage {
  token : any;
  lat : any;
  lng : any;
  type : any
  subtype : any
  name : any

  reviewinfo = {
		 review: '',
		 score: ''
	};

  constructor(public navCtrl: NavController, public navParams: NavParams, public addreviewservice: AddReviewService, public loginService : LoginService) {
    this.token = window.sessionStorage.getItem('token');
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
	this.type = navParams.get('type');
	this.subtype = navParams.get('subtype');
	this.name = navParams.get('name');
  }

  starClicked(value){
   this.reviewinfo.score = value;
   console.log("Avaliaram em :", value);
  }

  addreview(rev) {
     if ( rev.score == "" ){
       rev.score = 0;
     }
  	 this.addreviewservice.addreview(rev, this.lat, this.lng, this.type, this.subtype).then(data => {
  	 	if(data) {
  	 		this.navCtrl.pop();
  	 	}
  	 });
   }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AddReviewPage');
  }

}

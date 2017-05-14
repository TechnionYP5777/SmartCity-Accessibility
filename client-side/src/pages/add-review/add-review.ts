import {Component} from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {HomePage} from '../home/home';
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
  
  reviewinfo = {
		 review: '',
		 score: ''
	};
  
  constructor(public navCtrl: NavController, public navParams: NavParams, public addreviewservice: AddReviewService, public loginService : LoginService) {
    this.token = window.sessionStorage.getItem('token');
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
  }
  
  starClicked(value){
   console.log("Avaliaram em :", value);
  }
  
  addreview(rev) {
  	 this.addreviewservice.addreview(rev, lat, lng).then(data => {
  	 	if(data) {
  	 		this.navCtrl.setRoot(HomePage);
  	 	}
  	 });
     console.log(rev.review);
     console.log(rev.score);
   }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AddReviewPage');
  }

}

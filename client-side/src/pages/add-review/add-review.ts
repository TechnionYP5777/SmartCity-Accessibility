import {Component} from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {AddReviewService} from './AddReviewService';
import { LoginService } from '../login/LoginService';
import { SearchService } from '../mapview/searchService';


@Component({
  selector: 'page-add-review',
  templateUrl: 'add-review.html',
})

export class AddReviewPage {
  token : any;
  lat : any;
  lng : any;
  type : any;
  subtype : any;
  name : any;
  streetReview : boolean;
  address : any;

  reviewinfo = {
		 review: '',
		 score: ''
	};

  constructor(public navCtrl: NavController,
			public navParams: NavParams,
			public addreviewservice: AddReviewService,
			public loginService : LoginService,
			public searchService : SearchService) {
    this.token = window.sessionStorage.getItem('token');
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
	this.type = navParams.get('type');
	this.subtype = navParams.get('subtype');
	this.name = navParams.get('name');
	this.streetReview = false;
	this.searchService.getAdress(this.lat, this.lng).subscribe(data => {	
		this.address = data.res;
	});
  }

  starClicked(value){
   this.reviewinfo.score = value;
   console.log("Review score :", value);
  }

  addreview(rev) {
     if ( rev.score == "" ){
       rev.score = 0;
     }
     if(this.streetReview){
     	this.type = "street";
     	this.subtype = "default";
     }
  	 this.addreviewservice.addreview(rev, this.lat, this.lng, this.type, this.subtype, this.name).then(data => {
  	 	if(data) {
  	 		this.navCtrl.popToRoot();
  	 	}
  	 });
   }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AddReviewPage');
  }

}

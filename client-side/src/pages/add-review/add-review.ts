/*
 Author: ArthurSap
 */
import {Component} from '@angular/core';
import { NavController, NavParams, Events, ViewController } from 'ionic-angular';
import {AddReviewService} from './AddReviewService';
import { SearchService } from '../mapview/searchService';
import {SpecialConstants} from "../special-constants/special-constants"

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
  address : any;
  loading : any;

  reviewinfo = {
		 review: '',
		 score: ''
	};

  constructor(public viewCtrl: ViewController,
			public navCtrl: NavController,
			public navParams: NavParams,
			public addreviewservice: AddReviewService,
			public searchService : SearchService,
			public _constants : SpecialConstants,
			public events: Events) {
    this.token = window.sessionStorage.getItem('token');
	  this.lat = navParams.get('lat');
	  this.lng = navParams.get('lng');
	  this.type = navParams.get('type');
	  this.subtype = navParams.get('subtype');
	  this.name = navParams.get('name');
	  this.searchService.getAdress(this.lat, this.lng).subscribe(data => {
	  	this.address = data.res;
	  });
  }

  starClicked(value){
   this.reviewinfo.score = value;
   console.log("Review score :", value);
  }

  addreview(rev) {
    this.loading = this._constants.createCustomLoading();
    this.loading.present();

     if ( rev.score == "" ){
       rev.score = 0;
     }

  	 this.addreviewservice.addreview(rev, this.lat, this.lng, this.type, this.subtype, this.name).then(data => {
  	 	  if(data) {
			    this.events.publish('addreview:done', rev ,this.loading);
  	 		  this.viewCtrl.dismiss();
  	 	  }
  	 }, err => {
  	   this.loading.dismiss();
       this._constants.handleError(err);
     });
   }

  ionViewDidLoad() {
    console.log('ionViewDidLoad AddReviewPage');
  }

}

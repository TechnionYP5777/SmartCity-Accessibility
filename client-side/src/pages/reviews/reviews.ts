import {Component} from '@angular/core';
import {LoadingController, NavController, NavParams} from 'ionic-angular';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import {GetReviewsService} from './ReviewsService';

@Component({
  selector: 'page-get-reviews',
  templateUrl: 'reviews.html',
})

export class GetReviewsPage {
  lat : any;
  lng : any;
  revs : any;
  loading : any;
  service : any;
  
  constructor(public navCtrl: NavController,
   public navParams: NavParams,
   public http: Http,
   public loadingController: LoadingController,
   public getreviewsservice: GetReviewsService) {
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
	this.service = getreviewsservice;
	
	this.loading = this.loadingController.create({
      content: "ayyooo loading lmaooo"
    }); 
	
	this.revs = this.service.showMeStuff(this.loading, this.lat, this.lng);
  }
  

  ionViewDidLoad() {
    console.log('ionViewDidLoad GetReviewPage');
  }

}

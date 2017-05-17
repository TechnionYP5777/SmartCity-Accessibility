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
  
  constructor(public navCtrl: NavController,
   public navParams: NavParams,
   public http: Http,
   public loadingController: LoadingController,
   public getreviewsservice: GetReviewsService) {
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
	
	this.loading = this.loadingController.create({
      content: "ayyooo loading lmaooo"
    }); 
	
	this.loading.present();
	
	this.http.get('https://www.reddit.com/r/gifs/new/.json?limit=10').map(res => res.json()).subscribe(data => {
        this.revs = data.data.children;
        this.loading.dismiss();
    },
    err => {
        console.log("Oops!");
    });
  }
  

  ionViewDidLoad() {
    console.log('ionViewDidLoad GetReviewPage');
  }

}

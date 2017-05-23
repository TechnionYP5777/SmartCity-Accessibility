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
	this.lat = 35.2;//navParams.get('lat');
	this.lng = 34.2;//navParams.get('lng');
	this.service = getreviewsservice;
  }
  

  ionViewDidLoad() {
	  
    this.loading = this.loadingController.create({
      content: "ayyooo loading lmaooo"
    }); 
	
	this.loading.present();
	
	 this.service.showMeStuff(this.lat, this.lng).subscribe(data => {
		this.revs = data.json();
		this.loading.dismiss();
    },
    err => {
        this.loading.dismiss();
        console.log("Oops!");
    });
    
  }

}

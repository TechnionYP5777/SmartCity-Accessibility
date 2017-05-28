import {Component} from '@angular/core';
import {LoadingController, NavController, NavParams, AlertController} from 'ionic-angular';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import {GetReviewsService} from './ReviewsService';
import { LoginService } from '../login/LoginService';

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
  isLoggedin : any;
  
  constructor(public navCtrl: NavController,
   public navParams: NavParams,
   public http: Http,
   public loadingController: LoadingController,
   public getreviewsservice: GetReviewsService,
   public loginService : LoginService,
   public alertCtrl: AlertController) {
   
	this.lat = 35.2;//navParams.get('lat');
	this.lng = 34.2;//navParams.get('lng');
	this.service = getreviewsservice;
	this.isLoggedin = this.loginService.isLoggedIn();
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
  
  like(e, rev){
  	if(this.isLoggedin == true){
  		rev.upvotes++;
  		this.service.changeRevLikes(rev, 1);
  	}
  	else{
  		this.presentAlert();
  	}
  }
  
  dislike(e, rev){
  	if(this.isLoggedin == true){
  		rev.downvotes++;
  		this.service.changeRevLikes(rev, -1);
  	}
  	else{
  		this.presentAlert();
  	}
  }
  
  presentAlert() {
		let alert = this.alertCtrl.create({
		  title: 'Error',
		  subTitle: 'Please login to do that!',
		  buttons: ['OK']
		});
		alert.present();
	}

}

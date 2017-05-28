import {Component} from '@angular/core';
import {LoadingController, NavController, NavParams, AlertController} from 'ionic-angular';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import {GetReviewsService} from './ReviewsService';
import { LoginService } from '../login/LoginService';
import {AddReviewPage} from '../add-review/add-review';

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
  token : any;
  
  constructor(public navCtrl: NavController,
   public navParams: NavParams,
   public http: Http,
   public loadingController: LoadingController,
   public getreviewsservice: GetReviewsService,
   public loginService : LoginService,
   public alertCtrl: AlertController) {
   
    this.token = window.sessionStorage.getItem('token');
	this.lat = navParams.get('lat');//35.2;
	this.lng = navParams.get('lng');//34.2;
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
	
	openAddReview(){
		this.navCtrl.push(AddReviewPage, {lat : this.lat, lng : this.lng});
	}

}

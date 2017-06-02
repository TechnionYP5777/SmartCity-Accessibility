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
  type : any
  subtype : any
  name : any
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
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
	this.type = navParams.get('type');
	this.subtype = navParams.get('subtype');
	this.name = navParams.get('name');
	this.service = getreviewsservice;
	this.isLoggedin = this.loginService.isLoggedIn();
  }
  

  ionViewDidLoad() {
	  
    this.loading = this.loadingController.create({
      content: "ayyyy loading lmaooo"
    }); 
	
	this.loading.present();
	
	 this.service.showMeStuff(this.lat, this.lng, this.type, this.subtype).subscribe(data => {
		this.revs = data.json();
		this.loading.dismiss();
    },
    err => {
        this.loading.dismiss();
        this.presentAlert("Something went wrong");
    });
    
  }
  
  like(e, rev){
  	if(this.isLoggedin == true){
  		rev.upvotes++;
  		this.service.changeRevLikes(rev, 1).then(data => {
  	 		if(data) {
  	 			this.navCtrl.pop();
  	 		}
  	 	});
   	}
  	else{
  		this.presentAlert("Please login to do that!");
  	}
  }
  
  dislike(e, rev){
  	if(this.isLoggedin == true){
  		rev.downvotes++;
  		this.service.changeRevLikes(rev, -1).then(data => {
  	 		if(data) {
  	 			this.navCtrl.pop();
  	 		}
  	 	});
  	}
  	else{
  		this.presentAlert("Please login to do that!");
  	}
  }
  
  presentAlert(str) {
		let alert = this.alertCtrl.create({
		  title: 'Error',
		  subTitle: str,
		  buttons: ['OK']
		});
		alert.present();
	}
	
	openAddReview(){
		this.navCtrl.push(AddReviewPage, {lat : this.lat, lng : this.lng, type : this.type, subtype : this.subtype, name : this.name});
	}

}

import {Component} from '@angular/core';
import {LoadingController, NavController, NavParams, AlertController, ModalController, ViewController} from 'ionic-angular';
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
  
  
  constructor(public viewCtrl: ViewController,
   public modalCtrl: ModalController,
   public navCtrl: NavController,
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
	  
    this.loading = this.presentLoadingCustom();
	
	this.loading.present().then(() => {
	
	    this.service.showMeStuff(this.lat, this.lng, this.type, this.subtype, this.name).subscribe(data => {
	    	if(data) {
	    		this.revs = data.json();			 
	    	} else{
	    		this.presentAlert("Something went wrong");
	    	}
	    },
	    err => {
	    	this.presentAlert("Something went wrong");
	    });
		this.loading.dismiss();
	});
	    
  }
  
  like(e, rev){
  	if(this.isLoggedin == true){
  		rev.upvotes++;
  		this.service.changeRevLikes(rev.user.username, this.lat, this.lng, this.type, this.subtype, 1).then(data => {
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
  		this.service.changeRevLikes(rev.user.username, this.lat, this.lng, this.type, this.subtype, -1).then(data => {
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
		let clickMenu = this.modalCtrl.create(AddReviewPage, {lat : this.lat, lng : this.lng, type : this.type, subtype : this.subtype, name : this.name});
		clickMenu.present();
		this.viewCtrl.dismiss();
	}
	
	presentLoadingCustom() {
      let loading = this.loadingController.create({
        spinner: 'hide',
        content: `<div class="cssload-container">
                  <div class="cssload-whirlpool"></div>
              </div>`,
        cssClass: 'loader'
      });

       return loading;
   }

}

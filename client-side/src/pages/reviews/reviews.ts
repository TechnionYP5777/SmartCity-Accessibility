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
  userHasReview : any;
  token : any;
  username : any;
  userReview : any;
  
  
  
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
	this.userHasReview = false;
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
	});
	
	
	this.getPinnedToFront();
	
	
	if(this.isLoggedin){
			//username = 
			//this.userWroteReview();
			//this.userReviewFirst()
	}	
	
	this.loading.dismiss();
	    
  }
	
	like_dislike(e, toUpdate, rev, like){
		if(this.isLoggedin == true){
			toUpdate++;
			this.service.changeRevLikes(rev.user.username, this.lat, this.lng, this.type, this.subtype, like).then(data => {
				if(data) {
					this.navCtrl.pop();
				}
			});
		}
		else{
			this.presentAlert("Please login to do that!");
		}
	}
	
	openAddReview(){
		let clickMenu = this.modalCtrl.create(AddReviewPage, {lat : this.lat, lng : this.lng, type : this.type, subtype : this.subtype, name : this.name});
		clickMenu.present();
		this.viewCtrl.dismiss();
	}
	
	userWroteReview(){
		for(let rev of this.revs){
			if(rev.user.username == this.username){
				this.userHasReview = true;
				this.userReview = rev;
				this.revs = this.revs.filter(obj => obj !== rev);
				break;
			}
		}
	}
	
	userReviewFirst(){
		let temp = [this.userReview];
		temp.concat(this.revs);
		this.revs = temp;
	}
	
	getPinnedToFront(){
		let pinnedrevs = this.revs.filter(rev => rev.isPinned == true);
		this.revs = this.revs.filter(rev => rev.isPinned !== true);
		pinnedrevs.concat(this.revs);
		this.revs = pinnedrevs;
		
	}
	
	presentAlert(str) {
		let alert = this.alertCtrl.create({
		  title: 'Error',
		  subTitle: str,
		  buttons: ['OK']
		});
		alert.present();
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

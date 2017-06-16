import {Component} from '@angular/core';
import {LoadingController, NavController, NavParams, AlertController, ModalController, ViewController, Events} from 'ionic-angular';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import {GetReviewsService} from './ReviewsService';
import { LoginService } from '../login/LoginService';
import {AddReviewPage} from '../add-review/add-review';
import { SearchService } from '../mapview/searchService';
import { UserInformationService } from '../user-page/userInformationService';

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
  ready : any;
  address : any;
  
  
  
  constructor(public viewCtrl: ViewController,
   public modalCtrl: ModalController,
   public navCtrl: NavController,
   public navParams: NavParams,
   public http: Http,
   public loadingController: LoadingController,
   public getreviewsservice: GetReviewsService,
   public loginService : LoginService,
   public alertCtrl: AlertController,
   public userInformationService : UserInformationService,
   public searchService : SearchService,
   public events: Events) {
   
    this.token = window.sessionStorage.getItem('token');
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
	this.type = navParams.get('type');
	this.subtype = navParams.get('subtype');
	this.name = navParams.get('name');
	this.service = getreviewsservice;
	this.isLoggedin = this.loginService.isLoggedIn();	
	this.userHasReview = false;
	this.revs  = [];
	this.username = '';
	this.ready = false;
	this.searchService.getAdress(this.lat, this.lng).subscribe(data => {	
		this.address = data.res;
	});
	this.subscribeToAddReview();
  }
  

  ionViewWillEnter() {
	  
    this.presentLoadingCustom();
	
	this.service.showMeStuff(this.lat, this.lng, this.type, this.subtype, this.name).subscribe(data => {
	    	if(data) {
	    		this.revs = data.json();
				
				this.getPinnedToFront();
	
				if(this.isLoggedin){
					this.userInformationService.getUserProfile().subscribe(data => {
						if(data){
							this.username = data.username;
							this.userWroteReview();
							this.userReviewFirst();
						} else{
							this.presentAlert("Something went wrong");
						}
						
					});					
				}
				this.loading.dismiss();
				this.ready = true;
	    	} else{
				this.loading.dismiss();
	    		this.presentAlert("Something went wrong");
	    	}
	    },
	    err => {
	    	this.presentAlert("Something went wrong");
			this.loading.dismiss();
	});	
  }
	
	like_dislike(e, rev, like){
		if(this.isLoggedin == true){
			if(this.checkAlreadyLiked(rev, like)){
				return;
			}
			if(like>0) rev.upvotes++;
			else rev.downvotes++;
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
		let addReview = this.modalCtrl.create(AddReviewPage, {lat : this.lat, lng : this.lng, type : this.type, subtype : this.subtype, name : this.name});
		addReview.onDidDismiss(() => {
			this.ionViewWillEnter();
		});
		addReview.present();
	}
	
	subscribeToAddReview(){
		this.events.subscribe('addreview:done', (rev,loading) => {
			loading.dismiss();
		});			
	}
	
	checkAlreadyLiked(rev, like){
		for(let comm of rev.comments){
			if(comm.commentator.username == this.username && comm.rating == like) return true;
			else return false
		}
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
		if(this.userHasReview){
			let temp = [this.userReview];
			temp = temp.concat(this.revs);
			this.revs = temp;
		}
	}
	
	getPinnedToFront(){
		let pinnedrevs = this.revs.filter(rev => rev.isPinned == true);
		this.revs = this.revs.filter(rev => rev.isPinned !== true);
		pinnedrevs = pinnedrevs.concat(this.revs);
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
      this.loading = this.loadingController.create({
		spinner: 'hide',
		dismissOnPageChange: true,
        content: `<div class="cssload-container">
                  <div class="cssload-whirlpool"></div>
              </div>`,
        cssClass: 'loader'
      });

       this.loading.present();
   }

}

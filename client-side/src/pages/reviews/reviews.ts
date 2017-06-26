import {Component} from '@angular/core';
import {LoadingController, NavController, NavParams, AlertController, ModalController, ViewController, Events} from 'ionic-angular';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import {GetReviewsService} from './ReviewsService';
import { LoginService } from '../login/LoginService';
import {AddReviewPage} from '../add-review/add-review';
import { SearchService } from '../mapview/searchService';
import { UserInformationService } from '../user-page/userInformationService';
import {CommentPage} from "../comment/comment";

@Component({
  selector: 'page-get-reviews',
  templateUrl: 'reviews.html',
})

export class GetReviewsPage {
  lat : any;
  lng : any;
  type : any;
  subtype : any;
  name : any;
  location : any;
  revs : any;
  loading : any;
  service : any;
  isLoggedin : any;
  isAdmin : any;
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
	this.location = {lat : this.lat, lng : this.lng, type : this.type, subtype : this.subtype}
	this.name = navParams.get('name');
	this.service = getreviewsservice;
	this.isLoggedin = this.loginService.isLoggedIn();
	this.isAdmin = false;
	this.userHasReview = false;
	this.revs  = [];
	this.username = '';
	this.ready = false;
	this.searchService.getAdress(this.lat, this.lng).subscribe(data => {
		this.address = data.res;
	});
	this.subscribeToAddReview();
	this.subscribeToCommentPosted();
  }


  ionViewDidEnter() {

	 console.log('ionViewDidEnter ShowReviewPage');

    this.presentLoadingCustom();

	this.service.showMeStuff(this.location, this.name).subscribe(data => {

	    	if(data) {
	    		this.revs = data.json();
				  this.getPinnedToFront();

				if(this.isLoggedin){
				  this.isAdmin = JSON.parse(this.token).admin;
					this.userInformationService.getUserProfile().subscribe(data => {
						if(data){
							this.username = data.username;
							this.userWroteReview();
							this.userReviewFirst();
              this.loading.dismiss();
						} else{
              this.loading.dismiss();
							this.presentAlert("Something went wrong");
						}

					});
				} else {this.loading.dismiss();}
				this.ready = true;
	    	} else{
          this.loading.dismiss();
	    		this.presentAlert("Something went wrong");
	    	}
	    },
	    err => {
          this.loading.dismiss();
          this.presentAlert("Something went wrong");

	});
  }

	like_dislike(e, rev, like){
		if(this.isLoggedin == true){
			let arr = this.checkAlreadyLiked(rev, like);
			if(arr[0] && arr[1]){
				return;
			}

			this.presentLoadingCustom();

			if(like>0){
				rev.upvotes++;
				if(!arr[1]) rev.downvotes--;
			}
			else {
				rev.downvotes++;
				if(!arr[1]) rev.upvotes--;
			}

			this.service.changeRevLikes(rev.user.username, this.location, like).then(data => {
				this.loading.dismiss();
			});
		}
		else{
			this.presentAlert("Please login to do that!");
		}
	}

	deleteReview(e, rev){

  }

  pinUnpinReview(e, rev){

  }

	openAddReview(){
		let addReview = this.modalCtrl.create(AddReviewPage, {lat : this.lat, lng : this.lng, type : this.type, subtype : this.subtype, name : this.name});
		addReview.present();
	}

	openCommentPage(e, userRev){
    let commentPage = this.modalCtrl.create(CommentPage, {lat : this.lat, lng : this.lng, type : this.type, subtype : this.subtype, loggedIn : this.isLoggedin, username : this.username, rev : userRev});
    commentPage.present();
  }

	subscribeToAddReview(){
		this.events.subscribe('addreview:done', (rev,loading) => {
			loading.dismiss();
			console.log('AddReviewPage loading dismissed');
		});
	}

  subscribeToCommentPosted(){
    this.events.subscribe('commentposted:done', (rev,comm) => {
      console.log('Comment event captured');
      for(var r in this.revs){
        if(this.revs[r] == rev){
          this.revs[r].comments.push(comm);
          this.revs[r].realComments.push(comm);
        }
      }
    });
  }

	checkAlreadyLiked(rev, like){
		let boolArray = [false, false];
		for(let comm of rev.comments){
			if(comm.commentator.username == this.username){
				boolArray[0] = true;
				if(comm.rating == like)
					boolArray[1] = true;
				comm.rating = like;
			}
		}

		return boolArray;
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

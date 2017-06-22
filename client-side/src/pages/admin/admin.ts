import { Component } from '@angular/core';
import { NavController, ModalController, NavParams, LoadingController, Events, AlertController } from 'ionic-angular';
import { HelpfulUsersPage } from '../helpfulUsers/helpfulUsers';
import { MostRatedLocsPage } from '../mostRatedLocs/mostRatedLocs';
import { AdminService } from './adminService';
import { Constants } from "../constants";
import { UserPagePage } from '../user-page/user-page'

@Component({
  selector: 'page-admin',
  templateUrl: 'admin.html',
  
  
})

export class AdminPage {
  output :  any;
  name: any;
  rating: any;
  hlp: any;
  loading: any;
  numOfUsers: any = -1;
  numOfReviews: any = -1;
  serverAddress: any = Constants.serverAddress;
  imageProfileUrl : any;  
  
  constructor(public navCtrl: NavController, public navParams: NavParams,
			  public adminService : AdminService, public modalCtrl: ModalController,
			  public loadingCtrl: LoadingController, public events: Events,
			  public alertCtrl: AlertController) {
		this.adminService.getUserProfile().subscribe(data => {
			this.name = data.username;
			this.rating=data.rating;
			this.numOfReviews=data.numOfReviews;
			(this.numOfReviews == 0) ? this.hlp = 0 : this.hlp = (this.rating / this.numOfReviews);
			
		});
		this.findImageProfileURL();
		this.adminService.numOfUsers().subscribe(data => this.numOfUsers = data);
  }
  
  findImageProfileURL() {
	try{
		var token = JSON.parse(window.sessionStorage.getItem('token')).token;
	}
	catch(err){
		token = "no token";
	}
	this.imageProfileUrl = 	Constants.serverAddress+'/profileImg?token='+token;
  }
  
  showUsers(n) {
	  if (n <= 0 || n == null) {
			this.presentAlert('Parameter must be a positive number');
			return;
	  }
	  let users = this.modalCtrl.create(HelpfulUsersPage,{num: n});
	  users.present();
	  this.presentLoadingCustom();
  }
  
  getUserPage() {
	  this.navCtrl.push(UserPagePage);
  }
  
  showLocations(numOfLocations, radius, initLoc) {
	  if (numOfLocations <= 0 || numOfLocations == null) {
		  this.presentAlert('The number of locations must be a positive number');
		  return;
	  }
	  if (radius <= 0 || radius == null) {
		  this.presentAlert('The radius must be a positive number');
		  return;
	  }
	  let locations = this.modalCtrl.create(MostRatedLocsPage,{n: numOfLocations, r: radius, l: initLoc});
	  locations.present();
	  this.presentLoadingCustom();
  }
  
	presentLoadingCustom() {
            this.loading = this.loadingCtrl.create({
            spinner: 'bubbles',
		    showBackdrop: false,
		    cssClass: 'loader'
        });
        this.loading.present();
		 this.events.subscribe('gotResults', () => this.loading.dismiss() );
   }
   
   
   presentAlert(str) {
		let alert = this.alertCtrl.create({
		  title: 'Alert',
		  subTitle: str,
		  buttons: ['OK']
		});
		alert.present();
	}
	
	handleError(err) {
		this.presentAlert("error is: " + err.error + " message is: " + err.message);
    }
}

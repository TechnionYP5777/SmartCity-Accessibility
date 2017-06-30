import { Component } from '@angular/core';
import { NavController, ModalController, NavParams, LoadingController, Events, AlertController } from 'ionic-angular';
import { HelpfulUsersPage } from '../helpfulUsers/helpfulUsers';
import { MostRatedLocsPage } from '../mostRatedLocs/mostRatedLocs';
import { AdminService } from './adminService';
import { Constants } from "../constants";
import { UserPagePage } from '../user-page/user-page'
import {SpecialConstants} from "../special-constants/special-constants";

@Component({
  selector: 'page-admin',
  templateUrl: 'admin.html',
  
  
})

/*
	author: Koral Chapnik
*/

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
			  public alertCtrl: AlertController, public _constants : SpecialConstants) {
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
	  this.loading = this._constants.createCustomLoading();
      this.loading.present();
	  this.events.subscribe('gotResults', () => this.loading.dismiss().catch(() => {}) );
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
	  if (initLoc == null) {
		 this.presentAlert('Please enter an initial location');
		  return; 
	  }
	  if ( radius > 5 ) {
		  this.presentConfirmShowLocations(numOfLocations, radius, initLoc);
	  } else {
		   this.actualShowLocations(numOfLocations, radius, initLoc);
	  }
	  
  }
  
  actualShowLocations(numOfLocations, radius, initLoc) {
	  let locations = this.modalCtrl.create(MostRatedLocsPage,{n: numOfLocations, r: radius, l: initLoc});
	  locations.present();
	  this.loading = this._constants.createCustomLoading();
	  this.loading.present();
	  this.events.subscribe('gotResults', () => this.loading.dismiss().catch(() => {}) );
  }
   
   
   presentConfirmShowLocations(numOfLocations, radius, initLoc) {
	  let alert = this.alertCtrl.create({
		title: 'Alert!',
		message: 'You have chosen radius higher than 5 KM and this operation can take a few minutes. Are you sure you want to continue?',
		buttons: [
		  {
			text: 'Cancel',
			role: 'cancel',
			handler: () => {
			  console.log('Cancel clicked');
			}
		  },
		  {
			text: 'Continue',
			handler: () => {
			  this.actualShowLocations(numOfLocations, radius, initLoc);
			}
		  }
		]
	  });
	  alert.present();
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
		if(err.error == null)
			this.presentAlert(Constants.serverNotResponding);
		else 
			this.presentAlert("error: " + err.message);
    }
}

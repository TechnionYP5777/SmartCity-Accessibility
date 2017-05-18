import { Component } from '@angular/core';
import { LoginPage } from '../login/login';
import { NavController, AlertController } from 'ionic-angular';
import { MapviewPage } from '../mapview/mapview';
import { UserPagePage } from '../user-page/user-page';
import { LoginService } from '../login/LoginService';
import { AdminPage } from '../admin/admin';
import { AddReviewPage } from '../add-review/add-review';
import { GetReviewsPage } from '../reviews/reviews'; 

@Component({
  selector: 'page-home',  
  templateUrl: 'home.html'
})
export class HomePage {
  isLoggedin : any;
  loginPage = LoginPage;
  mapviewPage = MapviewPage;
  adminPage = AdminPage;
  addReviewPage = AddReviewPage;
  showReviews = GetReviewsPage;
  
    constructor(public navCtrl: NavController,public loginService : LoginService,public alertCtrl: AlertController) {
    }
  
    ionViewDidEnter(){
	    this.isLoggedin = this.loginService.isLoggedIn();
    }
  
    userprofile(){
		this.isLoggedin = this.loginService.isLoggedIn();
		if(!this.isLoggedin)
			this.presentAlert("Sorry, it seems you were not active for 10 minutes. Please re-login.");
		else 
		    this.navCtrl.push(UserPagePage);
	}

	presentAlert(str) {
		let alert = this.alertCtrl.create({
		  title: 'Alert',
		  subTitle: str,
		  buttons: ['OK']
		});
		alert.present();
	}
}

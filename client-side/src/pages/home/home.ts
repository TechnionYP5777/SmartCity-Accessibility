import { Component, trigger, state, style, transition, animate} from '@angular/core';
import { LoginPage } from '../login/login';
import { NavController, AlertController } from 'ionic-angular';
import { MapviewPage } from '../mapview/mapview';
import { UserPagePage } from '../user-page/user-page';
import { LoginService } from '../login/LoginService';
import { AdminPage } from '../admin/admin';
import { AddReviewPage } from '../add-review/add-review';
import { GetReviewsPage } from '../reviews/reviews'; 
import { Constants } from "../constants";

@Component({
  selector: 'page-home',  
  templateUrl: 'home.html',
  
   animations: [
 
    trigger('flip', [
      state('flipped', style({
        transform: 'rotate(180deg)',
        backgroundColor: '#f50e80'
      })),
      transition('* => flipped', animate('400ms ease'))
    ])
   ]
})
export class HomePage {
  isLoggedin : any;
  loginPage = LoginPage;
  mapviewPage = MapviewPage;
  adminPage = AdminPage;
  addReviewPage = AddReviewPage;
  showReviews = GetReviewsPage;
  flipState: String = 'notFlipped';
    constructor(public navCtrl: NavController,public loginService : LoginService,public alertCtrl: AlertController) {
    }
  
    ionViewDidEnter(){
	    this.isLoggedin = this.loginService.isLoggedIn();
    }
  
    userprofile(){
		this.isLoggedin = this.loginService.isLoggedIn();
		if(!this.isLoggedin)
			this.presentAlert(Constants.userExpiredMessage);
		else {
			var token = JSON.parse(window.sessionStorage.getItem('token'));	
		    if(token.admin == true)
				this.navCtrl.push(this.adminPage);
			else this.navCtrl.push(UserPagePage);
		}
	}

	presentAlert(str) {
		let alert = this.alertCtrl.create({
		  title: 'Alert',
		  subTitle: str,
		  buttons: ['OK']
		});
		alert.present();
	}
	
	toggleFlip() {
		this.flipState = (this.flipState == 'notFlipped') ? 'flipped' : 'notFlipped';
	}
}

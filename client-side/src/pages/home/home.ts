import { Component } from '@angular/core';
import { LoginPage } from '../login/login';
import { NavController } from 'ionic-angular';
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
  userProfile = UserPagePage;
  adminPage = AdminPage;
  addReviewPage = AddReviewPage;
  showReviews = GetReviewsPage;
  
  constructor(public navCtrl: NavController,public loginService : LoginService) {
  }
  
  ionViewDidEnter(){
	this.isLoggedin = this.loginService.isLoggedIn();
  }

}

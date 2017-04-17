import { Component } from '@angular/core';
import { LoginPage } from '../login/login';
import { NavController } from 'ionic-angular';
import { MapviewPage } from '../mapview/mapview';
import { UserPagePage } from '../user-page/user-page';
import { LoginService } from '../login/LoginService';

@Component({
  selector: 'page-home',  
  templateUrl: 'home.html'
})
export class HomePage {
  isLoggedin : any;
  loginPage = LoginPage;
  mapviewPage = MapviewPage;
  userProfile = UserPagePage;
  constructor(public navCtrl: NavController,public loginService : LoginService) {
    this.isLoggedin = this.loginService.isLoggedIn();
  }

}

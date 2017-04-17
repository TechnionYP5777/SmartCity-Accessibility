import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { NavigationService } from './navigationService';
import {MapviewPage} from '../mapview/mapview';
import { LoginService } from '../login/LoginService';
import { UserPagePage } from '../user-page/user-page';

@Component({
  selector: 'page-navigation',
  templateUrl: 'navigation.html'
}) 
export class NavigationPage {
    isWork : any;
    isLoggedin : any;
    constructor(public navCtrl: NavController, public navParams: NavParams, public navigationService: NavigationService,public loginService : LoginService) {
	    var token = window.sessionStorage.getItem('token');
		this.isWork = token;
		this.isLoggedin = this.loginService.isLoggedIn();
	}

	startNavigation(){
		let t = this.navigationService.navigatee();
		t.then(data => {
            if(data) {
            }
		});
		
	}
}

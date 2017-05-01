import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { NavigationService } from './navigationService';
import {MapviewPage} from '../mapview/mapview';
import { LoginService } from '../login/LoginService';
import { UserPagePage } from '../user-page/user-page';
import { Geolocation } from '@ionic-native/geolocation';

@Component({
  selector: 'page-navigation',
  templateUrl: 'navigation.html'
}) 
export class NavigationPage {
    isWork : any;
    isLoggedin : any;
	geolocation: Geolocation;
	srcLocation = {
		lat : '',
		lng : ''
	};
    constructor(public navCtrl: NavController, public navParams: NavParams, public navigationService: NavigationService,public loginService : LoginService) {
	    var token = window.sessionStorage.getItem('token');
		this.isWork = token;
		this.isLoggedin = this.loginService.isLoggedIn();
		this.geolocation = new Geolocation();
		this.geolocation.getCurrentPosition().then((position) => {
			this.srcLocation.lat = String(position.coords.latitude);
			this.srcLocation.lng = String(position.coords.longitude);
		});
	}

	startNavigation(){
		let t = this.navigationService.navigatee();
		t.then(data => {
            if(data) {
            }
		});
		
	}
}

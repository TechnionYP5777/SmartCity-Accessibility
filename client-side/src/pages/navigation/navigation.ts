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
	dstLocation = {
		lat : '',
		lng : ''
	};
    constructor(public navCtrl: NavController, public navParams: NavParams, public navigationService: NavigationService,public loginService : LoginService) {
	    var token = window.sessionStorage.getItem('token');
		this.isWork = token;
		this.isLoggedin = this.loginService.isLoggedIn();
		this.dstLocation.lat = navParams.get('lat');
		this.dstLocation.lng = navParams.get('lng');
	}
	ionViewDidLoad(){
        this.geolocation = new Geolocation();
		this.geolocation.getCurrentPosition().then((position) => {
			this.srcLocation.lat = String(position.coords.latitude);
			this.srcLocation.lng = String(position.coords.longitude);
		});
    }
	startNavigation(){
		this.navigationService.navigatee(this.srcLocation,this.dstLocation).subscribe(data => {
            this.isWork = data.json();
		});
		
	}
}

import { Component } from '@angular/core';
import { NavController, NavParams, Events, AlertController,LoadingController } from 'ionic-angular';
import { NavigationService } from './navigationService';
import { LoginService } from '../login/LoginService';
import { UserPagePage } from '../user-page/user-page';
import { Geolocation } from '@ionic-native/geolocation';
import { LoginPage } from '../login/login';
import { Constants } from "../constants";

@Component({
  selector: 'page-navigation',
  templateUrl: 'navigation.html'
}) 

/*
	author: Yael Amitay
*/
export class NavigationPage {
    isWork : any;
    isLoggedin : any;
	geolocation: Geolocation;
	minRating: any;
	userProfile = UserPagePage;
	loginPage = LoginPage;
	srcLocation = {
		lat : '',
		lng : ''
	};
	dstLocation = {
		lat : '',
		lng : ''
	};
	loading : any;
	
    constructor(public navCtrl: NavController, public navParams: NavParams,public loadingCtrl: LoadingController,public alertCtrl: AlertController, public navigationService: NavigationService,public loginService : LoginService,public events: Events) {
		this.isLoggedin = this.loginService.isLoggedIn();
		this.dstLocation.lat = navParams.get('lat');
		this.dstLocation.lng = navParams.get('lng');
		this.minRating = 5;
	}
	ionViewDidLoad(){
    }
	
	startNavigation(){
	    this.presentLoadingCustom();
		this.geolocation = new Geolocation();
		this.geolocation.getCurrentPosition().then((position) => {
			this.srcLocation.lat = String(position.coords.latitude);
			this.srcLocation.lng = String(position.coords.longitude);
			
			this.navigationService.navigatee(this.srcLocation,this.dstLocation,this.minRating).subscribe(
			data => {
				this.events.publish('navigation:done', data.json(),this.loading);
			}
			, err => {
				this.handleError(err.json());
			}
			);
		});
		this.navCtrl.pop();
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
			this.presentAlert("<p> error is: "+err.error+ "</p> <p> message is: "+ err.message+"</p>");
		this.events.publish('navigation:done', null,this.loading);
    }
	
	presentLoadingCustom() {
            this.loading = this.loadingCtrl.create({
            spinner: 'bubbles',
		    showBackdrop: false,
		    cssClass: 'loader'
        });
        this.loading.present();
    }
}

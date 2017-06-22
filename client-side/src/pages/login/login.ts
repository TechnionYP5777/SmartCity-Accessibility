import { Component } from '@angular/core';
import { NavController, Events, LoadingController, AlertController } from 'ionic-angular';
import { LoginService } from './LoginService';
import { SignupPage } from '../signup/signup';

@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {

	usercreds = {
		 name: '',
		 password: ''
	};
	loading : any;
    constructor(public navCtrl: NavController, public loginservice: LoginService, public alertCtrl: AlertController, public events: Events,public loadingCtrl: LoadingController) {}

    login(user) {
		if(user.name == ''){
			this.presentAlert("please insert a user-name");
			return;
		}
		if(user.password == ''){
			this.presentAlert("please insert a password");
			return;
		}
		
		this.presentLoadingCustom();
        this.loginservice.login(user).then(data => {
            if(data) {
				setTimeout(() => { this.events.publish('login:updateState'); }, this.loginservice.timeout());
				this.events.publish('login:updateState');
                this.navCtrl.popToRoot();
				this.loading.dismiss();
            } else{
				this.loading.dismiss();
			}
		});
    }
    signup() {
        this.navCtrl.push(SignupPage);
    }

	presentLoadingCustom() {
            this.loading = this.loadingCtrl.create({
            spinner: 'bubbles',
		    showBackdrop: false,
		    cssClass: 'loader'
        });
        this.loading.present();
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

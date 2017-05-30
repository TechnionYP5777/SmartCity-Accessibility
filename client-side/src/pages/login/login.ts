import { Component } from '@angular/core';
import { NavController, Events, LoadingController } from 'ionic-angular';
import { LoginService } from './LoginService';
import { HomePage } from '../home/home';
import { SignupPage } from '../signup/signup';
import { MapviewPage } from '../mapview/mapview';

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
    constructor(public navCtrl: NavController, public loginservice: LoginService, public events: Events,public loadingCtrl: LoadingController) {}

    login(user) {
		this.presentLoadingCustom();
        this.loginservice.login(user).then(data => {
            if(data) {
				setTimeout(() => { this.events.publish('login:updateState'); }, this.loginservice.timeout());
				this.events.publish('login:updateState');
                this.navCtrl.popToRoot();
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
}

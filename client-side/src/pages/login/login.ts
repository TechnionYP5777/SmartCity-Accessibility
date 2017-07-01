import { Component } from '@angular/core';
import { NavController, Events, LoadingController, AlertController } from 'ionic-angular';
import { LoginService } from './LoginService';
import { SignupPage } from '../signup/signup';

@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})

/*
	author: Yael Amitay
*/
export class LoginPage {

	usercreds = {
		 name: '',
		 password: ''
	};
	loading : any;
	
    constructor(public navCtrl: NavController, public loginservice: LoginService, 
	            public alertCtrl: AlertController, public events: Events,
				public loadingController: LoadingController) {
	}

    login(user) {
		if(user.name == ''){
			this.presentAlert("please insert a user-name");
			return;
		}
		if(user.password == ''){
			this.presentAlert("please insert a password");
			return;
		}
		
		this.createCustomLoading();
        this.loginservice.login(user).then(data => {
            if(data) {
				setTimeout(() => { this.events.publish('login:updateState'); }, this.loginservice.timeout());
				this.events.publish('login:updateState');
				this.loading.dismiss();
				this.navCtrl.popToRoot();
            } else{
				this.loading.dismiss();
			}
		});
    }
	
	createCustomLoading() {
		this.loading = this.loadingController.create({
		  spinner: 'hide',
		  dismissOnPageChange: false,
		  content: `<div class="cssload-container">
					  <div class="cssload-whirlpool"></div>
				  </div>`,
		  cssClass: 'loader'
		});
		this.loading.present();
    }
	
    signup() {
        this.navCtrl.push(SignupPage);
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

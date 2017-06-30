import { Component } from '@angular/core';
import { NavController, Events, AlertController, LoadingController } from 'ionic-angular';
import { LoginService } from '../login/LoginService';
import {SpecialConstants} from "../special-constants/special-constants";


@Component({
  selector: 'page-signup',
  templateUrl: 'signup.html'
})

/*
	author: Yael Amitay
*/
export class SignupPage {
	newcreds = {
	    name: '',
		password: ''
	};
  
  loading : any;
  
  constructor(public navCtrl: NavController, public loginService: LoginService,
              public loadingCtrl: LoadingController,public events: Events, 
			  public alertCtrl: AlertController, public _constants : SpecialConstants) {
  }

  register(user) {
		if(user.name == ''){
			this.presentAlert("please insert a user-name");
			return;
		}
		if(user.password == ''){
			this.presentAlert("please insert a password");
			return;
		}
		this.loading = this._constants.createCustomLoading();
		this.loading.present();
        this.loginService.signup(user).then(data => {
            if(data) {
                var alert = this.alertCtrl.create({
                    title: 'Success',
                    subTitle: 'User Created',
                    buttons: ['ok']
                });
				setTimeout(() => { this.events.publish('login:updateState'); }, this.loginService.timeout());
				this.events.publish('login:updateState');
                alert.present();
				this.loading.dismiss().catch(() => {});
                this.navCtrl.popToRoot();
            } else {
				this.loading.dismiss().catch(() => {});
			}
		});
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

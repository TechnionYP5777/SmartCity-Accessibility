import { Component } from '@angular/core';
import { NavController, Events, AlertController, LoadingController } from 'ionic-angular';
import { LoginService } from '../login/LoginService';


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
              public loadingController: LoadingController,public events: Events, 
			  public alertCtrl: AlertController) {
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
		this.createCustomLoading();
        this.loginService.signup(user).then(data => {
            if(data) {
				setTimeout(() => { this.events.publish('login:updateState'); }, this.loginService.timeout());
				this.loading.dismiss();
				this.events.publish('login:updateState');
				this.navCtrl.popToRoot();
            } else {
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
	
	presentAlert(str) {
		let alert = this.alertCtrl.create({
		  title: 'Alert',
		  subTitle: str,
		  buttons: ['OK']
		});
		alert.present();
	}
}

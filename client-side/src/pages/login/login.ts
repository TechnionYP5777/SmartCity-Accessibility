import { Component } from '@angular/core';
import { NavController,Events } from 'ionic-angular';
import { LoginService } from './LoginService';
import { HomePage } from '../home/home';
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
 
    constructor(public navCtrl: NavController, public loginservice: LoginService, public events: Events) {}
  
    login(user) {
        this.loginservice.login(user).then(data => {
            if(data) {
				setTimeout(() => { this.events.publish('login:updateState'); }, this.loginservice.timeout());
				this.events.publish('login:updateState');
                this.navCtrl.setRoot(HomePage);
            } 
		});
    }
    signup() {
        this.navCtrl.push(SignupPage);
    }
}





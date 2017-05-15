import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
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
 
    constructor(public navCtrl: NavController, public loginservice: LoginService) {}
  
    login(user) {
        this.loginservice.login(user).then(data => {
            if(data) {
                this.navCtrl.setRoot(HomePage);
            } 
		});
    }
    signup() {
        this.navCtrl.push(SignupPage);
    }
}





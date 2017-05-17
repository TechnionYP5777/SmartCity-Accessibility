import { Component } from '@angular/core';
import { NavController, AlertController } from 'ionic-angular';
import { LoginService } from '../login/LoginService'; 
import { HomePage } from '../home/home';


@Component({
  selector: 'page-signup',
  templateUrl: 'signup.html'
})
export class SignupPage {
	newcreds = {
	    name: '',
		password: ''
	};
  constructor(public navCtrl: NavController, public loginService: LoginService, public alertCtrl: AlertController) {}
 
  register(user) {
        this.loginService.signup(user).then(data => {
            if(data) {
                var alert = this.alertCtrl.create({
                    title: 'Success',
                    subTitle: 'User Created',
                    buttons: ['ok']
                });
                alert.present();
				this.navCtrl.setRoot(HomePage);
            }
    });
}
}

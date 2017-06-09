import { Component } from '@angular/core';
import { NavController, Events, AlertController } from 'ionic-angular';
import { LoginService } from '../login/LoginService';


@Component({
  selector: 'page-signup',
  templateUrl: 'signup.html'
})
export class SignupPage {
	newcreds = {
	    name: '',
		password: ''
	};
  constructor(public navCtrl: NavController, public loginService: LoginService,public events: Events, public alertCtrl: AlertController) {}

  register(user) {
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
                this.navCtrl.popToRoot();
            }
    });
}
}

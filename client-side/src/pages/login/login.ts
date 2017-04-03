import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {LoginService} from '../../providers/login-service';
@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
  providers: [LoginService]
})

export class LoginPage {
    str: any;
	num: any;
	serve : any;
  constructor(public navCtrl: NavController, public navParams: NavParams,serve: LoginService) {
	this.serve = serve;
  }
  ionViewDidLoad() {
	this.serve.callHttp().subscribe(data => {
      this.str = data.str;
	  this.num = data.num;
    });
  }
}






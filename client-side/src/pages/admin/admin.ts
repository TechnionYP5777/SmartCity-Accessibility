import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { LoginService } from '../login/LoginService';
import { AdminService } from './adminService';

@Component({
  selector: 'page-admin',
  templateUrl: 'admin.html'
})

export class AdminPage {
  output :  any;
  UserName: any;
  constructor(public navCtrl: NavController, public navParams: NavParams, public loginService : LoginService, public adminService : AdminService) {
	  
  }
 
  ionViewDidLoad() {
    console.log('ionViewDidLoad UserPagePage');
  }
  

}

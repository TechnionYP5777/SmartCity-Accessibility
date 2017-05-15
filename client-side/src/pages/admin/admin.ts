import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { LoginService } from '../login/LoginService';
import { AdminService } from './adminService';
import { UserInformationService } from '../user-page/userInformationService';

@Component({
  selector: 'page-admin',
  templateUrl: 'admin.html'
})

export class AdminPage {
  output :  any;
  name: any;
  constructor(public navCtrl: NavController, public navParams: NavParams,
			  public loginService : LoginService, public adminService : AdminService,
              public userInformationService : UserInformationService) {
		this.userInformationService.getUserProfile().subscribe(data => {
			this.name = data.username;
		});
  }
 
  ionViewDidLoad() {
    console.log('ionViewDidLoad UserPagePage');
  }
  

}

import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {AddSearchQueryPage} from '../addSearchQueryMenu/addsearchquerymenu';
import { LoginService } from '../login/LoginService';
import { UserInformationService } from './userInformationService';

@Component({
  selector: 'page-user-page',
  templateUrl: 'user-page.html'
})

export class UserPagePage {
  output :  any;
  UserName: any;
  addSearchQueryPage = AddSearchQueryPage;
	constructor(public navCtrl: NavController, public navParams: NavParams, public loginService : LoginService, public userInformationService : UserInformationService) {
		this.UserName = "WUT";
		this.output = "WUT";
	}
 
  ionViewDidLoad() {
    console.log('ionViewDidLoad UserPagePage');
  }
  
  addNewSearchQuery(){
		this.navCtrl.push(this.addSearchQueryPage);
  }
  
  showSearchQueries(){
		this.userInformationService.getEasyJsonExample().subscribe(data => {
			this.output = data.username;
			this.UserName = data.username;;
		}); 
  }

}

import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {AddSearchQueryPage} from '../addSearchQueryMenu/addsearchquerymenu';
import {ViewSearchQueryPage} from '../viewSearchQuery/viewsearchquery';
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
  viewSearchQueryPage = ViewSearchQueryPage;
	constructor(public navCtrl: NavController, public navParams: NavParams, public loginService : LoginService, public userInformationService : UserInformationService) {
		this.userInformationService.getUserProfile().subscribe(data => {
			this.UserName = data.username;
		});
	}
 
  ionViewDidLoad() {
    console.log('ionViewDidLoad UserPagePage');
  }
  
  addNewSearchQuery(){
		this.navCtrl.push(this.addSearchQueryPage);
  }
  
  showSearchQueries(){
	    this.navCtrl.push(this.viewSearchQueryPage);
  }

}

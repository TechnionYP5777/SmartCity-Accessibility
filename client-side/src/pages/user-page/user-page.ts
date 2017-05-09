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
  constructor(public navCtrl: NavController, public navParams: NavParams, public loginService : LoginService, public userInformationService : UserInformationService) {
	    this.userInformationService.getUserName().subscribe(data => {
		this.output = data.favouriteSearchQueries;
	    this.UserName = data.name;
		});
	  
  }
 
  ionViewDidLoad() {
    console.log('ionViewDidLoad UserPagePage');
  }
  
  addNewSearchQuery(){
	    this.userInformationService.getUserName().subscribe(data => {
		this.output = data.favouriteSearchQueries;
	    this.UserName = data.name;
		});
  }
  
  showSearchQueries(){
	 this.output = "!!!GMO"; 
	 console.log('this happened'); 
  }

}

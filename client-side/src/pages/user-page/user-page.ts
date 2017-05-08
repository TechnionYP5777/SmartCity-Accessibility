import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {AddSearchQueryPage} from '../addSearchQueryMenu/addsearchquerymenu';
import { LoginService } from '../login/LoginService';

@Component({
  selector: 'page-user-page',
  templateUrl: 'user-page.html'
})

export class UserPagePage {
  output :  string;
  UserName: any;
  constructor(public navCtrl: NavController, public navParams: NavParams, public loginService : LoginService) {
	  this.output = loginService.getToken();
	  this.UserName = "UserName";
  }
 
  ionViewDidLoad() {
    console.log('ionViewDidLoad UserPagePage');
  }
  
  addNewSearchQuery(){
	 this.output = "OMG!!!"; 
	 console.log('this happened');
  }
  
  showSearchQueries(){
	 this.output = "!!!GMO"; 
	 console.log('this happened'); 
  }

}

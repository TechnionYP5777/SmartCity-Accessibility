import { Component } from '@angular/core';
import { NavController, NavParams, ModalController, ViewController } from 'ionic-angular';
import {AddSearchQueryPage} from '../addSearchQueryMenu/addsearchquerymenu';
import {ViewSearchQueryPage} from '../viewSearchQuery/viewsearchquery';
import { LoginService } from '../login/LoginService';
import { UserInformationService } from './userInformationService';
import { MapviewPage } from '../mapview/mapview';

@Component({
  selector: 'page-user-page',
  templateUrl: 'user-page.html'
})

export class UserPagePage {
  output :  any;
  UserName: any;
  quries: any
  addSearchQueryPage = AddSearchQueryPage;
  viewSearchQueryPage = ViewSearchQueryPage;
  
	constructor(public navCtrl: NavController, public navParams: NavParams, public viewCtrl: ViewController, public modalCtrl: ModalController, public loginService : LoginService, public userInformationService : UserInformationService) {
		this.userInformationService.getUserProfile().subscribe(data => {
			this.UserName = data.username;
		});
		
		this.userInformationService.getUserQuries().subscribe(data => {
			this.quries = data.res2;
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
  
  cc(query){
	  let clickMenu = this.modalCtrl.create(MapviewPage,{adress:query.adress});
	  this.viewCtrl.dismiss();
  }

}

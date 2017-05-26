import { Component } from '@angular/core';
import { NavParams, ModalController,App, ViewController } from 'ionic-angular';
import { LocationListPage } from '../location-list/location-list';
import { AdminService } from '../admin/adminService';

@Component({
  selector: 'page-helpfulUsers',
  templateUrl: 'helpfulUsers.html',
})

export class HelpfulUsersPage {
  usersArray: any;  
  num: any = 0;
  users: any;
  len: any = 0;
  
   constructor(public viewCtrl: ViewController,public appCtrl: App, 
			public navParams: NavParams,public modalCtrl: ModalController,
			public adminService : AdminService) {
	this.num = navParams.get('num');
  } 
  
  ionViewDidLoad() {
	   this.adminService.helpfulUsers(this.num).subscribe(data => {
		  this.users = data;
	  });
	 
  }

}

import { Component } from '@angular/core';
import { NavParams, ModalController,App, ViewController , Events} from 'ionic-angular';
import { LocationListPage } from '../location-list/location-list';
import { AdminService } from '../admin/adminService';

@Component({
  selector: 'page-helpfulUsers',
  templateUrl: 'helpfulUsers.html',
})

export class HelpfulUsersPage {
  num: any = 0;
  users: any;
  
   constructor(public viewCtrl: ViewController,public appCtrl: App, 
			public navParams: NavParams,public modalCtrl: ModalController,
			public adminService : AdminService,
			public events: Events) {
	 this.num = navParams.get('num');
	 
  } 
  
  ionViewDidLoad() {
	   this.adminService.helpfulUsers(this.num).subscribe(data => {
		  this.users = data;
		  this.events.publish('gotResults');
	  });
	  
	 
  }
  
 

}

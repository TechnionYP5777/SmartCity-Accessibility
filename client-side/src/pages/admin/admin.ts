import { Component } from '@angular/core';
import { NavController, ModalController, NavParams } from 'ionic-angular';
import { HelpfulUsersPage } from '../helpfulUsers/helpfulUsers';
import { AdminService } from './adminService';

@Component({
  selector: 'page-admin',
  templateUrl: 'admin.html',
  
  
})

export class AdminPage {
  output :  any;
  name: any;
  rating: any;
  numOfReviews: any;
  hlp: any;
  usersArray: any;
  numOfLocations: any;
  radius: any;
  
  
  constructor(public navCtrl: NavController, public navParams: NavParams,
			  public adminService : AdminService, public modalCtrl: ModalController) {
		this.adminService.getUserProfile().subscribe(data => {
			this.name = data.username;
			this.rating=data.rating;
			this.numOfReviews=data.numOfReviews;
			(this.numOfReviews == 0) ? this.hlp = 0 : this.hlp = (this.rating / this.numOfReviews);
			
		});
			
  }
  
  showUsers(n) {
	  let users = this.modalCtrl.create(HelpfulUsersPage,{num: n});
	  users.present();
  }
  
  
  

}

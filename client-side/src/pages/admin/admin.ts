import {Component, trigger, state, style, transition, animate, keyframes } from '@angular/core';
import { NavController, ModalController, NavParams } from 'ionic-angular';
import { HelpfulUsersPage } from '../helpfulUsers/helpfulUsers';
import { AdminService } from './adminService';
import { UserInformationService } from '../user-page/userInformationService';

@Component({
  selector: 'page-admin',
  templateUrl: 'admin.html',
  
  animations: [
  
	trigger('flyInBottomSlow', [
      state('in', style({
        transform: 'translate3d(0,0,0)'
      })),
      transition('void => *', [
        style({transform: 'translate3d(0,2000px,0'}),
        animate('2000ms ease-in-out')
      ])
    ]),
	
	 //For login button
    trigger('fadeIn', [
      state('in', style({
        opacity: 1
      })),
      transition('void => *', [
        style({opacity: 0}),
        animate('1000ms 2000ms ease-in')
      ])
    ])
  ]
})

export class AdminPage {
  output :  any;
  name: any;
  rating: any;
  numOfReviews: any;
  hlp: any;
  usersArray: any;
  
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
	  this.adminService.helpfulUsers(n).subscribe(data => {
		  let clickMenu = this.modalCtrl.create(HelpfulUsersPage,{array: data});
		  clickMenu.present();
	  });
	  
  }
  
  
  

}

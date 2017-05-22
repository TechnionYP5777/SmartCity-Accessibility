import { Component, trigger, state, style, transition, animate } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { LoginService } from '../login/LoginService';
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
  logoState: any = 'in';
  loginState: any = 'in';
  constructor(public navCtrl: NavController, public navParams: NavParams,
			  public loginService : LoginService, public adminService : AdminService,
              public userInformationService : UserInformationService) {
		this.adminService.getUserProfile().subscribe(data => {
			this.name = data.username;
			this.rating=data.rating;
			this.numOfReviews=data.numOfReviews;
		});
		
  }
  
  
  

}

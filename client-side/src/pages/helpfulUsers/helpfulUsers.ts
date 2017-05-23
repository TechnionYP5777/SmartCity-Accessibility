import { Component } from '@angular/core';
import { NavParams, ModalController,App, ViewController } from 'ionic-angular';
import { AddReviewPage } from '../add-review/add-review';
import { NavigationPage } from '../navigation/navigation';
import { LocationListPage } from '../location-list/location-list';

@Component({
  selector: 'page-helpfulUsers',
  templateUrl: 'helpfulUsers.html',
})

export class HelpfulUsersPage {
  usersArray: any;  
  num: any = 0;
  
   constructor(public viewCtrl: ViewController,public appCtrl: App, public navParams: NavParams,public modalCtrl: ModalController) {
	this.usersArray = navParams.get('array');
	this.num = navParams.get('num');
  } 

}

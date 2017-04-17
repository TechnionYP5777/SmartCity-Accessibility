import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

@Component({
  selector: 'page-user-page',
  templateUrl: 'user-page.html'
})
export class UserPagePage {

  constructor(public navCtrl: NavController, public navParams: NavParams) {}
 
  ionViewDidLoad() {
    console.log('ionViewDidLoad UserPagePage');
  }

}

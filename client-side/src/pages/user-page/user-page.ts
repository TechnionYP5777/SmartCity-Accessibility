import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {AddSearchQueryPage} from '../addSearchQueryMenu/addsearchquerymenu';

@Component({
  selector: 'page-user-page',
  templateUrl: 'user-page.html'
})
export class UserPagePage {

  output :  any;
  constructor(public navCtrl: NavController, public navParams: NavParams) {
	  this.output = "";
  }
 
  ionViewDidLoad() {
    console.log('ionViewDidLoad UserPagePage');
  }
  
  openSearchQuery(){
	 this.output = "OMG!!!"; 
  }

}

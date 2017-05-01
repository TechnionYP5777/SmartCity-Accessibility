import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { App, ViewController } from 'ionic-angular';
import { AddReviewPage } from '../add-review/add-review';
import { NavigationPage } from '../navigation/navigation';

@Component({
  selector: 'page-addsearchquerymenu',
  templateUrl: 'addsearchquerymenu.html'
})

export class AddSearchQueryPage {
  lat : any;
  lng : any;
  constructor(public viewCtrl: ViewController,public appCtrl: App, public navParams: NavParams) {
	let temp = navParams.get('latlng');
	this.lat = temp.lat();
	this.lng = temp.lng();
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad MapClickMenuPage');
  }
  
  addReview(){
	this.viewCtrl.dismiss();
    this.appCtrl.getRootNav().push(AddReviewPage,{lat:this.lat,lng:this.lng});
  }
  
  navigateToLocation(){
	this.viewCtrl.dismiss();
    this.appCtrl.getRootNav().push(NavigationPage,{lat:this.lat,lng:this.lng});
  }
}

import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { App, ViewController } from 'ionic-angular';
import { AddReviewPage } from '../add-review/add-review';

@Component({
  selector: 'page-mapclickmenu',
  templateUrl: 'mapclickmenu.html'
})

export class MapClickMenuPage {
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
}

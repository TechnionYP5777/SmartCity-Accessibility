import { Component } from '@angular/core';
import { NavParams, ModalController,App, ViewController } from 'ionic-angular';
import { AddReviewPage } from '../add-review/add-review';
import { NavigationPage } from '../navigation/navigation';
import { LocationListPage } from '../location-list/location-list';

@Component({
  selector: 'page-mapclickmenu',
  templateUrl: 'mapclickmenu.html'
})

export class MapClickMenuPage {
  lat : any;
  lng : any;
  constructor(public viewCtrl: ViewController,public appCtrl: App, public navParams: NavParams,public modalCtrl: ModalController) {
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
  
  nearbyLocations() {
	this.viewCtrl.dismiss();
	let clickMenu = this.modalCtrl.create(LocationListPage,{lat:this.lat,lng:this.lng});
	clickMenu.present();
  }
  
  navigateToLocation(){
	this.viewCtrl.dismiss();
    this.appCtrl.getRootNav().push(NavigationPage,{lat:this.lat,lng:this.lng});
  }
}

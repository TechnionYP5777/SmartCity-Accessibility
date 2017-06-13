import { Component } from '@angular/core';
import { NavParams, ModalController,App, ViewController } from 'ionic-angular';
import { AddReviewPage } from '../add-review/add-review';
import { NavigationPage } from '../navigation/navigation';
import { LocationListPage } from '../location-list/location-list';
import { SearchService } from '../mapview/searchService';

@Component({
  selector: 'page-mapclickmenu',
  templateUrl: 'mapclickmenu.html'
})

export class MapClickMenuPage {
  lat : any;
  lng : any;
  address : any;
  constructor(public viewCtrl: ViewController,public appCtrl: App, public searchService : SearchService, public navParams: NavParams,public modalCtrl: ModalController) {
	let temp = navParams.get('latlng');
	this.lat = temp.lat();
	this.lng = temp.lng();
	this.address = "loading...";
	this.searchService.getAdress(this.lat, this.lng).subscribe(data => {	
		this.address = data.res;
	});
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
	let clickMenu = this.modalCtrl.create(NavigationPage,{lat:this.lat,lng:this.lng});
	clickMenu.present();
  }
}

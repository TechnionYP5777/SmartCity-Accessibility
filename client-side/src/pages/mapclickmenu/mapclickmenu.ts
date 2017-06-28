import { Component } from '@angular/core';
import { NavParams, ModalController,App, ViewController } from 'ionic-angular';
import { AddReviewPage } from '../add-review/add-review';
import { NavigationPage } from '../navigation/navigation';
import { LocationListPage } from '../location-list/location-list';
import { SearchService } from '../mapview/searchService';
import { GetReviewsPage } from '../reviews/reviews'; 

declare var google;

@Component({
  selector: 'page-mapclickmenu',
  templateUrl: 'mapclickmenu.html'
})

export class MapClickMenuPage {
  lat : any;
  lng : any;
  address : any;
  mapUrl : any;


  constructor(public viewCtrl: ViewController,public appCtrl: App, public searchService : SearchService, public navParams: NavParams,public modalCtrl: ModalController) {
	let temp = navParams.get('latlng');
	this.lat = temp.lat();
	this.lng = temp.lng();
	this.address = "loading...";
	this.searchService.getAdress(this.lat, this.lng).subscribe(data => {	
		this.address = data.res;
	});
	this.mapUrl = 'https://maps.googleapis.com/maps/api/streetview?size=400x200&location='+this.lat+','+this.lng+'&fov=90';
  }
  
  ionViewDidLoad() {
    console.log('ionViewDidLoad MapClickMenuPage');
  }
  
  reviewAsStreet(){
		let clickMenu = this.modalCtrl.create(GetReviewsPage, {lat:this.lat,lng:this.lng,type:'street',subtype: 'default',name:'STREET'});
		clickMenu.present();
		this.viewCtrl.dismiss();
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

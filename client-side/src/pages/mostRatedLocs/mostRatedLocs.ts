import { Component } from '@angular/core';
import { NavParams, ModalController,App, ViewController, AlertController } from 'ionic-angular';
import { LocationListPage } from '../location-list/location-list';
import { AdminService } from '../admin/adminService';
import { SearchService } from '../mapview/searchService';

@Component({
  selector: 'page-mostRatedLocs',
  templateUrl: 'mostRatedLocs.html',
})

export class MostRatedLocsPage {
  locations: any;
  num: any = 0;
  radius: any = 0;
  initLoc: any = 'empty';
  coordinates: any = null;
  
   constructor(public viewCtrl: ViewController,public appCtrl: App, 
			public navParams: NavParams,public modalCtrl: ModalController,
			public adminService : AdminService, public alertCtrl: AlertController,
			public searchService: SearchService) {
	this.num = navParams.get('n');
	this.radius = navParams.get('r');
	this.initLoc = navParams.get('l');
  } 
  
  ionViewDidLoad() {
		this.coordinates = this.searchService.search(this.initLoc).subscribe(
		data => {
			this.coordinates = data.coordinates;
			this.adminService.mostRatedLocs(this.radius, this.num, this.coordinates.lat, this.coordinates.lng).subscribe(data => {
			  this.locations = data;
			});
			
		});
		
		while(this.coordinates == null){continue;}
	   this.adminService.mostRatedLocs(this.radius, this.num, this.coordinates.lat, this.coordinates.lng).subscribe(data => {
		  this.locations = data;
	  });
	 
  }
  
  presentAlert(str) {
		let alert = this.alertCtrl.create({
		  title: 'Alert',
		  subTitle: str,
		  buttons: ['OK']
		});
		alert.present();
	}
	
   handleError(err) {
		this.presentAlert("error is: " + err.error + " message is: " + err.message);
    }

}

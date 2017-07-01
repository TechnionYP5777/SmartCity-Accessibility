import {Component} from '@angular/core';
import { NavController, NavParams, ViewController, AlertController, ModalController, LoadingController} from 'ionic-angular';
import { AddLocationService } from './AddLocationService';
import { GetReviewsPage } from '../reviews/reviews'; 
import {SpecialConstants} from "../special-constants/special-constants";
import { SearchService } from '../mapview/searchService';
/*
	author: Ariel Kolikant
*/

@Component({
  selector: 'page-add-location',
  templateUrl: 'add-location.html',
})

export class AddLocationPage {
  token : any;
  lat : any;
  lng : any;
  omg : any;
  type: string;
  name: string;
  notDone: any;
  retType: any;
  retSubType: any;
  loading : any;
  adress: any;
  constructor(public alertCtrl: AlertController,
				public loadingCtrl: LoadingController,
				public modalCtrl: ModalController,
				public viewCtrl: ViewController,
				public navCtrl: NavController,
				public navParams: NavParams,
				public addLocationService: AddLocationService,
				public _constants : SpecialConstants,
				public searchService : SearchService,) {
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
	this.notDone = true;
	this.retType = "";
	this.retSubType = ""
	this.type = "";
	this.name = "";
	this.loading = "";
	this.searchService.getAdress(this.lat, this.lng).subscribe(data => {
      this.adress = data.res;
    });
  }
  
  addToDataBase(){
		if(this.type == "" || this.name == ""){
			this.presentAlert('Please fill all of the fields first!');
		}else{
			this.notDone = false;
			this.addLocationService.addLocation(this.name, this.lat, this.lng, this.type).subscribe(data => {	
				this.retType = data.locationType;
				this.retSubType = data.locationSubType;
				if(this.loading == ""){
					;
				}else{
					this.loading.dismiss().catch(() => {});
					this.openLocationTab();
				}
			}, err => {
				this.notDone = true;
				this.presentAlert(err.json().message);
			});
		}	
  }
  
  exit(){
	  this.viewCtrl.dismiss();
  }
  
  presentAlert(string) {
  let alert = this.alertCtrl.create({
    title: string,
    buttons: ['OK']
  });
  alert.present(alert);
}

  openLocationTab(){
	 if(this.retType == "" || this.retSubType == ""){
		 this.loading = this._constants.createCustomLoading();
		 this.loading.present();
	 }else{
		let clickMenu = this.modalCtrl.create(GetReviewsPage, {lat:this.lat,lng:this.lng,type:this.retType,subtype:this.retSubType,name:this.name});
		clickMenu.present();
		this.viewCtrl.dismiss(); 	 
	 }
	 
  }
  ionViewDidLoad() {
    console.log('ionViewDidLoad AddReviewPage');
  }
  
  presentLoadingCustom() {
            this.loading = this.loadingCtrl.create({
            spinner: 'bubbles',
		    showBackdrop: false,
		    cssClass: 'loader'
        });
        this.loading.present();
    }
}

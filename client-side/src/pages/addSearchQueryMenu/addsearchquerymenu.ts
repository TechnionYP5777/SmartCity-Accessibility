import { Component } from '@angular/core';
import { AlertController, NavController, NavParams, LoadingController } from 'ionic-angular';
import { AddSearchQueryService } from './AddSearchQueryService';
import { Constants } from "../constants";


@Component({
  selector: 'page-addsearchquerymenu',
  templateUrl: 'addsearchquerymenu.html'
})

export class AddSearchQueryPage {
  Queries : any;
  name : string;
  adress: string;
  loading : any;
  constructor(public alertCtrl: AlertController, public navCtrl: NavController, public loadingCtrl: LoadingController, public navParams: NavParams, public addSearchQueryService: AddSearchQueryService){	  
	
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad MapClickMenuPage');
  }
  
  addSearchQuery(){
	  this.presentLoadingCustom();
	  this.addSearchQueryService.addQuery(this.name, this.adress).subscribe(data => {	
		this.name = "";
		this.adress = "";
		this.loading.dismiss();
		this.presentAlert("done! :)");
		this.navCtrl.pop();
	  }, err => {
		if(err.error == null)
			this.presentAlert(Constants.serverNotResponding);
		else 
			this.presentAlert("problem adding query: "+err.json().message);
		 this.loading.dismiss();
	  });
  }
  
  presentLoadingCustom() {
		this.loading = this.loadingCtrl.create({
		spinner: 'bubbles',
		showBackdrop: false,
		cssClass: 'loader'
	});
	this.loading.present();
 }
 
 presentAlert(string) {
  let alert = this.alertCtrl.create({
	title: string,
	buttons: ['OK']
  });
  alert.present(alert);
 }
}

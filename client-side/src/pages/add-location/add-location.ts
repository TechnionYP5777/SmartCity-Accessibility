import {Component} from '@angular/core';
import { NavController, NavParams, ViewController, AlertController  } from 'ionic-angular';
import { AddLocationService } from './AddLocationService';


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
  
  constructor(public alertCtrl: AlertController, public viewCtrl: ViewController, public navCtrl: NavController, public navParams: NavParams, public addLocationService: AddLocationService) {
	this.lat = navParams.get('lat');
	this.lng = navParams.get('lng');
	this.omg = "omg!";
	this.notDone = true;
	this.type = "";
	this.name = "";
  }
  
  addToDataBase(){
		if(this.type == "" || this.name == ""){
			this.presentAlert();
		}else{
			this.notDone = false;
			this.addLocationService.addLocation(this.name, this.lat, this.lng, this.type);	
		}	
  }
  
  exit(){
	  this.viewCtrl.dismiss();
  }
  
  presentAlert() {
  let alert = this.alertCtrl.create({
    title: 'Please fill all of the fields first!',
    buttons: ['OK']
  });
  alert.present(alert);
}

  ionViewDidLoad() {
    console.log('ionViewDidLoad AddReviewPage');
  }

}

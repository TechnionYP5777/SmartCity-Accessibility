import { Component } from '@angular/core';
import { AlertController, NavController, NavParams, ModalController, ViewController } from 'ionic-angular';
import {AddSearchQueryPage} from '../addSearchQueryMenu/addsearchquerymenu';
import {ViewSearchQueryPage} from '../viewSearchQuery/viewsearchquery';
import { LoginService } from '../login/LoginService';
import { UserInformationService } from './userInformationService';
import { MapviewPage } from '../mapview/mapview';
import { ImgUploadPage } from '../imgUpload/imgUpload';
import { Constants } from "../constants";
import {SpecialConstants} from "../special-constants/special-constants";



@Component({
  selector: 'page-user-page',
  templateUrl: 'user-page.html'
})

export class UserPagePage {
  output :  any;
  UserName: any;
  quries: any;
  addSearchQueryPage = AddSearchQueryPage;
  viewSearchQueryPage = ViewSearchQueryPage;
  imageProfileUrl : any;  
  loading : any;

	constructor(public alertCtrl: AlertController,
				public navCtrl: NavController,
				public navParams: NavParams,
				public viewCtrl: ViewController, 
				public modalCtrl: ModalController,
				public loginService : LoginService,
				public userInformationService : UserInformationService,
				public _constants : SpecialConstants) {
		this.userInformationService.getUserProfile().subscribe(data => {
			this.UserName = data.username;
		});

		this.userInformationService.getUserQuries().subscribe(data => {
			this.quries = data.res2;
		});
		
		this.findImageProfileURL();
	}
	
	findImageProfileURL() {
		try{
			var token = JSON.parse(window.sessionStorage.getItem('token')).token;
		}
		catch(err){
			token = "no token";
		}
		this.imageProfileUrl = 	Constants.serverAddress+'/profileImg?token='+token;
  }
	
imgUpload() {
	this.navCtrl.push(ImgUploadPage);
}
	
	
  ionViewDidLoad() {
    console.log('ionViewDidLoad UserPagePage');
  }

  addNewSearchQuery(){
		this.navCtrl.push(this.addSearchQueryPage);
  }

  showSearchQueries(){
	    this.navCtrl.push(this.viewSearchQueryPage);
  }

  cc(query){
	this.loading = this._constants.createCustomLoading();
    this.loading.present();
    this.navCtrl.setRoot(MapviewPage, {myQuery : query.query});
	this.loading.dismiss().catch(() => {});
  }
  
  refresh(){
	this.userInformationService.getUserQuries().subscribe(data => {
			this.quries = data.res2;
		});  
  }

  presentAlert(string) {
  let alert = this.alertCtrl.create({
	title: string,
	buttons: ['OK']
  });
  alert.present(alert);
}

}

import { Component } from '@angular/core';
import { AlertController, NavController, NavParams, ModalController, ViewController } from 'ionic-angular';
import {AddSearchQueryPage} from '../addSearchQueryMenu/addsearchquerymenu';
import {ViewSearchQueryPage} from '../viewSearchQuery/viewsearchquery';
import { LoginService } from '../login/LoginService';
import { UserInformationService } from './userInformationService';
import { MapviewPage } from '../mapview/mapview';
import { ImgUploadPage } from '../imgUpload/imgUpload';



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

	constructor(public alertCtrl: AlertController, public navCtrl: NavController, public navParams: NavParams, public viewCtrl: ViewController, public modalCtrl: ModalController, public loginService : LoginService, public userInformationService : UserInformationService) {
		this.userInformationService.getUserProfile().subscribe(data => {
			this.UserName = data.username;
		});

		this.userInformationService.getUserQuries().subscribe(data => {
			this.quries = data.res2;
		});
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
    this.navCtrl.setRoot(MapviewPage, {myQuery : query.query});

  }

  presentAlert(string) {
  let alert = this.alertCtrl.create({
	title: string,
	buttons: ['OK']
  });
  alert.present(alert);
}

}

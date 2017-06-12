import { Component } from '@angular/core';
import { NavController, ModalController, NavParams, LoadingController, Events, AlertController } from 'ionic-angular';

@Component({
  selector: 'page-imgUpload',
  templateUrl: 'imgUpload.html',
  
  
})

export class ImgUploadPage {
  
  
  constructor(public navCtrl: NavController, public navParams: NavParams,
			  public modalCtrl: ModalController,
			  public loadingCtrl: LoadingController, public events: Events,
			  public alertCtrl: AlertController) {
		}
  
}

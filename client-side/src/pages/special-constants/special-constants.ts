/*
 Author: ArthurSap
 */
import { Injectable } from '@angular/core';
import {LoadingController, AlertController} from 'ionic-angular';


@Injectable()
export class SpecialConstants {

  constructor(public loadingController : LoadingController, public alertCtrl: AlertController){}

  handleError(err) {
    if(err.error == null)
			this.presentAlert(Constants.serverNotResponding);
	else 
		this.presentAlert("<p> error is: " + err + "</p>");
  }

	presentAlert(str) {
    let alert = this.alertCtrl.create({
      title: 'Error',
      subTitle: str,
      buttons: ['OK']
    });
    alert.present();
  }

  createCustomLoading() {
    let loading = this.loadingController.create({
      spinner: 'hide',
      dismissOnPageChange: true,
      content: `<div class="cssload-container">
                  <div class="cssload-whirlpool"></div>
              </div>`,
      cssClass: 'loader'
    });

    return loading;
  }
}

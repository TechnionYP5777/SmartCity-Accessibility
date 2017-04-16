import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { NavigationService } from './navigationService';

@Component({
  selector: 'page-navigation',
  templateUrl: 'navigation.html'
})
export class NavigationPage {
    isWork : any;
    constructor(public navCtrl: NavController, public navParams: NavParams, public navigationService: NavigationService) {
	    var token = window.sessionStorage.getItem('token');
		this.isWork = token;
	}

    ionViewDidLoad() {
        this.navigationService.navigate().then(data => {
			this.isWork = data;
		})
    }
}

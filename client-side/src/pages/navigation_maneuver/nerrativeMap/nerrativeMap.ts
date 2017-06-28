import { Component } from '@angular/core';
import { NavController, NavParams} from 'ionic-angular';

@Component({
  selector: 'page-nerrativeMap',
  templateUrl: 'nerrativeMap.html'
}) 

/*
	author: Yael Amitay
*/
export class nerrativeMapPage {
	img : any;
    constructor(public navCtrl: NavController, public navParams: NavParams) {
		this.img = this.navParams.get('mapurl');
	}
}

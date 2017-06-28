import { Component } from '@angular/core';
import { NavController, NavParams,ModalController} from 'ionic-angular';
import { nerrativeMapPage } from './nerrativeMap/nerrativeMap';

@Component({
  selector: 'page-navigation_maneuver',
  templateUrl: 'navigation_maneuver.html'
}) 

/*
	author: Yael Amitay
*/
export class navigationManeuverPage {
    time : any;
	legs : any;
	nerratives : any;
	formatTime : any;
	distance : any;
    constructor(public navCtrl: NavController, public navParams: NavParams,public modalCtrl: ModalController) {
		this.time = this.navParams.get('time');
		this.legs = this.navParams.get('legs');
		this.distance = this.navParams.get('distance');
	}
	presentRouteTime(){
		var date = new Date(null);
		date.setSeconds(this.time);
		this.formatTime = date.toISOString().substr(11, 8);
	}
	
	ionViewDidLoad(){
		this.presentRouteTime();
		this.nerratives = [];
		var k = 0;
		for(var i = 0; i < this.legs.length; i++) {
			var leg = this.legs[i];
			for(var j = 0; j < leg.maneuvers.length; j++){
				this.nerratives[k] = {text: leg.maneuvers[j].narrative, mapurl: leg.maneuvers[j].mapUrl };
				k = k + 1;
			}
		}	
    }
	
	displayImage(n) {
		let clickMenu = this.modalCtrl.create(nerrativeMapPage,n);
		clickMenu.present();
	}
}

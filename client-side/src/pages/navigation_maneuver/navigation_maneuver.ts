import { Component } from '@angular/core';
import { NavController, NavParams} from 'ionic-angular';

@Component({
  selector: 'page-navigation_maneuver',
  templateUrl: 'navigation_maneuver.html'
}) 
export class navigationManeuverPage {
    time : any;
	legs : any;
	nerratives : any;
	formatTime : any;
	distance : any;
    constructor(public navCtrl: NavController, public navParams: NavParams) {
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
				this.nerratives[k] = leg.maneuvers[j].narrative;
				k = k + 1;
			}
		}	
    }
}

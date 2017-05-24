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
    constructor(public navCtrl: NavController, public navParams: NavParams) {
		this.time = this.navParams.get('time');
		this.legs = this.navParams.get('legs');
	}
	
	ionViewDidLoad(){
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

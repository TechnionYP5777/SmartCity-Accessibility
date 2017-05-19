import { Component } from '@angular/core';
import { NavController, NavParams} from 'ionic-angular';

@Component({
  selector: 'page-navigation_maneuver',
  templateUrl: 'navigation_maneuver.html'
}) 
export class navigationManeuverPage {
    time : any;
    constructor(public navCtrl: NavController, public navParams: NavParams) {
		this.time = this.navParams.get('time');
	}
	
	ionViewDidLoad(){
    }
}

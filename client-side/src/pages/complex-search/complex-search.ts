import { Component } from '@angular/core';
import { NavController, NavParams , Events, AlertController} from 'ionic-angular';
import {ComplexSearchService} from './complexSearchService';
import { SearchService } from '../mapview/searchService';

/*
  Generated class for the ComplexSearch page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Component({
  selector: 'page-complex-search',
  templateUrl: 'complex-search.html'
})
export class ComplexSearchPage {
  type: string;
  gender: string;
  minRating: number;
  raduis: number;
  music: string;
  initLoc: string;
  output: any;
  callback: any;
  startLocationCoordinates: any;

  constructor(public events: Events, public searchService: SearchService,public navCtrl: NavController, public navParams: NavParams, public complexSearchService : ComplexSearchService, public alertCtrl: AlertController) {
    
  }

  stpSelect() {
    console.log('STP selected');
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ComplexSearchPage');
  }

  callComplexSearch(type, radius, initLoc, minRating) {
		this.searchService.search(initLoc).subscribe(
			data => {
				this.startLocationCoordinates = data.coordinates;
			}
			, err => {
				this.handleError(err.json());
			}
		);
	    this.complexSearchService.complexSearch(type, radius, initLoc, minRating).subscribe(data => {
			
			this.events.publish('complexSearch:pressed', data, this.startLocationCoordinates);
        });
		this.navCtrl.pop();

	
   }
   
   presentAlert(str) {
		let alert = this.alertCtrl.create({
		  title: 'Alert',
		  subTitle: str,
		  buttons: ['OK']
		});
		alert.present();
	}
	
	handleError(err) {
		this.presentAlert("error is: " + err.error + " message is: " + err.message);
    }
}

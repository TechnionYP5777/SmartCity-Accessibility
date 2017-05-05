import { Component } from '@angular/core';
import { NavController, NavParams , Events} from 'ionic-angular';
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

  constructor(public events: Events, public searchService: SearchService,public navCtrl: NavController, public navParams: NavParams, public complexSearchService : ComplexSearchService) {
    
  }

  stpSelect() {
    console.log('STP selected');
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ComplexSearchPage');
  }

  callComplexSearch(type, radius, initLoc, minRating) {
		this.searchService.search(initLoc).subscribe(data => {
			this.startLocationCoordinates = data.coordinates;
		});
	    this.complexSearchService.complexSearch(type, radius, initLoc, minRating).subscribe(data => {
			
			this.events.publish('complexSearch:pressed', data, this.startLocationCoordinates);
        });
		this.navCtrl.pop();

	
   }
}

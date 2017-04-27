import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import {ComplexSearchService} from './complexSearchService';
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

  constructor(public navCtrl: NavController, public navParams: NavParams, public complexSearchService : ComplexSearchService) {
    
  }

  stpSelect() {
    console.log('STP selected');
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ComplexSearchPage');
  }

  callComplexSearch(type, radius, initLoc, minRating) {
	    this.complexSearchService.complexSearch(type, radius, initLoc, minRating).subscribe(data => {
			this.output = 'The first location is : ' + data[0].name;
        });
   }
}

import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
/*
	author: Ariel Kolikant
*/

@Component({
  selector: 'page-viewsearchquery',
  templateUrl: 'viewsearchquery.html'
})

export class ViewSearchQueryPage {
  Queries : any;
  constructor(public navCtrl: NavController, public navParams: NavParams){	  
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad MapClickMenuPage');
  }
}

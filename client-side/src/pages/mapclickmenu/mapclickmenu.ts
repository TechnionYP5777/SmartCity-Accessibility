import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';

@Component({
  selector: 'page-mapclickmenu',
  templateUrl: 'mapclickmenu.html'
})

export class MapClickMenuPage {
  latlng : any;
  constructor(public navCtrl: NavController, public navParams: NavParams) {
	this.latlng = navParams.get('latlng');
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad MapClickMenuPage');
  }
}

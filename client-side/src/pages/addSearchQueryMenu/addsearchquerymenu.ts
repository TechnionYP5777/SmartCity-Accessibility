import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { App, ViewController } from 'ionic-angular';


@Component({
  selector: 'page-addsearchquerymenu',
  templateUrl: 'addsearchquerymenu.html'
})

export class AddSearchQueryPage {
  Queries : any;
  constructor(public navCtrl: NavController, public navParams: NavParams){	  
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad MapClickMenuPage');
  }
}

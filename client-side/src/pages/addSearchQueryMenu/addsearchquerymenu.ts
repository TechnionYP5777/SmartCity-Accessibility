import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { AddSearchQueryService } from './AddSearchQueryService';


@Component({
  selector: 'page-addsearchquerymenu',
  templateUrl: 'addsearchquerymenu.html'
})

export class AddSearchQueryPage {
  Queries : any;
  name : string;
  adress: string;
  wutwut: string;
  constructor(public navCtrl: NavController, public navParams: NavParams, public addSearchQueryService: AddSearchQueryService){	  
  this.wutwut = "hells no";
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad MapClickMenuPage');
  }
  
  addSearchQuery(){
	  this.wutwut = "hells yeas";
	  this.addSearchQueryService.addQuery(this.name, this.adress);
  }
}

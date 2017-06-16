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
  
  constructor(public navCtrl: NavController, public navParams: NavParams, public addSearchQueryService: AddSearchQueryService){	  
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad MapClickMenuPage');
  }
  
  addSearchQuery(){
	  this.addSearchQueryService.addQuery();
  }
}

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
  yay: string
  constructor(public navCtrl: NavController, public navParams: NavParams, public addSearchQueryService: AddSearchQueryService){	  
	this.yay = "vlavlavla";
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad MapClickMenuPage');
  }
  
  addSearchQuery(){
	  this.addSearchQueryService.addQuery().subscribe(data => {	
		this.yay = data.name;
	  });
  }
}

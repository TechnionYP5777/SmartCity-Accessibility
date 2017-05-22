import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import {LoadingController} from 'ionic-angular';
import {Http, Headers} from "@angular/http";
import { Constants } from "../constants";



@Injectable()
export class GetReviewsService {

  revs : any;

  constructor(public http: Http) {
  	this.http = http;
    console.log('Hello GetReviewsService Provider');
  }
  
  showMeStuff(loading, lat, lng){
    loading.present();
	
	this.http.get('https://www.reddit.com/r/gifs/new/.json?limit=10').map(res => res.json()).subscribe(data => {
        this.revs = data.data.children;
        loading.dismiss();
    },
    err => {
        loading.dismiss();
        console.log("Oops!");
    });
    
    return this.revs;
  }

}
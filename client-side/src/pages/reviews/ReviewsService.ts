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
  
  showMeStuff(lat, lng){
	var params = "lat=" + lat + "&lng=" + lng;
	return this.http.get(Constants.serverAddress +'/reviews?'+params);
    //return this.revs;
  }
  
  changeRevLikes(rev, like){
  	
  }

}
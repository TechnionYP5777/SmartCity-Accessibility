import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import {LoadingController} from 'ionic-angular';
import {Http, Headers} from "@angular/http";
import { Constants } from "../constants";



@Injectable()
export class GetReviewsService {

  constructor(public http: Http) {
  	this.http = http;
    console.log('Hello GetReviewsService Provider');
  }
  
  showMeStuff(lat, lng){
	var params = "lat=" + lat + "&lng=" + lng;
	return this.http.get(Constants.serverAddress +'/reviews?'+params);
  }
  
  changeRevLikes(rev, like){
  	var params = "lat=" + rev.location.coordinates.lat + "&lng=" + rev.location.coordinates.lng + "&type=" + rev.location.locationType + "&subtype=" + rev.location.locationSubType + "&username=" + rev.user.username + "&likes=" + like;
  	
  	this.http.post(Constants.serverAddress +'/reviews?', params);
  }

}
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import {Http, Headers} from "@angular/http";
import { Constants } from "../constants";



@Injectable()
export class GetReviewsService {

  constructor(public http: Http) {
  	this.http = http;
    console.log('Hello GetReviewsService Provider');
  }
  
  showMeStuff(lat, lng, type, subtype, name){
	var params = "lat=" + lat + "&lng=" + lng + "&type=" + type + "&subtype=" + subtype + "&name=" + name;
	return this.http.get(Constants.serverAddress +'/reviews?'+params);
  }
  
  changeRevLikes(username, lat, lng, type, subtype, like){
  
  	try{
		var token = JSON.parse(window.sessionStorage.getItem('token')).token;
	}
	catch(err){
		token = "no token";
	}
	
  	var params = "lat=" + lat + "&lng=" + lng + "&type=" + type + "&subtype=" + subtype + "&username=" + username + "&likes=" + like;
  	
  	var headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('authToken',token);
    
    return new Promise(resolve => {
      	this.http.post(Constants.serverAddress +'/reviews?', params, {headers: headers}).subscribe(data => {
                if(data.status == 200){
                	console.log('Review liked successfully!')
                    resolve(true);
                }
                else
                    resolve(false);
            });
        });
  }

}

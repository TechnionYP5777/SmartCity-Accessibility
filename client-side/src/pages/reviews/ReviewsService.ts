import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import {Http, Headers} from "@angular/http";
import { Constants } from "../constants";



@Injectable()
export class GetReviewsService {

  headers : any;

  constructor(public http: Http) {
  	this.http = http;
  	this.headers = new Headers();
    this.headers.append('Content-Type', 'application/x-www-form-urlencoded');
    console.log('Hello GetReviewsService Provider');
  }

  showMeStuff(loc, name){
	var params = "lat=" + loc.lat + "&lng=" + loc.lng + "&type=" + loc.type + "&subtype=" + loc.subtype + "&name=" + name;
	return this.http.get(Constants.serverAddress +'/reviews?'+params);
  }

  changeRevLikes(username, loc, like){

  	var token = this.getToken();
  	var params = "lat=" + loc.lat + "&lng=" + loc.lng + "&type=" + loc.type + "&subtype=" + loc.subtype + "&username=" + username + "&likes=" + like;

    var headers = this.headers;
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

  deleteReview(loc, username){
    var params = this.constructParams(loc, username);

    return new Promise(resolve => {
      this.http.post(Constants.serverAddress +'/deleteReview?', params, {headers: params[1]}).subscribe(data => {
        if(data.status == 200){
          console.log('Review deleted successfully!')
          resolve(true);
        }
        else
          resolve(false);
      });
    });
  }

  pinUnpinReview(loc, username){
    var params = this.constructParams(loc, username);

    return new Promise(resolve => {
      this.http.post(Constants.serverAddress +'/pinUnpinReview?', params, {headers: params[1]}).subscribe(data => {
        if(data.status == 200){
          console.log('Review pin/unpinned successfully!')
          resolve(true);
        }
        else
          resolve(false);
      });
    });
  }

  private constructParams(loc, user){
    var params = [];
    var token = this.getToken();
    params[0] = "lat=" + loc.lat + "&lng=" + loc.lng + "&type=" + loc.type + "&subtype=" + loc.subtype + "&username=" + user;

    params[1] = this.headers;
    params[1].append('authToken',token);

    return params;
  }
  private getToken() {
    try {
      var token = JSON.parse(window.sessionStorage.getItem('token')).token;
    }
    catch (err) {
      token = "no token";
    }
    return token;
  }

}

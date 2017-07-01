/*
 Author: ArthurSap
 */
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import {Http, Headers} from "@angular/http";
import { Constants } from "../constants";

@Injectable()
export class AddReviewService {

  constructor(public http: Http) {
  	this.http = http;
    console.log('Hello AddReviewService Provider');
  }

  addreview(rev, lat, lng, type, subtype, name){
    try{
		var token = JSON.parse(window.sessionStorage.getItem('token')).token;
	}
	catch(err){
		token = "no token";
	}
    var params = "lat=" + lat + "&lng=" + lng  + "&type=" + type + "&subtype=" + subtype + "&name=" + name + "&review=" + rev.review + "&score=" + rev.score;
  	var headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('authToken',token);

    return new Promise(resolve => {
            this.http.post(Constants.serverAddress +'/addreview', params, {headers: headers}).subscribe(data => {
                if(data.status == 200){
                	console.log('Review added successfully!')
                    resolve(true);
                }
                else
                    resolve(false);
            });
        });
  }

}

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
  
  addreview(rev, lat, lng){
    var creds = '';
    var token = window.sessionStorage.getItem('token');
  	var headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('authToken',token);
    headers.append('lat',lat);
    headers.append('lng',lng);
    headers.append('rev.review',review);
    headers.append('rev.score',score);
    
    return new Promise(resolve => {
            this.http.post(Constants.serverAddress +'/addreview', creds, {headers: headers}).subscribe(data => {
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

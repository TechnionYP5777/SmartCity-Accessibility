/*
 Author: ArthurSap
 */
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import {Http, Headers} from "@angular/http";
import { Constants } from "../constants";
import {SpecialConstants} from "../special-constants/special-constants"


@Injectable()
export class CommentService {

  constructor(public http: Http, public _constants : SpecialConstants) {
    this.http = http;
    console.log('Hello CommentService Provider');
  }

  addComment(lat, lng, type, subtype, rev, comment){

    try{
      var token = JSON.parse(window.sessionStorage.getItem('token')).token;
    }
    catch(err){
      token = "no token";
    }

    var params = "lat=" + lat + "&lng=" + lng + "&type=" + type + "&subtype=" + subtype + "&rev=" + JSON.stringify(rev) + "&comment=" + comment;

    var headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('authToken',token);

    return new Promise(resolve => {
      this.http.post(Constants.serverAddress +'/addcomment?', params, {headers: headers}).subscribe(data => {
        if(data.status == 200){
          console.log('Comment \"' + comment + '\"added successfully!')
          resolve(true);
        }
        else
          resolve(false);
      }, err => {this._constants.handleError(err)});
    });
  }

}

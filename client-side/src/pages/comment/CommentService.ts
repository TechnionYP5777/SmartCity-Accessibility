import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import {Http, Headers} from "@angular/http";
import { Constants } from "../constants";



@Injectable()
export class CommentService {

  constructor(public http: Http) {
    this.http = http;
    console.log('Hello CommentService Provider');
  }

  addComment(username, rev, comment){

    try{
      var token = JSON.parse(window.sessionStorage.getItem('token')).token;
    }
    catch(err){
      token = "no token";
    }

    var params = "username=" + username + "&rev=" + JSON.stringify(rev) + "&comment=" + comment;

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
      });
    });
  }

}

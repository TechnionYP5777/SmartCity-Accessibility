import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';
import {Http, Headers} from "@angular/http";



@Injectable()
export class AddReviewService {

  constructor(public http: Http) {
  	this.http = http;
    console.log('Hello AddReviewService Provider');
  }
  
  addreview(rev){
  	var headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
  }

}

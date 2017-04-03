import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';
import {Observable} from 'rxjs/Observable';
import 'rxjs/Rx';

/*
  Generated class for the LoginService provider.

  See https://angular.io/docs/ts/latest/guide/dependency-injection.html
  for more info on providers and Angular 2 DI.
*/
@Injectable()
export class LoginService {
  constructor(public http: Http) {
    console.log('Hello LoginService Provider');
  }
  callHttp() {
    return this.http.get('http://localhost:8080/test').map(res=>res.json())
	.catch(this.handleError);
  }
   handleError(error) {
        console.error(error);
        return Observable.throw('Server error');
    }
}

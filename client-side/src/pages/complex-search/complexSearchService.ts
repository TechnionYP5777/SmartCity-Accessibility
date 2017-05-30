import {Injectable} from "@angular/core";
import { Http, Headers,RequestOptions } from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';
/*
  Generated class for the ComplexSearchService provider.

  See https://angular.io/docs/ts/latest/guide/dependency-injection.html
  for more info on providers and Angular 2 DI.
*/
@Injectable()
export class ComplexSearchService {
	constructor(public http: Http) {
        this.http = http;
    }

	complexSearch(type, radius, startLocation, threshold) {
		try{
		   var token = JSON.parse(window.sessionStorage.getItem('token')).token;
		}
		catch(err){
			token = "no token";
		}
		var headers = new Headers();
		headers.append('Content-Type', 'application/x-www-form-urlencoded');
		headers.append('authToken',token);
           return this.http.get(Constants.serverAddress + '/complexSearch?type=' + type + '&radius=' + radius + '&startLocation=' + startLocation + '&threshold=' + threshold, {headers: headers}).map(res=>res.json());
	}

}

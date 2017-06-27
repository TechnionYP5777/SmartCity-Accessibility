import {Injectable} from "@angular/core";
import { Http, Headers } from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';
/*
	author: Koral Chapnik
*/
@Injectable()
export class ComplexSearchService {
	headers: any;
	constructor(public http: Http) {
        this.http = http;
		try{
		   var token = JSON.parse(window.sessionStorage.getItem('token')).token;
		}
		catch(err){
			token = "no token";
		}
		this.headers = new Headers();
		this.headers.append('Content-Type', 'application/x-www-form-urlencoded');
		this.headers.append('authToken',token);
    }

	complexSearchAddress(type, radius, startLocation, threshold) {	
           return this.http.get(Constants.serverAddress + '/complexSearchAddress?type=' + type + '&radius=' + radius + '&startLocation=' + startLocation + '&threshold=' + threshold, {headers: this.headers}).map(res=>res.json());
	}
	
	complexSearchCoords(type, radius, startLocation, threshold) {	
           return this.http.get(Constants.serverAddress + '/complexSearchCoords?type=' + type + '&radius=' + radius + '&lat=' + startLocation.latitude + '&lng=' + startLocation.longitude + '&threshold=' + threshold, {headers: this.headers}).map(res=>res.json());
	}
	
	

}

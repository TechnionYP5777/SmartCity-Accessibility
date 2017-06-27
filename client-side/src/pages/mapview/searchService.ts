import {Injectable} from "@angular/core";
import { Http, Headers } from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';
/*
	author: Ariel Kolikant
*/
@Injectable()
export class SearchService {
    constructor(public http: Http) {
        this.http = http;
    }

	search(searchQuery) {
		try{
		   var token = JSON.parse(window.sessionStorage.getItem('token')).token;
		}
		catch(err){
			token = "no token";
		}
		var headers = new Headers();
		headers.append('Content-Type', 'application/x-www-form-urlencoded');
		headers.append('authToken',token);
           return this.http.get(Constants.serverAddress +'/simpleSearch/'+searchQuery, {headers: headers}).map(res=>res.json());
	}
	
	getAdress(lat, lng) {
		try{
		   var token = JSON.parse(window.sessionStorage.getItem('token')).token;
		}
		catch(err){
			token = "no token";
		}
		var headers = new Headers();
		var params = "?srcLat=" + lat + "&srcLng=" + lng;
		headers.append('Content-Type', 'application/x-www-form-urlencoded');
		headers.append('authToken',token);
           return this.http.get(Constants.serverAddress +'/getAdress'+ params, {headers: headers}).map(res=>res.json());
	}
	
	
}
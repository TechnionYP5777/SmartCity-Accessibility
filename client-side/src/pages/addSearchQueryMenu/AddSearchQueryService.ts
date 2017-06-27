/*
	author: Ariel Kolikant
*/

import {Injectable} from "@angular/core";
import { Http, Headers} from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';

@Injectable()
export class AddSearchQueryService {
    constructor(public http: Http) {
        this.http = http;
    }
	
	addQuery(name, adress){
		try{
		   var token = JSON.parse(window.sessionStorage.getItem('token')).token;
		}
		catch(err){
			token = "no token";
		}
		var headers = new Headers();
		headers.append('Content-Type', 'application/x-www-form-urlencoded');
		headers.append('authToken',token);

		var params = "query=" + adress + "&queryName=" + name;
		return this.http.post(Constants.serverAddress +'/addQuery', params,{headers: headers}).map(res=>res.json());
	}
}

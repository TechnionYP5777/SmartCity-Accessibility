import {Injectable} from "@angular/core";
import { Http, Headers} from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';

@Injectable()
export class LocationsInRadiusService {
    constructor(public http: Http) {
        this.http = http;
    }

	GetLocationsInRadiusFrom(lat, lng){
		var params = "srcLat=" + lat + "&srcLng=" + lng;
		try{
		   var token = JSON.parse(window.sessionStorage.getItem('token')).token;
		}
		catch(err){
			token = "no token";
		}
		var headers = new Headers();
		headers.append('Content-Type', 'application/x-www-form-urlencoded');
		headers.append('authToken',token);
		return this.http.get(Constants.serverAddress +'/locationsInRadius?'+params, {headers: headers}).map(res=>res.json());
	}
}
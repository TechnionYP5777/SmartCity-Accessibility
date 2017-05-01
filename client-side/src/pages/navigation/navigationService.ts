import {Injectable} from "@angular/core";
import { Http, Headers,RequestOptions } from "@angular/http";
import { Constants } from "../constants";

@Injectable()
export class NavigationService {
    constructor(public http: Http) {
        this.http = http;
    }
     
	navigatee(src,dst) {
		var token = window.sessionStorage.getItem('token');
		var params = "srcLat=" + src.lat + "&srcLng=" + src.lng + "&dstLat=" + dst.lat + "&dstLng=" + dst.lng+"&accessibilityThreshold=5";
        var headers = new Headers();
        headers.append('Content-Type', 'application/x-www-form-urlencoded');
		headers.append('authToken',token);
        return  this.http.post(Constants.serverAddress +'/navigation', params, {headers: headers});
	}
}
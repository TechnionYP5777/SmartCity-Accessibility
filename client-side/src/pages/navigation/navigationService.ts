import {Injectable} from "@angular/core";
import { Http, Headers,RequestOptions } from "@angular/http";
import { Constants } from "../constants";

@Injectable()
export class NavigationService {
    constructor(public http: Http) {
        this.http = http;
    }
     
	navigatee() {
		var token = window.sessionStorage.getItem('token');
		var creds = "name=" + "y" + "&password=" + "a";
		//var params = "srcLat=" + 0 + "&srcLng=" + 0 + "&dstLat=" + 0 + "&dstLng=" + 0;
        var headers = new Headers();
        headers.append('Content-Type', 'application/x-www-form-urlencoded');
		headers.append('authToken',token);
        return new Promise(resolve => {
            this.http.post(Constants.serverAddress +'/navigation', creds, {headers: headers}).subscribe(data => {
                if(data.status == 200){
                    resolve(true);
                }
                else
                    resolve(false);
            });
        });
	}
}
import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";

@Injectable()
export class NavigationService {
    constructor(public http: Http) {
        this.http = http;
    }
    
	navigate() {
		var token = window.sessionStorage.getItem('token');
		//var params = "token=" + token + "&srcLat=" + 0 + "&srcLng=" + 0 + "&dstLat=" + 0 + "&dstLng=" + 0;
		var params = "token=" + token;
        return new Promise(resolve => {
            this.http.post('http://localhost:8080/navigation', params).subscribe(data => {
                
            });
        });
	}
}
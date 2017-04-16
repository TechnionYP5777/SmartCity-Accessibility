import {Injectable} from "@angular/core";
import {Http, Headers,RequestOptions} from "@angular/http";

@Injectable()
export class NavigationService {
    constructor(public http: Http) {
        this.http = http;
    }
    
	navigate() {
		var token = window.sessionStorage.getItem('token');
		//var params = "srcLat=" + 0 + "&srcLng=" + 0 + "&dstLat=" + 0 + "&dstLng=" + 0;
		var headers = new Headers({'Content-Type': 'application/json'});
        headers.append('Authorization', token);
        var options = new RequestOptions({ headers: headers });
		var body = "sha=1";
        return new Promise(resolve => {
            this.http.post('http://localhost:8080/navigation',body,options).subscribe(data => {
				if(data.status == 200){
                    resolve(true);
                }
                else
                    resolve(false);
            });
        });
	}
}
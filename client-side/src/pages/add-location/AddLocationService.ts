import {Injectable} from "@angular/core";
import { Http, Headers} from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';

@Injectable()
export class AddLocationService {
    constructor(public http: Http) {
        this.http = http;
    }

	addLocation(name, lat, lng, type){
		var headers = new Headers();
        headers.append('Content-Type', 'application/x-www-form-urlencoded');
		var params = "name=" + name + "&srcLat=" + lat + "&srcLng=" + lng + "&type=" + type;
		 return new Promise(resolve => 
		 {this.http.post(Constants.serverAddress +'/addLocation?', params,{headers: headers}).subscribe(data => {
                if(data.status == 200){
                	console.log('Review added successfully!')
                    resolve(true);
                }
                else
                    resolve(false);
            });
		 });
	}
}
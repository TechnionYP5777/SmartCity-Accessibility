import {Injectable} from "@angular/core";
import { Http} from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';

@Injectable()
export class AddLocationService {
    constructor(public http: Http) {
        this.http = http;
    }

	addLocation(name, lat, lng){
		var params = "name=" + name + "&srcLat=" + lat + "&srcLng=" + lng;
		this.http.post(Constants.serverAddress +'/addLocation?'+params).subscribe(data => {
                if(data.status == 200){
                	console.log('Review added successfully!')
                    resolve(true);
                }
                else
                    resolve(false);
            });
	}
}
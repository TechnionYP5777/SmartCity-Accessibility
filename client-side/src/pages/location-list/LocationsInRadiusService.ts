import {Injectable} from "@angular/core";
import { Http, Headers,RequestOptions } from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';

@Injectable()
export class LocationsInRadiusService {
    constructor(public http: Http) {
        this.http = http;
    }

	GetLocationsInRadiusFrom(lat, lng){
		var params = "srcLat=" + lat + "&srcLng=" + lng;
		return this.http.get(Constants.serverAddress +'/locationsInRadius?'+params).map(res=>res.json());
	}
}
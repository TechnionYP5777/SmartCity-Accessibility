import {Injectable} from "@angular/core";
import { Http, Headers,RequestOptions } from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';

@Injectable()
export class UserInformationService {
    constructor(public http: Http) {
        this.http = http;
    }

	getUserName(token) {
           return this.http.get(Constants.serverAddress +'/userInfo/name/'+token).map(res=>res.json());
	}
}
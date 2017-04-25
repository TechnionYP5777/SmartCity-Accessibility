import {Injectable} from "@angular/core";
import { Http, Headers,RequestOptions } from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';

@Injectable()
export class SearchService {
    constructor(public http: Http) {
        this.http = http;
    }

	search(searchQuery) {
           return this.http.get(Constants.serverAddress +'/simpleSearch/'+"yehalom 17").map(res=>res.json());
	}
}
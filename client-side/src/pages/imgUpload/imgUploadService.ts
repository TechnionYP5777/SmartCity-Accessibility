import {Injectable} from "@angular/core";
import { Http, Headers } from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';

/*
	author: Koral Chapnik
*/

@Injectable()
export class ImgUploadService {
	headers: any;
	
    constructor(public http: Http) {
        this.http = http;
		try {
			var token = JSON.parse(window.sessionStorage.getItem('token')).token;
		} catch(err){
			token = "no token";
		}
		this.headers = new Headers();
		this.headers.append('authToken',token);
    }

	public upload(formData: FormData) {
	var params = formData;
	  return this.http.post(Constants.serverAddress + '/uploadProfileImg',params , {headers: this.headers})
      .map(response => response.text());
	}
}
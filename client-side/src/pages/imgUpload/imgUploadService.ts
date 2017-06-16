import {Injectable} from "@angular/core";
import { Http, Headers } from "@angular/http";
import { Constants } from "../constants";
import 'rxjs/add/operator/map';
import { Camera, CameraOptions } from '@ionic-native/camera';
import { File, FileEntry } from '@ionic-native/file';

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
		this.headers.append('Content-Type',  'multipart/form-data; boundary=&');
		this.headers.append('authToken',token);
    }

	public upload(formData: FormData) {
	  return this.http.post(Constants.serverAddress + '/uploadProfileImg', formData, {headers: this.headers})
      .map(response => response.text());
	}
}
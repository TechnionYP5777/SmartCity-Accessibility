import {Injectable} from "@angular/core";
import {Http} from "@angular/http";

@Injectable()
export class LoginService {

  http : Http;
  data : any;
  constructor(http: Http) {
    this.http = http;
    this.data = null;
  }

  retrieveData() {
    this.http.get('http://ionic.io')
    .subscribe(data => {
      this.data = data;
    });
  }

  getData() {
    return this.data;
  }
}
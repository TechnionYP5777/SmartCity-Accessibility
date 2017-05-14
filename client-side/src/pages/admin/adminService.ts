import {Injectable} from "@angular/core";
import { Http, Headers,RequestOptions } from "@angular/http";
import 'rxjs/add/operator/map';

@Injectable()
export class AdminService {
    constructor(public http: Http) {
        this.http = http;
    }

}
import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import { Constants } from "../constants";

@Injectable()
export class LoginService {
    isLogin: boolean;
    token : any;
    
    constructor(public http: Http) {
        this.http = http;
        this.isLogin = false;
        this.token = null;
    }
    
    storeUserCredentials(token) {
        window.sessionStorage.setItem('token', token);
        this.useCredentials(token);
    }
    
    useCredentials(token) {
        this.isLogin = true;
        this.token = token;
    }
    
    loadUserCredentials() {
        var token = window.sessionStorage.getItem('token');
        this.useCredentials(token);
    }
	
	isLoggedIn(){
		var token = window.sessionStorage.getItem('token');
		return (token != null);
	}
    
    destroyUserCredentials() {
        this.isLogin = false;
        this.token = null;
        window.sessionStorage.clear();
    }
    
    login(user) {
        var creds = "name=" + user.name + "&password=" + user.password;
        var headers = new Headers();
        headers.append('Content-Type', 'application/x-www-form-urlencoded');
        
        return new Promise(resolve => {
            this.http.post(Constants.serverAddress +'/login', creds, {headers: headers}).subscribe(data => {
                if(data.status == 200){
                    this.storeUserCredentials(data.json().token);
                    resolve(true);
                }
                else
                    resolve(false);
            });
        });
    }
	
	signup(user) {
        var creds = "name=" + user.name + "&password=" + user.password;
        var headers = new Headers();
        headers.append('Content-Type', 'application/x-www-form-urlencoded');
        
        return new Promise(resolve => {
            this.http.post(Constants.serverAddress +'/signup', creds, {headers: headers}).subscribe(data => {
                if(data.status == 200){
                    this.storeUserCredentials(data.json().token);
                    resolve(true);
                }
                else
                    resolve(false);
            });
        });
    }
    
    logout() {
        this.destroyUserCredentials();
    }
}
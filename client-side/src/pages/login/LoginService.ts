import {Injectable} from "@angular/core";
import {Http, Headers} from "@angular/http";
import { Constants } from "../constants";
import { AlertController } from 'ionic-angular';

@Injectable()
export class LoginService {
    isLogin: boolean;
    token : any;
    
    constructor(public http: Http,public alertCtrl: AlertController) {
        this.http = http;
        this.isLogin = false;
        this.token = null;
    }
    
    storeUserCredentials(token) {
        window.sessionStorage.setItem('token', JSON.stringify(token));
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
		var currDate = new Date();
        var token = JSON.parse(window.sessionStorage.getItem('token'));
		if(token == null)
			return false;
        var expirationDate = token.expirationDate;
        return (Date.parse(currDate.toISOString()) < expirationDate);
	}
    
	timeout(){
        var token = JSON.parse(window.sessionStorage.getItem('token'));
		if(token == null)
			return;
        var expirationDate = token.expirationDate;
		return expirationDate - Date.parse(new Date().toISOString());
	}
	
    destroyUserCredentials() {
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
                    this.storeUserCredentials(data.json());
                    resolve(true);
                }
			}, err => {
					this.presentAlert("<p>error: " + err.json().error  + "</p> <p> message: " + err.json().message + "</p>");
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
                    this.storeUserCredentials(data.json());
                    resolve(true);
                }
			}
            , err =>{
				this.presentAlert("<p>error: " + err.json().error  + "</p> <p> message: " + err.json().message + "</p>");
                resolve(false);
            });
        });
    }
    
    logout() {
		this.destroyUserCredentials();
		try{
			var token = JSON.parse(window.sessionStorage.getItem('token')).token;
		}
		catch(err){
			token = "no token";
		}
        var headers = new Headers();
        headers.append('Content-Type', 'application/x-www-form-urlencoded');
		headers.append('authToken',token);
        return  this.http.post(Constants.serverAddress +'/logout', '', {headers: headers});
    }
	
	presentAlert(str) {
		let alert = this.alertCtrl.create({
		  title: 'Alert',
		  subTitle: str,
		  buttons: ['OK']
		});
		alert.present();
	}
}
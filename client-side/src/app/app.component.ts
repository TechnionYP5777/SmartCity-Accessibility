import { Component, ViewChild } from '@angular/core';
import { Platform, Nav } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { AdminPage } from '../pages/admin/admin';
import { LoginService } from '../pages/login/LoginService';
import { UserPagePage } from '../pages/user-page/user-page';
import { LoginPage } from '../pages/login/login';
import { AlertController,Events } from 'ionic-angular';
import { MapviewPage } from '../pages/mapview/mapview';


@Component({
  templateUrl: 'app.html'
})
export class MyApp {
	@ViewChild(Nav) nav: Nav;
    rootPage:any = MapviewPage;

    isLoggedin : any;
	isAdmin : any;

  constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen,public loginService : LoginService,public alertCtrl: AlertController, public events: Events) {
    platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      statusBar.styleDefault();
      splashScreen.hide();
    });
    this.isLoggedin = this.loginService.isLoggedIn();
	this.events.subscribe('login:updateState',() => {
		this.updateState();
	});
  }

    updateState(){
	    this.isLoggedin = this.loginService.isLoggedIn();
		if(this.isLoggedin){
			var token = JSON.parse(window.sessionStorage.getItem('token'));
			this.isAdmin = token.admin;
		} else{
			this.isAdmin = false;
		}
	}

	loginPage(){
		if(!(this.nav.getActive().instance instanceof LoginPage)){
			this.nav.push(LoginPage);
		}
	}

	mapPage(){
		if(!(this.nav.getActive().instance instanceof MapviewPage)){
			this.nav.push(MapviewPage);
		}
	}

	userprofile(){
		this.nav.push(UserPagePage);
	}

	admin(){
		this.nav.push(AdminPage);
	}

	logout(){
		this.loginService.logout().subscribe(()=>
		{
			this.updateState();
		});
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

import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import {LoginPage} from '../pages/login/login';
import {MapviewPage} from '../pages/mapview/mapview';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import {MapClickMenuPage} from '../pages/mapclickmenu/mapclickmenu';
import { AddReviewPage } from '../pages/add-review/add-review';
import {SignupPage} from '../pages/signup/signup';
import {UserPagePage} from '../pages/user-page/user-page';
import {LoginService} from '../pages/login/LoginService';
import {NavigationService} from '../pages/navigation/navigationService';
import { NavigationPage } from '../pages/navigation/navigation';

@NgModule({
  declarations: [
    MyApp,
    HomePage,
	LoginPage,
	MapviewPage,
	MapClickMenuPage,
	AddReviewPage,
	SignupPage,
	UserPagePage,
	NavigationPage
  ],
  imports: [
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    HomePage,
	LoginPage,
	MapviewPage,
	MapClickMenuPage,
	AddReviewPage,
	SignupPage,
	UserPagePage,
	NavigationPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
	LoginService,
	NavigationService
  ]
})
export class AppModule {}

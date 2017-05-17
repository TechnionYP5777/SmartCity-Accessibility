import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';
import { HomePage } from '../pages/home/home';
import {LoginPage} from '../pages/login/login';
import {AdminPage} from '../pages/admin/admin';
import {MapviewPage} from '../pages/mapview/mapview';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import {MapClickMenuPage} from '../pages/mapclickmenu/mapclickmenu';
import { AddReviewPage } from '../pages/add-review/add-review';
import {SignupPage} from '../pages/signup/signup';
import {UserPagePage} from '../pages/user-page/user-page';
import {LoginService} from '../pages/login/LoginService';
import {AdminService} from '../pages/admin/adminService';
import {NavigationService} from '../pages/navigation/navigationService';
import { NavigationPage } from '../pages/navigation/navigation';
import { AddReviewService } from '../pages/add-review/AddReviewService';
import { IonRating } from '../components/ion-rating/ion-rating';
import {ComplexSearchPage} from '../pages/complex-search/complex-search';
import {ComplexSearchService} from '../pages/complex-search/complexSearchService';
import {SearchService} from '../pages/mapview/searchService';
import {AddSearchQueryPage} from '../pages/addSearchQueryMenu/addsearchquerymenu';
import {UserInformationService} from '../pages/user-page/userInformationService';
import { LocationListPage } from '../pages/location-list/location-list';
import { ViewSearchQueryPage } from '../pages/viewSearchQuery/viewsearchquery';
import { GetReviewsPage } from '../pages/reviews/reviews';
import {GetReviewsService} from '../pages/reviews/ReviewsService';
import { navigationManeuverPage } from '../pages/navigation_maneuver/navigation_maneuver';

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
	NavigationPage,
	ComplexSearchPage,
	IonRating,
	AddSearchQueryPage,
	LocationListPage,
	AdminPage,
	ViewSearchQueryPage,
	GetReviewsPage,
	navigationManeuverPage
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
	NavigationPage,
	ComplexSearchPage,
	AddSearchQueryPage,
	LocationListPage,
	AdminPage,
	ViewSearchQueryPage,
	GetReviewsPage,
	navigationManeuverPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
	LoginService,
	NavigationService,
	AddReviewService,
	SearchService,
	ComplexSearchService,
	UserInformationService,
	AdminService,
	GetReviewsService
  ]
})
export class AppModule {}

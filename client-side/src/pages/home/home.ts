import { Component } from '@angular/core';
import {LoginPage} from '../login/login';
import { NavController } from 'ionic-angular';
import {MapviewPage} from '../mapview/mapview';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {
  loginPage = LoginPage;
  mapviewPage = MapviewPage;
  constructor(public navCtrl: NavController) {
    
  }

}

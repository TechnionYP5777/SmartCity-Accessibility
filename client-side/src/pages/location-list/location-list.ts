import { Component } from '@angular/core';
import { NavController, NavParams } from 'ionic-angular';
import { LocationsInRadiusService } from './LocationsInRadiusService';

@Component({
  selector: 'page-location-list',
  templateUrl: 'location-list.html'
})
export class LocationListPage {
  constructor(public navCtrl: NavController, public navParams: NavParams, public locationsInRadius: LocationsInRadiusService) {}
}

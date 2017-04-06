import { Component, ViewChild, ElementRef } from '@angular/core';
import { NavController } from 'ionic-angular';
import { Geolocation } from '@ionic-native/geolocation';
import { AlertController } from 'ionic-angular';

declare var google;
 
 
@Component({
  selector: 'page-mapview',
  templateUrl: 'mapview.html'
})
export class MapviewPage {
 
  @ViewChild('map') mapElement: ElementRef;
  map: any;
  marker:any;
  geolocation: Geolocation
  constructor(public navCtrl: NavController,public alertCtrl: AlertController) {}
  
  ionViewDidLoad(){
    this.loadMap();
  }
presentAlert(str) {
    let alert = this.alertCtrl.create({
      title: 'Alert',
      subTitle: str,
      buttons: ['OK']
    });
    alert.present();
}
loadMap(){
	this.geolocation = new Geolocation();
    this.geolocation.getCurrentPosition().then((position) => {
 
    let latLng = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
 
    let mapOptions = {
        center: latLng,
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    }
	
    this.map = new google.maps.Map(document.getElementById('map'), mapOptions);
	google.maps.event.addListener(this.map,'click',(event)=>{ this.presentAlert('hi'+event.latLng); } );
    }, (err) => {
      console.log(err);
    });
 
  }
 
}

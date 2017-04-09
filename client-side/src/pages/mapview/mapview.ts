import { Component, ViewChild, ElementRef } from '@angular/core';
import { NavController,ModalController } from 'ionic-angular';
import { Geolocation } from '@ionic-native/geolocation';
import { AlertController } from 'ionic-angular';
import {MapClickMenuPage} from '../mapclickmenu/mapclickmenu';

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
  constructor(public navCtrl: NavController,public alertCtrl: AlertController,public modalCtrl: ModalController) {}
  
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
		google.maps.event.addListener(this.map,'click',(event)=>{ 
			let clickMenu = this.modalCtrl.create(MapClickMenuPage,{latlng : event.latLng});
			clickMenu.present();
		} );
    }, (err) => {
      console.log(err);
    });
 
  }
}
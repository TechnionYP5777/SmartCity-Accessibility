import { Component } from '@angular/core';
import { NavController, ModalController, NavParams, LoadingController, Events, AlertController } from 'ionic-angular';
import { Camera, CameraOptions } from '@ionic-native/camera';
import { File } from '@ionic-native/file';


@Component({
  selector: 'page-imgUpload',
  templateUrl: 'imgUpload.html',
  
  
})

export class ImgUploadPage {
  myPhoto: any;
  error: any;
  loading: any;
  
  constructor(public navCtrl: NavController, public navParams: NavParams,
			  public modalCtrl: ModalController,
			  public loadingCtrl: LoadingController, public events: Events,
			  public alertCtrl: AlertController, private camera: Camera,
			  private file:File) {
		}
  
  
  takePhoto() {
    this.camera.getPicture({
      quality: 100,
      destinationType: this.camera.DestinationType.FILE_URI,
      sourceType: this.camera.PictureSourceType.CAMERA,
      encodingType: this.camera.EncodingType.PNG,
      saveToPhotoAlbum: true
    }).then(imageData => {
      this.myPhoto = imageData;
 //     this.uploadPhoto(imageData);
    }, error => {
      this.error = JSON.stringify(error);
    });
  }

  selectPhoto(): void {
    this.camera.getPicture({
      sourceType: this.camera.PictureSourceType.PHOTOLIBRARY,
      destinationType: this.camera.DestinationType.FILE_URI,
      quality: 100,
      encodingType: this.camera.EncodingType.PNG,
    }).then(imageData => {
      this.myPhoto = imageData;
//      this.uploadPhoto(imageData);
    }, error => {
      this.error = JSON.stringify(error);
    });
  }
  

  
}

import {Component} from '@angular/core';
import {NavController, NavParams, AlertController, ModalController, ViewController, Events} from 'ionic-angular';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import {CommentService} from "./CommentService";
import {GetReviewsPage} from "../reviews/reviews";
import { Constants } from "../constants";
import {SpecialConstants} from "../special-constants/special-constants"


@Component({
  selector: 'page-comment',
  templateUrl: 'comment.html',
})

export class CommentPage {

  token : any;
  lat : any;
  lng : any;
  type : any;
  subtype : any;
  isLoggedin = false;
  username = "";
  rev : any;
  loading : any;
  allComments = [];
  userComm = "";
  reviewsPage: GetReviewsPage;

  constructor(public viewCtrl: ViewController,
              public modalCtrl: ModalController,
              public navCtrl: NavController,
              public navParams: NavParams,
              public http: Http,
              public alertCtrl: AlertController,
              public events: Events,
              public _constants : SpecialConstants,
              public commentService: CommentService) {
    this.token = window.sessionStorage.getItem('token');
    this.lat = navParams.get('lat');
    this.lng = navParams.get('lng');
    this.type = navParams.get('type');
    this.subtype = navParams.get('subtype');
    this.isLoggedin = navParams.get('loggedIn');
    this.username = navParams.get('username');
    this.rev = navParams.get('rev');
    this.allComments = this.rev.realComments;
  }

  postComment(){
    this.loading = this._constants.createCustomLoading();
    this.loading.present();

    this.commentService.addComment(this.lat, this.lng, this.type, this.subtype, this.rev, this.userComm).then(data => {

      this.loading.dismiss();
      this.events.publish('commentposted:done', this.rev, this.userComm);
      this.viewCtrl.dismiss();

    }
    , err => {
      this.loading.dismiss();
        this._constants.handleError(err.json());
    });
  }


}

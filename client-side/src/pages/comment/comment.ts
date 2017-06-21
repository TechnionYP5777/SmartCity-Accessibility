import {Component} from '@angular/core';
import {LoadingController, NavController, NavParams, AlertController, ModalController, ViewController, Events} from 'ionic-angular';
import {Http} from '@angular/http';
import 'rxjs/add/operator/map';
import {CommentService} from "./CommentService";
import {GetReviewsPage} from "../reviews/reviews";

@Component({
  selector: 'page-comment',
  templateUrl: 'comment.html',
})

export class CommentPage {

  token : any;
  isLoggedin = false;
  username = "";
  rev : any;
  loading : any;
  allComments = [];
  userComm = "";

  constructor(public viewCtrl: ViewController,
              public modalCtrl: ModalController,
              public navCtrl: NavController,
              public navParams: NavParams,
              public http: Http,
              public loadingController: LoadingController,
              public alertCtrl: AlertController,
              public events: Events,
              public commentService: CommentService,
              public reviewsPage: GetReviewsPage) {
    this.token = window.sessionStorage.getItem('token');
    this.isLoggedin = navParams.get('loggedIn');
    this.username = navParams.get('username');
    this.rev = navParams.get('rev');
    this.allComments = this.rev.realComments;
  }

  postComment(){
    this.presentLoadingCustom();
    this.commentService.addComment(this.username, this.rev, this.userComm).then(data => {

      this.loading.dismiss();
      this.events.publish('commentposted:done', this.rev, this.userComm);
      this.viewCtrl.dismiss();

    }
    , err => {
      this.loading.dismiss();
      this.handleError(err.json());
    });
  }


  handleError(err) {
    this.reviewsPage.presentAlert("<p> error is: "+err.error+ "</p> <p> message is: "+ err.message+"</p>");
  }

  presentLoadingCustom() {
    this.loading = this.loadingController.create({
      spinner: 'hide',
      dismissOnPageChange: true,
      content: `<div class="cssload-container">
                  <div class="cssload-whirlpool"></div>
              </div>`,
      cssClass: 'loader'
    });

    this.loading.present();
  }

}

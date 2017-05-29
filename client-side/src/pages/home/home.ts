import { Component, trigger, state, style, transition, animate} from '@angular/core';
import { NavController } from 'ionic-angular';
import { MapviewPage } from '../mapview/mapview';
import { AddReviewPage } from '../add-review/add-review';
import { GetReviewsPage } from '../reviews/reviews'; 

@Component({
  selector: 'page-home',  
  templateUrl: 'home.html',
  
   animations: [
 
    trigger('flip', [
      state('flipped', style({
        transform: 'rotate(180deg)',
        backgroundColor: '#f50e80'
      })),
      transition('* => flipped', animate('400ms ease'))
    ])
   ]
})
export class HomePage {
  mapviewPage = MapviewPage;
  addReviewPage = AddReviewPage;
  showReviews = GetReviewsPage;
  flipState: String = 'notFlipped';
    constructor(public navCtrl: NavController) {
    }
  
	toggleFlip() {
		this.flipState = (this.flipState == 'notFlipped') ? 'flipped' : 'notFlipped';
	}
}

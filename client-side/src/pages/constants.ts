import {LoadingController, AlertController} from 'ionic-angular';

export class Constants {

	public static serverAddress='http://localhost:8080';
	public static userExpiredMessage = "Sorry, it seems you were not active for 60 minutes. Please re-login.";
	public static serverNotResponding = "Looks like the server is not responding, try again later";
	public static notLoggedIn = "Please login to do that!";
	public static generalError = "Something went wrong";

  private static alertCtrl: AlertController;
  private static loadingController : LoadingController;

  public static handleError(err) {
    this.presentAlert("<p> error is: " + err + "</p>");
  }

	public static presentAlert(str) {
    let alert = this.alertCtrl.create({
      title: 'Error',
      subTitle: str,
      buttons: ['OK']
    });
    alert.present();
  }

  public static createCustomLoading() {
    var loading = this.loadingController.create({
      spinner: 'hide',
      dismissOnPageChange: true,
      content: `<div class="cssload-container">
                  <div class="cssload-whirlpool"></div>
              </div>`,
      cssClass: 'loader'
    });

    return loading;
  }
}

import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {ClockService, NotificationsService, UserService} from './_services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {

  showInstallButton = false;

  deferredPrompt = null;

  intervalID;
  clock: Date;

  constructor(
    public userService: UserService,
    public clockService: ClockService,
    private notifs: NotificationsService,
  ) {

  }

  @HostListener('window:beforeinstallprompt', ['$event'])

  onBeforeinstallprompt(ev) {

    ev.preventDefault();

// on affiche le bouton install

    this.showInstallButton = true;

// on "stocke" le prompt.

    this.deferredPrompt = ev;

  }

  installApp() {

    this.deferredPrompt.prompt();

    this.deferredPrompt.userChoice

      .then((choiceResult) => {

        if (choiceResult.outcome === 'accepted') {
          this.notifs.showSuccess('App installed');

        } else {
          this.notifs.showError('App not installed');

          this.showInstallButton = false;
        }

        this.deferredPrompt = null;

      });

  }

  ngOnInit() {
    this.clockService.getClock();

    this.intervalID = setInterval(self => {
      self.updateClock();
    }, 1000, this);
  }

  ngOnDestroy() {
    clearInterval(this.intervalID);
  }

  updateClock() {
    if (this.clockService.currentClock
      && this.clockService.currentClock.time
      && this.clockService.currentClock.status) {
      this.clock = new Date(Date.now().valueOf() - this.clockService.currentClock.time.valueOf());
      this.clock.setHours(this.clock.getHours() - 1);
    } else {
      this.clock = undefined;
    }
  }

}

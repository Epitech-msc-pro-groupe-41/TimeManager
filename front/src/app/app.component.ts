import {Component, HostListener, OnInit} from '@angular/core';
import {ClockService, NotificationsService, UserService} from './_services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  showInstallButton = false;

  deferredPrompt = null;

  constructor(
    public userService: UserService,
    public clockService: ClockService,
    private notifs: NotificationsService,
  ) {

  }

  @HostListener('window:beforeinstallprompt', ['$event'])

  onBeforeinstallprompt(ev) {

    this.notifs.showSuccess('Event catched');
    console.log('Event catched !!!');

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

  getWorkingHour() {
    if (this.clockService.currentClock
      && this.clockService.currentClock.time
      && this.clockService.currentClock.status) {
      const time = new Date(Date.now().valueOf() - this.clockService.currentClock.time.valueOf());
      return `${Math.floor(time.getSeconds() / 3600)}:${Math.floor(time.getSeconds() / 60)}:${time.getSeconds() % 60}`;
    } else {
      return '00:00:00';
    }
  }

  ngOnInit(): void {
    this.clockService.getClock();
  }
}

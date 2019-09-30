import {Component, OnDestroy, OnInit} from '@angular/core';
import {ClockService, UserService} from './_services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {

  intervalID;
  clock: Date;

  constructor(
    public userService: UserService,
    public clockService: ClockService,
  ) {

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

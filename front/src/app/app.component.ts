import {Component, OnInit} from '@angular/core';
import {ClockService, UserService} from './_services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(
    public userService: UserService,
    public clockService: ClockService,
  ) {

  }

  getWorkingHour() {
    if (this.clockService.currentClock
      && this.clockService.currentClock.time
      && this.clockService.currentClock.status) {
      const time = new Date(Date.now().valueOf() - this.clockService.currentClock.time.valueOf());
      return  `${Math.floor(time.getSeconds() / 3600)}:${Math.floor(time.getSeconds() / 60)}:${time.getSeconds() % 60}`;
    } else {
      return '00:00:00';
    }
  }

  ngOnInit(): void {
    this.clockService.getClock();
  }
}

import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {UserService} from './user.service';
import {Clock} from '../_models';
import {NotificationsService} from './notifications.service';

@Injectable({
  providedIn: 'root'
})
export class ClockService {

  currentClock: Clock;

  constructor(private http: HttpClient,
              private userService: UserService,
              private notifs: NotificationsService) {
    this.currentClock = new Clock();
    this.currentClock.status = false;
  }

  getClock() {
    if (this.userService.currentUserValue) {
      return this.http.get<any>(environment.apiUrl + 'clocks/' + this.userService.currentUserValue.userID)
        .subscribe(response => {
          console.log('getClock: ', response);
          if (response) {
            this.currentClock.time = new Date(response.time);
            this.currentClock.status = response.status;
          }
        },
          err => {
          this.notifs.showError(err);
          });
    }
  }

  clock() {
    if (this.userService.currentUserValue) {
      return this.http.post<any>(environment.apiUrl + 'clocks/' + this.userService.currentUserValue.userID,
        {
          status: !this.currentClock.status
        })
        .subscribe(response => {
          console.log('getClock: ', response);
          if (response) {
            this.currentClock.status = response.status;
            this.getClock();
          }
        }, error =>  {
          this.notifs.showError(error);
        });
    }
  }
}

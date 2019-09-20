import {Injectable} from '@angular/core';
import {UserService} from './user.service';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {MatDialog} from '@angular/material';
import {WorkingTime} from '../_models';
import {NotificationsService} from './notifications.service';

@Injectable({
  providedIn: 'root'
})
export class WorkingtimeService {

  workingTimes: any[];

  constructor(
    private userService: UserService,
    private http: HttpClient,
    private notifs: NotificationsService,
  ) {
  }

  getWorkingTimes() {
    if (this.userService.currentUserValue) {
      return this.http.get<any>(environment.apiUrl + 'workingtimes/' + this.userService.currentUserValue.userID)
        .subscribe(response => {
          console.log('getworkingTimes: ', response);
          if (response) {
            this.workingTimes = response;
          }
        }, err => {
          this.notifs.showError('getWorkingtimes: ' + err);
        });
    }
  }

  createWorkingTime(start: Date, end: Date) {
    if (this.userService.currentUserValue && start && end) {
      return this.http.post<any>(environment.apiUrl + 'workingtimes/' + this.userService.currentUserValue.userID,
        {start: start.valueOf(), end: end.valueOf()})
        .subscribe(response => {
          this.notifs.showSuccess('Working time created');
          this.getWorkingTimes();
        }, err => {
          this.notifs.showError('Working time not created');
        });
    }
  }

  updateWorkingTime(workingTimeId , start: Date, end: Date) {
    if (this.userService.currentUserValue && workingTimeId && (start || end)) {
      return this.http.put<any>(environment.apiUrl + 'workingtimes/' + workingTimeId,
        {start: start.valueOf(), end: end.valueOf(), userID: this.userService.currentUserValue.userID})
        .subscribe(response => {
          console.log('updateWorkingTime: ', response);
          this.notifs.showSuccess('Working time updated');
          this.getWorkingTimes();
        }, err => {
          this.notifs.showError('Working time not updated');
        });
    }
  }

  deleteWorkingTime(workingTimeID: string) {
    if (this.userService.currentUserValue && workingTimeID) {
      return this.http.delete<any>(environment.apiUrl + 'workingtimes/' + workingTimeID)
        .subscribe(response => {
          console.log('deleteWorkingTime: ', response);
          this.notifs.showSuccess('Working time deleted');
          this.getWorkingTimes();
        }, err => {
          this.notifs.showError('Working time not deleted');
        });
    }
  }
}

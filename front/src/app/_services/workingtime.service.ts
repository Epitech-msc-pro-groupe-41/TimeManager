import {Injectable} from '@angular/core';
import {UserService} from './user.service';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {MatDialog} from '@angular/material';
import {WorkingTime} from '../_models';

@Injectable({
  providedIn: 'root'
})
export class WorkingtimeService {

  workingTimes: any[];

  constructor(
    private userService: UserService,
    private http: HttpClient,
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
        });
    }
  }

  createWorkingTime(form: any) {
    if (this.userService.currentUserValue && form) {
      return this.http.post<any>(environment.apiUrl + 'workingtimes/' + this.userService.currentUserValue.userID,
        {start: form.start, end: form.end})
        .subscribe(response => {
          console.log('createWorkingTime: ', response);
          this.getWorkingTimes();
        });
    }
  }

  updateWorkingTime(workingTimeId , start, end) {
    if (this.userService.currentUserValue && workingTimeId && (start || end)) {
      return this.http.put<any>(environment.apiUrl + 'workingtimes/' + workingTimeId,
        {start, end, userID: this.userService.currentUserValue.userID})
        .subscribe(response => {
          console.log('updateWorkingTime: ', response);
          this.getWorkingTimes();
        });
    }
  }

  deleteWorkingTime(workingTimeID: string) {
    if (this.userService.currentUserValue && workingTimeID) {
      return this.http.delete<any>(environment.apiUrl + 'workingtimes/' + workingTimeID)
        .subscribe(response => {
          console.log('deleteWorkingTime: ', response);
          this.getWorkingTimes();
        });
    }
  }
}

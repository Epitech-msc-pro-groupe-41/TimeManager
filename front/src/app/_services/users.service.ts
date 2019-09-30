import {Injectable, OnInit} from '@angular/core';
import {User} from '../_models';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {NotificationsService} from './notifications.service';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  users: User[];

  constructor(
    private http: HttpClient,
    private notifs: NotificationsService,
  ) {
    this.users = [];
  }

  getUsers() {
    return this.http.get<any>(environment.apiUrl + 'users/all')
      .subscribe(response => {
        if (response) {
          this.users = response;
        }
      }, error => {
        this.notifs.showError('Can\'t get users');
      });
  }

  getUserByID(userID: string) {
    return this.http.get<any>(environment.apiUrl + 'users/' + userID);
  }

  changeRole({userID, type}) {
    if (type === 'Employee' || type === 'Admin' || type === 'Manager') {
      return this.http.post<any>(environment.apiUrl + 'users/changeRole/' + userID +
        '?type=' + type, {})
        .subscribe(response => {
          this.notifs.showSuccess('Role changed');
          this.getUsers();
        }, error => {
          this.notifs.showError('Role not changed');
        });
    }
  }

}

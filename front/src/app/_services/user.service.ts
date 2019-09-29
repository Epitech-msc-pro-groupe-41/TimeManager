import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {first, map} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {User} from '../_models';
import {Router} from '@angular/router';
import {NotificationsService} from './notifications.service';

export interface RegisterParams {
  email: string;
  lastName: string;
  firstName: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;
  public sidenavOpened = true;

  constructor(private http: HttpClient, private router: Router, private notifs: NotificationsService) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  toggleSideNav() {
    this.sidenavOpened = !this.sidenavOpened;
  }

  public get currentUserValue() {
    return this.currentUserSubject.value;
  }

  isEmployee() {
    return this.currentUserValue.type === 'Employee';
  }

  isAdmin() {
    return this.currentUserValue.type === 'Admin';
  }

  isManager() {
    return this.currentUserValue.type === 'Manager';
  }

  login(email, password) {
    return this.http.post<any>(environment.apiUrl + 'auth/signIn', {email, password});
  }

  register(params: RegisterParams) {
    return this.http.post<any>(environment.apiUrl + 'auth/signUp', {
      email: params.email,
      firstName: params.firstName,
      lastName: params.lastName,
      password: params.password,
    });
  }

  refreshUser() {
    this.getMe(this.currentUserValue.userID).pipe(first()).subscribe(data => {
    }, error => {
      this.logout();
      this.router.navigate(['login']);
    });
  }

  getMe(userID: string) {
    return this.http.get<User>(environment.apiUrl + 'users/' + userID)
      .pipe(map(user => {
        // store user details and jwt token in local storage to keep user logged in between page refreshes
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
        return user;
      }));
  }

  updateUser(email, firstName, lastName) {
    return this.http.put<any>(environment.apiUrl + 'users/' + this.currentUserValue.userID, {
      email,
      firstName,
      lastName,
    });
  }

  deleteUser() {
    return this.http.delete<any>(environment.apiUrl + 'users/' + this.currentUserValue.userID);
  }

  logout() {
    if (this.currentUserValue) {
      this.deleteUser().subscribe(response => {},
        err => {
          this.notifs.showError('User not deleted');
        });
    }
    localStorage.removeItem('currentUser');
    localStorage.removeItem('user-token');
    this.currentUserSubject.next(null);
  }
}

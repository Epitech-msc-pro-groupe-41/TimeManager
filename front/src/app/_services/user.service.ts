import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {first, map} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {User} from '../_models';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;
  public sidenavOpened = true;

  constructor(private http: HttpClient, private router: Router) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  toggleSideNav() {
    this.sidenavOpened = !this.sidenavOpened;
  }

  public get currentUserValue() {
    return this.currentUserSubject.value;
  }

  login(email, username) {
    return this.http.get<any>(environment.apiUrl + '/users?email=' + email + '&username=' + username);
  }

  register(email, username) {
    return this.http.post<any>(environment.apiUrl + '/users', { email, username});
  }

  refreshUser() {
    this.getMe(this.currentUserValue.userID).pipe(first()).subscribe(data => {
    }, error => {
      this.logout();
      this.router.navigate(['login']);
    });
  }

  getMe(userID: string) {
    return this.http.get<User>(environment.apiUrl + '/users/' + userID)
      .pipe(map(user => {
        // store user details and jwt token in local storage to keep user logged in between page refreshes
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
        return user;
      }));
  }

  updateUser(email, username) {
    return this.http.put<any>(environment.apiUrl + '/users/' + this.currentUserValue.userID, {
      email,
      username,
      id: this.currentUserValue.id,
      userID: this.currentUserValue.userID
    });
  }

  deleteUser() {
    return this.http.delete<any>(environment.apiUrl + '/users/' + this.currentUserValue.userID);
  }

  logout() {
    // remove user from local storage and set current user to null
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}

import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserService} from '../../_services';
import {Subscription} from 'rxjs';
import {User} from '../../_models';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit, OnDestroy{

  userSubscription: Subscription;
  currentUser: User;

  constructor(
    public userSevice: UserService,
    private router: Router,
    ) {
    this.userSubscription = this.userSevice.currentUser.subscribe(user => {
      console.log('user: ', user);
      this.currentUser = user;
    });
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.userSubscription.unsubscribe();
  }

  logout() {
    this.userSevice.logout();
    this.router.navigate(['login']);
  }

}

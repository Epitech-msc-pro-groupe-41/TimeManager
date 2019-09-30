import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {NotificationsService, UserService, UsersService} from '../../_services';
import {User} from '../../_models';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  user: User;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private notifs: NotificationsService,
    private usersService: UsersService
  ) {
  }

  ngOnInit() {
    if (this.route.snapshot.params.userID) {
      this.refreshUser();
    } else {
      this.router.navigate(['users']);
      this.notifs.showError('No UserID specified');
    }
  }

  goToUsers() {
    this.router.navigate(['users']);
  }

  refreshUser() {
    this.usersService.getUserByID(this.route.snapshot.params.userID)
      .subscribe(response => {
        if (response) {
          this.user = response;
        }
      }, error => {
        this.router.navigate(['user']);
        this.notifs.showError('Bad userID');
      });
  }

}

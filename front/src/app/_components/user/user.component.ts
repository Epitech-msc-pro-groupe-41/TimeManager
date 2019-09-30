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
  returnUrl: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private notifs: NotificationsService,
    private usersService: UsersService
  ) {
  }

  ngOnInit() {
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || 'users';
    if (this.route.snapshot.params.userID) {
      this.refreshUser();
    } else {
      this.router.navigate([this.returnUrl]);
      this.notifs.showError('No UserID specified');
    }
  }

  goBack() {
    this.router.navigate([this.returnUrl]);
  }

  refreshUser() {
    this.usersService.getUserByID(this.route.snapshot.params.userID)
      .subscribe(response => {
        if (response) {
          this.user = response;
        }
      }, error => {
        this.router.navigate([this.returnUrl]);
        this.notifs.showError('Bad userID');
      });
  }

}

import {Component, OnInit} from '@angular/core';
import {NotificationsService, UserService} from '../../_services';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent implements OnInit {

  userSubscription: Subscription;

  email: string;
  firstName: string;
  lastName: string;
  loading = false;
  type: string;

  constructor(
    public userService: UserService,
    private notifs: NotificationsService
  ) {
    this.userSubscription = this.userService.currentUser.subscribe(user => {
      if (user) {
        this.email = user.email;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.type = user.type;
      }
    });
  }

  ngOnInit() {
  }

  isFormValid() {
    return this.email
      && this.firstName
      && this.lastName
      && (this.email !== this.userService.currentUserValue.email
        || this.firstName !== this.userService.currentUserValue.firstName
        || this.lastName !== this.userService.currentUserValue.lastName);
  }

  onSubmit() {
    if (this.isFormValid()) {
      this.loading = true;
      this.userService.updateUser(this.email, this.firstName, this.lastName)
        .subscribe(response => {
          this.loading = false;
          this.notifs.showSuccess('Profil updated');
          this.userService.refreshUser();
        }, error => {
          this.loading = false;
          this.notifs.showSuccess('Profil not updated');
        });
    }
  }

}

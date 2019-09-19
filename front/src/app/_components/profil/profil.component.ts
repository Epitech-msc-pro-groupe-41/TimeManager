import {Component, OnInit} from '@angular/core';
import {UserService} from '../../_services';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-profil',
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent implements OnInit {

  userSubscription: Subscription;

  email: string;
  username: string;
  loading = false;

  constructor(
    private userService: UserService
  ) {
    this.userSubscription = this.userService.currentUser.subscribe(user => {
      this.email = user.email;
    });
  }

  ngOnInit() {
  }

  isFormValid() {
    return this.email
      && this.username
      && (this.email !== this.userService.currentUserValue.email);
  }

  onSubmit() {
    if (this.isFormValid()) {
      this.loading = true;
      this.userService.updateUser(this.email, this.username)
        .subscribe(response => {
          this.loading = false;
          this.userService.refreshUser();
        }, error => {
          this.loading = false;
        });
    }
  }

}

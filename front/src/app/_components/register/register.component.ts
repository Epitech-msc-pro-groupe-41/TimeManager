import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {NotificationsService, UserService} from '../../_services';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private notifs: NotificationsService,
  ) {
    // redirect to home if already logged in
    if (this.userService.currentUserValue) {
      this.router.navigate(['/']);
    } else {
      this.userService.logout();
    }
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      password: ['', Validators.required],
      confirmedPassword: ['', Validators.required]
    });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registerForm.invalid && this.f.password.value !== this.f.confirmedPAssword.value) {
      return;
    }

    this.loading = true;
    this.userService.register({
      email: this.f.email.value,
      firstName: this.f.firstName.value,
      lastName: this.f.lastName.value,
      password: this.f.password.value,
    })
      .subscribe(response => {

          if (response && response.userID) {
            localStorage.setItem('user-token', response.token);
            this.userService.getMe(response.userID)
              .pipe(first())
              .subscribe(
                data => {
                  this.loading = false;
                  this.router.navigate([this.returnUrl]);
                },
                err => {
                  this.notifs.showError('Bad User token or ID');
                  this.loading = false;
                });
          }
        },
        err => {
          this.notifs.showError('Email is already used or bad infos');
          this.loading = false;
        }
      );
  }
}

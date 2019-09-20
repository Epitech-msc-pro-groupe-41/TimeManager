import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {NotificationsService, UserService} from '../../_services';
import {first} from 'rxjs/operators';
import {error} from 'util';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private  notifs: NotificationsService,
  ) {
    // redirect to home if already logged in
    if (this.userService.currentUserValue) {
      this.router.navigate(['/']);
    } else {
      this.userService.logout();
    }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;

    this.userService.login(this.f.email.value, this.f.password.value).subscribe(response => {

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
                this.notifs.showError('Bad User Token or id');
                this.loading = false;
              });
        }
      },
      err => {
        this.notifs.showError('Bad credentials');
        this.loading = false;
      }
    );

  }
}

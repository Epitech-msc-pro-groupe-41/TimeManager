import { Component, OnInit } from '@angular/core';
import {UserService} from '../_services';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(
    private userService: UserService,
    private router: Router
  ) { }

  ngOnInit() {
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['login']);
  }

}

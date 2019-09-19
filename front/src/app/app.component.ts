import {Component, OnInit} from '@angular/core';
import {ClockService, UserService} from './_services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(
    public userService: UserService,
    public clockService: ClockService,
  ) {

  }

  ngOnInit(): void {
  }
}

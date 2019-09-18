import { Component } from '@angular/core';
import {UserService} from './_services';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  constructor(
    public userService: UserService
  ) {

  }
}

import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NotificationsService, UserService, WorkingtimeService} from '../../_services';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  constructor(
    public userService: UserService,
  ) {
  }

}

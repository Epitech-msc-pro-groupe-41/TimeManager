import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UserService, WorkingtimeService} from '../../_services';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor(
    private http: HttpClient,
    private userService: UserService,
  ) {
  }

  ngOnInit() {
  }

  refresh() {
    if (this.userService.currentUserValue) {
      return this.http.get<any>(environment.apiUrl + 'chartManager/' + this.userService.currentUserValue.userID)
        .subscribe(response => {
          console.log('getChart: ', response);
          if (response) {
          }
        });
    }
  }

}

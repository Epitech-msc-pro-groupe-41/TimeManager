import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NotificationsService, UserService, WorkingtimeService} from '../../_services';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  chartType = 'bar';

  chartDatasets: Array<any> = [
    {data: []}
  ];

  chartLabels: Array<any> = [];

  chartColors: Array<any> = [
    {
      backgroundColor: '#8FA2EA',
      borderColor: '#4159B5',
      borderWidth: 2,
    }
  ];

  chartOptions: any = {
    responsive: true
  };

  constructor(
    private http: HttpClient,
    private userService: UserService,
    private notifs: NotificationsService
  ) {
  }

  ngOnInit() {
    this.refresh();
  }

  refresh() {
    if (this.userService.currentUserValue) {
      return this.http.get<any>(environment.apiUrl + 'chartManager/' + this.userService.currentUserValue.userID)
        .subscribe((response: any) => {
          console.log('getChart: ', response);
          if (response) {
            this.chartDatasets[0].data = [];
            this.chartLabels = [];

            const array: Array<any> = response.charData;
            array.forEach(elem => {
              this.chartLabels.push(elem.date);
              this.chartDatasets[0].data.push(elem.hours);

            });
          }
        }, err => {
          this.notifs.showError('getchart Error: ' + err);
        });
    }
  }

}

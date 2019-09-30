import {Component, Input, OnInit} from '@angular/core';
import {Team, User} from '../../_models';
import {NotificationsService, StatsService, UserService} from '../../_services';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.scss']
})
export class ChartComponent implements OnInit {

  ChartType = [
    {label: 'Bar', value: 'bar'},
    {label: 'Line', value: 'line'},
    {label: 'Horizontal Bar', value: 'horizontalBar'},
  ];

  @Input() team: Team;
  @Input() user: User;

  chartType = this.ChartType[0].value;

  scale = 'daily';

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

  startDate: Date;

  endDate: Date;

  constructor(
    private userService: UserService,
    private notifs: NotificationsService,
    private statsService: StatsService,
  ) {

  }

  ngOnInit() {
    this.setStart();
    this.setEnd();
    this.refresh();
  }

  setStart() {
    this.startDate = new Date();
    if (this.scale === 'daily') {
      this.startDate.setDate(this.startDate.getDate() - 6);
    } else if (this.scale === 'weekly') {
      this.startDate.setMonth(this.startDate.getMonth() - 1);
    }
    return 0;
  }

  setEnd() {
    this.endDate = new Date();
  }

  updateChartFromArray(data: any) {
    if (data && data.data) {
      this.chartDatasets[0].data = [''];
      this.chartLabels = [0];

      data.data.forEach(elem => {
        this.chartLabels.push(elem.date);
        this.chartDatasets[0].data.push(elem.hours);

      });
    }
  }

  getTeamStats() {
    if (this.scale === 'daily') {
      this.statsService.getDailyStatsForTeam({
        teamID: this.team.teamID,
        start: this.startDate.valueOf(), end: this.endDate.valueOf()
      }).subscribe(response => {
        if (response) {
          this.updateChartFromArray(response);
        }
      }, error => {
        this.notifs.showError('Can\'t get stats');
      });

    } else if (this.scale === 'weekly') {
      this.statsService.getWeeklyStatsForTeam({
        teamID: this.team.teamID,
        start: this.startDate.valueOf(), end: this.endDate.valueOf()
      }).subscribe(response => {
        if (response) {
          this.updateChartFromArray(response);
        }
      }, error => {
        this.notifs.showError('Can\'t get stats');
      });
    }
  }

  getUserStats() {
    if (this.scale === 'daily') {
      this.statsService.getDailyStatsForUser({
        userID: this.user.userID,
        start: this.startDate.valueOf(), end: this.endDate.valueOf()
      }).subscribe(response => {
        if (response) {
          this.updateChartFromArray(response);
        }
      }, error => {
        this.notifs.showError('Can\'t get stats');
      });

    } else if (this.scale === 'weekly') {
      this.statsService.getWeeklyStatsForUser({
        userID: this.user.userID,
        start: this.startDate.valueOf(), end: this.endDate.valueOf()
      }).subscribe(response => {
        if (response) {
          this.updateChartFromArray(response);
        }
      }, error => {
        this.notifs.showError('Can\'t get stats');
      });
    }
  }

  startChange(start: Date) {
    if (start) {
      this.startDate = start;
      this.refresh();
    }
  }

  endChange(end: Date) {
    if (end) {
      this.endDate = end;
      this.refresh();
    }
  }

  scaleChange(newScale: string) {
    if (newScale) {
      this.scale = newScale;
      this.setStart();
      this.setEnd();
      this.refresh();
    }
  }

  refresh() {
    if (this.userService.currentUserValue) {
      if (this.user) {
        this.getUserStats();
      } else if (this.team) {
        this.getTeamStats();
      }
    }
  }

}

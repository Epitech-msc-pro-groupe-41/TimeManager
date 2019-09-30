import { Injectable } from '@angular/core';
import {Team} from '../_models';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StatsService {

  constructor(
    private http: HttpClient,
  ) { }

  getDailyStatsForTeam({teamID, start, end}) {
    return this.http.get<any>(environment.apiUrl + 'stats/team/daily/' + teamID
      + '?start=' + start + '&end=' + end);
  }

  getWeeklyStatsForTeam({teamID, start, end}) {
    return this.http.get<any>(environment.apiUrl + 'stats/team/weekly/' + teamID
      + '?start=' + start + '&end=' + end);
  }

  getDailyStatsForUser({userID, start, end}) {
    return this.http.get<any>(environment.apiUrl + 'stats/user/daily/' + userID
      + '?start=' + start + '&end=' + end);
  }

  getWeeklyStatsForUser({userID, start, end}) {
    return this.http.get<any>(environment.apiUrl + 'stats/user/weekly/' + userID
      + '?start=' + start + '&end=' + end);
  }
}

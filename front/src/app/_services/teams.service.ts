import {Injectable} from '@angular/core';
import {UserService} from './user.service';
import {HttpClient} from '@angular/common/http';
import {NotificationsService} from './notifications.service';
import {Team, User} from '../_models';
import {environment} from '../../environments/environment';
import {Subscription} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TeamsService {

  teams: Team[];
  currentUser: User;
  userSubscription: Subscription;

  constructor(
    private userService: UserService,
    private http: HttpClient,
    private notifs: NotificationsService,
  ) {
    this.userSubscription = this.userService.currentUser
      .subscribe(user => {
        this.currentUser = user;
      });
    this.teams = [
      {
        name: 'Team1',
        teamID: '1234567',
        managerID: '12345678',
        createDate: new Date(123456789)
      },
      {
        name: 'Team2',
        teamID: '1234567',
        managerID: '12345678',
        createDate: new Date(123456789)
      },
      {
        name: 'Team3',
        teamID: '1234567',
        managerID: '12345678',
        createDate: new Date(123456789)
      },
    ];
  }

  getAllTeams() {
   /* return this.http.get<any>(environment.apiUrl + 'teams/' + this.currentUser.userID)
      .subscribe(teams => {
        if (teams) {
          this.teams = teams;
        }
      });*/
  }

  createTeam(name: string) {
    return this.http.post<any>(environment.apiUrl + 'teams/' + this.currentUser.userID,
      {
        name
      }).subscribe( response => {
        if (response) {
          this.notifs.showSuccess('Team created');
          this.getAllTeams();
        }
    }, error => {
      this.notifs.showError('Team not created');
    });
  }

  getTeamById(teamID: string) {
    return this.http.get<any>(environment.apiUrl + 'teams/' + teamID);
  }

  updateTeam(teamID: string, name: string) {
    return this.http.put<any>(environment.apiUrl + 'teams/' + teamID,
      {
        name
      });
  }

  deleteTeam(teamID: string) {
    return this.http.delete<any>(environment.apiUrl + 'teams/' + teamID);
  }

  getTeamMembers(teamID: string) {
    return this.http.get<any>(environment.apiUrl + 'teamMembers/' + teamID);
  }

  addUserToTeam(userID: string, teamID: string) {
    return this.http.post<any>(environment.apiUrl + 'teamMembers', {
      userID,
      teamID
    });
  }

  removeUserFromTeam(teamID: string, userID: string) {
    return this.http.delete<any>(environment.apiUrl + 'teamMembers/' + teamID + '/' + userID);
  }
}

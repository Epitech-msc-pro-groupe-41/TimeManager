import {Component, OnInit} from '@angular/core';
import {Team, User} from '../../_models';
import {MatDialog} from '@angular/material';
import {NotificationsService, TeamsService} from '../../_services';
import {UpdateTeamDialog} from './update-team.dialog';
import {ActivatedRoute, Router} from '@angular/router';
import {DeleteTeamDialog} from './delete-team.dialog';
import {RemoveEmployeeDialog} from './remove-employee.dialog';
import {AddEmployeeDialog} from './add-employee.dialog';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.scss']
})
export class TeamComponent implements OnInit {

  team: Team;
  employees: User[];

  constructor(
    public dialog: MatDialog,
    public teamsService: TeamsService,
    private route: ActivatedRoute,
    private router: Router,
    private notifs: NotificationsService,
  ) {
  }

  goToTeams() {
    this.router.navigate(['teams']);
  }

  ngOnInit() {
    if (this.route.snapshot.params.teamID) {
      this.refreshTeam();
    } else {
      this.router.navigate(['teams']);
      this.notifs.showError('No TeamID specified');
    }
  }

  refreshTeam() {
    this.teamsService.getTeamById(this.route.snapshot.params.teamID)
      .subscribe(response => {
        if (response) {
          this.team = response;
          this.getEmployees();
        }
      }, error => {
        this.router.navigate(['teams']);
        this.notifs.showError('Bad teamID');
      });
  }

  openAddEmployeeDialog() {
    const dialogRef = this.dialog.open(AddEmployeeDialog,
      {
        data: {team: this.team},
        width: '300px',
      });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed: ', result);
      if (result) {
        this.teamsService.addUserToTeam({userID: result.user.userID, teamID: result.team.teamID})
          .subscribe(response => {
            this.notifs.showSuccess('User added to team');
            this.getEmployees();
          }, error => {
            this.notifs.showError('User not added to team');
          });
      }
    });
  }

  openUpdateTeamDialog() {
    const dialogRef = this.dialog.open(UpdateTeamDialog,
      {
        data: {name: this.team.name},
        width: '300px',
      });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed: ', result);
      if (result) {
        this.teamsService.updateTeam(this.team.teamID, result)
          .subscribe(response => {
            this.notifs.showSuccess('Team updated');
            this.refreshTeam();
          }, error => {
            this.notifs.showError('Team not updated');
          });
      }
    });
  }

  openDeleteTeamDialog() {
    const dialogRef = this.dialog.open(DeleteTeamDialog,
      {
        width: '300px',
      });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed: ', result);
      if (result) {
        this.teamsService.deleteTeam(this.team.teamID)
          .subscribe(response => {
            this.notifs.showSuccess('Team deleted');
            this.router.navigate(['teams']);
          }, error => {
            this.notifs.showError('Team not deleted');
          });
      }
    });
  }

  showUser(user: User) {
    if (user) {
      this.router.navigate(['users/' + user.userID], { queryParams: { returnUrl: '/teams/' + this.team.teamID }});
    }
  }

  getEmployees() {
    this.teamsService.getTeamMembers(this.team.teamID)
      .subscribe(employees => {
        this.employees = employees;
      });
  }

  openRemoveEmployee(user: User) {
    const dialogRef = this.dialog.open(RemoveEmployeeDialog,
      {
        data: user,
        width: '300px',
      });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed: ', result);
      if (result) {
        this.teamsService.removeUserFromTeam(this.team.teamID, user.userID)
          .subscribe(response => {
            this.notifs.showSuccess('Employee removed from team');
            this.getEmployees();
          }, error => {
            this.notifs.showError('Employee not removed from team');
          });
      }
    });
  }

}

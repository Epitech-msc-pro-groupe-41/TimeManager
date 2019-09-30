import {Component, OnInit} from '@angular/core';
import {NotificationsService, TeamsService, UserService} from '../../_services';
import {Router} from '@angular/router';
import {UpdateWorkingtimeDialog} from '..';
import {MatDialog} from '@angular/material';
import {UpdateTeamDialog} from '../team/update-team.dialog';
import {DeleteTeamDialog} from '../team/delete-team.dialog';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.scss']
})
export class TeamsComponent implements OnInit {

  constructor(
    public teamsService: TeamsService,
    private notifs: NotificationsService,
    private router: Router,
    public dialog: MatDialog,
  ) {
  }

  ngOnInit() {
    this.teamsService.getAllTeams();
  }

  openCreateTeamDialog() {
    const dialogRef = this.dialog.open(UpdateTeamDialog,
      {
        width: '300px',
      });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed: ', result);
      if (result) {
        this.teamsService.createTeam(result);
      }
    });
  }

  openDeleteTeamDialog(teamID: string) {
    const dialogRef = this.dialog.open(DeleteTeamDialog,
      {
        width: '300px',
      });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed: ', result);
      if (result) {
        this.teamsService.deleteTeam(teamID).subscribe(response => {
          this.notifs.showSuccess('Team deleted');
          this.teamsService.getAllTeams();
        }, error => {
          this.notifs.showError('Team not deleted');
        });
      }
    });
  }

  showTeam(teamID: string) {
    this.router.navigate(['/teams/' + teamID]);
  }

}

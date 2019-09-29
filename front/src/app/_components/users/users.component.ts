import {Component, OnInit} from '@angular/core';
import {NotificationsService, TeamsService, UserService, UsersService} from '../../_services';
import {AddEmployeeDialog} from '../team/add-employee.dialog';
import {MatDialog} from '@angular/material';
import {User} from '../../_models';
import {RemoveEmployeeDialog} from '../team/remove-employee.dialog';
import {ChangeRoleDialog} from './change-role.dialog';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {

  constructor(
    public usersService: UsersService,
    public userService: UserService,
    public dialog: MatDialog,
    private teamsService: TeamsService,
    private notifs: NotificationsService,
  ) {
  }

  ngOnInit() {
    this.usersService.getUsers();
  }

  openAddEmployeeDialog(user: User) {
    const dialogRef = this.dialog.open(AddEmployeeDialog,
      {
        data: {user},
        width: '300px',
      });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed: ', result);
      if (result) {
        this.teamsService.addUserToTeam({userID: user.userID, teamID: result.team.teamID})
          .subscribe(response => {
            this.notifs.showSuccess('User added to team');
            this.usersService.getUsers();
          }, error => {
            this.notifs.showError('User not added to team');
          });
      }
    });
  }

  openChangeRoleDialog(user: User) {
    const dialogRef = this.dialog.open(ChangeRoleDialog,
      {
        data: {user},
        width: '300px',
      });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed: ', result);
      if (result) {
        this.usersService.changeRole({userID: user.userID, type: result});
      }
    });
  }

}

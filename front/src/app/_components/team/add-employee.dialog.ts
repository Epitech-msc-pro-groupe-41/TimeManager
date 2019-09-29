import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Team, User} from '../../_models';
import {TeamsService, UsersService} from '../../_services';

@Component({
  selector: 'app-add-employee-dialog',
  templateUrl: 'add-employee.dialog.html',
})
export class AddEmployeeDialog {

  currentUser: User;
  currentTeam: Team;
  userMissing;

  constructor(
    public dialogRef: MatDialogRef<AddEmployeeDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public teamsServices: TeamsService,
    public usersService: UsersService) {
    if (data) {
      if (data.user) {
        this.currentUser = data.user;
        this.userMissing = false;
        this.teamsServices.getAllTeams();
      } else if (data.team) {
        this.currentTeam = data.team;
        this.userMissing = true;
        this.usersService.getUsers();
      } else {
        this.onNoClick();
      }
    } else {
      this.onNoClick();
    }
  }

  isFormValid() {
    return this.currentUser && this.currentTeam;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit() {
    if (this.isFormValid()) {
      this.dialogRef.close({user: this.currentUser, team: this.currentTeam});
    }
  }

}

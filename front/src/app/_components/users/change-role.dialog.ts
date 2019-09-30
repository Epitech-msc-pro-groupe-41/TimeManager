import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Team, User} from '../../_models';
import {TeamsService, UsersService} from '../../_services';

@Component({
  selector: 'app-change-role-dialog',
  templateUrl: 'change-role.dialog.html',
})
export class ChangeRoleDialog {

  user: User;

  type: string;

  constructor(
    public dialogRef: MatDialogRef<ChangeRoleDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    if (data.user) {
      this.user = data.user;
      this.type = this.user.type;
    } else {
      this.onNoClick();
    }
  }

  isFormValid() {
    return this.type && this.type !== this.user.type;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onSubmit() {
    if (this.isFormValid()) {
      this.dialogRef.close(this.type);
    }
  }

}

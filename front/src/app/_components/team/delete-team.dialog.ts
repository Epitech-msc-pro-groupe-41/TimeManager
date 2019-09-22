import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-delete-team-dialog',
  templateUrl: 'delete-team.dialog.html',
})
export class DeleteTeamDialog {

  constructor(
    public dialogRef: MatDialogRef<DeleteTeamDialog>) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}

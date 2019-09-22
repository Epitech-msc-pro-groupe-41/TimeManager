import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Team} from '../../_models';

@Component({
  selector: 'app-update-team-dialog',
  templateUrl: 'update-team.dialog.html',
})
export class UpdateTeamDialog {

  teamName: string;

  constructor(
    public dialogRef: MatDialogRef<UpdateTeamDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {
    if (data) {
      this.teamName = data.name;
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  isFormValid() {
    return this.teamName && (!this.data || this.teamName !== this.data.name);
  }

  onSubmit() {
    if (this.isFormValid()) {
      this.dialogRef.close(this.teamName);
    }
  }

}

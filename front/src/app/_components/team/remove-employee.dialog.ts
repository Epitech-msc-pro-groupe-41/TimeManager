import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Team, User} from '../../_models';

@Component({
  selector: 'app-remove-employee-dialog',
  templateUrl: 'remove-employee.dialog.html',
})
export class RemoveEmployeeDialog {

  constructor(
    public dialogRef: MatDialogRef<RemoveEmployeeDialog>,
    @Inject(MAT_DIALOG_DATA) public data: User) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}

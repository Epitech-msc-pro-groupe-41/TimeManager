import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {WorkingTime} from '../../_models';

@Component({
  selector: 'app-delete-workingtime-dialog',
  templateUrl: 'delete-workingtime.dialog.html',
})
export class DeleteWorkingtimeDialog {

  constructor(
    public dialogRef: MatDialogRef<DeleteWorkingtimeDialog>) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}

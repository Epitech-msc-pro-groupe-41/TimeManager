import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {WorkingTime} from '../../_models';

@Component({
  selector: 'app-update-workingtime-dialog',
  templateUrl: 'update-workingtime.dialog.html',
})
export class UpdateWorkingtimeDialog {

  wt: WorkingTime;

  constructor(
    public dialogRef: MatDialogRef<UpdateWorkingtimeDialog>,
    @Inject(MAT_DIALOG_DATA) public data: WorkingTime) {
    this.wt = new WorkingTime();
    if (data) {
      this.wt.start = data.start;
      this.wt.end = data.end;
      this.wt.workingTimeID = data.workingTimeID;
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  isFormValid() {
    return this.wt && this.wt.start && this.wt.end;
  }

}

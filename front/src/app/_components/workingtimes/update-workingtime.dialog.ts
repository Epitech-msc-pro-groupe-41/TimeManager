import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {WorkingTime} from '../../_models';

@Component({
  selector: 'app-update-workingtime-dialog',
  templateUrl: 'update-workingtime.dialog.html',
})
export class UpdateWorkingtimeDialog {

  wt: WorkingTime;

  _start: string;
  _end: string;

  constructor(
    public dialogRef: MatDialogRef<UpdateWorkingtimeDialog>,
    @Inject(MAT_DIALOG_DATA) public data: WorkingTime) {
    this.wt = new WorkingTime();
    if (data) {
      this.wt.start = new Date(data.start);
      this.wt.end = new Date(data.end);
      this.wt.workingTimeID = data.workingTimeID;

      this._start = this.wt.start.toISOString().slice(0, 16);
      this._end = this.wt.end.toISOString().slice(0, 16);
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  isFormValid() {
    return this.wt && this._start && this._end;
  }

  onSubmit() {
    if (this.isFormValid()) {
      const start = new Date(this._start);
      const end = new Date(this._end);
      if (start && end) {
        this.wt.start = start;
        this.wt.end = end;
        this.dialogRef.close(this.wt);
      }
    }
  }

}

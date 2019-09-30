import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {WorkingTime} from '../../_models';

@Component({
  selector: 'app-update-workingtime-dialog',
  templateUrl: 'update-workingtime.dialog.html',
})
export class UpdateWorkingtimeDialog {

  wt: WorkingTime;

  startDate: Date;
  startHours: number;
  startMinutes: number;
  startSeconds: number;

  endDate: Date;
  endHours: number;
  endMinutes: number;
  endSeconds: number;

  constructor(
    public dialogRef: MatDialogRef<UpdateWorkingtimeDialog>,
    @Inject(MAT_DIALOG_DATA) public data: WorkingTime) {
    this.wt = new WorkingTime();
    if (data) {
      this.wt.start = new Date(data.start);
      this.wt.end = new Date(data.end);
      this.wt.workingTimeID = data.workingTimeID;

      this.startDate = new Date(this.wt.start);
      this.startHours = this.wt.start.getHours();
      this.startMinutes = this.wt.start.getMinutes();
      this.startSeconds = this.wt.start.getSeconds();

      this.endDate = new Date(this.wt.end);
      this.endHours = this.wt.end.getHours();
      this.endMinutes = this.wt.end.getMinutes();
      this.endSeconds = this.wt.end.getSeconds();

    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  isFormValid() {
    return this.wt && this.startDate && this.endDate;
  }

  onSubmit() {
    if (this.isFormValid()) {
      const start = new Date(this.startDate);
      start.setHours(this.startHours);
      start.setMinutes(this.startMinutes);
      start.setSeconds(this.startSeconds);

      const end = new Date(this.endDate);
      end.setHours(this.endHours);
      end.setMinutes(this.endMinutes);
      end.setSeconds(this.endSeconds);

      if (start && end) {
        this.wt.start = start;
        this.wt.end = end;
        this.dialogRef.close(this.wt);
      }
    }
  }

}

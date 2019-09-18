import {Component, OnInit} from '@angular/core';
import {WorkingtimeService} from '../../_services';
import {MatDialog} from '@angular/material';
import {UpdateWorkingtimeDialog} from './update-workingtime.dialog';
import {WorkingTime} from '../../_models';
import {DeleteWorkingtimeDialog} from './delete-workingtime.dialog';

@Component({
  selector: 'app-workingtimes',
  templateUrl: './workingtimes.component.html',
  styleUrls: ['./workingtimes.component.scss']
})
export class WorkingtimesComponent implements OnInit {

  constructor(
    public wtService: WorkingtimeService,
    public dialog: MatDialog,
  ) {
  }

  ngOnInit() {
    this.wtService.getWorkingTimes();
  }

  openCreateWtDialog() {
    const dialogRef = this.dialog.open(UpdateWorkingtimeDialog,
      {
        width: '300px',
      });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed: ', result);
      if (result) {
        this.wtService.createWorkingTime(result);
      }
    });
  }

  openUpdateWtDialog(workingtime: WorkingTime) {
    const dialogRef = this.dialog.open(UpdateWorkingtimeDialog, {
      width: '300px',
      data: workingtime
    });

    dialogRef.afterClosed().subscribe((result: WorkingTime) => {
      console.log('The dialog was closed: ', result);
      if (result) {
        this.wtService.updateWorkingTime(workingtime.workingTimeID, result.start, result.end);
      }
    });
  }

  openDeleteDialog(workingTime: WorkingTime) {
    const dialogRef = this.dialog.open(DeleteWorkingtimeDialog, {
      width: '300px',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.wtService.deleteWorkingTime(workingTime.workingTimeID);
      }
    });
  }

}

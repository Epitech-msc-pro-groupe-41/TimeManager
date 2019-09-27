import { NgModule } from '@angular/core';

import {MatButtonModule} from '@angular/material/button';
import {
  MatCardModule,
  MatDialogModule,
  MatDividerModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule, MatListModule,
  MatMenuModule,
  MatProgressSpinnerModule, MatSelectModule,
  MatSidenavModule, MatSnackBarModule
} from '@angular/material';

@NgModule({
  imports: [
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatSidenavModule,
    MatIconModule,
    MatMenuModule,
    MatListModule,
    MatDividerModule,
    MatDialogModule,
    MatCardModule,
    MatSelectModule,
    MatSnackBarModule,
  ],
  exports: [
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatProgressSpinnerModule,
    MatSidenavModule,
    MatIconModule,
    MatMenuModule,
    MatListModule,
    MatDividerModule,
    MatDialogModule,
    MatCardModule,
    MatSelectModule,
    MatSnackBarModule,
  ]
})
export class MaterialModule {}

import { NgModule } from '@angular/core';

import {MatButtonModule} from '@angular/material/button';
import {
  MatFormFieldModule,
  MatIconModule,
  MatInputModule, MatListModule,
  MatMenuModule,
  MatProgressSpinnerModule,
  MatSidenavModule
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
  ]
})
export class MaterialModule {}

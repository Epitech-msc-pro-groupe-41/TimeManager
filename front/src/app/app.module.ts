import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { MaterialModule } from './material.module';

import { FlexLayoutModule } from '@angular/flex-layout';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {
  ClockmanagerComponent,
  DashboardComponent,
  LoginComponent,
  NavbarComponent, ProfilComponent,
  RegisterComponent,
  TeamsComponent, UpdateWorkingtimeDialog,
  WorkingtimesComponent
} from './_components';
import {DeleteWorkingtimeDialog} from './_components/workingtimes/delete-workingtime.dialog';
import {ChartModule} from 'angular-highcharts';


@NgModule({
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    FormsModule,
    ChartModule,
  ],
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    NavbarComponent,
    WorkingtimesComponent,
    TeamsComponent,
    ClockmanagerComponent,
    ProfilComponent,
    UpdateWorkingtimeDialog,
    DeleteWorkingtimeDialog,
  ],
  entryComponents: [
    UpdateWorkingtimeDialog,
    DeleteWorkingtimeDialog,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

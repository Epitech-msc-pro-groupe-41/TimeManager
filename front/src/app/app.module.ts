import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { MaterialModule } from './material.module';

import { FlexLayoutModule } from '@angular/flex-layout';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {
  DashboardComponent,
  LoginComponent,
  NavbarComponent, ProfilComponent,
  RegisterComponent, TeamComponent,
  TeamsComponent, UpdateWorkingtimeDialog, UsersComponent,
  WorkingtimesComponent
} from './_components';
import {DeleteWorkingtimeDialog} from './_components/workingtimes/delete-workingtime.dialog';

import {ChartsModule, MDBBootstrapModule} from 'angular-bootstrap-md';
import {JwtInterceptor} from './_helpers/jwt.interceptor';
import {ErrorInterceptor} from './_helpers';
import {UpdateTeamDialog} from './_components/team/update-team.dialog';
import {DeleteTeamDialog} from './_components/team/delete-team.dialog';
import {RemoveEmployeeDialog} from './_components/team/remove-employee.dialog';
import {AddEmployeeDialog} from './_components/team/add-employee.dialog';
import {ChangeRoleDialog} from './_components/users/change-role.dialog';

@NgModule({
  imports: [
    BrowserModule,
    MDBBootstrapModule.forRoot(),
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    FormsModule,
    ChartsModule,
  ],
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    NavbarComponent,
    WorkingtimesComponent,
    TeamsComponent,
    ProfilComponent,
    UpdateWorkingtimeDialog,
    DeleteWorkingtimeDialog,
    TeamComponent,
    UpdateTeamDialog,
    DeleteTeamDialog,
    RemoveEmployeeDialog,
    UsersComponent,
    AddEmployeeDialog,
    ChangeRoleDialog,
  ],
  entryComponents: [
    UpdateWorkingtimeDialog,
    DeleteWorkingtimeDialog,
    UpdateTeamDialog,
    DeleteTeamDialog,
    RemoveEmployeeDialog,
    AddEmployeeDialog,
    ChangeRoleDialog,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

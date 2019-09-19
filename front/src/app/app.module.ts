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
  RegisterComponent,
  TeamsComponent, UpdateWorkingtimeDialog,
  WorkingtimesComponent
} from './_components';
import {DeleteWorkingtimeDialog} from './_components/workingtimes/delete-workingtime.dialog';

import {ChartsModule, MDBBootstrapModule} from 'angular-bootstrap-md';
import {JwtInterceptor} from './_helpers/jwt.interceptor';
import {ErrorInterceptor} from './_helpers';

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
  ],
  entryComponents: [
    UpdateWorkingtimeDialog,
    DeleteWorkingtimeDialog,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

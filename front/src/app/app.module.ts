import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { MaterialModule } from './material.module';

import { FlexLayoutModule } from '@angular/flex-layout';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {DashboardComponent, LoginComponent, NavbarComponent, RegisterComponent} from './_components';
import { WorkingtimesComponent } from './_components/workingtimes/workingtimes.component';
import { TeamsComponent } from './_components/teams/teams.component';
import { ClockmanagerComponent } from './_components/clockmanager/clockmanager.component';
import { ProfilComponent } from './_components/profil/profil.component';


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
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

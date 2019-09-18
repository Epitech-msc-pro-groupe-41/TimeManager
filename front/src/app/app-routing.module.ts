import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthGuard} from './_helpers';
import {
  ClockmanagerComponent,
  DashboardComponent,
  LoginComponent, ProfilComponent,
  RegisterComponent,
  TeamsComponent,
  WorkingtimesComponent
} from './_components';


const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},

  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard]},
  { path: 'clockmanager', component: ClockmanagerComponent, canActivate: [AuthGuard]},
  { path: 'workingtimes', component: WorkingtimesComponent, canActivate: [AuthGuard]},
  { path: 'teams', component: TeamsComponent, canActivate: [AuthGuard]},
  { path: 'profil', component: ProfilComponent, canActivate: [AuthGuard]},

  // otherwise redirect to home
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

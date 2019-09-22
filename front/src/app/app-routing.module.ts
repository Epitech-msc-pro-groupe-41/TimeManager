import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AuthGuard} from './_helpers';
import {
  DashboardComponent,
  LoginComponent, ProfilComponent,
  RegisterComponent, TeamComponent,
  TeamsComponent,
  WorkingtimesComponent
} from './_components';


const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},

  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard]},
  { path: 'profil', component: ProfilComponent, canActivate: [AuthGuard]},
  { path: 'workingtimes', component: WorkingtimesComponent, canActivate: [AuthGuard]},
  { path: 'teams', component: TeamsComponent, canActivate: [AuthGuard]},
  { path: 'teams/:teamID', component: TeamComponent, canActivate: [AuthGuard]},

  // otherwise redirect to home
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

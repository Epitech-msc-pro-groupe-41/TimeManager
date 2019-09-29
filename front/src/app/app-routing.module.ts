import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './_helpers';
import {
  DashboardComponent,
  LoginComponent, ProfilComponent,
  RegisterComponent, TeamComponent,
  TeamsComponent, UsersComponent,
  WorkingtimesComponent
} from './_components';


const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
    { path: 'profil', component: ProfilComponent, canActivate: [AuthGuard] },
    {
        path: 'workingtimes', component: WorkingtimesComponent, canActivate: [AuthGuard], data: {
            allowedRoles: ['Admin', 'Manager', 'Employee']
        }
    },
    {
        path: 'teams', component: TeamsComponent, canActivate: [AuthGuard], data: {
            allowedRoles: ['Admin', 'Manager']
        }
    },
    {
        path: 'teams/:teamID', component: TeamComponent, canActivate: [AuthGuard], data: {
            allowedRoles: ['Admin', 'Manager']
        }
    },
  {
    path: 'users', component: UsersComponent, canActivate: [AuthGuard], data: {
      allowedRoles: ['Admin', 'Manager']
    }
  },

    // otherwise redirect to home
    { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }

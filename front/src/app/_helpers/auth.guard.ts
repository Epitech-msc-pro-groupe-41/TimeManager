import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../_services';
import { NotificationsService } from '../_services';
@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(
        private router: Router,
        private userService: UserService,
        private notifs: NotificationsService
    ) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const currentUser = this.userService.currentUserValue;
        if (currentUser) {
            // authorised so return true
            const expectedRole = route.data.allowedRoles;
            if (expectedRole != null) {
                if (expectedRole.includes(currentUser.type)) {
                    return true;
                } else {
                    this.notifs.showError('You don\'t have the rights to access this page');
                    this.router.navigate(['/dashboard'], { queryParams: { returnUrl: state.url } });
                    return false
                }
            } else
                return true
        } else
            // not logged in so redirect to login page with the return url
            this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
        return false;
    }
}

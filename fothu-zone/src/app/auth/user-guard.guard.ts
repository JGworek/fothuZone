import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { UserService } from "../service/user.service";

@Injectable({
  providedIn: 'root'
})
export class UserGuardGuard implements CanActivate {
  constructor(
    private userService: UserService,
    private router: Router
  ) { }
  
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
    ) {
    if (this.userService.currentUser.id == -1) {
      return this.router.parseUrl('/login');
    }
    return true;
  }
}

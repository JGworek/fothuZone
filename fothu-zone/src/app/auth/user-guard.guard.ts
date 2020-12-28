import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { ProfileService } from "../service/profile.service";

@Injectable({
  providedIn: 'root'
})
export class UserGuardGuard implements CanActivate {
  constructor(
    private profileService: ProfileService,
    private router: Router
  ) { }
  
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
    ) {
    if (this.profileService.currentUser.id == -1) {
      return this.router.parseUrl('/login');
    }
    return true;
  }
}

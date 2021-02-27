import { Injectable } from "@angular/core";
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from "@angular/router";
import { UserService } from "../service/user.service";

@Injectable({
	providedIn: "root",
})
export class UserGuard implements CanActivate {
	constructor(private userService: UserService, private router: Router) {}

	canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
		if (!this.userService.currentUser.id) {
			return this.router.parseUrl("/login");
		}
		return true;
	}
}

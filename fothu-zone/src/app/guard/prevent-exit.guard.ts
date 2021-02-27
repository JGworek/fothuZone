import { Injectable } from "@angular/core";
import { CanDeactivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";

@Injectable({
	providedIn: "root",
})
export class PreventExitGuard implements CanDeactivate<unknown> {
	canDeactivate(component: unknown, currentRoute: ActivatedRouteSnapshot, currentState: RouterStateSnapshot, nextState?: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
		//     if(!dungeonService.leavable) {
		// 		let leaveBoolean = confirm("Leave your pets in the dungeon and coward out?");
		// 	if (leaveBoolean) {
		// 		this.userService.killAllPets();
		// 	}
		//   }
		return true;
	}
}

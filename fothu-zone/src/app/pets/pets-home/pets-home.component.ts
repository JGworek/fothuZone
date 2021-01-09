import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { UserService } from "src/app/service/user.service";

@Component({
	selector: "app-pets-home",
	templateUrl: "./pets-home.component.html",
	styleUrls: ["./pets-home.component.css"],
})
export class PetsHomeComponent implements OnInit {
	constructor(public userService: UserService, private router: Router) {}

	ngOnInit(): void {
		if (this.userService.currentUser.id != -1) {
			this.userService.updateUser();
		}
		this.router.navigate(["", { outlets: { petbar: "available", battles: "available", levelUps: "possible" } }]);
	}
}

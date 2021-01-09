import { Component, OnInit } from "@angular/core";
import { UserService } from "../service/user.service";

@Component({
	selector: "app-navbar",
	templateUrl: "./navbar.component.html",
	styleUrls: ["./navbar.component.css"],
})
export class NavbarComponent implements OnInit {
	constructor(public userService: UserService) {}

	ngOnInit() {
		//WHILE DEVELOPING IF YOU LEAVE THIS IN I SWEAR
		this.userService.loggingInUser.username = "Fothu";
		this.userService.loggingInUser.userPassword = "password123";
		this.userService.logIn();
	}
}

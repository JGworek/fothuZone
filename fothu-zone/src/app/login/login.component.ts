import { Component, OnInit } from "@angular/core";
import { UserService } from "../service/user.service";
import { Router } from "@angular/router";
import { SupportedColor } from "../models/SupportedColor";
import { environment } from "src/environments/environment";

@Component({
	selector: "app-login",
	templateUrl: "./login.component.html",
	styleUrls: ["./login.component.css"],
})
export class LoginComponent implements OnInit {
	constructor(public userService: UserService, private router: Router) {}

	supportedColors: Array<SupportedColor> = [];

	async getSupportedColors() {
		let colorJSON = await fetch(`${environment.fothuZoneEC2Link}/supportedColors/all`);
		this.supportedColors = await colorJSON.json();
	}

	ngOnInit(): void {
		this.getSupportedColors();
	}
}

import { Component, OnInit } from "@angular/core";
import { UserService } from "../service/user.service";
import { SupportedColor } from "../models/SupportedColor";
import { environment } from "src/environments/environment";

@Component({
	selector: "app-sign-up",
	templateUrl: "./sign-up.component.html",
	styleUrls: ["./sign-up.component.css"],
})
export class SignUpComponent implements OnInit {
	constructor(public userService: UserService) {}

	supportedColors: Array<SupportedColor> = [];

	async getSupportedColors() {
		let colorJSON = await fetch(`${environment.fothuZoneEC2Link}/supportedColors/all`);
		this.supportedColors = await colorJSON.json();
	}
	ngOnInit(): void {
		this.getSupportedColors();
	}
}

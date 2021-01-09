import { Component, OnInit } from "@angular/core";
import { UserService } from "../service/user.service";
import { Router } from "@angular/router";
import { UserDTO } from "../models/UserDTO";
import { environment } from "../../environments/environment";

@Component({
	selector: "app-login",
	templateUrl: "./login.component.html",
	styleUrls: ["./login.component.css"],
})
export class LoginComponent implements OnInit {
	constructor(public userService: UserService, private router: Router) {}

	ngOnInit(): void {}
}

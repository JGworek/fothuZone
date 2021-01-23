import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { environment } from "src/environments/environment";
import { User } from "../models/User";
import { UserDTO } from "../models/UserDTO";

@Injectable({
	providedIn: "root",
})
export class UserService {
	constructor(private router: Router) {}
	newUserCreation: boolean = false;
	incorrectLogin: boolean = false;
	samePassword: string;

	newUser: UserDTO = {
		id: 0,
		username: "",
		favoriteColor: "",
		userPassword: "",
		secretPassword: "",
		adminStatus: false,
	};

	currentUser: User = {
		id: -1,
		username: "",
		favoriteColor: "",
		adminStatus: false,
		pets: [],
	};

	loggingInUser: UserDTO = {
		id: 0,
		username: "",
		favoriteColor: "",
		userPassword: "",
		secretPassword: "",
		adminStatus: false,
	};

	getHealthBarColor(percentNumber) {
		if (percentNumber >= 51) {
			return "progress-bar bg-success";
		} else if (percentNumber >= 26 && percentNumber < 51) {
			return "progress-bar bg-warning";
		} else if (percentNumber < 26) {
			return "progress-bar bg-danger";
		}
	}

	async logIn() {
		let returnedPromise = await fetch(`${environment.fothuZoneEC2Link}/users/login`, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(this.loggingInUser) });
		let returnedUser = await returnedPromise.json();
		if (returnedPromise.status.toString()[0] == "1" || returnedPromise.status.toString()[0] == "4" || returnedPromise.status.toString()[0] == "5") {
			this.incorrectLogin = true;
		} else {
			this.currentUser = returnedUser;
			this.incorrectLogin = false;
			this.router.navigate(["home"]);
		}
	}

	logout() {
		window.location.href = environment.homeURL;
	}

	//Why?
	async updateUser() {
		let returnedPromise = await fetch(`${environment.fothuZoneEC2Link}/users/username/${this.currentUser.username}`);
		let returnedUser = await returnedPromise.json();
		this.currentUser = returnedUser;
	}
}

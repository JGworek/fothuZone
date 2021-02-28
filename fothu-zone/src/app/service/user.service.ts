import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { RxStompService } from "@stomp/ng2-stompjs";
import { environment } from "src/environments/environment";
import { User } from "../models/User";
import { UserDTO } from "../models/UserDTO";
import { ToastService } from "./toast.service";
import { StatusCodeService } from "./status-code.service";

@Injectable({
	providedIn: "root",
})
export class UserService {
	constructor(private router: Router, private RxStompService: RxStompService, private toastService: ToastService, private statusCodeService: StatusCodeService) {}
	newUserCreation: boolean = false;
	samePassword: string;
	userSubscription: any;
	errorSubscription: any;

	newUser: UserDTO = {
		id: 0,
		username: "",
		favoriteColor: "",
		userPassword: "",
		secretPassword: "",
		adminStatus: false,
	};

	currentUser: User = {
		id: 0,
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
		let userJSON = await fetch(`${environment.fothuZoneEC2Link}/users/login`, { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(this.loggingInUser) });
		if (this.statusCodeService.checkSuccessStatus(userJSON)) {
			let returnedUser = await userJSON.json();
			this.currentUser = returnedUser;
			this.keepUserUpdated();
			this.trackErrorMessageResponses();
			this.router.navigate(["home"]);
		} else {
			this.toastService.badRequestToast("Incorrect Username or Password");
		}
	}

	logout() {
		this.userSubscription.unsubscribe();
		window.location.href = environment.homeURL;
	}

	keepUserUpdated() {
		this.userSubscription = this.RxStompService.watch(`/userSubscription/${this.currentUser.id}`, { id: this.currentUser.id as any }).subscribe((userMessage) => {
			this.currentUser = userMessage as any;
		});
	}

	trackErrorMessageResponses() {
		this.errorSubscription = this.RxStompService.watch(`/errorMessageSubscription/${this.currentUser.id}`, { id: this.currentUser.id as any }).subscribe((errorMessage) => {
			this.toastService.badRequestToast(errorMessage);
		});
	}

	//Why?
	async updateUser() {
		let returnedPromise = await fetch(`${environment.fothuZoneEC2Link}/users/username/${this.currentUser.username}`);
		let returnedUser = await returnedPromise.json();
		this.currentUser = returnedUser;
	}
}

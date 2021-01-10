import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { User } from "src/app/models/User";
import { environment } from "src/environments/environment";
import { UserService } from "../../service/user.service";

@Component({
	selector: "app-create-challenge",
	templateUrl: "./create-challenge.component.html",
	styleUrls: ["./create-challenge.component.css"],
})
export class CreateChallengeComponent implements OnInit {
	constructor(public userService: UserService, public router: Router) {}

	userList: Array<User> = [];
	challengeUserId: number = 0;
	unableToCreateChallenge: boolean = false;
	filterString: string = "";
	filterArray: Array<User> = [];

	async getAllUsersBesideLoggedInUser() {
		let jsonUserList = await fetch(`${environment.fothuZoneEC2Link}/users/availableChallengeUsers/userId/${this.userService.currentUser.id}`);
		this.userList = await jsonUserList.json();
		this.filterArray = this.userList;
	}

	async createNewChallengeRequest() {
		let jsonChallengeRequest = await fetch(`${environment.fothuZoneEC2Link}/challengeRequest/new/challengerId/${this.userService.currentUser.id}/opponentId/${this.challengeUserId}`, { method: "POST" });
		let returnedChallengeRequest = await jsonChallengeRequest.json();
		if (jsonChallengeRequest.status.toString()[0] == "1" || jsonChallengeRequest.status.toString()[0] == "4" || jsonChallengeRequest.status.toString()[0] == "5") {
			this.unableToCreateChallenge = true;
		} else {
			//CREATE TOAST MESSAGE TO SAY CHALLENGE REQUEST
			this.router.navigate(["/FothuPets"]);
		}
	}

	filterUserList() {
		this.filterArray = this.userList;
		this.filterArray = this.filterArray.filter((user) => {
			return user.username.toLowerCase().includes(this.filterString.toLowerCase());
		});
	}

	getPetsForChallenger(id: number) {
		for (let i = 0; i < this.userList.length; i++) {
			if (this.userList[i].id == id) {
				return this.userList[i].pets;
			}
		}
	}

	ngOnInit(): void {
		this.getAllUsersBesideLoggedInUser();
	}
}

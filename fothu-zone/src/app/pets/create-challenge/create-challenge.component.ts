import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Pet } from "src/app/models/Pet";
import { User } from "src/app/models/User";
import { UserDTO } from "src/app/models/UserDTO";
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
	challengerPets: Array<Pet> = [];

	async getAllUsersBesideLoggedInUser() {
		let userListJSON = await fetch(`${environment.fothuZoneEC2Link}/users/availableChallengeUserDTOs/userId/${this.userService.currentUser.id}`);
		this.userList = await userListJSON.json();
		this.filterArray = this.userList;
	}

	async createNewChallengeRequest() {
		let challengeRequestJSON = await fetch(`${environment.fothuZoneEC2Link}/challengeRequest/new/challengerId/${this.userService.currentUser.id}/opponentId/${this.challengeUserId}`, { method: "POST" });
		let returnedChallengeRequest = await challengeRequestJSON.json();
		if (challengeRequestJSON.status.toString()[0] == "1" || challengeRequestJSON.status.toString()[0] == "4" || challengeRequestJSON.status.toString()[0] == "5") {
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

	async getPetsForChallenger(id: number) {
		let challengerJSON = await fetch(`${environment.fothuZoneEC2Link}/users/id/${id}`);
		let challenger = await challengerJSON.json();
		this.challengerPets = challenger.pets;
	}

	ngOnInit(): void {
		this.getAllUsersBesideLoggedInUser();
	}
}

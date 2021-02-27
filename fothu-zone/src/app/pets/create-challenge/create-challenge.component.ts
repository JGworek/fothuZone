import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { Pet } from "src/app/models/Pet";
import { User } from "src/app/models/User";
import { UserDTO } from "src/app/models/UserDTO";
import { StatusCodeService } from "src/app/service/status-code.service";
import { ToastService } from "src/app/service/toast.service";
import { environment } from "src/environments/environment";
import { UserService } from "../../service/user.service";

@Component({
	selector: "app-create-challenge",
	templateUrl: "./create-challenge.component.html",
	styleUrls: ["./create-challenge.component.css"],
})
export class CreateChallengeComponent implements OnInit {
	constructor(public userService: UserService, public router: Router, private statusCodeService: StatusCodeService, private toastService: ToastService) {}

	userList: Array<UserDTO> = [];
	challengeUserId: number = 0;
	filterString: string = "";
	filterArray: Array<UserDTO> = [];
	challengerPets: Array<Pet> = [];
	challengerUser: User;

	async getAllUsersBesideLoggedInUser() {
		let userListJSON = await fetch(`${environment.fothuZoneEC2Link}/users/availableChallengeUserDTOs/userId/${this.userService.currentUser.id}`);
		this.userList = await userListJSON.json();
		this.filterArray = this.userList;
	}

	async createNewChallengeRequest() {
		let challengeRequestJSON = await fetch(`${environment.fothuZoneEC2Link}/challengeRequests/new/challengerId/${this.userService.currentUser.id}/opponentId/${this.challengeUserId}`, { method: "POST" });
		if (this.statusCodeService.checkSuccessStatus(challengeRequestJSON)) {
			this.toastService.successfulRequestToast("Challenge Successfully Created!");
			this.router.navigate(["/FothuPets"]);
		} else {
			this.toastService.unableToSendRequestToast("Unable To Create Challenge. Try Again!");
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
		this.challengerUser = challenger;
		this.challengerPets = challenger.pets;
	}

	ngOnInit(): void {
		this.getAllUsersBesideLoggedInUser();
	}
}

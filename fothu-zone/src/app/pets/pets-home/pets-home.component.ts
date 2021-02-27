import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { RxStompService } from "@stomp/ng2-stompjs";
import { BattleDTO } from "src/app/models/BattleDTO";
import { ChallengeRequest } from "src/app/models/ChallengeRequest";
import { UserService } from "src/app/service/user.service";
import { environment } from "src/environments/environment";

@Component({
	selector: "app-pets-home",
	templateUrl: "./pets-home.component.html",
	styleUrls: ["./pets-home.component.css"],
})
export class PetsHomeComponent implements OnInit {
	constructor(public userService: UserService, private RxStompService: RxStompService) {}

	challengeRequests: Array<ChallengeRequest>;
	currentBattles: Array<BattleDTO>;
	challengeSubscription: any;
	battleSubscription: any;

	async getChallengeRequests() {
		let challengeRequestJSON = await fetch(`${environment.fothuZoneEC2Link}/challengeRequests/all/pending/userId/${this.userService.currentUser.id}`, { method: "GET" });
		this.challengeRequests = await challengeRequestJSON.json();
	}

	async getCurrentBattles() {
		let currentBattlesJSON = await fetch(`${environment.fothuZoneEC2Link}/battles/all/pvp/userId/${this.userService.currentUser.id}`, { method: "GET" });
		this.currentBattles = await currentBattlesJSON.json();
	}

	keepChallengeRequestsUpdated() {
		this.challengeSubscription = this.RxStompService.watch(`/challengeSubscription/${this.userService.currentUser.id}`).subscribe((challengeMessage) => {
			this.challengeRequests = challengeMessage as any;
		});
	}

	keepCurrentBattlesUpdated() {
		this.battleSubscription = this.RxStompService.watch(`/currentBattlesSubscription/${this.userService.currentUser.id}`).subscribe((battleMessage) => {
			this.challengeRequests = battleMessage as any;
		});
	}

	ngOnInit(): void {
		this.getChallengeRequests();
		this.getCurrentBattles();
		this.keepChallengeRequestsUpdated();
		this.keepCurrentBattlesUpdated();
	}

	ngOnDestroy(): void {
		this.challengeSubscription.unsubscribe();
		this.battleSubscription.unsubscribe();
	}
}

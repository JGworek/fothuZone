import { Message } from "@angular/compiler/src/i18n/i18n_ast";
import { Component, OnInit } from "@angular/core";
import { RxStompService } from "@stomp/ng2-stompjs";
import { Battle } from "src/app/models/Battle";
import { ChallengeRequest } from "src/app/models/ChallengeRequest";
import { ChallengeRequestDTO } from "src/app/models/ChallengeRequestDTO";
import { BattleService } from "src/app/service/battle.service";
import { UserService } from "src/app/service/user.service";
import { environment } from "src/environments/environment";

@Component({
	selector: "app-pets-home",
	templateUrl: "./pets-home.component.html",
	styleUrls: ["./pets-home.component.css"],
})
export class PetsHomeComponent implements OnInit {
	constructor(public userService: UserService, private RxStompService: RxStompService, public battleService: BattleService) {}

	challengeRequests: Array<ChallengeRequest> = [];
	currentBattles: Array<Battle> = [];

	challengeSubscription: any;
	battleSubscription: any;

	async getChallengeRequests() {
		let challengeRequestJSON = await fetch(`${environment.fothuZoneEC2Link}/challengeRequests/all/pending/userId/${this.userService.currentUser.id}`, { method: "GET" });
		this.challengeRequests = await challengeRequestJSON.json();
	}

	async getCurrentBattles() {
		let currentBattlesJSON = await fetch(`${environment.fothuZoneEC2Link}/battles/all/pvp/current/userId/${this.userService.currentUser.id}`, { method: "GET" });
		this.currentBattles = await currentBattlesJSON.json();
	}

	keepChallengeRequestsUpdated() {
		this.challengeSubscription = this.RxStompService.watch(`/challengeSubscription/${this.userService.currentUser.id}`).subscribe((challengeMessage) => {
			if (challengeMessage) {
				let convertedChallengeRequests = JSON.parse(challengeMessage.body);
				this.challengeRequests = convertedChallengeRequests;
			}
		});
	}

	keepCurrentBattlesUpdated() {
		this.battleSubscription = this.RxStompService.watch(`/currentBattlesSubscription/${this.userService.currentUser.id}`).subscribe((battleMessage) => {
			if (battleMessage) {
				let convertedCurrentBattles = JSON.parse(battleMessage.body);
				this.currentBattles = convertedCurrentBattles;
			}
		});
	}

	ngOnInit(): void {
		this.keepChallengeRequestsUpdated();
		this.keepCurrentBattlesUpdated();
		this.getChallengeRequests();
		this.getCurrentBattles();
	}

	ngOnDestroy(): void {
		this.challengeSubscription.unsubscribe();
		this.battleSubscription.unsubscribe();
	}
}

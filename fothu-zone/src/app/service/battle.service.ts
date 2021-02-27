import { Battle } from "../models/Battle";
import { ChallengeRequestDTO } from "../models/ChallengeRequestDTO";

import { Injectable } from "@angular/core";
import { UserService } from "./user.service";
import { RxStompService } from "@stomp/ng2-stompjs";
import { environment } from "src/environments/environment";
import { Pet } from "../models/Pet";
import { StatusCodeService } from "./status-code.service";
import { ToastService } from "./toast.service";
declare var SockJS: any;
declare var Stomp: any;

@Injectable({
	providedIn: "root",
})
export class BattleService {
	constructor(public userService: UserService, private RXStompService: RxStompService, private statusCodeService: StatusCodeService, private toastService: ToastService) {}
	public stompClient;

	currentChallengeRequest: ChallengeRequestDTO = {
		id: 0,
		acceptedStatus: false,
		rejectedStatus: false,
		attackingUserId: 0,
		defendingUserId: 0,
		resultingBattleId: 0,
		createdOn: "",
	};

	currentBattle: Battle = {
		id: 0,
		battleType: "",
		attackingUser: {
			id: 0,
			username: "",
			favoriteColor: "",
			adminStatus: false,
			pets: [],
		},
		defendingUser: {
			id: 0,
			username: "",
			favoriteColor: "",
			adminStatus: false,
			pets: [],
		},
		attackingPet: {
			id: 0,
			name: "",
			image: { id: 0, imageURL: "" },
			hunger: 0,
			type: "",
			agility: 0,
			strength: 0,
			intelligence: 0,
			petLevel: 0,
			currentXP: 0,
			currentHealth: 0,
			maxHealth: 0,
			availableLevelUps: 0,
			owner: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
			},
		},
		defendingPet: {
			id: 0,
			name: "",
			image: {
				id: 0,
				imageURL: "",
			},
			hunger: 0,
			type: "",
			agility: 0,
			strength: 0,
			intelligence: 0,
			petLevel: 0,
			currentXP: 0,
			currentHealth: 0,
			maxHealth: 0,
			availableLevelUps: 0,
			owner: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
			},
		},
		winningPet: {
			id: 0,
			name: "",
			image: { id: 0, imageURL: "" },
			hunger: 0,
			type: "",
			agility: 0,
			strength: 0,
			intelligence: 0,
			petLevel: 0,
			currentXP: 0,
			currentHealth: 0,
			maxHealth: 0,
			availableLevelUps: 0,
			owner: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
			},
		},
		losingPet: {
			id: 0,
			name: "",
			image: { id: 0, imageURL: "" },
			hunger: 0,
			type: "",
			agility: 0,
			strength: 0,
			intelligence: 0,
			petLevel: 0,
			currentXP: 0,
			currentHealth: 0,
			maxHealth: 0,
			availableLevelUps: 0,
			owner: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
			},
		},
		attackingPetCurrentHealth: 0,
		defendingPetCurrentHealth: 0,
		attackingPetCurrentAttackModifier: 0,
		defendingPetCurrentAttackModifier: 0,
		attackingPetCurrentArmorModifier: 0,
		defendingPetCurrentArmorModifier: 0,
		attackingPetCurrentAccuracyModifier: 0,
		defendingPetCurrentAccuracyModifier: 0,
		attackingPetBaseAttackPower: 0,
		defendingPetBaseAttackPower: 0,
		attackingPetBaseArmor: 0,
		defendingPetBaseArmor: 0,
		attackingPetBaseSpeed: 0,
		defendingPetBaseSpeed: 0,
		currentTurnCount: 0,
		currentTurnPet: {
			id: 0,
			name: "",
			image: { id: 0, imageURL: "" },
			hunger: 0,
			type: "",
			agility: 0,
			strength: 0,
			intelligence: 0,
			petLevel: 0,
			currentXP: 0,
			currentHealth: 0,
			maxHealth: 0,
			availableLevelUps: 0,
			owner: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
			},
		},
		battleFinished: false,
		createdOn: ",",
		battleLogs: [],
	};

	async getPVEBattle(battleId: number) {
		let currentBattleJSON = await fetch(`${environment.fothuZoneEC2Link}/battles/id/${battleId}`);
		this.currentBattle = await currentBattleJSON.json();
	}

	async getPVPBattle(battleId: number) {
		let currentBattleJSON = await fetch(`${environment.fothuZoneEC2Link}/battles/id/${battleId}`);
		this.currentBattle = await currentBattleJSON.json();
	}

	async acceptChallengeRequest(challengeRequestId: number) {
		let acceptedChallengeRequestJSON = await fetch(`${environment.fothuZoneEC2Link}/challengeRequests/accept/challengeRequestId/${challengeRequestId}`);
		if (this.statusCodeService.checkSuccessStatus(acceptedChallengeRequestJSON)) {
			let acceptedChallenge = await acceptedChallengeRequestJSON.json();
			this.toastService.successfulRequestToast("Challenge Accepted!");
		} else {
			this.toastService.unableToSendRequestToast("Unable to accept challenge, please try again!");
		}
	}

	async rejectChallengeRequest(challengeRequestId: number) {
		let rejectedChallengeRequestJSON = await fetch(`${environment.fothuZoneEC2Link}/challengeRequests/reject/challengeRequestId/${challengeRequestId}`);
		if (this.statusCodeService.checkSuccessStatus(rejectedChallengeRequestJSON)) {
			let rejectedChallenge = await rejectedChallengeRequestJSON.json();
			this.toastService.successfulRequestToast("Challenge Rejected!");
		} else {
			this.toastService.unableToSendRequestToast("Unable to reject challenge, please try again!");
		}
	}

	/* sends */
	setAttackingPet(battleId: number, battleType: string, attackingPetId: number) {
		this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/${battleType}/battleId/${battleId}/setAttackingPetId/${attackingPetId}` });
	}

	setDefendingPet(battleId: number, battleType: string, defendingPetId: number) {
		this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/${battleType}/battleId/${battleId}/setDefendingPetId/${defendingPetId}` });
	}

	attack(battleId: number, actingPetId: number) {
		if (this.currentBattle.attackingPet.id == actingPetId) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingPet/${actingPetId}/attack` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingPet/${actingPetId}/attack` });
		}
	}

	defend(battleId: number, actingPetId: number) {
		if (this.currentBattle.attackingPet.id == actingPetId) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingPet/${actingPetId}/defend` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingPet/${actingPetId}/defend` });
		}
	}

	aim(battleId: number, actingPetId: number) {
		if (this.currentBattle.attackingPet.id == actingPetId) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingPet/${actingPetId}/aim` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingPet/${actingPetId}/aim` });
		}
	}

	sharpen(battleId: number, actingPetId: number) {
		if (this.currentBattle.attackingPet.id == actingPetId) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingPet/${actingPetId}/sharpen` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingPet/${actingPetId}/sharpen` });
		}
	}

	async prematureEndPveBattle(userId: number) {
		let battleJSON = await fetch(`${environment.fothuZoneEC2Link}/battles/pve/prematureEnd/battleId/${this.currentBattle.id}`, { method: "PUT" });
		let battle = await battleJSON.json();
	}
}

//connect to FothuPetsWebsocket on login
//get all current pvp battles from SB
//get all current battle requests from SB
//subscribe to the requests and battles ws endpoints to dynamically update new requests

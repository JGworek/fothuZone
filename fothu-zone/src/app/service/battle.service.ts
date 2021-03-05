import { Battle } from "../models/Battle";
import { ChallengeRequestDTO } from "../models/ChallengeRequestDTO";

import { Injectable } from "@angular/core";
import { UserService } from "./user.service";
import { RxStompService } from "@stomp/ng2-stompjs";
import { environment } from "src/environments/environment";
import { StatusCodeService } from "./status-code.service";
import { ToastService } from "./toast.service";

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
		let acceptedChallengeRequestJSON = await fetch(`${environment.fothuZoneEC2Link}/challengeRequests/accept/challengeRequestId/${challengeRequestId}`, { method: "PUT" });
		if (this.statusCodeService.checkSuccessStatus(acceptedChallengeRequestJSON)) {
			let acceptedChallenge = await acceptedChallengeRequestJSON.json();
			this.toastService.successfulRequestToast("Challenge Accepted!");
		} else {
			this.toastService.unableToSendRequestToast("Unable to accept challenge, please try again!");
		}
	}

	async rejectChallengeRequest(challengeRequestId: number) {
		let rejectedChallengeRequestJSON = await fetch(`${environment.fothuZoneEC2Link}/challengeRequests/reject/challengeRequestId/${challengeRequestId}`, { method: "PUT" });
		if (this.statusCodeService.checkSuccessStatus(rejectedChallengeRequestJSON)) {
			let rejectedChallenge = await rejectedChallengeRequestJSON.json();
			this.toastService.successfulRequestToast("Challenge Rejected!");
		} else {
			this.toastService.unableToSendRequestToast("Unable to reject challenge, please try again!");
		}
	}

	setAttackingPVPPet(battleId: number, attackingPetId: number) {
		this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/pvp/battleId/${battleId}/setAttackingPetId/${attackingPetId}`, body: `${this.userService.currentUser.id}` });
	}

	setDefendingPVPPet(battleId: number, defendingPetId: number) {
		this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/pvp/battleId/${battleId}/setDefendingPetId/${defendingPetId}`, body: `${this.userService.currentUser.id}` });
	}

	async createPVEBattle(userId: number, defendingPetId: number) {
		let newPVEBattleJSON = await fetch(`${environment.fothuZoneEC2Link}/battles/pve/new/userId/${userId}/defendingPetId/${defendingPetId}`, { method: "POST" });
		this.currentBattle = await newPVEBattleJSON.json();
	}

	attack(battleId: number, actingPetId: number) {
		if (this.currentBattle.attackingPet.id == actingPetId) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingPet/petId/${actingPetId}/attack`, body: `${this.userService.currentUser.id}` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingPet/petId/${actingPetId}/attack`, body: `${this.userService.currentUser.id}` });
		}
	}

	defend(battleId: number, actingPetId: number) {
		if (this.currentBattle.attackingPet.id == actingPetId) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingPet/petId/${actingPetId}/defend`, body: `${this.userService.currentUser.id}` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingPet/petId/${actingPetId}/defend`, body: `${this.userService.currentUser.id}` });
		}
	}

	aim(battleId: number, actingPetId: number) {
		if (this.currentBattle.attackingPet.id == actingPetId) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingPet/petId/${actingPetId}/aim`, body: `${this.userService.currentUser.id}` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingPet/petId/${actingPetId}/aim`, body: `${this.userService.currentUser.id}` });
		}
	}

	sharpen(battleId: number, actingPetId: number) {
		if (this.currentBattle.attackingPet.id == actingPetId) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingPet/petId/${actingPetId}/sharpen`, body: `${this.userService.currentUser.id}` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingPet/petId/${actingPetId}/sharpen`, body: `${this.userService.currentUser.id}` });
		}
	}

	closePVPBattle() {
		this.currentBattle = {
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
	}

	async prematureEndPveBattle(userId: number) {
		let battleJSON = await fetch(`${environment.fothuZoneEC2Link}/battles/pve/prematureEnd/battleId/${this.currentBattle.id}`, { method: "PUT" });
		let battle = await battleJSON.json();
	}
}

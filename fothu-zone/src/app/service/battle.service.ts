import { Battle } from "../models/Battle";
import { ChallengeRequest } from "../models/ChallengeRequest";

import { Injectable } from "@angular/core";
import { UserService } from "./user.service";
import { RxStompService } from "@stomp/ng2-stompjs";
import { environment } from "src/environments/environment";
import { StatusCodeService } from "./status-code.service";
import { ToastService } from "./toast.service";
import { ModalService } from "./modal.service";


@Injectable({
	providedIn: "root",
})
export class BattleService {
	constructor(private userService: UserService, private RXStompService: RxStompService, private statusCodeService: StatusCodeService, private toastService: ToastService, private modalService: ModalService) {}
	public stompClient;

	battleOn:boolean = false;

	currentChallengeRequest: ChallengeRequest = {
		id: 0,
		acceptedStatus: false,
		rejectedStatus: false,
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
		resultingBattle: {
			id: 0,
			battleType: "",
			maxNumberOfAttackingPets: 0,
			maxNumberOfDefendingPets: 0,
			attackingUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				pets: [],
			},
			attackingBattlePets: [],
			defendingUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				pets: [],
			},
			defendingBattlePets: [],
			nextTurnUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				pets: [],
			},
			battleFinished: false,
			winningUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				pets: [],
			},
			losingUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				pets: [],
			},
			createdOn: ",",
			turns: [{
				id: 0,
				turnNumber: 0,
				attackingPet: {
					id: 0,
					name: "",
					image: {
						id: 0,
						imageURL: "",
					},
					type: "",
					hunger: 0,
					currentHealth: 0,
					maxHealth: 0,
					strength: 0,
					agility: 0,
					intelligence: 0,
					petLevel: 0,
					currentXP: 0,
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
					type: "",
					hunger: 0,
					currentHealth: 0,
					maxHealth: 0,
					strength: 0,
					agility: 0,
					intelligence: 0,
					petLevel: 0,
					currentXP: 0,
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
				attackingPetAttackModifier: 0,
				defendingPetAttackModifier: 0,
				attackingPetArmorModifier: 0,
				defendingPetArmorModifier: 0,
				attackingPetAccuracyModifier: 0,
				defendingPetAccuracyModifier: 0,
				attackingPetEvasionModifier: 0,
				defendingPetEvasionModifier: 0,
				turnFlavorText: "",
				attackerReplacedDeadPet: false,
				defenderReplacedDeadPet: false,
				battleFinished: false,
				createdOn: "",
			}
			],
		},
		createdOn: "",
	};

	currentBattle: Battle = {
		id: 0,
		battleType: "",
		maxNumberOfAttackingPets: 0,
		maxNumberOfDefendingPets: 0,
		attackingUser: {
			id: 0,
			username: "",
			favoriteColor: "",
			adminStatus: false,
			pets: [],
		},
		attackingBattlePets: [],
		defendingUser: {
			id: 0,
			username: "",
			favoriteColor: "",
			adminStatus: false,
			pets: [],
		},
		defendingBattlePets: [],
		nextTurnUser: {
			id: 0,
			username: "",
			favoriteColor: "",
			adminStatus: false,
			pets: [],
		},
		battleFinished: false,
		winningUser: {
			id: 0,
			username: "",
			favoriteColor: "",
			adminStatus: false,
			pets: [],
		},
		losingUser: {
			id: 0,
			username: "",
			favoriteColor: "",
			adminStatus: false,
			pets: [],
		},
		createdOn: ",",
		turns: [{
			id: 0,
			turnNumber: 0,
			attackingPet: {
				id: 0,
				name: "",
				image: {
					id: 0,
					imageURL: "",
				},
				type: "",
				hunger: 0,
				currentHealth: 0,
				maxHealth: 0,
				strength: 0,
				agility: 0,
				intelligence: 0,
				petLevel: 0,
				currentXP: 0,
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
				type: "",
				hunger: 0,
				currentHealth: 0,
				maxHealth: 0,
				strength: 0,
				agility: 0,
				intelligence: 0,
				petLevel: 0,
				currentXP: 0,
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
			attackingPetAttackModifier: 0,
			defendingPetAttackModifier: 0,
			attackingPetArmorModifier: 0,
			defendingPetArmorModifier: 0,
			attackingPetAccuracyModifier: 0,
			defendingPetAccuracyModifier: 0,
			attackingPetEvasionModifier: 0,
			defendingPetEvasionModifier: 0,
			turnFlavorText: "",
			attackerReplacedDeadPet: false,
			defenderReplacedDeadPet: false,
			battleFinished: false,
			createdOn: "",
		}
		],
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
		if (this.currentBattle.attackingBattlePets.length < 1) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/setStartingAttackingPet/${attackingPetId}`, body: `${this.userService.currentUser.id}` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/setAttackingPet/${attackingPetId}`, body: `${this.userService.currentUser.id}` });
		}
	}

	setDefendingPVPPet(battleId: number, defendingPetId: number) {
		if (this.currentBattle.defendingBattlePets.length < 1) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/setStartingDefendingPet/${defendingPetId}`, body: `${this.userService.currentUser.id}` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/setDefendingPet/${defendingPetId}`, body: `${this.userService.currentUser.id}` });
		}
	}

	async createPVEBattle(userId: number, defendingPetId: number) {
		let newPVEBattleJSON = await fetch(`${environment.fothuZoneEC2Link}/battles/new/userId/${userId}/defendingPetId/${defendingPetId}/pve`, { method: "POST" });
		this.currentBattle = await newPVEBattleJSON.json();
	}

	attack(battleId: number) {
		if (this.currentBattle.attackingUser.id == this.userService.currentUser.id) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingUser/userId/${this.userService.currentUser.id}/attack` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingUser/userId/${this.userService.currentUser.id}/attack` });
		}
	}

	defend(battleId: number) {
		if (this.currentBattle.attackingUser.id == this.userService.currentUser.id) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingUser/userId/${this.userService.currentUser.id}/defend` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingUser/userId/${this.userService.currentUser.id}/defend` });
		}
	}

	aim(battleId: number) {
		if (this.currentBattle.attackingUser.id == this.userService.currentUser.id) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingUser/userId/${this.userService.currentUser.id}/aim` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingUser/userId/${this.userService.currentUser.id}/aim` });
		}
	}

	sharpen(battleId: number) {
		if (this.currentBattle.attackingUser.id == this.userService.currentUser.id) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingUser/userId/${this.userService.currentUser.id}/sharpen` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingUser/userId/${this.userService.currentUser.id}/sharpen` });
		}
	}

	evade(battleId: number) {
		if (this.currentBattle.attackingUser.id == this.userService.currentUser.id) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingUser/userId/${this.userService.currentUser.id}/evade` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingUser/userId/${this.userService.currentUser.id}/evade` });
		}
	}

	swap(battleId: number, petId: number) {
		if (this.currentBattle.attackingUser.id == this.userService.currentUser.id) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/swapAttackingPet/${petId}` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/swapDefendingPet/${petId}` });
		}
	}

	replaceDeadPet(battleId: number, deadPetId: number, newPetId: number) {
		if (this.currentBattle.attackingUser.id == this.userService.currentUser.id) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/replaceDeadAttackingPet/${deadPetId}/newAttackingPet/${newPetId}` });
		} else {
			this.RXStompService.publish({ destination: `fothuZoneSendPoint/battles/battleId/${battleId}/replaceDeadDefendingPet/${deadPetId}/newAttackingPet/${newPetId}` });
		}
	}

	resetBattleServiceBattle() {
		this.currentBattle = {
			id: 0,
			battleType: "",
			maxNumberOfAttackingPets: 0,
			maxNumberOfDefendingPets: 0,
			attackingUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				pets: [],
			},
			attackingBattlePets: [],
			defendingUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				pets: [],
			},
			defendingBattlePets: [],
			nextTurnUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				pets: [],
			},
			battleFinished: false,
			winningUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				pets: [],
			},
			losingUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				pets: [],
			},
			createdOn: ",",
			turns: [{
				id: 0,
				turnNumber: 0,
				attackingPet: {
					id: 0,
					name: "",
					image: {
						id: 0,
						imageURL: "",
					},
					type: "",
					hunger: 0,
					currentHealth: 0,
					maxHealth: 0,
					strength: 0,
					agility: 0,
					intelligence: 0,
					petLevel: 0,
					currentXP: 0,
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
					type: "",
					hunger: 0,
					currentHealth: 0,
					maxHealth: 0,
					strength: 0,
					agility: 0,
					intelligence: 0,
					petLevel: 0,
					currentXP: 0,
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
				attackingPetAttackModifier: 0,
				defendingPetAttackModifier: 0,
				attackingPetArmorModifier: 0,
				defendingPetArmorModifier: 0,
				attackingPetAccuracyModifier: 0,
				defendingPetAccuracyModifier: 0,
				attackingPetEvasionModifier: 0,
				defendingPetEvasionModifier: 0,
				turnFlavorText: "",
				attackerReplacedDeadPet: false,
				defenderReplacedDeadPet: false,
				battleFinished: false,
				createdOn: "",
			}
			],
		};
	}

	async prematureEndPveBattle(userId: number) {
		let battleJSON = await fetch(`${environment.fothuZoneEC2Link}/battles/pve/prematureEnd/battleId/${this.currentBattle.id}`, { method: "PUT" });
		let battle = await battleJSON.json();
	}
}

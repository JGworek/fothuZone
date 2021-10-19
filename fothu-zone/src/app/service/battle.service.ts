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
	modalFirstOpened:boolean = false;
	sendingRequest:boolean = false;

	currentChallengeRequest: ChallengeRequest = {
		id: 0,
		acceptedStatus: false,
		rejectedStatus: false,
		attackingUser: {
			id: 0,
			username: "",
			favoriteColor: "",
			adminStatus: false,
			verifiedStatus: false,
			pets: [],
		},
		defendingUser: {
			id: 0,
			username: "",
			favoriteColor: "",
			adminStatus: false,
			verifiedStatus: false,
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
				verifiedStatus: false,
				pets: [],
			},
			attackingBattlePets: [],
			defendingUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				verifiedStatus: false,
				pets: [],
			},
			defendingBattlePets: [],
			nextTurnUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				verifiedStatus: false,
				pets: [],
			},
			battleFinished: false,
			winningUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				verifiedStatus: false,
				pets: [],
			},
			losingUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				verifiedStatus: false,
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
			verifiedStatus: false,
			pets: [],
		},
		attackingBattlePets: [
			{
				id: 0,
				pet: {
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
				currentHealth: 0,
				aliveStatus: false,
			},
		],
		defendingUser: {
			id: 0,
			username: "",
			favoriteColor: "",
			adminStatus: false,
			verifiedStatus: false,
			pets: [],
		},
		defendingBattlePets: [
			{
				id: 0,
				pet: {
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
				currentHealth: 0,
				aliveStatus: false,
			},
		],
		nextTurnUser: {
			id: 0,
			username: "",
			favoriteColor: "",
			adminStatus: false,
			verifiedStatus: false,
			pets: [],
		},
		battleFinished: false,
		winningUser: {
			id: 0,
			username: "",
			favoriteColor: "",
			adminStatus: false,
			verifiedStatus: false,
			pets: [],
		},
		losingUser: {
			id: 0,
			username: "",
			favoriteColor: "",
			adminStatus: false,
			verifiedStatus: false,
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
		console.log(this.currentBattle);
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
		if (this.isBattlePetsArrayEmpty(this.currentBattle.attackingBattlePets)) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/setStartingAttackingPet/${attackingPetId}`, body: `${this.userService.currentUser.id}` });
			console.log("1")
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/setAttackingPet/${attackingPetId}`, body: `${this.userService.currentUser.id}` });
		}
		this.sendingRequest = true;
	}

	setDefendingPVPPet(battleId: number, defendingPetId: number) {
		if (this.isBattlePetsArrayEmpty(this.currentBattle.defendingBattlePets)) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/setStartingDefendingPet/${defendingPetId}`, body: `${this.userService.currentUser.id}` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/setDefendingPet/${defendingPetId}`, body: `${this.userService.currentUser.id}` });
		}
		this.sendingRequest = true;
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
		this.sendingRequest = true;
	}

	defend(battleId: number) {
		if (this.currentBattle.attackingUser.id == this.userService.currentUser.id) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingUser/userId/${this.userService.currentUser.id}/defend` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingUser/userId/${this.userService.currentUser.id}/defend` });
		}
		this.sendingRequest = true;
	}

	aim(battleId: number) {
		if (this.currentBattle.attackingUser.id == this.userService.currentUser.id) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingUser/userId/${this.userService.currentUser.id}/aim` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingUser/userId/${this.userService.currentUser.id}/aim` });
		}
		this.sendingRequest = true;
	}

	sharpen(battleId: number) {
		if (this.currentBattle.attackingUser.id == this.userService.currentUser.id) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingUser/userId/${this.userService.currentUser.id}/sharpen` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingUser/userId/${this.userService.currentUser.id}/sharpen` });
		}
		this.sendingRequest = true;
	}

	evade(battleId: number) {
		if (this.currentBattle.attackingUser.id == this.userService.currentUser.id) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/attackingUser/userId/${this.userService.currentUser.id}/evade` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/defendingUser/userId/${this.userService.currentUser.id}/evade` });
		}
		this.sendingRequest = true;
	}

	swap(battleId: number, petId: number) {
		if (this.currentBattle.attackingUser.id == this.userService.currentUser.id) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/swapAttackingPet/${petId}` });
		} else {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/swapDefendingPet/${petId}` });
		}
		this.sendingRequest = true;
	}

	replaceDeadPet(battleId: number, deadPetId: number, newPetId: number) {
		if (this.currentBattle.attackingUser.id == this.userService.currentUser.id) {
			this.RXStompService.publish({ destination: `/fothuZoneSendPoint/battles/battleId/${battleId}/replaceDeadAttackingPet/${deadPetId}/newAttackingPet/${newPetId}` });
		} else {
			this.RXStompService.publish({ destination: `fothuZoneSendPoint/battles/battleId/${battleId}/replaceDeadDefendingPet/${deadPetId}/newAttackingPet/${newPetId}` });
		}
		this.sendingRequest = true;
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
				verifiedStatus: false,
				pets: [],
			},
			attackingBattlePets: [
				{
					id: 0,
					pet: {
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
					currentHealth: 0,
					aliveStatus: false,
				},
			],
			defendingUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				verifiedStatus: false,
				pets: [],
			},
			defendingBattlePets: [
				{
					id: 0,
					pet: {
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
					currentHealth: 0,
					aliveStatus: false,
				},
			],
			nextTurnUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				verifiedStatus: false,
				pets: [],
			},
			battleFinished: false,
			winningUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				verifiedStatus: false,
				pets: [],
			},
			losingUser: {
				id: 0,
				username: "",
				favoriteColor: "",
				adminStatus: false,
				verifiedStatus: false,
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

	challengeRequests: Array<ChallengeRequest> = [];
	currentBattles: Array<Battle> = [];

	async getChallengeRequests() {
		let challengeRequestJSON = await fetch(`${environment.fothuZoneEC2Link}/challengeRequests/all/pending/userId/${this.userService.currentUser.id}`, { method: "GET" });
		this.challengeRequests = await challengeRequestJSON.json();
	}

	async getCurrentBattles() {
		let currentBattlesJSON = await fetch(`${environment.fothuZoneEC2Link}/battles/all/pvp/current/userId/${this.userService.currentUser.id}`, { method: "GET" });
		this.currentBattles = await currentBattlesJSON.json();
	}

	isBattlePetsArrayEmpty(array: Array<any>) {
		if (array.length == 0) {
			return true;
		} else if(array[0].id == 0){
			return true;
		} else {
			return false;
		}
	}
}

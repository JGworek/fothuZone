import { Battle } from "../models/Battle";
import { ChallengeRequestDTO } from "../models/ChallengeRequestDTO";

import { Injectable } from "@angular/core";
import { UserService } from "./user.service";
declare var SockJS: any;
declare var Stomp: any;

@Injectable({
	providedIn: "root",
})
export class BattleService {
	constructor(public userService: UserService) {}
	public stompClient;

	currentChallengeRequest: ChallengeRequestDTO = {
		id: -1,
		acceptedStatus: false,
		rejectedStatus: false,
		attackingUserId: -1,
		defendingUserId: -1,
		resultingBattleId: -1,
		createdOn: "",
	};

	currentBattle: Battle = {
		id: -1,
		battleType: "",
		attackingPet: {
			id: -1,
			name: "",
			image: { id: -1, imageURL: "" },
			hunger: -1,
			type: "",
			agility: -1,
			strength: -1,
			intelligence: -1,
			petLevel: -1,
			currentXP: -1,
			currentHealth: -1,
			maxHealth: -1,
			availableLevelUps: -1,
			owner: {
				id: -1,
				username: "",
				favoriteColor: "",
				adminStatus: false,
			},
		},
		defendingPet: {
			id: -1,
			name: "",
			image: {
				id: -1,
				imageURL: "",
			},
			hunger: -1,
			type: "",
			agility: -1,
			strength: -1,
			intelligence: -1,
			petLevel: -1,
			currentXP: -1,
			currentHealth: -1,
			maxHealth: -1,
			availableLevelUps: -1,
			owner: {
				id: -1,
				username: "",
				favoriteColor: "",
				adminStatus: false,
			},
		},
		winningPet: {
			id: -1,
			name: "",
			image: { id: -1, imageURL: "" },
			hunger: -1,
			type: "",
			agility: -1,
			strength: -1,
			intelligence: -1,
			petLevel: -1,
			currentXP: -1,
			currentHealth: -1,
			maxHealth: -1,
			availableLevelUps: -1,
			owner: {
				id: -1,
				username: "",
				favoriteColor: "",
				adminStatus: false,
			},
		},
		losingPet: {
			id: -1,
			name: "",
			image: { id: -1, imageURL: "" },
			hunger: -1,
			type: "",
			agility: -1,
			strength: -1,
			intelligence: -1,
			petLevel: -1,
			currentXP: -1,
			currentHealth: -1,
			maxHealth: -1,
			availableLevelUps: -1,
			owner: {
				id: -1,
				username: "",
				favoriteColor: "",
				adminStatus: false,
			},
		},
		attackingPetCurrentHealth: -1,
		defendingPetCurrentHealth: -1,
		attackingPetCurrentAttackModifier: -1,
		defendingPetCurrentAttackModifier: -1,
		attackingPetCurrentArmorModifier: -1,
		defendingPetCurrentArmorModifier: -1,
		attackingPetCurrentAccuracyModifier: -1,
		defendingPetCurrentAccuracyModifier: -1,
		attackingPetBaseAttackPower: -1,
		defendingPetBaseAttackPower: -1,
		attackingPetBaseArmor: -1,
		defendingPetBaseArmor: -1,
		attackingPetBaseSpeed: -1,
		defendingPetBaseSpeed: -1,
		currentTurnCount: -1,
		currentTurnPet: {
			id: -1,
			name: "",
			image: { id: -1, imageURL: "" },
			hunger: -1,
			type: "",
			agility: -1,
			strength: -1,
			intelligence: -1,
			petLevel: -1,
			currentXP: -1,
			currentHealth: -1,
			maxHealth: -1,
			availableLevelUps: -1,
			owner: {
				id: -1,
				username: "",
				favoriteColor: "",
				adminStatus: false,
			},
		},
		battleFinished: false,
		createdOn: ",",
		battleLogs: [],
	};

	currentChallengeRequests: Array<ChallengeRequestDTO> = [];
	currentActiveBattles: Array<Battle> = [];
	sentChallengeRequests: Array<ChallengeRequestDTO> = [];

	/* connect to ws */
	connectToWebsocket() {}

	/* normal GET requests */
	getCurrentChallengeRequests() {}

	getCurrentActiveBattles() {}

	getBattle(battleId: number) {}

	getChallengeRequest(challengeRequestId: number) {
		//this.currentChallengeRequest = SBResult
	}

	/* normal POST requests */
	createNewBattle() {}

	createNewChallengeRequest() {}

	/* subscribes */
	subscribeToBattle(battleId: number) {
		//subscribe to battle websocket with id being user id
	}

	unsubscribeToBattle(battleId: number) {}

	/* sends */
	setPVPPet(petId: number) {
		//also use currentUser ID int the request
	}

	attack(battleId: number, actingPetId: number) {}

	defend(battleId: number, actingPetId: number) {}

	aim(battleId: number, actingPetId: number) {}

	sharpen(battleId: number, actingPetId: number) {}
}

//connect to FothuPetsWebsocket on login
//get all current pvp battles from SB
//get all current battle requests from SB
//subscribe to the requests and battles ws endpoints to dynamically update new requests

import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { PetDTO } from "../models/PetDTO";
import { UserService } from "./user.service";

@Injectable({
	providedIn: "root",
})
export class LevelUpService {
	constructor() {}

	startingHealth: number = 0;
	startingStrength: number = 0;
	startingAgility: number = 0;
	startingIntelligence: number = 0;

	currentLevelingUpPet: PetDTO = {
		id: -1,
		name: "",
		imageId: -1,
		hunger: -1,
		type: "",
		agility: -1,
		strength: -1,
		intelligence: -1,
		petLevel: -1,
		curentXP: -1,
		currentHealth: -1,
		maxHealth: -1,
		ownerId: -1,
		availableLevelUps: 0,
	};

	highStat: string = "";
	mediumStat: string = "";
	lowStat: string = "";

	async levelUp() {
		this.startingHealth = this.currentLevelingUpPet.maxHealth;
		this.startingStrength = this.currentLevelingUpPet.strength;
		this.startingAgility = this.currentLevelingUpPet.agility;
		this.startingIntelligence = this.currentLevelingUpPet.intelligence;
		let petDTOJSON = await fetch(`${environment.fothuZoneEC2Link}/pets/levelUp/petId/${this.currentLevelingUpPet.id}?highStat=${this.highStat}&mediumStat=${this.mediumStat}&lowStat=${this.lowStat}`, { method: "PUT" });
		let petDTO = await petDTOJSON.json();
	}
}

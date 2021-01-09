import { Injectable } from "@angular/core";
import { PetDTO } from "../models/PetDTO";

@Injectable({
	providedIn: "root",
})
export class LevelUpService {
	constructor() {}

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
}

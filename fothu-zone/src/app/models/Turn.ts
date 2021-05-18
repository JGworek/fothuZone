import { Pet } from "./Pet";

export class Turn {
	id: number;
	turnNumber: number;
	attackingPet: Pet;
	defendingPet: Pet;
	attackingPetCurrentHealth: number;
	defendingPetCurrentHealth: number;
	attackingPetAttackModifier: number;
	defendingPetAttackModifier: number;
	attackingPetArmorModifier: number;
	defendingPetArmorModifier: number;
	attackingPetAccuracyModifier: number;
	defendingPetAccuracyModifier: number;
	attackingPetEvasionModifier: number;
	defendingPetEvasionModifier: number;
	turnFlavorText: string;
	attackerReplacedDeadPet: boolean;
	defenderReplacedDeadPet: boolean;
	battleFinished: boolean;
	createdOn: string;
}

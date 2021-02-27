import { BattleLog } from "./BattleLog";
import { Pet } from "./Pet";
import { User } from "./User";

export class Battle {
	id: number;
	battleType: string;
	attackingUser: User;
	defendingUser: User;
	attackingPet: Pet;
	defendingPet: Pet;
	winningPet: Pet;
	losingPet: Pet;
	attackingPetCurrentHealth: number;
	defendingPetCurrentHealth: number;
	attackingPetCurrentAttackModifier: number;
	defendingPetCurrentAttackModifier: number;
	attackingPetCurrentArmorModifier: number;
	defendingPetCurrentArmorModifier: number;
	attackingPetCurrentAccuracyModifier: number;
	defendingPetCurrentAccuracyModifier: number;
	attackingPetBaseAttackPower: number;
	defendingPetBaseAttackPower: number;
	attackingPetBaseArmor: number;
	defendingPetBaseArmor: number;
	attackingPetBaseSpeed: number;
	defendingPetBaseSpeed: number;
	currentTurnCount: number;
	currentTurnPet: Pet;
	battleFinished: boolean;
	createdOn: string;
	battleLogs: Array<BattleLog>;
}

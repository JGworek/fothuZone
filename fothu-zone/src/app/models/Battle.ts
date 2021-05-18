import { AttackingBattlePet } from "./AttackingBattlePet";
import { DefendingBattlePet } from "./DefendingBattlePet";
import { User } from "./User";
import { Turn } from "./Turn";

export class Battle {
	id: number;
	battleType: string;
	maxNumberOfAttackingPets: number;
	maxNumberOfDefendingPets: number;
	attackingUser: User;
	attackingBattlePets: Array<AttackingBattlePet>;
	defendingUser: User;
	defendingBattlePets: Array<DefendingBattlePet>;
	nextTurnUser: User;
	battleFinished: boolean;
	winningUser: User;
	losingUser: User;
	createdOn: string;
	turns: Array<Turn>;
}

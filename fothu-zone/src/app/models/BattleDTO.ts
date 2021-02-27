export class BattleDTO {
	id: number;
	battleType: string;
	attackingUserId: number;
	defendingUserId: number;
	attackingPetId: number;
	defendingPetId: number;
	winningPetId: number;
	losingPetId: number;
	battleFinished: boolean;
	createdOn: string;
}

export class ChallengeRequestDTO {
	id: number;
	acceptedStatus: boolean;
	rejectedStatus: boolean;
	attackingUserId: number;
	defendingUserId: number;
	resultingBattleId: number;
	createdOn: string;
}

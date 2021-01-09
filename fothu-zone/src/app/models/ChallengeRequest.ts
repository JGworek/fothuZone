import { Battle } from "./Battle";
import { User } from "./User";

export class ChallengeRequest {
	id: number;
	acceptedStatus: boolean;
	rejectedStatus: boolean;
	attackingUser: User;
	defendingUser: User;
	resultingBattle: Battle;
	createdOn: string;
}

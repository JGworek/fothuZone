import { Image } from "./Image";

export class User {
	id: number;
	username: string;
	favoriteColor: string;
	adminStatus: boolean;
	verifiedStatus: boolean;
	pets: Array<{
		id: number;
		name: string;
		image: Image;
		type: string;
		hunger: number;
		currentHealth: number;
		maxHealth: number;
		strength: number;
		agility: number;
		intelligence: number;
		petLevel: number;
		currentXP: number;
		availableLevelUps: number;
	}>;
}

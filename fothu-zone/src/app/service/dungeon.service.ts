import { Injectable } from '@angular/core';
import { Dungeon } from '../models/Dungeon';

@Injectable({
  providedIn: 'root'
})
export class DungeonService {

  constructor() { }

  currentDungeon: Dungeon = {
	id: 1,
	name: "Mumble's Creek",
	startingRoom: 30,
	bossRoom: 89,
	ladderRoom: -1,
	enemyLevel: 1,
	monsterSpawnRate: 50,
	nonBarrenCells: [11, 12, 13, 21, 23, 25, 26, 27, 30, 31, 32, 33, 35, 37, 38, 41, 43, 44, 45, 47, 51, 52, 57, 65, 66, 67, 73, 74, 75, 85, 86, 87, 88, 89],
	bossPet: {
		id: 3,
		name: "Chumbo",
		image: {
			id: 789,
			imageURL: "https://fothuzone-pets.s3.amazonaws.com/angry-dinosaur-showing-teeth-clipart.jpg",
		},
		type: "Strength",
		hunger: 5,
		currentHealth: 14,
		maxHealth: 14,
		strength: 8,
		agility: 6,
		intelligence: 4,
		petLevel: 1,
		currentXP: 0,
		availableLevelUps: 0,
		owner: {
			id: 2,
			username: "UberMentch",
			favoriteColor: "purple",
			adminStatus: false,
		},
	},
};


}

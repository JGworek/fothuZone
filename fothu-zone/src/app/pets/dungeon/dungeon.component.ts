import { Component, OnInit } from "@angular/core";
import { BattleService } from "src/app/service/battle.service";
import { LevelUpService } from "src/app/service/level-up.service";
import { UserService } from "src/app/service/user.service";
import { DungeonService } from "src/app/service/dungeon.service";
import { Dungeon } from "../../models/Dungeon";

@Component({
	selector: "app-dungeon",
	templateUrl: "./dungeon.component.html",
	styleUrls: ["./dungeon.component.css"],
})
export class DungeonComponent implements OnInit {
	constructor(public userService: UserService, public battleService: BattleService, public levelUpService: LevelUpService, public dungeonService: DungeonService) {}

	currentMap: Dungeon = {
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

	truthArray: Array<boolean> = new Array<boolean>(100);
	notExploredArray: Array<boolean> = new Array<boolean>(100);
	currentRoom: number;
	petSelect: boolean = false;
	mapLog: Array<string> = [];
	fullLog: boolean = false;

	checkIfNearby(roomId: number) {
		return this.adjacentVertical(roomId) || this.adjacentHorizontal(roomId);
	}

	onSameColumn(roomId: number) {
		// Checks that the ones digit is the same
		return this.currentRoom % 10 === roomId % 10;
	}

	onSameRow(roomId: number) {
		// Check that the tens digit is the same
		return Math.floor(this.currentRoom / 10) === Math.floor(roomId / 10);
	}

	adjacentHorizontal(roomId: number) {
		// On the same Row and their IDs are off by 1
		return this.onSameRow(roomId) && Math.abs(roomId - this.currentRoom) === 1;
	}

	adjacentVertical(roomId: number) {
		// On the same Column and their IDs are off by 10
		return this.onSameColumn(roomId) && Math.abs(roomId - this.currentRoom) === 10;
	}

	setCurrentRoom(roomId: number) {
		this.truthArray[this.currentRoom] = false;
		this.currentRoom = roomId;
		this.notExploredArray[roomId] = false;
		this.truthArray[roomId] = true;
		this.mapLog.push(`Moved to room ${this.currentRoom + 1}`);
	}

	//DO THIS IN A SERVICE AND DO IT IN THE MAIN MENU AND ALSO AFTER BATTLES ARE COMPLETE
	checkLevelUps() {
		console.log(this.levelUpService.currentLevelingUpPet.availableLevelUps);
	}

	ngOnInit(): void {
		this.notExploredArray.fill(true);
		this.truthArray.fill(false);
		this.setCurrentRoom(this.currentMap.startingRoom);
		this.mapLog.push(`Welcome to ${this.currentMap.name}`);
	}

	ngOnDestroy(): void {
		if (this.battleService.currentBattle.battleType.toLowerCase() == "pve") {
			if (confirm("Quit this battle and let the monster kill all of your pets?")) {
				this.battleService.prematureEndPveBattle(this.userService.currentUser.id);
				this.battleService.resetBattleServiceBattle();
			}
		}
	}
}

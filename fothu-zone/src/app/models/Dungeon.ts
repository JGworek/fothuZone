import { Pet } from "./Pet";

export class Dungeon {
	id: number;
	name: string;
	startingRoom: number;
	bossRoom: number;
	ladderRoom: number;
	enemyLevel: number;
	monsterSpawnRate: number;
	nonBarrenCells: Array<number>;
	bossPet: Pet;
}

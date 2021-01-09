import { Injectable } from "@angular/core";

@Injectable({
	providedIn: "root",
})
export class BattleService {
	constructor() {}

	currentBattle = {
		id: -1,
	};
}

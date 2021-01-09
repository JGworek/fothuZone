import { Component } from "@angular/core";
import { UserService } from "./service/user.service";
import { BattleService } from "./service/battle.service";
import { LevelUpService } from "./service/level-up.service";

@Component({
	selector: "app-root",
	templateUrl: "./app.component.html",
	styleUrls: ["./app.component.css"],
})
export class AppComponent {
	constructor(public userService: UserService, public battleService: BattleService, public levelUpService: LevelUpService) {}
	title = "fothu-zone";
}

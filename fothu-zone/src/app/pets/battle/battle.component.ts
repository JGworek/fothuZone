import { Component, OnInit } from "@angular/core";
import { BattleService } from "src/app/service/battle.service";
import { UserService } from "src/app/service/user.service";

@Component({
	selector: "app-battle",
	templateUrl: "./battle.component.html",
	styleUrls: ["./battle.component.css"],
})
export class BattleComponent implements OnInit {
	constructor(public battleService: BattleService, public userService: UserService) {}

	ngOnInit(): void {}
}

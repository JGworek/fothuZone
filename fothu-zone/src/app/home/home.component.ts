import { Component, OnInit } from "@angular/core";
import { BattleService } from "../service/battle.service";
import { ToastService } from "../service/toast.service";
import { UserService } from "../service/user.service";

@Component({
	selector: "app-links",
	templateUrl: "./home.component.html",
	styleUrls: ["./home.component.css"],
})
export class HomeComponent implements OnInit {
	constructor(public userService: UserService, public toastService: ToastService, public battleService: BattleService) {}

	ngOnInit() {}
}

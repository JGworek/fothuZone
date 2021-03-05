import { Component, OnInit } from "@angular/core";
import { RxStompService } from "@stomp/ng2-stompjs";
import { BattleService } from "src/app/service/battle.service";
import { UserService } from "src/app/service/user.service";

@Component({
	selector: "app-battle",
	templateUrl: "./battle.component.html",
	styleUrls: ["./battle.component.css"],
})
export class BattleComponent implements OnInit {
	constructor(public battleService: BattleService, public userService: UserService, private RxStompService: RxStompService) {}

	battleSubscription;

	subscribeToBattle(battleId: number) {
		this.battleSubscription = this.RxStompService.watch(`/battleSubscription/battleId/${this.battleService.currentBattle.id}`, { id: this.userService.currentUser.id as any }).subscribe((battleMessage) => {
			if (battleMessage.body) {
				console.log(battleMessage);
				console.log(battleMessage.body);
				let convertedBattleMessage = JSON.parse(battleMessage.body);
				this.battleService.currentBattle = convertedBattleMessage;
			}
		});
	}

	unsubscribeToBattle() {
		this.battleSubscription.unsubscribe();
	}

	ngOnInit(): void {
		this.subscribeToBattle(this.battleService.currentBattle.id);
		console.log(this.battleService.currentBattle);
	}

	ngOnDestroy(): void {
		if (this.battleService.currentBattle.battleType.toLowerCase() == "pve") {
			if (confirm("Quit this battle and let the monster kill all of your pets?")) {
				this.battleService.prematureEndPveBattle(this.userService.currentUser.id);
				this.unsubscribeToBattle();
			}
		} else {
			this.unsubscribeToBattle();
		}
	}
}

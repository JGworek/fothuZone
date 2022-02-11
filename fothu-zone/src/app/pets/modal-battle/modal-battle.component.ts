import { NgClass, NgStyle } from "@angular/common";
import { Component, OnInit, TemplateRef, ViewChild } from "@angular/core";
import { NgbActiveModal, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { BattleService } from "src/app/service/battle.service";
import { UserService } from "src/app/service/user.service";
import { ModalService } from "src/app/service/modal.service";
import { Battle } from "src/app/models/Battle";
import { RxStompService } from "@stomp/ng2-stompjs";

@Component({
	selector: "app-modal-battle",
	templateUrl: "./modal-battle.component.html",
	styleUrls: ["./modal-battle.component.css"],
	providers: [NgbActiveModal],
})
export class ModalBattleComponent implements OnInit {
	constructor(public modalService: ModalService, public NgbActiveModal: NgbActiveModal, public userService: UserService, public battleService: BattleService, private RxStompService: RxStompService) {}
	battleSubscription;

	@ViewChild("battleModal", { static: true }) battleModal: TemplateRef<any>;

	subscribeToBattle(battleId: number) {
		this.battleSubscription = this.RxStompService.watch(`/battleSubscription/battleId/${this.battleService.currentBattle.id}`, { id: this.userService.currentUser.id.toString() }).subscribe((battleMessage) => {
			if (battleMessage.body) {
				let convertedBattleMessage = JSON.parse(battleMessage.body);
				this.battleService.currentBattle = convertedBattleMessage;
				this.battleService.sendingRequest = false;
			}
		});
	}

	unsubscribeToBattle() {
		this.battleSubscription.unsubscribe();
	}

	getBattlePetsArraySize(array: Array<any>) {
		if (array[0].id == 0) {
			return 0;
		} else {
			return array.length;
		}
	}

	closeBattle() {
		this.modalService.dismissAll();
		this.battleService.resetBattleServiceBattle();
		this.battleService.getCurrentBattles();
	}

	ngOnInit(): void {
		this.subscribeToBattle(this.battleService.currentBattle.id);
		this.modalService.openOverlay(this.battleModal);
		console.log(this.battleService.currentBattle);
		if (!this.battleService.modalFirstOpened) {
			this.unsubscribeToBattle();
			this.subscribeToBattle(this.battleService.currentBattle.id);
			this.battleService.modalFirstOpened = true;
		}
	}

	ngOnDestroy(): void {
		if (this.battleService.currentBattle.battleType.toLowerCase() == "pve") {
			if (confirm("Quit this battle and let the monster kill all of your pets?")) {
				this.battleService.prematureEndPveBattle(this.userService.currentUser.id);
				this.unsubscribeToBattle();
			}
		} else {
			this.battleService.resetBattleServiceBattle();
			this.unsubscribeToBattle();
		}
	}
}

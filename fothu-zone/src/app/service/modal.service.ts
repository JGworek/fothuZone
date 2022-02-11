import { Injectable } from "@angular/core";
import { ModalDismissReasons, NgbActiveModal, NgbModal } from "@ng-bootstrap/ng-bootstrap";

@Injectable({
	providedIn: "root",
})
export class ModalService {
	title = "appBootstrap";

	closeResult: string;

	constructor(private NgbModal: NgbModal) {}

	open(content) {
		this.NgbModal.open(content, { ariaLabelledBy: "modal-basic-title" }).result.then(
			(result) => {
				this.closeResult = `Closed with: ${result}`;
			},
			(reason) => {
				this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
			}
		);
	}

	openOverlay(content) {
		this.NgbModal.open(content, { size: "xl", backdropClass: "overlay-backdrop", backdrop: "static", keyboard: false }).result.then(
			(result) => {
				this.closeResult = `Closed with: ${result}`;
				console.log(this.closeResult);
			},
			(reason) => {
				this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
				console.log(this.closeResult);
			}
		);
	}

	private getDismissReason(reason: any): string {
		if (reason === ModalDismissReasons.ESC) {
			console.log("by pressing ESC");
			return "by pressing ESC";
		} else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
			console.log("by clicking on a backdrop");
			return "by clicking on a backdrop";
		} else {
			console.log(`with: ${reason}`);
			return `with: ${reason}`;
		}
	}

	dismissAll() {
		this.NgbModal.dismissAll();
	}
}

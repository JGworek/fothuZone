import { Component, OnInit } from "@angular/core";
import { TemplateRef } from "@angular/core";

import { ToastService } from "../service/toast.service";

@Component({
	selector: "app-toast-container",
	templateUrl: "./toast-container.component.html",
	styleUrls: ["./toast-container.component.css"],
	host: { "[class.ngb-toasts]": "true" },
})
export class ToastContainerComponent implements OnInit {
	constructor(public toastService: ToastService) {}

	isTemplate(toast) {
		return toast.textOrTpl instanceof TemplateRef;
	}

	ngOnInit(): void {}
}

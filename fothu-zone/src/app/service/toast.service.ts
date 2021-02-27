import { Injectable, TemplateRef } from "@angular/core";

@Injectable({ providedIn: "root" })
export class ToastService {
	toasts: any[] = [];

	show(textOrTpl: string | TemplateRef<any>, options: any = {}) {
		this.toasts.push({ textOrTpl, ...options });
	}

	remove(toast) {
		this.toasts = this.toasts.filter((t) => t !== toast);
	}

	unableToSendRequestToast(message) {
		this.show(message, { classname: "bg-warning-fothu text-dark-fothu font-weight-bold", delay: 4000 });
	}

	badRequestToast(message) {
		this.show(message, { classname: "bg-danger text-white font-weight-bold", delay: 4000 });
	}

	successfulRequestToast(message) {
		this.show(message, { classname: "bg-secondary-fothu text-light font-weight-bold", delay: 4000 });
	}
}

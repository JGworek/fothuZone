import { Injectable } from "@angular/core";

@Injectable({
	providedIn: "root",
})
export class StatusCodeService {
	constructor() {}

	/**
	 * Takes in an httpResponse and returns true if successful and false with a non 2xx status code
	 */
	checkSuccessStatus(httpResponse: Response) {
		if (httpResponse.status.toString()[0] == "1" || httpResponse.status.toString()[0] == "3" || httpResponse.status.toString()[0] == "4" || httpResponse.status.toString()[0] == "5") {
			return false;
		} else {
			return true;
		}
	}
}

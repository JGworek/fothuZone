import { TestBed } from "@angular/core/testing";

import { NewPetService } from "./new-pet.service";

describe("NewPetService", () => {
	let service: NewPetService;

	beforeEach(() => {
		TestBed.configureTestingModule({});
		service = TestBed.inject(NewPetService);
	});

	it("should be created", () => {
		expect(service).toBeTruthy();
	});
});

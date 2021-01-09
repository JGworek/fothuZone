import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { PetbarComponent } from "./petbar.component";

describe("PetbarComponent", () => {
	let component: PetbarComponent;
	let fixture: ComponentFixture<PetbarComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [PetbarComponent],
		}).compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(PetbarComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it("should create", () => {
		expect(component).toBeTruthy();
	});
});

import { ComponentFixture, TestBed } from "@angular/core/testing";

import { HymnalComponent } from "./hymnal.component";

describe("HymnalComponent", () => {
	let component: HymnalComponent;
	let fixture: ComponentFixture<HymnalComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [HymnalComponent],
		}).compileComponents();
	});

	beforeEach(() => {
		fixture = TestBed.createComponent(HymnalComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it("should create", () => {
		expect(component).toBeTruthy();
	});
});

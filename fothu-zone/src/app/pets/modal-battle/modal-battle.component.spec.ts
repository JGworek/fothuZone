import { ComponentFixture, TestBed } from "@angular/core/testing";

import { ModalBattleComponent } from "./modal-battle.component";

describe("ModalBattleComponent", () => {
	let component: ModalBattleComponent;
	let fixture: ComponentFixture<ModalBattleComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [ModalBattleComponent],
		}).compileComponents();
	});

	beforeEach(() => {
		fixture = TestBed.createComponent(ModalBattleComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it("should create", () => {
		expect(component).toBeTruthy();
	});
});

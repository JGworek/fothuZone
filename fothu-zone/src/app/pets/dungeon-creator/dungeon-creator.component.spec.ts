import { ComponentFixture, TestBed } from "@angular/core/testing";

import { DungeonCreatorComponent } from "./dungeon-creator.component";

describe("DungeonCreatorComponent", () => {
	let component: DungeonCreatorComponent;
	let fixture: ComponentFixture<DungeonCreatorComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [DungeonCreatorComponent],
		}).compileComponents();
	});

	beforeEach(() => {
		fixture = TestBed.createComponent(DungeonCreatorComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it("should create", () => {
		expect(component).toBeTruthy();
	});
});

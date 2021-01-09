import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { RosaryAppComponent } from "./rosary-app.component";

describe("RosaryAppComponent", () => {
	let component: RosaryAppComponent;
	let fixture: ComponentFixture<RosaryAppComponent>;

	beforeEach(async(() => {
		TestBed.configureTestingModule({
			declarations: [RosaryAppComponent],
		}).compileComponents();
	}));

	beforeEach(() => {
		fixture = TestBed.createComponent(RosaryAppComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it("should create", () => {
		expect(component).toBeTruthy();
	});
});

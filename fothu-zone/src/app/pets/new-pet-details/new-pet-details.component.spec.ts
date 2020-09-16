import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewPetDetailsComponent } from './new-pet-details.component';

describe('NewPetDetailsComponent', () => {
  let component: NewPetDetailsComponent;
  let fixture: ComponentFixture<NewPetDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [NewPetDetailsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewPetDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

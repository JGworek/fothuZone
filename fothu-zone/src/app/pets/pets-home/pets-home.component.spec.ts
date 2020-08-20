import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PetsHomeComponent } from './pets-home.component';

describe('PetsHomeComponent', () => {
  let component: PetsHomeComponent;
  let fixture: ComponentFixture<PetsHomeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PetsHomeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PetsHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

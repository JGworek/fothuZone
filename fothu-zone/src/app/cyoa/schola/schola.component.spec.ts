import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScholaComponent } from './schola.component';

describe('ScholaComponent', () => {
  let component: ScholaComponent;
  let fixture: ComponentFixture<ScholaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScholaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScholaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SalfaggeVisualizerComponent } from './salfagge-visualizer.component';

describe('SalfaggeVisualizerComponent', () => {
  let component: SalfaggeVisualizerComponent;
  let fixture: ComponentFixture<SalfaggeVisualizerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SalfaggeVisualizerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SalfaggeVisualizerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { PreventExitGuard } from './prevent-exit.guard';

describe('PreventExitGuard', () => {
  let guard: PreventExitGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(PreventExitGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});

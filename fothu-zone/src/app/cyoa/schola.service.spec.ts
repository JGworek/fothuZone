import { TestBed } from '@angular/core/testing';

import { ScholaService } from './schola.service';

describe('ScholaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ScholaService = TestBed.get(ScholaService);
    expect(service).toBeTruthy();
  });
});

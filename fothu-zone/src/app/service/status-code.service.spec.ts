import { TestBed } from '@angular/core/testing';

import { StatusCodeService } from './status-code.service';

describe('StatusCodeService', () => {
  let service: StatusCodeService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatusCodeService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

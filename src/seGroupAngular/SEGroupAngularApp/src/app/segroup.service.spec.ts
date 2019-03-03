import { TestBed } from '@angular/core/testing';

import { SegroupService } from './segroup.service';

describe('SegroupService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SegroupService = TestBed.get(SegroupService);
    expect(service).toBeTruthy();
  });
});

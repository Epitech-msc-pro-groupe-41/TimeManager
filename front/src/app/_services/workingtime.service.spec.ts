import { TestBed } from '@angular/core/testing';

import { WorkingtimeService } from './workingtime.service';

describe('WorkingtimeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: WorkingtimeService = TestBed.get(WorkingtimeService);
    expect(service).toBeTruthy();
  });
});

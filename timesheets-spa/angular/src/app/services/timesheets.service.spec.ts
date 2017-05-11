import { TestBed, inject } from '@angular/core/testing';

import { TimesheetsService } from './timesheets.service';

describe('TimesheetsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TimesheetsService]
    });
  });

  it('should ...', inject([TimesheetsService], (service: TimesheetsService) => {
    expect(service).toBeTruthy();
  }));
});

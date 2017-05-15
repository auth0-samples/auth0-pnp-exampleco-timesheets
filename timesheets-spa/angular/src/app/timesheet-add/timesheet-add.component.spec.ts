import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TimesheetAddComponent } from './timesheet-add.component';

describe('TimesheetAddComponent', () => {
  let component: TimesheetAddComponent;
  let fixture: ComponentFixture<TimesheetAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TimesheetAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TimesheetAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

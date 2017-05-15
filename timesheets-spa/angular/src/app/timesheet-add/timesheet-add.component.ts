import { Component, OnInit } from '@angular/core';
import { TimesheetsService } from '../services/timesheets.service';
import { NewTimesheetModel } from '../models/new-timesheet-model';

@Component({
  selector: 'app-timesheet-add',
  templateUrl: './timesheet-add.component.html',
  styleUrls: ['./timesheet-add.component.css']
})
export class TimesheetAddComponent implements OnInit {

  model = new NewTimesheetModel();

  constructor(private timesheetsService : TimesheetsService) { }

  onSubmit() {
    this.timesheetsService.addTimesheet(this.model);
  }

  ngOnInit() {
  }

}

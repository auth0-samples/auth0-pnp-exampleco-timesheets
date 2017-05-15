import { Component, OnInit } from '@angular/core';
import { TimesheetsService } from '../services/timesheets.service';

@Component({
  selector: 'app-timesheet-list',
  templateUrl: './timesheet-list.component.html',
  styleUrls: ['./timesheet-list.component.css']
})
export class TimesheetListComponent implements OnInit {

  timesheets: Array<any>;
  error: string;

  constructor(private timesheetsService: TimesheetsService) { }

  ngOnInit() {
    this.timesheetsService.getAllTimesheets()
      .subscribe(
      data => this.timesheets = data,
      error => this.error = error.statusText
      );
  }

}

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TimesheetsService } from '../services/timesheets.service';
import { NewTimesheetModel } from '../models/new-timesheet-model';

@Component({
  selector: 'app-timesheet-add',
  templateUrl: './timesheet-add.component.html',
  styleUrls: ['./timesheet-add.component.css']
})
export class TimesheetAddComponent implements OnInit {
  
  error: string;
  model = new NewTimesheetModel();

  constructor(private router: Router, private timesheetsService: TimesheetsService) { }

  onSubmit() {
    this.timesheetsService.addTimesheet(this.model)
      .subscribe(
        data => this.router.navigate(['/timesheets']),
      error => this.error = error.statusText
      );

  }

  ngOnInit() {
  }

}

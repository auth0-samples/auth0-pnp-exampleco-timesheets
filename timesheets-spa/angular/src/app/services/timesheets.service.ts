import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { AuthHttp } from 'angular2-jwt';
import 'rxjs/add/operator/map';
import { NewTimesheetModel } from '../models/new-timesheet-model';

@Injectable()
export class TimesheetsService {

  constructor(public authHttp: AuthHttp) { }

  addTimesheet(model: NewTimesheetModel) {
    this.authHttp.post('http://localhost:8080/timesheets', JSON.stringify(model))
    .subscribe(
      err => console.log(err),
      () => console.log('Request Complete')
    );
    //return this.authHttp.post(, model);
  }

  getAllTimesheets() {
    return this.authHttp.get('http://localhost:8080/timesheets')
      .map(res => res.json())
  }
}

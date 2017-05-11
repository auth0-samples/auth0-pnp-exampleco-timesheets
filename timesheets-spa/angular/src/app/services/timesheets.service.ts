import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { AuthHttp } from 'angular2-jwt';
import 'rxjs/add/operator/map';

@Injectable()
export class TimesheetsService {

  constructor(public authHttp: AuthHttp) { }

  getAllTimesheets() {
    return this.authHttp.get('http://localhost:8080/timesheets')
      .map(res => res.json())
  }
}

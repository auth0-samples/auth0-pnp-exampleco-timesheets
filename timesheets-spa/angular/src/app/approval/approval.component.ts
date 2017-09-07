import { Component, OnInit } from '@angular/core';
import { TimesheetsService } from '../services/timesheets.service';
@Component({
  selector: 'app-approval',
  templateUrl: './approval.component.html',
  styleUrls: ['./approval.component.css']
})
export class ApprovalComponent implements OnInit {

  timesheets: Array<any>;
  error: string;

  constructor(private timesheetsService: TimesheetsService) { }
  
    ngOnInit() {
      this.timesheetsService.getUnapprovedTimesheets()
        .subscribe(
        data => this.timesheets = data,
        error => this.error = error.statusText
        );
    }

    approve(id: number) {
      this.timesheetsService.approveTimesheet(id)
      .subscribe(
        data => this.timesheets = this.timesheets.filter(i => i.id !== id),
        error => this.error = error.statusText
      );
      
    }

}

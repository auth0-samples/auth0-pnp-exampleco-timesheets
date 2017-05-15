import { Routes, CanActivate } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CallbackComponent } from './callback/callback.component';
import { AuthGuardService as AuthGuard } from './auth/auth-guard.service';
import { TimesheetListComponent } from './timesheet-list/timesheet-list.component';
import { TimesheetAddComponent } from './timesheet-add/timesheet-add.component';

export const ROUTES: Routes = [
  { path: '', component: HomeComponent },
  { path: 'callback', component: CallbackComponent },
  { path: 'timesheets/add', component: TimesheetAddComponent, canActivate: [AuthGuard] },
  { path: 'timesheets', component: TimesheetListComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '' }
];

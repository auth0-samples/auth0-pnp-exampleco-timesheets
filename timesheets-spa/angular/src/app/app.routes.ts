import { Routes, CanActivate } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { PingComponent } from './ping/ping.component';
import { AdminComponent } from './admin/admin.component';
import { ProfileComponent } from './profile/profile.component';
import { CallbackComponent } from './callback/callback.component';
import { AuthGuardService as AuthGuard } from './auth/auth-guard.service';
import { TimesheetListComponent } from './timesheet-list/timesheet-list.component';

export const ROUTES: Routes = [
  { path: '', component: HomeComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuard] },
  { path: 'ping', component: PingComponent },
  { path: 'callback', component: CallbackComponent },
  { path: 'timesheets', component: TimesheetListComponent },
  { path: '**', redirectTo: '' }
];

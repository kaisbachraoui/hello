import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { RisksComponent } from './pages/risks/risks.component';
import { ProjectsComponent } from './pages/projects/projects.component';
import { FindingsComponent } from './pages/findings/findings.component';
import { FindingDetailsComponent } from './pages/finding-details/finding-details.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'risks', component: RisksComponent },
  { path: 'projects', component: ProjectsComponent },
  { path: 'findings', component: FindingsComponent },
  { path: 'findings/:id', component: FindingDetailsComponent }
];

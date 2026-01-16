import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { ApiService, Page } from '../../services/api.service';

interface Project {
  id: string;
  name: string;
  description: string;
  businessOwner: string;
  technicalOwner: string;
  status: string;
}

@Component({
  selector: 'app-projects',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatTableModule],
  template: `
    <div class="page-container">
      <mat-card>
        <h2>Projects</h2>
        <table mat-table [dataSource]="projects">
          <ng-container matColumnDef="name">
            <th mat-header-cell *matHeaderCellDef>Project</th>
            <td mat-cell *matCellDef="let row">{{ row.name }}</td>
          </ng-container>
          <ng-container matColumnDef="businessOwner">
            <th mat-header-cell *matHeaderCellDef>Business Owner</th>
            <td mat-cell *matCellDef="let row">{{ row.businessOwner }}</td>
          </ng-container>
          <ng-container matColumnDef="technicalOwner">
            <th mat-header-cell *matHeaderCellDef>Technical Owner</th>
            <td mat-cell *matCellDef="let row">{{ row.technicalOwner }}</td>
          </ng-container>
          <ng-container matColumnDef="status">
            <th mat-header-cell *matHeaderCellDef>Status</th>
            <td mat-cell *matCellDef="let row">{{ row.status }}</td>
          </ng-container>
          <tr mat-header-row *matHeaderRowDef="columns"></tr>
          <tr mat-row *matRowDef="let row; columns: columns"></tr>
        </table>
      </mat-card>
    </div>
  `
})
export class ProjectsComponent {
  columns = ['name', 'businessOwner', 'technicalOwner', 'status'];
  projects: Project[] = [];

  constructor(private readonly api: ApiService) {
    this.api.getPage<Project>('/api/projects').subscribe((page: Page<Project>) => {
      this.projects = page.content;
    });
  }
}

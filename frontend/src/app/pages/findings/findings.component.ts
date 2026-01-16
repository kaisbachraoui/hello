import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { ApiService, Page } from '../../services/api.service';

interface Finding {
  id: string;
  projectName: string;
  riskId: string;
  title: string;
  severity: string;
  status: string;
  owner: string;
}

@Component({
  selector: 'app-findings',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTableModule,
    MatButtonModule,
    RouterLink
  ],
  template: `
    <div class="page-container">
      <mat-card>
        <h2>Findings</h2>
        <form [formGroup]="filters" class="table-actions" (ngSubmit)="load()">
          <mat-form-field appearance="outline">
            <mat-label>Project ID</mat-label>
            <input matInput formControlName="projectId" />
          </mat-form-field>
          <mat-form-field appearance="outline">
            <mat-label>Status</mat-label>
            <mat-select formControlName="status">
              <mat-option value="">All</mat-option>
              <mat-option value="DRAFT">Draft</mat-option>
              <mat-option value="REVIEWED">Reviewed</mat-option>
              <mat-option value="ACCEPTED">Accepted</mat-option>
              <mat-option value="MITIGATION_IN_PROGRESS">Mitigation In Progress</mat-option>
              <mat-option value="MITIGATED">Mitigated</mat-option>
              <mat-option value="CLOSED">Closed</mat-option>
            </mat-select>
          </mat-form-field>
          <button mat-raised-button color="primary" type="submit">Filter</button>
        </form>
        <table mat-table [dataSource]="findings">
          <ng-container matColumnDef="title">
            <th mat-header-cell *matHeaderCellDef>Finding</th>
            <td mat-cell *matCellDef="let row">
              <a [routerLink]="['/findings', row.id]">{{ row.title }}</a>
            </td>
          </ng-container>
          <ng-container matColumnDef="projectName">
            <th mat-header-cell *matHeaderCellDef>Project</th>
            <td mat-cell *matCellDef="let row">{{ row.projectName }}</td>
          </ng-container>
          <ng-container matColumnDef="riskId">
            <th mat-header-cell *matHeaderCellDef>Risk ID</th>
            <td mat-cell *matCellDef="let row">{{ row.riskId }}</td>
          </ng-container>
          <ng-container matColumnDef="severity">
            <th mat-header-cell *matHeaderCellDef>Severity</th>
            <td mat-cell *matCellDef="let row">{{ row.severity }}</td>
          </ng-container>
          <ng-container matColumnDef="status">
            <th mat-header-cell *matHeaderCellDef>Status</th>
            <td mat-cell *matCellDef="let row">{{ row.status }}</td>
          </ng-container>
          <ng-container matColumnDef="owner">
            <th mat-header-cell *matHeaderCellDef>Owner</th>
            <td mat-cell *matCellDef="let row">{{ row.owner }}</td>
          </ng-container>
          <tr mat-header-row *matHeaderRowDef="columns"></tr>
          <tr mat-row *matRowDef="let row; columns: columns"></tr>
        </table>
      </mat-card>
    </div>
  `
})
export class FindingsComponent {
  columns = ['title', 'projectName', 'riskId', 'severity', 'status', 'owner'];
  findings: Finding[] = [];
  filters = this.fb.group({
    projectId: [''],
    status: ['']
  });

  constructor(private readonly api: ApiService, private readonly fb: FormBuilder) {
    this.load();
  }

  load() {
    const { projectId, status } = this.filters.value;
    this.api
      .getPage<Finding>('/api/findings', {
        projectId: projectId || undefined,
        status: status || undefined
      })
      .subscribe((page: Page<Finding>) => {
        this.findings = page.content;
      });
  }
}

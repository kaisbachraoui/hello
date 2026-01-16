import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { ApiService } from '../../services/api.service';

interface DomainCount {
  domain: string;
  count: number;
}

interface SeverityStatusCount {
  severity: string;
  status: string;
  count: number;
}

interface RiskCount {
  riskId: string;
  riskTitle: string;
  count: number;
}

interface DashboardSummary {
  findingsByDomain: DomainCount[];
  findingsBySeverityStatus: SeverityStatusCount[];
  topRecurringRisks: RiskCount[];
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatTableModule],
  template: `
    <div class="page-container">
      <h2>Security Overview</h2>
      <div class="card-grid">
        <mat-card>
          <h3>Findings by Risk Domain</h3>
          <table mat-table [dataSource]="summary?.findingsByDomain || []">
            <ng-container matColumnDef="domain">
              <th mat-header-cell *matHeaderCellDef>Domain</th>
              <td mat-cell *matCellDef="let row">{{ row.domain }}</td>
            </ng-container>
            <ng-container matColumnDef="count">
              <th mat-header-cell *matHeaderCellDef>Findings</th>
              <td mat-cell *matCellDef="let row">{{ row.count }}</td>
            </ng-container>
            <tr mat-header-row *matHeaderRowDef="domainColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: domainColumns"></tr>
          </table>
        </mat-card>
        <mat-card>
          <h3>Findings by Severity & Status</h3>
          <table mat-table [dataSource]="summary?.findingsBySeverityStatus || []">
            <ng-container matColumnDef="severity">
              <th mat-header-cell *matHeaderCellDef>Severity</th>
              <td mat-cell *matCellDef="let row">{{ row.severity }}</td>
            </ng-container>
            <ng-container matColumnDef="status">
              <th mat-header-cell *matHeaderCellDef>Status</th>
              <td mat-cell *matCellDef="let row">{{ row.status }}</td>
            </ng-container>
            <ng-container matColumnDef="count">
              <th mat-header-cell *matHeaderCellDef>Count</th>
              <td mat-cell *matCellDef="let row">{{ row.count }}</td>
            </ng-container>
            <tr mat-header-row *matHeaderRowDef="severityColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: severityColumns"></tr>
          </table>
        </mat-card>
        <mat-card>
          <h3>Top 10 Recurring Risks</h3>
          <table mat-table [dataSource]="summary?.topRecurringRisks || []">
            <ng-container matColumnDef="riskId">
              <th mat-header-cell *matHeaderCellDef>Risk ID</th>
              <td mat-cell *matCellDef="let row">{{ row.riskId }}</td>
            </ng-container>
            <ng-container matColumnDef="riskTitle">
              <th mat-header-cell *matHeaderCellDef>Risk Title</th>
              <td mat-cell *matCellDef="let row">{{ row.riskTitle }}</td>
            </ng-container>
            <ng-container matColumnDef="count">
              <th mat-header-cell *matHeaderCellDef>Count</th>
              <td mat-cell *matCellDef="let row">{{ row.count }}</td>
            </ng-container>
            <tr mat-header-row *matHeaderRowDef="riskColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: riskColumns"></tr>
          </table>
        </mat-card>
      </div>
    </div>
  `
})
export class DashboardComponent implements OnInit {
  summary: DashboardSummary | null = null;
  domainColumns = ['domain', 'count'];
  severityColumns = ['severity', 'status', 'count'];
  riskColumns = ['riskId', 'riskTitle', 'count'];

  constructor(private readonly api: ApiService) {}

  ngOnInit() {
    this.api.get<DashboardSummary>('/api/reports/dashboard').subscribe({
      next: (data) => {
        this.summary = data as unknown as DashboardSummary;
      }
    });
  }
}

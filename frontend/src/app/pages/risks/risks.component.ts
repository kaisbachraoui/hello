import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { ApiService, Page } from '../../services/api.service';

interface Risk {
  riskId: string;
  domain: string;
  title: string;
  description: string;
  annexAMapping: string;
}

@Component({
  selector: 'app-risks',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatTableModule
  ],
  template: `
    <div class="page-container">
      <mat-card>
        <h2>Risk Library</h2>
        <form [formGroup]="filters" class="table-actions" (ngSubmit)="load()">
          <mat-form-field appearance="outline">
            <mat-label>Domain</mat-label>
            <input matInput formControlName="domain" placeholder="Application Security" />
          </mat-form-field>
          <mat-form-field appearance="outline">
            <mat-label>Keyword</mat-label>
            <input matInput formControlName="keyword" placeholder="Authentication" />
          </mat-form-field>
          <button mat-raised-button color="primary" type="submit">Search</button>
        </form>
        <table mat-table [dataSource]="risks">
          <ng-container matColumnDef="riskId">
            <th mat-header-cell *matHeaderCellDef>Risk ID</th>
            <td mat-cell *matCellDef="let row">{{ row.riskId }}</td>
          </ng-container>
          <ng-container matColumnDef="domain">
            <th mat-header-cell *matHeaderCellDef>Domain</th>
            <td mat-cell *matCellDef="let row">{{ row.domain }}</td>
          </ng-container>
          <ng-container matColumnDef="title">
            <th mat-header-cell *matHeaderCellDef>Title</th>
            <td mat-cell *matCellDef="let row">{{ row.title }}</td>
          </ng-container>
          <ng-container matColumnDef="annex">
            <th mat-header-cell *matHeaderCellDef>Annex A</th>
            <td mat-cell *matCellDef="let row">{{ row.annexAMapping }}</td>
          </ng-container>
          <tr mat-header-row *matHeaderRowDef="columns"></tr>
          <tr mat-row *matRowDef="let row; columns: columns"></tr>
        </table>
      </mat-card>
    </div>
  `
})
export class RisksComponent {
  columns = ['riskId', 'domain', 'title', 'annex'];
  risks: Risk[] = [];
  filters = this.fb.group({
    domain: [''],
    keyword: ['']
  });

  constructor(private readonly api: ApiService, private readonly fb: FormBuilder) {
    this.load();
  }

  load() {
    const { domain, keyword } = this.filters.value;
    this.api
      .getPage<Risk>('/api/risks', { domain: domain ?? undefined, keyword: keyword ?? undefined })
      .subscribe((page: Page<Risk>) => {
        this.risks = page.content;
      });
  }
}

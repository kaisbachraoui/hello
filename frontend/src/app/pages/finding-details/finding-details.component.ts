import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatTableModule } from '@angular/material/table';
import { ApiService } from '../../services/api.service';

interface Finding {
  id: string;
  projectName: string;
  riskId: string;
  riskTitle: string;
  title: string;
  description: string;
  severity: string;
  status: string;
  owner: string;
  controlTags: string[];
}

interface Comment {
  author: string;
  comment: string;
  createdAt: string;
}

interface History {
  actor: string;
  action: string;
  fromStatus: string;
  toStatus: string;
  createdAt: string;
}

interface Evidence {
  filename: string;
  url: string;
  createdAt: string;
}

@Component({
  selector: 'app-finding-details',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatChipsModule, MatTableModule],
  template: `
    <div class="page-container" *ngIf="finding">
      <mat-card>
        <h2>{{ finding.title }}</h2>
        <p>{{ finding.description }}</p>
        <p><strong>Project:</strong> {{ finding.projectName }}</p>
        <p><strong>Risk:</strong> {{ finding.riskId }} - {{ finding.riskTitle }}</p>
        <p><strong>Severity:</strong> {{ finding.severity }} | <strong>Status:</strong> {{ finding.status }}</p>
        <p><strong>Owner:</strong> {{ finding.owner }}</p>
        <mat-chip-listbox>
          <mat-chip *ngFor="let tag of finding.controlTags">{{ tag }}</mat-chip>
        </mat-chip-listbox>
      </mat-card>

      <div class="card-grid">
        <mat-card>
          <h3>Comments</h3>
          <table mat-table [dataSource]="comments">
            <ng-container matColumnDef="author">
              <th mat-header-cell *matHeaderCellDef>Author</th>
              <td mat-cell *matCellDef="let row">{{ row.author }}</td>
            </ng-container>
            <ng-container matColumnDef="comment">
              <th mat-header-cell *matHeaderCellDef>Comment</th>
              <td mat-cell *matCellDef="let row">{{ row.comment }}</td>
            </ng-container>
            <tr mat-header-row *matHeaderRowDef="commentColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: commentColumns"></tr>
          </table>
        </mat-card>
        <mat-card>
          <h3>History</h3>
          <table mat-table [dataSource]="history">
            <ng-container matColumnDef="actor">
              <th mat-header-cell *matHeaderCellDef>Actor</th>
              <td mat-cell *matCellDef="let row">{{ row.actor }}</td>
            </ng-container>
            <ng-container matColumnDef="action">
              <th mat-header-cell *matHeaderCellDef>Action</th>
              <td mat-cell *matCellDef="let row">{{ row.action }}</td>
            </ng-container>
            <tr mat-header-row *matHeaderRowDef="historyColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: historyColumns"></tr>
          </table>
        </mat-card>
        <mat-card>
          <h3>Evidence</h3>
          <table mat-table [dataSource]="evidence">
            <ng-container matColumnDef="filename">
              <th mat-header-cell *matHeaderCellDef>Filename</th>
              <td mat-cell *matCellDef="let row">{{ row.filename }}</td>
            </ng-container>
            <ng-container matColumnDef="url">
              <th mat-header-cell *matHeaderCellDef>Link</th>
              <td mat-cell *matCellDef="let row">{{ row.url }}</td>
            </ng-container>
            <tr mat-header-row *matHeaderRowDef="evidenceColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: evidenceColumns"></tr>
          </table>
        </mat-card>
      </div>
    </div>
  `
})
export class FindingDetailsComponent implements OnInit {
  finding: Finding | null = null;
  comments: Comment[] = [];
  history: History[] = [];
  evidence: Evidence[] = [];

  commentColumns = ['author', 'comment'];
  historyColumns = ['actor', 'action'];
  evidenceColumns = ['filename', 'url'];

  constructor(private readonly api: ApiService, private readonly route: ActivatedRoute) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) {
      return;
    }
    this.api.get<Finding>(`/api/findings/${id}`).subscribe((data) => (this.finding = data));
    this.api.get<Comment[]>(`/api/findings/${id}/comments`).subscribe((data) => (this.comments = data));
    this.api.get<History[]>(`/api/findings/${id}/history`).subscribe((data) => (this.history = data));
    this.api.get<Evidence[]>(`/api/findings/${id}/evidence`).subscribe((data) => (this.evidence = data));
  }
}

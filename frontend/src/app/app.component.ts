import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    RouterLinkActive,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatIconModule,
    MatButtonModule
  ],
  template: `
    <mat-toolbar color="primary">
      <span class="toolbar-title">Security Findings Hub</span>
      <span class="spacer"></span>
      <button mat-button (click)="auth.logout()">Logout</button>
    </mat-toolbar>
    <mat-sidenav-container class="shell">
      <mat-sidenav mode="side" opened>
        <mat-nav-list>
          <a mat-list-item routerLink="/dashboard" routerLinkActive="active">Dashboard</a>
          <a mat-list-item routerLink="/risks" routerLinkActive="active">Risk Library</a>
          <a mat-list-item routerLink="/projects" routerLinkActive="active">Projects</a>
          <a mat-list-item routerLink="/findings" routerLinkActive="active">Findings</a>
        </mat-nav-list>
      </mat-sidenav>
      <mat-sidenav-content>
        <router-outlet></router-outlet>
      </mat-sidenav-content>
    </mat-sidenav-container>
  `,
  styles: [
    `
      .shell {
        height: calc(100vh - 64px);
      }
      .spacer {
        flex: 1 1 auto;
      }
    `
  ]
})
export class AppComponent {
  constructor(public auth: AuthService) {}
}

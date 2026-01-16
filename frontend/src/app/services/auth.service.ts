import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, map, tap } from 'rxjs';

interface AuthResponse {
  token: string;
  username: string;
  roles: string[];
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly tokenKey = 'security-findings-token';
  private readonly rolesKey = 'security-findings-roles';
  private readonly userKey = 'security-findings-user';
  readonly roles$ = new BehaviorSubject<string[]>(this.getRoles());

  constructor(private readonly http: HttpClient) {}

  login(username: string, password: string) {
    return this.http.post<AuthResponse>('/api/auth/login', { username, password }).pipe(
      tap((response) => this.storeSession(response))
    );
  }

  logout() {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.rolesKey);
    localStorage.removeItem(this.userKey);
    this.roles$.next([]);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  getRoles(): string[] {
    const raw = localStorage.getItem(this.rolesKey);
    return raw ? JSON.parse(raw) : [];
  }

  hasRole(role: string) {
    return this.roles$.pipe(map((roles) => roles.includes(role)));
  }

  private storeSession(response: AuthResponse) {
    localStorage.setItem(this.tokenKey, response.token);
    localStorage.setItem(this.rolesKey, JSON.stringify(response.roles));
    localStorage.setItem(this.userKey, response.username);
    this.roles$.next(response.roles);
  }
}

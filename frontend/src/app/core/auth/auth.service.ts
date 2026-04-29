import { Injectable, inject } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { ApiClientService } from '../http/api-client.service';
import { AuthResponse, User } from '../models/platform.models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly api = inject(ApiClientService);
  private readonly tokenKey = 'preventhub_token';
  private readonly userKey = 'preventhub_user';

  readonly currentUser$ = new BehaviorSubject<User | null>(this.restoreUser());
  readonly token$ = new BehaviorSubject<string | null>(localStorage.getItem(this.tokenKey));

  login(email: string, password: string): Observable<AuthResponse> {
    return this.api.login({ email, password }).pipe(
      tap((response) => {
        localStorage.setItem(this.tokenKey, response.accessToken);
        localStorage.setItem(this.userKey, JSON.stringify(response.user));
        this.token$.next(response.accessToken);
        this.currentUser$.next(response.user);
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userKey);
    this.token$.next(null);
    this.currentUser$.next(null);
  }

  isAuthenticated(): boolean {
    return !!this.token$.value;
  }

  token(): string | null {
    return this.token$.value;
  }

  private restoreUser(): User | null {
    const raw = localStorage.getItem(this.userKey);
    return raw ? JSON.parse(raw) as User : null;
  }
}

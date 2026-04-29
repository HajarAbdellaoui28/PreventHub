import { AsyncPipe, NgClass } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';
import { AuthService } from '../auth/auth.service';
import { ThemeService } from './theme.service';

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [AsyncPipe, NgClass, RouterOutlet, RouterLink, RouterLinkActive, MatButtonModule, MatIconModule, ButtonModule, TagModule],
  template: `
    <div class="app-shell grid min-h-screen lg:grid-cols-[280px_1fr]">
      <aside class="glass-panel m-4 hidden rounded-2xl p-5 lg:block">
        <div class="mb-8 flex items-center gap-3">
          <div class="flex h-12 w-12 items-center justify-center rounded-xl bg-teal-700 text-lg font-bold text-white">PH</div>
          <div>
            <div class="text-lg font-semibold">PreventHub</div>
            <div class="soft-label">Public Health Intelligence</div>
          </div>
        </div>

        <nav class="space-y-2">
          @for (item of navItems; track item.path) {
            <a
              [routerLink]="item.path"
              routerLinkActive="bg-teal-700 text-white shadow-lg"
              class="flex items-center gap-3 rounded-xl px-4 py-3 text-sm font-medium text-[color:var(--text)] transition hover:bg-[color:var(--surface-soft)]"
            >
              <i class="pi" [ngClass]="item.icon"></i>
              <span>{{ item.label }}</span>
            </a>
          }
        </nav>

        <div class="mt-8 rounded-2xl bg-[color:var(--surface-soft)] p-4">
          <div class="soft-label">Workspace</div>
          <div class="mt-2 text-sm font-semibold">Distributed Prevention Ops</div>
          <p class="mt-2 text-sm text-[color:var(--muted)]">
            Decision support, targeting and population analytics from one control plane.
          </p>
        </div>
      </aside>

      <main class="p-4 lg:p-6">
        <header class="glass-panel mb-6 flex flex-wrap items-center justify-between gap-4 rounded-2xl px-5 py-4">
          <div>
            <div class="soft-label">Operational command center</div>
            <div class="text-2xl font-semibold">Population prevention intelligence</div>
          </div>

          <div class="flex items-center gap-3">
            <button mat-stroked-button type="button" (click)="themeService.toggle()">
              <i class="pi" [ngClass]="themeService.isDark ? 'pi-sun' : 'pi-moon'"></i>
            </button>
            <div class="rounded-xl border border-[color:var(--border)] px-4 py-2">
              <div class="text-sm font-semibold">{{ (authService.currentUser$ | async)?.fullName }}</div>
              <div class="soft-label">{{ (authService.currentUser$ | async)?.organization }}</div>
            </div>
            <button pButton type="button" label="Logout" severity="contrast" (click)="logout()"></button>
          </div>
        </header>

        <router-outlet></router-outlet>
      </main>
    </div>
  `
})
export class ShellComponent {
  readonly authService = inject(AuthService);
  readonly themeService = inject(ThemeService);
  private readonly router = inject(Router);

  readonly navItems = [
    { label: 'Dashboard', path: '/dashboard', icon: 'pi-home' },
    { label: 'Campaigns', path: '/campaigns', icon: 'pi-megaphone' },
    { label: 'Risk', path: '/risk', icon: 'pi-chart-bar' },
    { label: 'Engagement', path: '/engagement', icon: 'pi-users' },
    { label: 'Communication', path: '/communication', icon: 'pi-send' }
  ];

  constructor() {
    this.themeService.initialize();
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}

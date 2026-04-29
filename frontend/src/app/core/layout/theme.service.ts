import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private readonly key = 'preventhub_theme';

  initialize(): void {
    const theme = localStorage.getItem(this.key) ?? 'light';
    document.documentElement.classList.toggle('dark', theme === 'dark');
  }

  toggle(): void {
    const isDark = document.documentElement.classList.toggle('dark');
    localStorage.setItem(this.key, isDark ? 'dark' : 'light');
  }

  get isDark(): boolean {
    return document.documentElement.classList.contains('dark');
  }
}

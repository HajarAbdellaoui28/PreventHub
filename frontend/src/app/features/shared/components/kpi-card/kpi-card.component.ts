import { Component, Input } from '@angular/core';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-kpi-card',
  standalone: true,
  imports: [DecimalPipe],
  template: `
    <article class="metric-card glass-panel rounded-2xl p-5">
      <div class="soft-label">{{ label }}</div>
      <div class="mt-3 text-3xl font-semibold">{{ value | number:'1.0-2' }}</div>
      <div class="mt-2 text-sm text-[color:var(--muted)]">{{ hint }}</div>
    </article>
  `
})
export class KpiCardComponent {
  @Input() label = '';
  @Input() value = 0;
  @Input() hint = '';
}

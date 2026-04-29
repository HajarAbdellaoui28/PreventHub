import { Component, inject } from '@angular/core';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ApiClientService } from '../../../../core/http/api-client.service';
import { CommunicationHistory, MessageTemplate } from '../../../../core/models/platform.models';

@Component({
  selector: 'app-communication-page',
  standalone: true,
  imports: [TableModule, TagModule],
  template: `
    <section class="grid gap-6 xl:grid-cols-[1fr_1fr]">
      <article class="glass-panel rounded-2xl p-5">
        <div class="mb-4">
          <div class="section-title">Validated message library</div>
          <div class="soft-label">Clinically reviewed multi-channel prevention content</div>
        </div>
        <div class="space-y-3">
          @for (template of templates; track template.id) {
            <div class="rounded-2xl border border-[color:var(--border)] p-4">
              <div class="mb-2 flex items-center justify-between gap-3">
                <div class="font-semibold">{{ template.title }}</div>
                <p-tag [value]="template.channel" severity="info"></p-tag>
              </div>
              <div class="soft-label">{{ template.category }} - {{ template.medicalValidationLevel }}</div>
              <p class="mt-3 text-sm text-[color:var(--muted)]">{{ template.body }}</p>
            </div>
          }
        </div>
      </article>

      <article class="glass-panel rounded-2xl p-5">
        <div class="mb-4">
          <div class="section-title">Dispatch history</div>
          <div class="soft-label">Simulated sends recorded by channel and campaign</div>
        </div>
        <p-table [value]="history" [tableStyle]="{ 'min-width': '100%' }">
          <ng-template pTemplate="header">
            <tr>
              <th>Campaign</th>
              <th>Subject</th>
              <th>Channel</th>
              <th>Recipients</th>
              <th>Status</th>
            </tr>
          </ng-template>
          <ng-template pTemplate="body" let-entry>
            <tr>
              <td>#{{ entry.campaignId }}</td>
              <td>{{ entry.subject }}</td>
              <td>{{ entry.channel }}</td>
              <td>{{ entry.recipientCount }}</td>
              <td><p-tag [value]="entry.status" severity="success"></p-tag></td>
            </tr>
          </ng-template>
        </p-table>
      </article>
    </section>
  `
})
export class CommunicationPageComponent {
  private readonly api = inject(ApiClientService);
  templates: MessageTemplate[] = [];
  history: CommunicationHistory[] = [];

  constructor() {
    this.api.getTemplates().subscribe((templates) => this.templates = templates);
    this.api.getCommunicationHistory().subscribe((history) => this.history = history);
  }
}

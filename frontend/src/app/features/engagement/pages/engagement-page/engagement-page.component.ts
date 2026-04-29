import { Component, inject } from '@angular/core';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ApiClientService } from '../../../../core/http/api-client.service';
import { EngagementRecord, EngagementStats } from '../../../../core/models/platform.models';
import { KpiCardComponent } from '../../../shared/components/kpi-card/kpi-card.component';

@Component({
  selector: 'app-engagement-page',
  standalone: true,
  imports: [TableModule, TagModule, KpiCardComponent],
  template: `
    <section class="space-y-6">
      <div class="grid gap-4 md:grid-cols-2 xl:grid-cols-4">
        <app-kpi-card label="Open rate" [value]="(stats?.openRate ?? 0) * 100" hint="Recipients who opened a prevention message"></app-kpi-card>
        <app-kpi-card label="Participation %" [value]="(stats?.participationRate ?? 0) * 100" hint="Recipients converted to concrete action"></app-kpi-card>
        <app-kpi-card label="Dropout %" [value]="(stats?.dropoutRate ?? 0) * 100" hint="Users abandoning the care path"></app-kpi-card>
        <app-kpi-card label="Avg score" [value]="stats?.averageEngagementScore ?? 0" hint="Behavioral quality index"></app-kpi-card>
      </div>

      <article class="glass-panel rounded-2xl p-5">
        <div class="mb-4">
          <div class="section-title">Engagement stream</div>
          <div class="soft-label">Operational follow-up by citizen and by campaign</div>
        </div>
        <p-table [value]="records" [paginator]="true" [rows]="8" [tableStyle]="{ 'min-width': '100%' }">
          <ng-template pTemplate="header">
            <tr>
              <th>Citizen</th>
              <th>Campaign</th>
              <th>Channel</th>
              <th>Region</th>
              <th>Status</th>
              <th>Score</th>
            </tr>
          </ng-template>
          <ng-template pTemplate="body" let-record>
            <tr>
              <td>{{ record.citizenName }}</td>
              <td>#{{ record.campaignId }}</td>
              <td>{{ record.channel }}</td>
              <td>{{ record.region }}</td>
              <td>
                <p-tag
                  [value]="record.participated ? 'Participated' : record.droppedOut ? 'Dropped' : record.opened ? 'Opened' : 'Sent'"
                  [severity]="record.participated ? 'success' : record.droppedOut ? 'danger' : 'info'">
                </p-tag>
              </td>
              <td>{{ record.engagementScore }}</td>
            </tr>
          </ng-template>
        </p-table>
      </article>
    </section>
  `
})
export class EngagementPageComponent {
  private readonly api = inject(ApiClientService);
  stats: EngagementStats | null = null;
  records: EngagementRecord[] = [];

  constructor() {
    this.api.getEngagementStats().subscribe((stats) => this.stats = stats);
    this.api.getEngagementRecords().subscribe((records) => this.records = records);
  }
}

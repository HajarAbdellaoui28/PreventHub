import { Component, inject } from '@angular/core';
import { NgApexchartsModule } from 'ng-apexcharts';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ApiClientService } from '../../../../core/http/api-client.service';
import { DashboardResponse } from '../../../../core/models/platform.models';
import { KpiCardComponent } from '../../../shared/components/kpi-card/kpi-card.component';

@Component({
  selector: 'app-dashboard-page',
  standalone: true,
  imports: [NgApexchartsModule, TableModule, TagModule, KpiCardComponent],
  template: `
    <section class="space-y-6">
      <div class="grid gap-4 md:grid-cols-2 xl:grid-cols-4">
        <app-kpi-card label="Campaigns" [value]="dashboard?.globalKpis?.totalCampaigns ?? 0" hint="All active and scheduled programs"></app-kpi-card>
        <app-kpi-card label="Engagement %" [value]="dashboard?.globalKpis?.engagementRate ?? 0" hint="Population opening rate"></app-kpi-card>
        <app-kpi-card label="Coverage %" [value]="dashboard?.globalKpis?.coverageRate ?? 0" hint="Targeted citizens reached"></app-kpi-card>
        <app-kpi-card label="Estimated ROI" [value]="dashboard?.globalKpis?.estimatedHealthRoi ?? 0" hint="Average preventive return"></app-kpi-card>
      </div>

      <div class="grid gap-6 xl:grid-cols-[1.3fr_0.7fr]">
        <article class="glass-panel rounded-2xl p-5">
          <div class="mb-4 flex items-center justify-between">
            <div>
              <div class="section-title">Risk distribution</div>
              <div class="soft-label">Population segmentation by severity</div>
            </div>
          </div>
          <apx-chart
            [series]="chartSeries"
            [chart]="chartConfig"
            [labels]="['High risk', 'Medium risk', 'Low risk']"
            [colors]="['#dc2626', '#f59e0b', '#10b981']"
            [legend]="{ position: 'bottom' }"
            [dataLabels]="{ enabled: true }">
          </apx-chart>
        </article>

        <article class="glass-panel rounded-2xl p-5">
          <div class="section-title">Strategic insights</div>
          <div class="mt-4 space-y-3">
            @for (insight of dashboard?.strategicInsights ?? []; track insight) {
              <div class="rounded-xl border border-[color:var(--border)] p-4 text-sm text-[color:var(--muted)]">
                {{ insight }}
              </div>
            }
          </div>
        </article>
      </div>

      <article class="glass-panel rounded-2xl p-5">
        <div class="mb-4">
          <div class="section-title">Active campaigns</div>
          <div class="soft-label">Operational visibility across the platform</div>
        </div>
        <p-table [value]="dashboard?.activeCampaigns ?? []" [tableStyle]="{ 'min-width': '100%' }">
          <ng-template pTemplate="header">
            <tr>
              <th>Campaign</th>
              <th>Status</th>
              <th>Audience</th>
            </tr>
          </ng-template>
          <ng-template pTemplate="body" let-item>
            <tr>
              <td>{{ item.title }}</td>
              <td><p-tag [value]="item.status" severity="success"></p-tag></td>
              <td>{{ item.currentAudienceSize }}</td>
            </tr>
          </ng-template>
        </p-table>
      </article>
    </section>
  `
})
export class DashboardPageComponent {
  private readonly api = inject(ApiClientService);

  dashboard: DashboardResponse | null = null;
  chartSeries: number[] = [0, 0, 0];
  chartConfig = {
    type: 'donut' as const,
    height: 340,
    toolbar: { show: false }
  };

  constructor() {
    this.api.getDashboard().subscribe((dashboard) => {
      this.dashboard = dashboard;
      this.chartSeries = [
        dashboard.riskDistribution.highRisk,
        dashboard.riskDistribution.mediumRisk,
        dashboard.riskDistribution.lowRisk
      ];
    });
  }
}

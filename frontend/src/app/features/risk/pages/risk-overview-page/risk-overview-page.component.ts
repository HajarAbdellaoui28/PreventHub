import { Component, inject } from '@angular/core';
import { NgApexchartsModule } from 'ng-apexcharts';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ApiClientService } from '../../../../core/http/api-client.service';
import { CitizenRisk, RiskOverview } from '../../../../core/models/platform.models';
import { KpiCardComponent } from '../../../shared/components/kpi-card/kpi-card.component';

@Component({
  selector: 'app-risk-overview-page',
  standalone: true,
  imports: [NgApexchartsModule, TableModule, TagModule, KpiCardComponent],
  template: `
    <section class="space-y-6">
      <div class="grid gap-4 md:grid-cols-2 xl:grid-cols-4">
        <app-kpi-card label="Population" [value]="overview?.totalCitizens ?? 0" hint="Profiles available in the risk registry"></app-kpi-card>
        <app-kpi-card label="High risk" [value]="overview?.highRisk ?? 0" hint="Requires immediate preventive outreach"></app-kpi-card>
        <app-kpi-card label="Medium risk" [value]="overview?.mediumRisk ?? 0" hint="Needs behavioral follow-up"></app-kpi-card>
        <app-kpi-card label="Top region" [value]="overview?.highRisk ?? 0" [hint]="overview?.topRiskRegion ?? 'N/A'"></app-kpi-card>
      </div>

      <article class="glass-panel rounded-2xl p-5">
        <div class="mb-4">
          <div class="section-title">Population risk map</div>
          <div class="soft-label">{{ overview?.topRiskCondition }} is currently the most frequent condition.</div>
        </div>
        <apx-chart
          [series]="riskSeries"
          [chart]="riskChart"
          [xaxis]="riskXAxis"
          [colors]="['#0f766e']">
        </apx-chart>
      </article>

      <article class="glass-panel rounded-2xl p-5">
        <p-table [value]="citizens" [paginator]="true" [rows]="8" [tableStyle]="{ 'min-width': '100%' }">
          <ng-template pTemplate="header">
            <tr>
              <th>Citizen</th>
              <th>Location</th>
              <th>Risk</th>
              <th>Conditions</th>
              <th>Score</th>
            </tr>
          </ng-template>
          <ng-template pTemplate="body" let-citizen>
            <tr>
              <td>
                <div class="font-semibold">{{ citizen.fullName }}</div>
                <div class="soft-label">{{ citizen.age }} years - {{ citizen.gender }}</div>
              </td>
              <td>{{ citizen.city }}, {{ citizen.region }}</td>
              <td><p-tag [value]="citizen.riskLevel" [severity]="severity(citizen.riskLevel)"></p-tag></td>
              <td>{{ citizen.chronicConditions.join(', ') }}</td>
              <td>{{ citizen.riskScore }}</td>
            </tr>
          </ng-template>
        </p-table>
      </article>
    </section>
  `
})
export class RiskOverviewPageComponent {
  private readonly api = inject(ApiClientService);
  overview: RiskOverview | null = null;
  citizens: CitizenRisk[] = [];
  riskSeries = [{ name: 'Risk score', data: [] as number[] }];
  riskChart = { type: 'bar' as const, height: 320, toolbar: { show: false } };
  riskXAxis = { categories: [] as string[] };

  constructor() {
    this.api.getRiskOverview().subscribe((overview) => this.overview = overview);
    this.api.getRiskCitizens().subscribe((citizens) => {
      this.citizens = citizens;
      this.riskSeries = [{ name: 'Risk score', data: citizens.map((citizen) => citizen.riskScore) }];
      this.riskXAxis = { categories: citizens.map((citizen) => citizen.fullName) };
    });
  }

  severity(level: string): 'danger' | 'warn' | 'success' {
    switch (level) {
      case 'HIGH': return 'danger';
      case 'MEDIUM': return 'warn';
      default: return 'success';
    }
  }
}

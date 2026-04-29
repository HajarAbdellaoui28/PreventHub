import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { TagModule } from 'primeng/tag';
import { ApiClientService } from '../../../../core/http/api-client.service';
import { Campaign } from '../../../../core/models/platform.models';

@Component({
  selector: 'app-campaign-list-page',
  standalone: true,
  imports: [RouterLink, ButtonModule, TableModule, TagModule],
  template: `
    <section class="space-y-6">
      <div class="glass-panel flex flex-wrap items-center justify-between gap-4 rounded-2xl p-5">
        <div>
          <div class="section-title">Campaign management</div>
          <div class="soft-label">Targeting, scheduling and operational follow-up</div>
        </div>
        <a pButton routerLink="/campaigns/new" label="New campaign"></a>
      </div>

      <article class="glass-panel rounded-2xl p-5">
        <p-table [value]="campaigns" [paginator]="true" [rows]="8" [tableStyle]="{ 'min-width': '100%' }">
          <ng-template pTemplate="header">
            <tr>
              <th>Title</th>
              <th>Type</th>
              <th>Status</th>
              <th>Audience</th>
              <th>Region focus</th>
              <th>Scheduled</th>
            </tr>
          </ng-template>
          <ng-template pTemplate="body" let-campaign>
            <tr>
              <td>
                <div class="font-semibold">{{ campaign.title }}</div>
                <div class="soft-label">{{ campaign.objective }}</div>
              </td>
              <td>{{ campaign.campaignType }}</td>
              <td><p-tag [value]="campaign.status" [severity]="severity(campaign.status)"></p-tag></td>
              <td>{{ campaign.currentAudienceSize || campaign.expectedPopulation }}</td>
              <td>{{ campaign.targetRegions.join(', ') }}</td>
              <td>{{ campaign.scheduledDate }}</td>
            </tr>
          </ng-template>
        </p-table>
      </article>
    </section>
  `
})
export class CampaignListPageComponent {
  private readonly api = inject(ApiClientService);
  campaigns: Campaign[] = [];

  constructor() {
    this.api.getCampaigns().subscribe((campaigns) => this.campaigns = campaigns);
  }

  severity(status: string): 'success' | 'warn' | 'info' | 'contrast' {
    switch (status) {
      case 'ACTIVE': return 'success';
      case 'SCHEDULED': return 'info';
      case 'DRAFT': return 'warn';
      default: return 'contrast';
    }
  }
}

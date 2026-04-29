import { Routes } from '@angular/router';
import { authGuard } from './core/auth/auth.guard';
import { ShellComponent } from './core/layout/shell.component';
import { LoginPageComponent } from './features/auth/pages/login-page/login-page.component';
import { CampaignFormPageComponent } from './features/campaigns/pages/campaign-form-page/campaign-form-page.component';
import { CampaignListPageComponent } from './features/campaigns/pages/campaign-list-page/campaign-list-page.component';
import { CommunicationPageComponent } from './features/communication/pages/communication-page/communication-page.component';
import { DashboardPageComponent } from './features/dashboard/pages/dashboard-page/dashboard-page.component';
import { EngagementPageComponent } from './features/engagement/pages/engagement-page/engagement-page.component';
import { RiskOverviewPageComponent } from './features/risk/pages/risk-overview-page/risk-overview-page.component';

export const routes: Routes = [
  { path: 'login', component: LoginPageComponent },
  {
    path: '',
    component: ShellComponent,
    canActivate: [authGuard],
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
      { path: 'dashboard', component: DashboardPageComponent },
      { path: 'campaigns', component: CampaignListPageComponent },
      { path: 'campaigns/new', component: CampaignFormPageComponent },
      { path: 'risk', component: RiskOverviewPageComponent },
      { path: 'engagement', component: EngagementPageComponent },
      { path: 'communication', component: CommunicationPageComponent }
    ]
  },
  { path: '**', redirectTo: '' }
];

import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  AuthResponse,
  Campaign,
  CitizenRisk,
  CommunicationHistory,
  DashboardResponse,
  EngagementRecord,
  EngagementStats,
  MessageTemplate,
  RiskOverview
} from '../models/platform.models';

@Injectable({ providedIn: 'root' })
export class ApiClientService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = environment.apiBaseUrl;

  login(payload: { email: string; password: string }): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/api/auth/login`, payload);
  }

  currentUser() {
    return this.http.get(`${this.baseUrl}/api/auth/me`);
  }

  getDashboard(): Observable<DashboardResponse> {
    return this.http.get<DashboardResponse>(`${this.baseUrl}/api/analytics/dashboard`);
  }

  getCampaigns(): Observable<Campaign[]> {
    return this.http.get<Campaign[]>(`${this.baseUrl}/api/campaigns`);
  }

  createCampaign(payload: Partial<Campaign>) {
    return this.http.post<Campaign>(`${this.baseUrl}/api/campaigns`, payload);
  }

  getRiskCitizens(): Observable<CitizenRisk[]> {
    return this.http.get<CitizenRisk[]>(`${this.baseUrl}/api/risk/citizens`);
  }

  getRiskOverview(): Observable<RiskOverview> {
    return this.http.get<RiskOverview>(`${this.baseUrl}/api/risk/overview`);
  }

  getEngagementRecords(): Observable<EngagementRecord[]> {
    return this.http.get<EngagementRecord[]>(`${this.baseUrl}/api/engagement/records`);
  }

  getEngagementStats(): Observable<EngagementStats> {
    return this.http.get<EngagementStats>(`${this.baseUrl}/api/engagement/stats`);
  }

  getTemplates(): Observable<MessageTemplate[]> {
    return this.http.get<MessageTemplate[]>(`${this.baseUrl}/api/communications/templates`);
  }

  getCommunicationHistory(): Observable<CommunicationHistory[]> {
    return this.http.get<CommunicationHistory[]>(`${this.baseUrl}/api/communications/history`);
  }
}

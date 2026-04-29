export interface AuthResponse {
  accessToken: string;
  tokenType: string;
  expiresInSeconds: number;
  user: User;
}

export interface User {
  id: number;
  fullName: string;
  email: string;
  organization: string;
  roles: string[];
}

export interface Campaign {
  id: number;
  title: string;
  objective: string;
  campaignType: string;
  status: string;
  minAge: number;
  maxAge: number;
  gender: string;
  targetCities: string[];
  targetRegions: string[];
  targetConditions: string[];
  scheduledDate: string;
  expectedPopulation: number;
  currentAudienceSize: number;
  ownerOrganization: string;
  createdAt: string;
  updatedAt: string;
}

export interface DashboardResponse {
  globalKpis: {
    totalCampaigns: number;
    engagementRate: number;
    participationRate: number;
    coverageRate: number;
    estimatedHealthRoi: number;
  };
  activeCampaigns: Array<{
    id: number;
    title: string;
    status: string;
    currentAudienceSize: number;
  }>;
  riskDistribution: {
    highRisk: number;
    mediumRisk: number;
    lowRisk: number;
    topRiskRegion: string;
  };
  strategicInsights: string[];
}

export interface CitizenRisk {
  citizenId: number;
  fullName: string;
  age: number;
  gender: string;
  city: string;
  region: string;
  riskLevel: 'LOW' | 'MEDIUM' | 'HIGH';
  riskScore: number;
  chronicConditions: string[];
}

export interface RiskOverview {
  totalCitizens: number;
  highRisk: number;
  mediumRisk: number;
  lowRisk: number;
  topRiskRegion: string;
  topRiskCondition: string;
}

export interface EngagementRecord {
  id: number;
  campaignId: number;
  citizenId: number;
  citizenName: string;
  region: string;
  channel: string;
  opened: boolean;
  enrolled: boolean;
  participated: boolean;
  droppedOut: boolean;
  engagementScore: number;
  eventTimestamp: string;
}

export interface EngagementStats {
  totalRecords: number;
  openedCount: number;
  enrolledCount: number;
  participatedCount: number;
  droppedOutCount: number;
  openRate: number;
  participationRate: number;
  dropoutRate: number;
  averageEngagementScore: number;
}

export interface MessageTemplate {
  id: number;
  title: string;
  category: string;
  channel: string;
  medicalValidationLevel: string;
  body: string;
}

export interface CommunicationHistory {
  id: number;
  campaignId: number;
  subject: string;
  channel: string;
  recipientCount: number;
  status: string;
  sentAt: string;
}

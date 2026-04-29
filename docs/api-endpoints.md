# API REST PreventHub

## Gateway

Toutes les routes sont exposees via `http://localhost:8080/api/...`

## Authentification

- `POST /api/auth/login`
- `GET /api/auth/me`
- `GET /api/auth/users`

## Campagnes

- `GET /api/campaigns`
- `POST /api/campaigns`
- `GET /api/campaigns/{id}`
- `PUT /api/campaigns/{id}`
- `PATCH /api/campaigns/{id}/status`
- `POST /api/campaigns/{id}/schedule`
- `GET /api/campaigns/{id}/history`
- `POST /api/campaigns/{id}/launch`

## Stratification du risque

- `GET /api/risk/citizens`
- `POST /api/risk/evaluate`
- `POST /api/risk/segments/search`
- `GET /api/risk/overview`

## Engagement

- `GET /api/engagement/records`
- `POST /api/engagement/records`
- `GET /api/engagement/stats`
- `GET /api/engagement/campaigns/{campaignId}/summary`

## Communication

- `GET /api/communications/templates`
- `POST /api/communications/send`
- `GET /api/communications/history`

## Analytics

- `GET /api/analytics/dashboard`
- `GET /api/analytics/campaigns/{campaignId}`
- `GET /api/analytics/kpis`

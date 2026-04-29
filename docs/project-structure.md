# Structure du projet

## Monorepo

```text
PreventHub/
|-- backend/
|   |-- pom.xml
|   |-- config-repo/
|   |-- service-registry/
|   |-- config-server/
|   |-- api-gateway/
|   |-- identity-service/
|   |-- campaign-management-service/
|   |-- risk-stratification-service/
|   |-- engagement-tracking-service/
|   |-- impact-analytics-service/
|   `-- communication-service/
|-- frontend/
|   |-- package.json
|   |-- angular.json
|   `-- src/app/
|       |-- core/
|       |-- features/
|       `-- shared/
|-- docs/
|-- report/
|-- docker-compose.yml
`-- README.md
```

## Modules backend

- `service-registry` : decouverte de services avec Eureka.
- `config-server` : centralisation de la configuration.
- `api-gateway` : point d'entree unique, securite JWT, routage.
- `identity-service` : authentification et roles.
- `campaign-management-service` : orchestration du cycle de vie des campagnes.
- `risk-stratification-service` : segmentation de population et scoring du risque.
- `engagement-tracking-service` : suivi de l'adhesion et de la participation.
- `impact-analytics-service` : KPIs, couverture, ROI sante, dashboard.
- `communication-service` : templates et envoi simule multicanal.

## Modules frontend

- `core/` : auth, guard, interceptor, layout, services transverses.
- `features/auth` : page de connexion.
- `features/dashboard` : KPIs et visualisation executive.
- `features/campaigns` : liste et creation de campagnes.
- `features/risk` : lecture des segments et niveaux de risque.
- `features/engagement` : suivi des conversions et abandons.
- `features/communication` : templates et historique d'envoi.

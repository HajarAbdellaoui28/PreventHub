# Architecture PreventHub

## Positionnement

PreventHub cible les autorites sanitaires, collectivites, agences regionales et professionnels de sante qui doivent piloter des campagnes de prevention a grande echelle avec une vision territoriale fine.

## Choix techniques

- `Spring Boot 3.4` pour des services modernes, observables et facilement containerisables.
- `Spring Cloud` pour `Eureka`, `Gateway`, `Config Server` et `OpenFeign`.
- `PostgreSQL` avec une base par service pour garantir l'autonomie des donnees.
- `JWT` au niveau `identity-service` et filtrage centralise au `gateway`.
- `Angular 19 standalone` avec `Tailwind`, `PrimeNG`, `Angular Material` et `ApexCharts`.

## Microservices

### `service-registry`

Catalogue de services pour la decouverte dynamique et la tolerance aux changements d'adresse.

### `config-server`

Centralise les parametres de chaque service dans `backend/config-repo`.

### `api-gateway`

Point d'entree unique, routage, CORS, propagation du contexte de securite, securisation JWT, simplification du frontend.

### `identity-service`

Authentification, roles, comptes de demonstration, emission de JWT, endpoint `me`.

### `campaign-management-service`

Pilote le cycle de vie d'une campagne: creation, ciblage, statut, historique, orchestration vers le moteur de risque et la communication.

### `risk-stratification-service`

Segmentation intelligente fondee sur des regles demographiques, geographiques et cliniques. Le modele est interpretable, defendable et evolutif vers du ML plus pousse.

### `engagement-tracking-service`

Suit les reactions de la population ciblee et mesure l'engagement reel sur les campagnes.

### `impact-analytics-service`

Construit les KPIs globaux, le taux de couverture, l'efficacite et une estimation de ROI sante.

### `communication-service`

Simule l'envoi email, SMS et push avec bibliotheque de messages valides et historique d'emission.

## Pipeline technique

1. Authentification via `identity-service`.
2. Emission d'un JWT signe.
3. Appel du frontend vers `api-gateway`.
4. Routage vers les services metier decouverts via Eureka.
5. Lecture des proprietes via Config Server.
6. Coordination inter-services via OpenFeign.
7. Consolidation analytique pour alimenter les dashboards Angular.

## Pourquoi cette architecture est defendable

- Separation stricte des responsabilites et meilleure maintenabilite.
- Scalabilite selective: un service charge peut etre replique sans toucher au reste.
- Autonomie des equipes et des schemas de donnees.
- Bonne demonstration academique de la virtualisation, de l'orchestration et des principes distribues.
- Trajectoire claire vers observabilite, CI/CD, traces distribuees et deploiement Kubernetes.

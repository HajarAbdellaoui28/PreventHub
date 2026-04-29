# Runbook PreventHub

## Option recommandee: Docker Compose

```bash
docker compose up --build
```

Puis ouvrir:

- Frontend: `http://localhost:4200`
- Gateway: `http://localhost:8080`
- Eureka: `http://localhost:8761`
- Config Server: `http://localhost:8888`

## Option locale backend

Depuis `backend/`:

```bash
mvn clean package
```

Lancer les services dans cet ordre:

1. `config-server`
2. `service-registry`
3. `identity-service`
4. `risk-stratification-service`
5. `communication-service`
6. `campaign-management-service`
7. `engagement-tracking-service`
8. `impact-analytics-service`
9. `api-gateway`

## Option locale frontend

Depuis `frontend/`:

```bash
npm.cmd install
npm.cmd run start
```

Le serveur Angular local est expose sur `http://localhost:4301`.

## Tests API rapides

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"admin@preventhub.io\",\"password\":\"PreventHub!2026\"}"
```

### Liste des campagnes

Utiliser le JWT recupere au login:

```bash
curl http://localhost:8080/api/campaigns ^
  -H "Authorization: Bearer <TOKEN>"
```

### Dashboard analytique

```bash
curl http://localhost:8080/api/analytics/dashboard ^
  -H "Authorization: Bearer <TOKEN>"
```

## Conseils d'execution

- Attendre que `config-server` et `service-registry` soient sains avant de lancer les services metier.
- Verifier que les ports PostgreSQL `5433` a `5438` ne sont pas deja occupes.
- Sous PowerShell Windows, preferer `npm.cmd` si la politique d'execution bloque `npm`.
- Pour la soutenance, privilegier Docker Compose afin d'eviter les oublis d'ordre de demarrage.

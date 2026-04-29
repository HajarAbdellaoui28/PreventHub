# Guide de soutenance

## Scenario de demonstration

1. Ouvrir le dashboard et presenter les KPIs nationaux.
2. Se connecter en `admin`.
3. Montrer la creation d'une campagne de depistage cardiovasculaire.
4. Expliquer les criteres de ciblage puis lancer la campagne.
5. Visualiser la segmentation de risque calculee par territoire.
6. Ouvrir le module communication et presenter les messages email, SMS et push.
7. Passer au module engagement pour montrer les ouvertures, inscriptions et abandons.
8. Terminer sur le dashboard analytique et le ROI sante estime.

## Points a faire ressortir devant le jury

- Decouplage fort entre services et bases de donnees.
- OpenFeign pour l'orchestration.
- JWT et roles metier.
- Tableaux de bord modernes et utiles.
- Prise en compte RGPD, confidentialite et biais algorithmiques.

## Conseils

- Lancer la plateforme via Docker Compose pour une demonstration stable.
- Garder Swagger ouvert sur les services pour illustrer la qualite des contrats REST.
- Expliquer qu'une logique interpretable a ete preferee pour la stratification initiale afin de faciliter l'auditabilite.

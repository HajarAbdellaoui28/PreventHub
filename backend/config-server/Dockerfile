FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace

# 1. Copier le POM parent
COPY pom.xml pom.xml

# 2. Copier TOUS les POMs des sous-modules déclarés dans le parent
COPY config-server/pom.xml config-server/pom.xml
COPY service-registry/pom.xml service-registry/pom.xml
COPY api-gateway/pom.xml api-gateway/pom.xml
COPY identity-service/pom.xml identity-service/pom.xml
COPY campaign-management-service/pom.xml campaign-management-service/pom.xml
COPY risk-stratification-service/pom.xml risk-stratification-service/pom.xml
COPY engagement-tracking-service/pom.xml engagement-tracking-service/pom.xml
COPY impact-analytics-service/pom.xml impact-analytics-service/pom.xml
COPY communication-service/pom.xml communication-service/pom.xml

# 3. Résolution des dépendances hors-ligne (fonctionnera car Maven voit toute la structure)
RUN mvn -pl config-server -am dependency:go-offline

# 4. Copier uniquement le code source de CE microservice
COPY config-server/src config-server/src

# 5. Compilation du microservice
RUN mvn -pl config-server -am clean package -DskipTests

# ---------------------------------------------------
# Étape d'exécution (Image finale plus légère)
# ---------------------------------------------------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Récupérer le jar généré à l'étape précédente
COPY --from=build /workspace/config-server/target/config-server-1.0.0.jar app.jar

EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
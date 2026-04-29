FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /workspace

# 1. Copier le POM parent
COPY pom.xml pom.xml

# 2. Copier TOUS les POMs des sous-modules (OBLIGATOIRE POUR TOUS LES SERVICES)
COPY config-server/pom.xml config-server/pom.xml
COPY service-registry/pom.xml service-registry/pom.xml
COPY api-gateway/pom.xml api-gateway/pom.xml
COPY identity-service/pom.xml identity-service/pom.xml
COPY campaign-management-service/pom.xml campaign-management-service/pom.xml
COPY risk-stratification-service/pom.xml risk-stratification-service/pom.xml
COPY engagement-tracking-service/pom.xml engagement-tracking-service/pom.xml
COPY impact-analytics-service/pom.xml impact-analytics-service/pom.xml
COPY communication-service/pom.xml communication-service/pom.xml

# 3. Résolution des dépendances
RUN mvn -pl risk-stratification-service -am dependency:go-offline

# 4. Copier uniquement le code source du service-registry
COPY risk-stratification-service/src risk-stratification-service/src

# 5. Compilation
RUN mvn -pl risk-stratification-service -am clean package -DskipTests

# ---------------------------------------------------
# Étape d'exécution
# ---------------------------------------------------


FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/risk-stratification-service/target/risk-stratification-service-1.0.0.jar app.jar
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

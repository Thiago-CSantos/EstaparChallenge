# Etapa de build
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia primeiro o pom para aproveitar cache
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa de execução
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 3003

ENTRYPOINT ["java", "-jar", "app.jar"]
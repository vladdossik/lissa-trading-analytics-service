FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/analytics-service-0.0.1-SNAPSHOT.jar /app/service.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "/app/service.jar"]
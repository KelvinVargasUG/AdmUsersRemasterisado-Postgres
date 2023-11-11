FROM adoptopenjdk:11-jdk-hotspot
MAINTAINER kelvinVargas

RUN groupadd -r spring && useradd -r -g spring spring

USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} AdmUsersRemasterisado-postgres-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/AdmUsersRemasterisado-postgres-0.0.1-SNAPSHOT.jar"]
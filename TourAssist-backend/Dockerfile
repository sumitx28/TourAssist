FROM ubuntu AS build
LABEL authors="sumit"

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .
RUN ./mvnw clean install -DskipTests

FROM openjdk:17-jdk-slim
EXPOSE 8080
COPY --from=build /target/backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
FROM amazoncorretto:17-al2023-jdk

WORKDIR /app

COPY ServerSide/TourAssist/target/tour-assist-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]

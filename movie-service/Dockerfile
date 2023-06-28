FROM openjdk:11-jdk-slim
VOLUME /tmp
COPY target/movie-booking-app.jar app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]

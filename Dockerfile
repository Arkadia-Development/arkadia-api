FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY target/*.jar app.jar
COPY games.json games.json
ENTRYPOINT ["java", "-jar", "app.jar"]

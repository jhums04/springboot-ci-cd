FROM openjdk:17-jdk

WORKDIR /app

COPY target/notes-api-0.0.1-SNAPSHOT.jar /app/notes-api.jar

EXPOSE 8081

CMD ["java", "-jar", "notes-api.jar"]
FROM adoptopenjdk/openjdk11:latest
RUN mkdir -p /logs
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev","-jar","/app.jar"]
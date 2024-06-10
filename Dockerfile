FROM amazoncorretto:21
LABEL authors="pablo"

WORKDIR /home/app
COPY target/spacecraft-api-0.0.1-SNAPSHOT.jar /home/app/application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]
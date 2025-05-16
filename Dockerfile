FROM openjdk:latest
COPY ./target/App-0.1.0.0.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "App-0.1.0.0.jar", "db:3306", "30000"]
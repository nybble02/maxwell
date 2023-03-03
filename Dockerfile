FROM openjdk:latest
COPY ./target/maxwell-0.1.0.3-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "maxwell-0.1.0.3-jar-with-dependencies.jar"]
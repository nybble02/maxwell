FROM openjdk:latest
COPY ./target/maxwell-0.1.0.3-jar-with-dependencies.jar /tmp
WORKDIR /tmp
EXPOSE 3306
EXPOSE 3366
ENTRYPOINT ["java", "-jar", "maxwell-0.1.0.3-jar-with-dependencies.jar", "db:3306", "3366"]

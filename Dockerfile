FROM openjdk:latest
COPY ./target/maxwell.jar /tmp
WORKDIR /tmp
EXPOSE 3306
EXPOSE 3366
ENTRYPOINT ["java", "-jar", "maxwell.jar", "db:3306", "3366"]

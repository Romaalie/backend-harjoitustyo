FROM maven:3.8.6-eclipse-temurin-17-focal AS build
WORKDIR /home/app

# Copying only the necessary parts
COPY budjetointi-harjoitustyo/pom.xml budjetointi-harjoitustyo/pom.xml
COPY budjetointi-harjoitustyo/src budjetointi-harjoitustyo/src

# Building the application
RUN mvn -f budjetointi-harjoitustyo/pom.xml clean package

# New stage for the actual runtime image
FROM adoptopenjdk:17-jre AS runtime
WORKDIR /usr/local/lib
COPY --from=build /home/app/budjetointi-harjoitustyo/target/budjetointi-harjoitustyo-0.0.1-SNAPSHOT.jar budjetointi-harjoitustyo.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/pkg.jar"]
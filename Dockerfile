FROM maven:3.8.6-eclipse-temurin-17-focal AS build
WORKDIR /home/app

COPY budjetointi-harjoitustyo/pom.xml budjetointi-harjoitustyo/pom.xml
COPY budjetointi-harjoitustyo/src budjetointi-harjoitustyo/src

RUN mvn -f budjetointi-harjoitustyo/pom.xml clean package

FROM eclipse-temurin:17-jre-focal
WORKDIR /usr/local/lib
COPY --from=build /home/app/budjetointi-harjoitustyo/target/budjetointi-harjoitustyo-0.0.1-SNAPSHOT.jar budjetointi-harjoitustyo.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/pkg.jar"]